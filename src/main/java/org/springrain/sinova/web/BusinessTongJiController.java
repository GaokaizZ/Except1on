package org.springrain.sinova.web;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springrain.frame.common.BaseLogger;
import org.springrain.frame.common.SessionUser;
import org.springrain.frame.controller.BaseController;
import org.springrain.frame.util.DateUtils;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.MessageUtils;
import org.springrain.frame.util.Page;
import org.springrain.frame.util.ReturnDatas;
import org.springrain.sinova.dto.AcceptRecordDTO;
import org.springrain.sinova.dto.RotateDTO;
import org.springrain.sinova.dto.UserOfficeDTO;
import org.springrain.sinova.entity.AcceptRecord;
import org.springrain.sinova.entity.Brand;
import org.springrain.sinova.entity.Goods;
import org.springrain.sinova.entity.Office;
import org.springrain.sinova.entity.Qrcode;
import org.springrain.sinova.entity.Region;
import org.springrain.sinova.entity.UserOffice;
import org.springrain.sinova.interfaces.sitech.ParseXML;
import org.springrain.sinova.interfaces.sitech.s4000cfmb.New_S4000Cfm_BReruest;
import org.springrain.sinova.service.IAcceptRecordService;
import org.springrain.sinova.service.IRegionService;
import org.springrain.sinova.service.IRotateService;
import org.springrain.sinova.util.Const;
import org.springrain.sinova.util.Util;
import org.springrain.system.entity.User;
import org.springrain.system.entity.UserRole;
import org.springrain.system.service.IUserRoleService;
import org.springrain.system.util.PropertyFileCache;

/**
 * 业务统计分析入口
 *
 */
@Controller
@RequestMapping(value = "/businesstongji")
public class BusinessTongJiController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(BusinessTongJiController.class);
	
	private static final String XMLHEAD_U = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>";
	private static final String XMLSTART = "<ROOT>";
	private static final String XMLEND = "</ROOT>";
	private static final String ENTER = "\n";
	
	@Resource
	private IAcceptRecordService acceptRecordService;
	@Resource
	private IUserRoleService userRoleService;
	@Resource
	private IRegionService regionService;
	@Resource
	private IRotateService rotateService;

	private PropertyFileCache pfCache = PropertyFileCache.getInstance();

	private String listurl = "/sinova/businesstongji/businesstongjiList";
	
	private String listCityurl = "/sinova/businesstongji/businessReportByRegion";

	/**
	 * 进入统计 Web页面后直接展示全部数据
	 * 
	 * @param request
	 * @param model
	 * @param acceptRecord
	 * @return
	 * @author <Auto generate>
	 * @version 2015-01-09 09:17:04
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model,
			AcceptRecordDTO acceptRecord) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		String bDate = " 00:00:00";
		String eDate = " 23:59:59";
		Page page = newPage(request);
		page.setPageSize(99);
		Finder finder = new Finder();
		finder.append("select a.rcode as account,a.rname as mobile,sum(a.cou) as ip from(SELECT   ff.office_name oname, rr.region_code rcode,rr.region_name rname,count(*) cou ");
		finder.append("FROM t_accept_record aa,t_user_office oo,t_goods gg, t_user uu,t_office ff, t_region rr ");
		finder.append("WHERE aa.ACCOUNT = uu.workno ");
		finder.append("AND gg.fee_code = aa.fee_code  AND uu.ID = oo.user_id  AND ff.office_code = oo.office_code   AND rr.region_code = oo.region_code");

		if (null != acceptRecord.getBeginTime()
				&& null != acceptRecord.getEndTime()) {
			logger.info("beginDate==" + acceptRecord.getBeginTime()
					+ "=====endDate==" + acceptRecord.getEndTime());
			String bTmpDate = DateUtils.formatDateTime(
					acceptRecord.getBeginTime(), "yyyy-MM-dd")
					+ bDate;
			String eTmpDate = DateUtils.formatDateTime(
					acceptRecord.getEndTime(), "yyyy-MM-dd")
					+ eDate;
			Date btime = DateUtils.parseDateTime(bTmpDate);
			Date etime = DateUtils.parseDateTime(eTmpDate);
			finder.append(" and aa.datetime between :beginTime and :endTime ");
			finder.setParam("beginTime", btime);
			finder.setParam("endTime", etime);
			model.addAttribute("beginTime", acceptRecord.getBeginTime());
			model.addAttribute("endTime", acceptRecord.getEndTime());
		} else if (null != acceptRecord.getBeginTime()
				&& null == acceptRecord.getEndTime()) {
			logger.info("beginDate==" + acceptRecord.getBeginTime()
					+ "=====endDate==" + acceptRecord.getEndTime());
			String bTmpDate = DateUtils.formatDateTime(
					acceptRecord.getBeginTime(), "yyyy-MM-dd")
					+ bDate;
			Date btime = DateUtils.parseDateTime(bTmpDate);
			finder.append(" and aa.datetime >= :beginTime ");
			finder.setParam("beginTime", btime);
			model.addAttribute("beginTime", acceptRecord.getBeginTime());
		} else if (null == acceptRecord.getBeginTime()
				&& null != acceptRecord.getEndTime()) {
			logger.info("beginDate==" + acceptRecord.getBeginTime()
					+ "=====endDate==" + acceptRecord.getEndTime());
			String eTmpDate = DateUtils.formatDateTime(
					acceptRecord.getEndTime(), "yyyy-MM-dd")
					+ eDate;
			Date etime = DateUtils.parseDateTime(eTmpDate);
			finder.append(" and aa.datetime <=:endTime ");
			finder.setParam("endTime", etime);
			model.addAttribute("endTime", acceptRecord.getEndTime());
		}

		finder.append(" GROUP BY ff.office_name,rr.region_name,rr.region_code) a group by a.rcode,a.rname order by ip desc");
		
		logger.info("数据库查询开始");
		List<AcceptRecordDTO> datas = acceptRecordService.queryForList(finder,
				AcceptRecordDTO.class, page);
		
		logger.info("数据库查询结束");
		logger.info("得到查询的结果集"+datas);
		
		Region region = new Region();
		List<Region> datar = regionService.findListDataByFinder(null, null, Region.class, region);
		request.setAttribute("datar", datar);
		
		//对查询数据进行逻辑判断如果不为空才要去走下面的方法否则就不走
		if(null != datas && datas.size() > 0){//说明查询到了数据
			//根据地市查询业务办理量
			String byCityXml = xmlByCity(datas);
			request.setAttribute("byCityXml",byCityXml);
		}else {
			String otherDataXml = xmlOtherData(); 
			request.setAttribute("byCityXml",otherDataXml);
		}
		returnObject.setPage(page);
		returnObject.setData(datas);

		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return listurl;
	}
	
	
	@RequestMapping("/listBusinessEntrance")
	public String getListBusinessEntrance(HttpServletRequest request, Model model,
			AcceptRecordDTO acceptRecord) throws Exception{
		//查询方式 0 地市 1 营业厅 2 营业员 3 业务
		String searchType = request.getParameter("searchType");
		logger.info("查询方式"+searchType);
		//查询地市
		String searchCity = request.getParameter("searchCity");
		logger.info("查询地市"+searchCity);
		if("0".equals(searchType)){//按照地市来进行查询
			if (StringUtils.isNotBlank(searchCity) ){//传进来了地市编码
					if("请选择".equals(searchCity)){
						return list(request,model, acceptRecord);
					}else {
						return listByCity(searchCity,request,model, acceptRecord);
					}
			}
		}
		return searchCity;
	}


	
	
	/**
	 * 进入统计 Web页面后直接展示全部数据
	 * 
	 * @param request
	 * @param model
	 * @param acceptRecord
	 * @return
	 * @author <Auto generate>
	 * @version 2015-01-09 09:17:04
	 */
	
	public String listByCity(String searchCity,HttpServletRequest request, Model model,
			AcceptRecordDTO acceptRecord) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		String bDate = " 00:00:00";
		String eDate = " 23:59:59";
		Page page = newPage(request);
		page.setPageSize(99);
		Finder finder = new Finder();
		finder.append("select a.rcode as account,a.rname as mobile,sum(a.cou) as ip from(SELECT   ff.office_name oname, rr.region_code rcode,rr.region_name rname,count(*) cou ");
		finder.append("FROM t_accept_record aa,t_user_office oo,t_goods gg, t_user uu,t_office ff, t_region rr ");
		finder.append("WHERE aa.ACCOUNT = uu.workno ");
		finder.append("AND gg.fee_code = aa.fee_code  AND uu.ID = oo.user_id  AND ff.office_code = oo.office_code   AND rr.region_code = oo.region_code");

		if (null != acceptRecord.getBeginTime()
				&& null != acceptRecord.getEndTime()) {
			logger.info("beginDate==" + acceptRecord.getBeginTime()
					+ "=====endDate==" + acceptRecord.getEndTime());
			String bTmpDate = DateUtils.formatDateTime(
					acceptRecord.getBeginTime(), "yyyy-MM-dd")
					+ bDate;
			String eTmpDate = DateUtils.formatDateTime(
					acceptRecord.getEndTime(), "yyyy-MM-dd")
					+ eDate;
			Date btime = DateUtils.parseDateTime(bTmpDate);
			Date etime = DateUtils.parseDateTime(eTmpDate);
			finder.append(" and aa.datetime between :beginTime and :endTime ");
			finder.setParam("beginTime", btime);
			finder.setParam("endTime", etime);
			model.addAttribute("beginTime", acceptRecord.getBeginTime());
			model.addAttribute("endTime", acceptRecord.getEndTime());
		} else if (null != acceptRecord.getBeginTime()
				&& null == acceptRecord.getEndTime()) {
			logger.info("beginDate==" + acceptRecord.getBeginTime()
					+ "=====endDate==" + acceptRecord.getEndTime());
			String bTmpDate = DateUtils.formatDateTime(
					acceptRecord.getBeginTime(), "yyyy-MM-dd")
					+ bDate;
			Date btime = DateUtils.parseDateTime(bTmpDate);
			finder.append(" and aa.datetime >= :beginTime ");
			finder.setParam("beginTime", btime);
			model.addAttribute("beginTime", acceptRecord.getBeginTime());
		} else if (null == acceptRecord.getBeginTime()
				&& null != acceptRecord.getEndTime()) {
			logger.info("beginDate==" + acceptRecord.getBeginTime()
					+ "=====endDate==" + acceptRecord.getEndTime());
			String eTmpDate = DateUtils.formatDateTime(
					acceptRecord.getEndTime(), "yyyy-MM-dd")
					+ eDate;
			Date etime = DateUtils.parseDateTime(eTmpDate);
			finder.append(" and aa.datetime <=:endTime ");
			finder.setParam("endTime", etime);
			model.addAttribute("endTime", acceptRecord.getEndTime());
		}
		
		finder.append(" GROUP BY ff.office_name,rr.region_name,rr.region_code) a ");
		finder.append(" where a.rcode =:searchCity");
		finder.setParam("searchCity", searchCity);
		finder.append(" group by a.rcode,a.rname order by ip desc");
		
		List<AcceptRecordDTO> datas = acceptRecordService.queryForList(finder,
				AcceptRecordDTO.class, page);
		
		logger.info("查询到是数据量"+datas);
			
		
		Region region = new Region();
		List<Region> datar = regionService.findListDataByFinder(null, null, Region.class, region);
		request.setAttribute("datar", datar);
		
		
		//对查询数据进行逻辑判断如果不为空才要去走下面的方法否则就不走
		if(null != datas && datas.size() > 0){//说明查询到了数据
			//根据地市查询业务办理量
			String byCityXml = xmlByCity(datas);
			request.setAttribute("byCityXml",byCityXml);
		}else {
			String otherDataXml = xmlOtherData(); 
			request.setAttribute("byCityXml",otherDataXml);
		}
		
		returnObject.setPage(page);
		returnObject.setData(datas);

		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return listCityurl;
	}
	
	
	/**
	 * 根据地市查询业务办理量查询到了数据
	 * xml报文拼接
	 * @param request
	 * @return
	 */
	public static String xmlByCity(List<AcceptRecordDTO> acceptRecords) {
		logger.info("根据地市查询业务办理量   xml   start");
		String xml = "";
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0' encoding='UTF-8' standalone='no'?>");
		/*sb.append(XMLSTART).append(ENTER);*/
		sb.append("<chart caption='地市业务办理排行'  yAxisName='办理量(笔)' showValues='0' " +
				"palette='2' shownames='1' legendBorderAlpha='0' useRoundEdges='1' "+
				"animation='1' decimalPrecision='0' formatNumberScale='0' baseFont='Arial' " +
				"baseFontSize='12' rotateYAxisName='0' showFCMenuItem='0'" +
				"canvasBgColor ='#f3f3f3'>");
				for(AcceptRecordDTO acceptRecordDTO : acceptRecords){
					sb.append("<set label='"+acceptRecordDTO.getMobile()+"' value='"+acceptRecordDTO.getIp()+"' color = '#6A6AFF'/>");
				}
		sb.append("</chart>");
		/*sb.append(ENTER);
		sb.append(XMLEND).append(ENTER);*/
		xml = sb.toString();
		logger.info("根据地市查询业务办理量   xml" + ENTER + xml);
		logger.info("根据地市查询业务办理量   xml   end");
		return xml;
	}
	
	
	/**
	 * 没有查询到数据
	 * xml报文拼接
	 * @param request
	 * @return
	 */
	public static String xmlOtherData() {
		logger.info("没有查询到数据   xml   start");
		String xml = "";
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0' encoding='UTF-8' standalone='no'?>");
		/*sb.append(XMLSTART).append(ENTER);*/
		sb.append("<chart caption='地市业务办理排行'  yAxisName='办理量(笔)' showValues='0' " +
				"palette='2' shownames='1' legendBorderAlpha='0' useRoundEdges='1' "+
				"animation='1' decimalPrecision='0' formatNumberScale='0' baseFont='Arial' " +
				"baseFontSize='12' rotateYAxisName='0' showFCMenuItem='0'" +
				"canvasBgColor ='#f3f3f3'>");
					sb.append("<set label='对不起，没有查询到你要的数据，请重新选择查询条件' value='0' color = '#6A6AFF'/>");
		sb.append("</chart>");
		/*sb.append(ENTER);
		sb.append(XMLEND).append(ENTER);*/
		xml = sb.toString();
		logger.info("没有查询到数据   xml   end");
		return xml;
	}
	
	/**
	 * 导出地市受理记录报表 全部导出
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param acceptRecord
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportRegion")
	public ReturnDatas listexport(HttpServletRequest request,
			HttpServletResponse response, Model model,
			AcceptRecordDTO acceptRecord) throws Exception {
		logger.info("导出地市受理记录报表开始");
		String path = pfCache.get(Const.SYSTEM_FILE, "expPath");
		String sysPath = request.getServletContext().getRealPath("/");
		String fileName = path + Util.getSendTime() + GlobalStatic.excelext;

		String bDate = " 00:00:00";
		String eDate = " 23:59:59";
		
		Finder finder = new Finder();
		finder.append("select a.rcode as account,a.rname as mobile,sum(a.cou) as ip from(SELECT   ff.office_name oname, rr.region_code rcode,rr.region_name rname,count(*) cou ");
		finder.append("FROM t_accept_record aa,t_user_office oo,t_goods gg, t_user uu,t_office ff, t_region rr ");
		finder.append("WHERE aa.ACCOUNT = uu.workno ");
		finder.append("AND gg.fee_code = aa.fee_code  AND uu.ID = oo.user_id  AND ff.office_code = oo.office_code   AND rr.region_code = oo.region_code");
		
		if (null != acceptRecord.getBeginTime()
				&& null != acceptRecord.getEndTime()) {
			logger.info("beginDate==" + acceptRecord.getBeginTime()
					+ "=====endDate==" + acceptRecord.getEndTime());
			String bTmpDate = DateUtils.formatDateTime(
					acceptRecord.getBeginTime(), "yyyy-MM-dd")
					+ bDate;
			String eTmpDate = DateUtils.formatDateTime(
					acceptRecord.getEndTime(), "yyyy-MM-dd")
					+ eDate;
			Date btime = DateUtils.parseDateTime(bTmpDate);
			Date etime = DateUtils.parseDateTime(eTmpDate);
			finder.append(" and aa.datetime between :beginTime and :endTime ");
			finder.setParam("beginTime", btime);
			finder.setParam("endTime", etime);
		} else if (null != acceptRecord.getBeginTime()
				&& null == acceptRecord.getEndTime()) {
			logger.info("beginDate==" + acceptRecord.getBeginTime()
					+ "=====endDate==" + acceptRecord.getEndTime());
			String bTmpDate = DateUtils.formatDateTime(
					acceptRecord.getBeginTime(), "yyyy-MM-dd")
					+ bDate;
			Date btime = DateUtils.parseDateTime(bTmpDate);
			finder.append(" and aa.datetime >= :beginTime ");
			finder.setParam("beginTime", btime);
		} else if (null == acceptRecord.getBeginTime()
				&& null != acceptRecord.getEndTime()) {
			logger.info("beginDate==" + acceptRecord.getBeginTime()
					+ "=====endDate==" + acceptRecord.getEndTime());
			String eTmpDate = DateUtils.formatDateTime(
					acceptRecord.getEndTime(), "yyyy-MM-dd")
					+ eDate;
			Date etime = DateUtils.parseDateTime(eTmpDate);
			finder.append(" and aa.datetime <=:endTime ");
			finder.setParam("endTime", etime);
		}

		finder.append(" GROUP BY ff.office_name,rr.region_name,rr.region_code) a group by a.rcode,a.rname order by ip desc");
		
		List<AcceptRecordDTO> datas = acceptRecordService.queryForList(finder,AcceptRecordDTO.class, null);

		try {

			WritableWorkbook book = Workbook.createWorkbook(new File(sysPath
					+ fileName));
			WritableSheet sheet = book.createSheet("地市受理报表", 0);
			sheet.addCell(new Label(0, 0, "地市编号"));
			sheet.addCell(new Label(1, 0, "地市名称"));
			sheet.addCell(new Label(2, 0, "办理量"));
			
			if (datas != null && !datas.isEmpty()) {
				for (int i = 0; i < datas.size(); i++) {
					sheet.addCell(new jxl.write.Label(0, i + 1, datas.get(i).getAccount()));
					sheet.addCell(new Label(1, i + 1, datas.get(i).getMobile()));
					sheet.addCell(new Label(2, i + 1, datas.get(i).getIp()));
				}
			}
			book.write();
			book.close();

		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}

		downFile(response, sysPath+fileName, true, true);
	    logger.info("导出地市受理记录报表结束");
		return null;
	}
}
