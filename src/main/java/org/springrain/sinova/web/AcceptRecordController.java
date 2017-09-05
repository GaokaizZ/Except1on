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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
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
import org.springrain.sinova.service.IAcceptRecordService;
import org.springrain.sinova.service.IRegionService;
import org.springrain.sinova.service.IRotateService;
import org.springrain.sinova.util.Const;
import org.springrain.sinova.util.Util;
import org.springrain.system.entity.User;
import org.springrain.system.entity.UserRole;
import org.springrain.system.service.IUserRoleService;
import org.springrain.system.util.Constant;
import org.springrain.system.util.PropertyFileCache;

/**
 * TODO 在此加入类描述
 * 
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version 2015-01-09 09:17:04
 * @see org.springrain.sinova.web.AcceptRecordDTO
 */
@Controller
@RequestMapping(value = "/acceptrecord")
public class AcceptRecordController extends BaseController {
	@Resource
	private IAcceptRecordService acceptRecordService;
	@Resource
	private IUserRoleService userRoleService;
	@Resource
	private IRegionService regionService;
	@Resource
	private IRotateService rotateService;

	private PropertyFileCache pfCache = PropertyFileCache.getInstance();

	private String listurl = "/sinova/acceptrecord/acceptrecordList";

	/**
	 * 进入acceptrecord Web页面后直接展现第一页数据,调用listjson方法,保证和app端数据统一
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
		// ReturnDatas returnObject = listjson(request, model, acceptRecord);
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		String bDate = " 00:00:00";
		String eDate = " 23:59:59";
		UserOfficeDTO userOfficeDTO = SessionUser.getShiroUser().getUserOfficeDTO();
		String userId = SessionUser.getShiroUser().getId();
		Finder finders = new Finder("select * from ").append(Finder.getTableName(UserRole.class)).append(" where user_id =:userId");
		finders.setParam("userId", userId);
		// 查询当前用户的角色
		List<UserRole> dataz = userRoleService.queryForList(finders, UserRole.class);
		String roleId = "";
		//
		if (null != dataz && dataz.size() > 0) {
			UserRole ur = dataz.get(0);
			roleId = ur.getRoleId();
		}
		Region region = new Region();
		List<Region> datar = regionService.findListDataByFinder(null, null, Region.class, region);
		request.setAttribute("datar", datar);

		Page page = newPage(request);

		Finder finder = new Finder();
		finder.append("select a.*, g.goods_Name as feeName, r.region_name as regionName, o.office_name as officeName  from ").append(Finder.getTableName(AcceptRecord.class)).append(" a, ");
		finder.append(Finder.getTableName(Goods.class)).append(" g, ");
		finder.append(Finder.getTableName(Region.class)).append(" r, ");
		finder.append(Finder.getTableName(UserOffice.class)).append(" uo, ");
		finder.append(Finder.getTableName(Office.class)).append(" o, ");
		finder.append(Finder.getTableName(User.class)).append(" u ");
		finder.append(" where a.fee_Code=g.fee_Code and a.account=u.workno and u.id=uo.user_id and uo.region_code=r.region_code and uo.office_code=o.office_code");
		// 条件查询使用
		if (StringUtils.isNotBlank(acceptRecord.getRegionName())) {
			finder.append(" and r.region_name like :regionName ");
			finder.setParam("regionName", "%" + acceptRecord.getRegionName() + "%");
			model.addAttribute("regionName", acceptRecord.getRegionName());
		}
		if (StringUtils.isNotBlank(acceptRecord.getOfficeName())) {
			finder.append(" and o.office_name like :officeName ");
			finder.setParam("officeName", "%" + acceptRecord.getOfficeName().trim() + "%");
			model.addAttribute("officeName", acceptRecord.getOfficeName().trim());
		}
		if (StringUtils.isNotBlank(acceptRecord.getMobile())) {
			finder.append(" and a.mobile like :mobile ");
			finder.setParam("mobile", "%" + acceptRecord.getMobile().trim() + "%");
			model.addAttribute("mobile", acceptRecord.getMobile().trim());
		}
		if (null != acceptRecord.getBeginTime() && null != acceptRecord.getEndTime()) {
			logger.info("beginDate==" + acceptRecord.getBeginTime() + "=====endDate==" + acceptRecord.getEndTime());
			String bTmpDate = DateUtils.formatDateTime(acceptRecord.getBeginTime(), "yyyy-MM-dd") + bDate;
			String eTmpDate = DateUtils.formatDateTime(acceptRecord.getEndTime(), "yyyy-MM-dd") + eDate;
			Date btime = DateUtils.parseDateTime(bTmpDate);
			Date etime = DateUtils.parseDateTime(eTmpDate);
			finder.append(" and a.datetime between :beginTime and :endTime ");
			finder.setParam("beginTime", btime);
			finder.setParam("endTime", etime);
			model.addAttribute("beginTime", acceptRecord.getBeginTime());
			model.addAttribute("endTime", acceptRecord.getEndTime());
		} else if (null != acceptRecord.getBeginTime() && null == acceptRecord.getEndTime()) {
			logger.info("beginDate==" + acceptRecord.getBeginTime() + "=====endDate==" + acceptRecord.getEndTime());
			String bTmpDate = DateUtils.formatDateTime(acceptRecord.getBeginTime(), "yyyy-MM-dd") + bDate;
			Date btime = DateUtils.parseDateTime(bTmpDate);
			finder.append(" and a.datetime >= :beginTime ");
			finder.setParam("beginTime", btime);
			model.addAttribute("beginTime", acceptRecord.getBeginTime());
		} else if (null == acceptRecord.getBeginTime() && null != acceptRecord.getEndTime()) {
			logger.info("beginDate==" + acceptRecord.getBeginTime() + "=====endDate==" + acceptRecord.getEndTime());
			String eTmpDate = DateUtils.formatDateTime(acceptRecord.getEndTime(), "yyyy-MM-dd") + eDate;
			Date etime = DateUtils.parseDateTime(eTmpDate);
			finder.append(" and a.datetime <=:endTime ");
			finder.setParam("endTime", etime);
			model.addAttribute("endTime", acceptRecord.getEndTime());
		}
		if (StringUtils.isNotBlank(acceptRecord.getAccount())) {
			finder.append(" and a.account like :account ");
			finder.setParam("account", "%" + acceptRecord.getAccount() + "%");
			model.addAttribute("account", acceptRecord.getAccount());
		}

		// 如果是全省人员，则返回全部用户数据
		if ("admin".equals(roleId)) {

		} else {
			// 如果不是全省人员，判断岗位（营业厅管理员/营业员）
			String[] post = SessionUser.getShiroUser().getUserOfficeDTO().getPost();
			if (post == null || post.length == 0) {
				logger.warn("当前用户岗位信息为空，返回空列表");
				model.addAttribute(GlobalStatic.returnDatas, returnObject);
				return listurl;
			}

			// 地市管理员
			if(StringUtils.equals(Constant.ROLE_REGION_MANAGER, SessionUser.getShiroUser().getRole().getCode())){
				finder.append(" and o.region_code =:regionCode ");
				finder.setParam("regionCode", userOfficeDTO.getRegionCode());
			}
			
			// 营业厅管理员
			if(StringUtils.equals(Constant.ROLE_OFFICE_MANAGE, SessionUser.getShiroUser().getRole().getCode())){
				finder.append(" and o.office_code =:officeCode ");
				finder.setParam("officeCode", userOfficeDTO.getOfficeCode());
			}
			
			// 营业员	
			if(StringUtils.equals(Constant.ROLE_OFFICE, SessionUser.getShiroUser().getRole().getCode())){
				finder.append(" and a.account =:account ");
				finder.setParam("account", SessionUser.getShiroUser().getWorkno());
			}

		}

		finder.append(" order by a.datetime desc ");
		List<AcceptRecordDTO> datas = acceptRecordService.queryForList(finder, AcceptRecordDTO.class, page);
		returnObject.setQueryBean(acceptRecord);
		returnObject.setPage(page);
		returnObject.setData(datas);

		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return listurl;

	}

	/**
	 * json数据,为APP提供数据
	 * 
	 * @param request
	 * @param model
	 * @param acceptRecord
	 * @return
	 * @author <Auto generate>
	 * @version 2015-01-09 09:17:04
	 */
	@RequestMapping("/list/json")
	@ResponseBody
	public ReturnDatas listjson(HttpServletRequest request, Model model,
			AcceptRecord acceptRecord) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();

		Page page = newPage(request);

		List<AcceptRecord> datas = acceptRecordService.findListDataByFinder(
				null, page, AcceptRecord.class, acceptRecord);
		returnObject.setQueryBean(acceptRecord);
		returnObject.setPage(page);
		returnObject.setData(datas);
		return returnObject;
	}

	/**
	 * 导出Excle格式的数据
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param acceptRecord
	 * @throws Exception
	 */
	@RequestMapping("/list/export")
	public void listexport(HttpServletRequest request,
			HttpServletResponse response, Model model, AcceptRecord acceptRecord)
			throws Exception {
		// ==构造分页请求
		Page page = newPage(request);

		File file = acceptRecordService.findDataExportExcel(null, listurl,
				page, AcceptRecord.class, acceptRecord);
		String fileName = "acceptRecord" + GlobalStatic.excelext;
		// downFile(response, file, fileName, true);
		return;
	}

	/**
	 * 查看操作,调用APP端lookjson方法
	 */
	@RequestMapping(value = "/look")
	public String look(Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = lookjson(model, request, response);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/sinova/acceptrecord/acceptrecordLook";
	}

	/**
	 * 查看的Json格式数据,为APP端提供数据
	 */
	@RequestMapping(value = "/look/json")
	@ResponseBody
	public ReturnDatas lookjson(Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		java.lang.String id = request.getParameter("id");
		if (StringUtils.isNotBlank(id)) {
			AcceptRecord acceptRecord = acceptRecordService
					.findAcceptRecordById(id);
			returnObject.setData(acceptRecord);
		} else {
			returnObject.setStatus(ReturnDatas.ERROR);
		}
		return returnObject;
	}

	/**
	 * 新增/修改save操作,返回json格式数据
	 * 
	 * @param model
	 * @param acceptRecord
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author <Auto generate>
	 * @version 2015-01-09 09:17:04
	 */
	@RequestMapping("/update")
	@ResponseBody
	public ReturnDatas saveorupdate(Model model, AcceptRecord acceptRecord,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);

		try {
			java.lang.String id = acceptRecord.getId();
			if (StringUtils.isBlank(id)) {
				acceptRecord.setId(null);
			}
			acceptRecordService.saveorupdate(acceptRecord);

		} catch (Exception e) {
			String errorMessage = e.getLocalizedMessage();
			logger.error(errorMessage);
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage(MessageUtils.UPDATE_ERROR);
		}
		return returnObject;
	}

	/**
	 * 进入修改页面,APP端可以调用 lookjson 获取json格式数据
	 */
	@RequestMapping(value = "/update/pre")
	public String edit(Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = lookjson(model, request, response);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/sinova/acceptrecord/acceptrecordCru";
	}

	/**
	 * 删除操作
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public ReturnDatas destroy(HttpServletRequest request) throws Exception {
		// 执行删除
		try {
			java.lang.String id = request.getParameter("id");
			if (StringUtils.isNotBlank(id)) {
				acceptRecordService.deleteById(id, AcceptRecord.class);
				return new ReturnDatas(ReturnDatas.SUCCESS,
						MessageUtils.DELETE_SUCCESS);
			} else {
				return new ReturnDatas(ReturnDatas.WARNING,
						MessageUtils.DELETE_WARNING);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ReturnDatas(ReturnDatas.WARNING, MessageUtils.DELETE_WARNING);
	}

	/**
	 * 删除多条记录
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @author <Auto generate>
	 * @version 2015-01-09 09:17:04
	 */
	@RequestMapping("/delete/more")
	@ResponseBody
	public ReturnDatas delMultiRecords(HttpServletRequest request, Model model) {
		String records = request.getParameter("records");
		if (StringUtils.isBlank(records)) {
			return new ReturnDatas(ReturnDatas.ERROR,
					MessageUtils.DELETE_ALL_ERROR);
		}

		String[] rs = records.split(",");
		if (rs == null || rs.length < 1) {
			return new ReturnDatas(ReturnDatas.ERROR,
					MessageUtils.DELETE_NULL_FAIL);
		}
		try {
			List<String> ids = Arrays.asList(rs);
			acceptRecordService.deleteByIds(ids, AcceptRecord.class);
		} catch (Exception e) {
			return new ReturnDatas(ReturnDatas.ERROR,
					MessageUtils.DELETE_ALL_ERROR);
		}
		return new ReturnDatas(ReturnDatas.SUCCESS,
				MessageUtils.DELETE_ALL_SUCCESS);
	}

	/**
	 * 上传文件
	 * 
	 * @param uploadFile
	 * @param request
	 * @param model
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/upload")
	@ResponseBody
	public Map<String, String> upload(@RequestParam MultipartFile uploadFile,
			Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String info = "";
		File destFile = null;

		try {
			String uploadDirPath = request.getSession().getServletContext()
					.getRealPath("/upload");
			File dir = new File(uploadDirPath);
			if (dir.exists() == false) {
				dir.mkdirs();
			}
			destFile = new File(uploadDirPath + "/"
					+ uploadFile.getOriginalFilename());
			FileCopyUtils.copy(uploadFile.getBytes(), destFile);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			info = "上传失败,文件处理异常.";
		}

		try {
			info = acceptRecordService.saveImportExcelFile(destFile,
					AcceptRecord.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			info = e.getMessage();
		}
		if (StringUtils.isBlank(info)) {
			info = "数据导入成功....";
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("msg", info);
		return map;
	}

	/**
	 * 地市受理记录报表
	 * 
	 * @param request
	 * @param model
	 * @param acceptRecord
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listReport")
	public String listReport(HttpServletRequest request, Model model,
			AcceptRecordDTO acceptRecord) throws Exception {
		// ReturnDatas returnObject = listjson(request, model, acceptRecord);
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
		List<AcceptRecordDTO> datas = acceptRecordService.queryForList(finder,
				AcceptRecordDTO.class, page);
		returnObject.setPage(page);
		returnObject.setData(datas);

		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/sinova/acceptrecord/accepReport";
	}

	/**
	 * 营业厅受理记录报表
	 * 
	 * @param request
	 * @param model
	 * @param acceptRecord
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listReportByRegion")
	public String listReportByRegion(HttpServletRequest request, Model model,
			AcceptRecordDTO acceptRecord) throws Exception {
		// ReturnDatas returnObject = listjson(request, model, acceptRecord);
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		String feeCode = request.getParameter("regionCode");
		if (StringUtils.isNotBlank(feeCode)) {
			acceptRecord.setFeeCode(feeCode);
		}

		String bDate = " 00:00:00";
		String eDate = " 23:59:59";
		Page page = newPage(request);
		Finder finder = new Finder();
		finder.append("SELECT  rr.region_name as account ,ff.office_code  mobile,ff.office_name as hint,count(*) as ip ");
		finder.append("FROM t_accept_record aa,t_user_office oo,t_goods gg, t_user uu,t_office ff, t_region rr ");
		finder.append("WHERE aa.ACCOUNT = uu.workno ");
		finder.append("AND gg.fee_code = aa.fee_code  AND uu.ID = oo.user_id  AND ff.office_code = oo.office_code   AND rr.region_code = oo.region_code ");
		finder.append("and oo.region_code =:regionCode");
		finder.setParam("regionCode", acceptRecord.getFeeCode());
		model.addAttribute("feeCode", acceptRecord.getFeeCode());
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

		finder.append("  GROUP BY ff.office_name,rr.region_name,ff.office_code order by ip desc");
		List<AcceptRecordDTO> datas = acceptRecordService.queryForList(finder,
				AcceptRecordDTO.class, page);
		returnObject.setPage(page);
		returnObject.setData(datas);

		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/sinova/acceptrecord/accepReportByRegion";
	}

	/**
	 * 个人受理记录报表
	 * 
	 * @param request
	 * @param model
	 * @param acceptRecord
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listReportByOffice")
	public String listReportByOffice(HttpServletRequest request, Model model,
			AcceptRecordDTO acceptRecord) throws Exception {
		// ReturnDatas returnObject = listjson(request, model, acceptRecord);
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		String feeCode = request.getParameter("officeCode");
		String busId = request.getParameter("regionCode");
		if (StringUtils.isNotBlank(feeCode)) {
			acceptRecord.setFeeCode(feeCode);
		}

		if (StringUtils.isNotBlank(busId)) {
			acceptRecord.setBusId(busId);
		}
		model.addAttribute("busId", acceptRecord.getBusId());
		String bDate = " 00:00:00";
		String eDate = " 23:59:59";
		Page page = newPage(request);
		Finder finder = new Finder();
		finder.append("SELECT rr.region_name as account,ff.office_name as mobile,aa.ACCOUNT as hint,count(*) as ip ");
		finder.append("FROM t_accept_record aa,t_user_office oo,t_goods gg, t_user uu,t_office ff, t_region rr ");
		finder.append("WHERE aa.ACCOUNT = uu.workno ");
		finder.append("AND gg.fee_code = aa.fee_code  AND uu.ID = oo.user_id  AND ff.office_code = oo.office_code   AND rr.region_code = oo.region_code ");
		finder.append("and oo.office_code =:office_code");
		finder.setParam("office_code", acceptRecord.getFeeCode());
		model.addAttribute("feeCode", acceptRecord.getFeeCode());
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

		finder.append(" GROUP BY aa.ACCOUNT ,rr.region_name ,ff.office_name order by ip desc");
		List<AcceptRecordDTO> datas = acceptRecordService.queryForList(finder,
				AcceptRecordDTO.class, page);
		returnObject.setPage(page);
		returnObject.setData(datas);

		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/sinova/acceptrecord/accepReportByOffice";
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
	
	/**
	 * 导出营业厅受理记录报表 全部导出
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param acceptRecord
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportOffice")
	public ReturnDatas exportOffice(HttpServletRequest request,
			HttpServletResponse response, Model model,
			AcceptRecordDTO acceptRecord) throws Exception {
		logger.info("导出营业厅受理记录报表开始");
		String path = pfCache.get(Const.SYSTEM_FILE, "expPath");
		String sysPath = request.getServletContext().getRealPath("/");
		String fileName = path + Util.getSendTime() + GlobalStatic.excelext;

		String feeCode = request.getParameter("regionCode");
		if (StringUtils.isNotBlank(feeCode)) {
			acceptRecord.setFeeCode(feeCode);
		}

		String bDate = " 00:00:00";
		String eDate = " 23:59:59";
		Finder finder = new Finder();
		finder.append("SELECT  rr.region_name as account ,ff.office_code  mobile,ff.office_name as hint,count(*) as ip ");
		finder.append("FROM t_accept_record aa,t_user_office oo,t_goods gg, t_user uu,t_office ff, t_region rr ");
		finder.append("WHERE aa.ACCOUNT = uu.workno ");
		finder.append("AND gg.fee_code = aa.fee_code  AND uu.ID = oo.user_id  AND ff.office_code = oo.office_code   AND rr.region_code = oo.region_code ");
		finder.append("and oo.region_code =:regionCode");
		finder.setParam("regionCode", acceptRecord.getFeeCode());
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

		finder.append("  GROUP BY ff.office_name,rr.region_name,ff.office_code order by ip desc");
		List<AcceptRecordDTO> datas = acceptRecordService.queryForList(finder,AcceptRecordDTO.class, null);

		try {

			WritableWorkbook book = Workbook.createWorkbook(new File(sysPath
					+ fileName));
			WritableSheet sheet = book.createSheet("营业厅受理报表", 0);
			sheet.addCell(new Label(0, 0, "地市名称"));
			sheet.addCell(new Label(1, 0, "营业厅编号"));
			sheet.addCell(new Label(2, 0, "营业厅名称"));
			sheet.addCell(new Label(3, 0, "办理量"));
			
			if (datas != null && !datas.isEmpty()) {
				for (int i = 0; i < datas.size(); i++) {
					sheet.addCell(new jxl.write.Label(0, i + 1, datas.get(i).getAccount()));
					sheet.addCell(new Label(1, i + 1, datas.get(i).getMobile()));
					sheet.addCell(new Label(2, i + 1, datas.get(i).getHint()));
					sheet.addCell(new Label(3, i + 1, datas.get(i).getIp()));
				}
			}
			book.write();
			book.close();

		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}

		downFile(response, sysPath+fileName, true, true);
	    logger.info("导出营业厅受理记录报表结束");
		return null;
	}
	
	/**
	 * 导出营业员受理记录报表 全部导出
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param acceptRecord
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportSales")
	public ReturnDatas exportSales(HttpServletRequest request,
			HttpServletResponse response, Model model,
			AcceptRecordDTO acceptRecord) throws Exception {
		logger.info("导出营业员受理记录报表开始");
		String path = pfCache.get(Const.SYSTEM_FILE, "expPath");
		String sysPath = request.getServletContext().getRealPath("/");
		String fileName = path + Util.getSendTime() + GlobalStatic.excelext;

		String feeCode = request.getParameter("officeCode");
		String busId = request.getParameter("regionCode");
		if (StringUtils.isNotBlank(feeCode)) {
			acceptRecord.setFeeCode(feeCode);
		}

		if (StringUtils.isNotBlank(busId)) {
			acceptRecord.setBusId(busId);
		}
		String bDate = " 00:00:00";
		String eDate = " 23:59:59";
		Finder finder = new Finder();
		finder.append("SELECT rr.region_name as account,ff.office_name as mobile,aa.ACCOUNT as hint,count(*) as ip ");
		finder.append("FROM t_accept_record aa,t_user_office oo,t_goods gg, t_user uu,t_office ff, t_region rr ");
		finder.append("WHERE aa.ACCOUNT = uu.workno ");
		finder.append("AND gg.fee_code = aa.fee_code  AND uu.ID = oo.user_id  AND ff.office_code = oo.office_code   AND rr.region_code = oo.region_code ");
		finder.append("and oo.office_code =:office_code");
		finder.setParam("office_code", acceptRecord.getFeeCode());
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

		finder.append(" GROUP BY aa.ACCOUNT ,rr.region_name ,ff.office_name order by ip desc");
		List<AcceptRecordDTO> datas = acceptRecordService.queryForList(finder,AcceptRecordDTO.class, null);

		try {

			WritableWorkbook book = Workbook.createWorkbook(new File(sysPath
					+ fileName));
			WritableSheet sheet = book.createSheet("营业员受理报表", 0);
			sheet.addCell(new Label(0, 0, "地市名称"));
			sheet.addCell(new Label(1, 0, "营业厅名称"));
			sheet.addCell(new Label(2, 0, "boss工号"));
			sheet.addCell(new Label(3, 0, "办理量"));
			
			if (datas != null && !datas.isEmpty()) {
				for (int i = 0; i < datas.size(); i++) {
					sheet.addCell(new jxl.write.Label(0, i + 1, datas.get(i).getAccount()));
					sheet.addCell(new Label(1, i + 1, datas.get(i).getMobile()));
					sheet.addCell(new Label(2, i + 1, datas.get(i).getHint()));
					sheet.addCell(new Label(3, i + 1, datas.get(i).getIp()));
				}
			}
			book.write();
			book.close();

		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}

		downFile(response, sysPath+fileName, true, true);
	    logger.info("导出营业员受理记录报表结束");
		return null;
	}
	

	/**
	 * 导出受理记录
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param acceptRecord
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listexport")
	public ReturnDatas exportAcceptRecord(HttpServletRequest request,HttpServletResponse response, Model model,
 AcceptRecordDTO acceptRecord) throws Exception {

		logger.info("导出受理记录开始");
		String path = pfCache.get(Const.SYSTEM_FILE, "expPath");
		String sysPath = request.getServletContext().getRealPath("/");
		String fileName = path + Util.getSendTime() + GlobalStatic.excelext;

		String bDate = " 00:00:00";
		String eDate = " 23:59:59";
		UserOfficeDTO userOfficeDTO = SessionUser.getShiroUser().getUserOfficeDTO();
		String userId = SessionUser.getShiroUser().getId();
		Finder finders = new Finder("select * from ").append(Finder.getTableName(UserRole.class)).append(" where user_id =:userId");
		finders.setParam("userId", userId);
		// 查询当前用户的角色
		List<UserRole> dataz = userRoleService.queryForList(finders, UserRole.class);
		String roleId = "";
		//
		if (null != dataz && dataz.size() > 0) {
			UserRole ur = dataz.get(0);
			roleId = ur.getRoleId();
		}

		Finder finder = new Finder();
		finder.append("select a.*, g.goods_Name as feeName, r.region_name as regionName, o.office_name as officeName  from ").append(Finder.getTableName(AcceptRecord.class)).append(" a, ");
		finder.append(Finder.getTableName(Goods.class)).append(" g, ");
		finder.append(Finder.getTableName(Region.class)).append(" r, ");
		finder.append(Finder.getTableName(UserOffice.class)).append(" uo, ");
		finder.append(Finder.getTableName(Office.class)).append(" o, ");
		finder.append(Finder.getTableName(User.class)).append(" u ");
		finder.append(" where a.fee_Code=g.fee_Code and a.account=u.workno and u.id=uo.user_id and uo.region_code=r.region_code and uo.office_code=o.office_code");

		// 如果是全省人员，则返回全部用户数据
		if ("admin".equals(roleId)) {

		} else {
			// 如果不是全省人员，判断岗位（营业厅管理员/营业员）
			String[] post = SessionUser.getShiroUser().getUserOfficeDTO().getPost();
			if (post == null || post.length == 0) {
				logger.warn("当前用户岗位信息为空，返回空列表");
				return null;
			}

			// 营业厅管理员
			if (ArrayUtils.contains(post, Const.USER_OFFICE_MANAGER)) {
				finder.append(" and o.office_code =:officeCode ");
				finder.setParam("officeCode", userOfficeDTO.getOfficeCode());
			}
			// 营业厅管理员
			if (ArrayUtils.contains(post, Const.USER_OFFICE_ASSISTANT)) {
				finder.append(" and a.account =:account ");
				finder.setParam("account", SessionUser.getShiroUser().getWorkno());
			}

		}

		String type = request.getParameter("type");
		if (StringUtils.isNotBlank(type) && "PART".equals(type)) {
			String records = request.getParameter("records");
			String[] rs = records.split(",");
			List<String> ids = Arrays.asList(rs);
			finder.append(" and a.id in (:ids)");
			finder.setParam("ids", ids);
		}

		if (StringUtils.isNotBlank(type) && "ALL".equals(type)) {
			// 条件查询使用
			if (StringUtils.isNotBlank(acceptRecord.getRegionName())) {
				finder.append(" and r.region_name like :regionName ");
				finder.setParam("regionName", "%" + acceptRecord.getRegionName().trim() + "%");
			}
			if (StringUtils.isNotBlank(acceptRecord.getOfficeName())) {
				finder.append(" and o.office_name like :officeName ");
				finder.setParam("officeName", "%" + acceptRecord.getOfficeName().trim() + "%");
			}
			if (StringUtils.isNotBlank(acceptRecord.getMobile())) {
				finder.append(" and a.mobile like :mobile ");
				finder.setParam("mobile", "%" + acceptRecord.getMobile().trim() + "%");
			}
			if (null != acceptRecord.getBeginTime() && null != acceptRecord.getEndTime()) {
				logger.info("beginDate==" + acceptRecord.getBeginTime() + "=====endDate==" + acceptRecord.getEndTime());
				String bTmpDate = DateUtils.formatDateTime(acceptRecord.getBeginTime(), "yyyy-MM-dd") + bDate;
				String eTmpDate = DateUtils.formatDateTime(acceptRecord.getEndTime(), "yyyy-MM-dd") + eDate;
				Date btime = DateUtils.parseDateTime(bTmpDate);
				Date etime = DateUtils.parseDateTime(eTmpDate);
				finder.append(" and a.datetime between :beginTime and :endTime ");
				finder.setParam("beginTime", btime);
				finder.setParam("endTime", etime);
			} else if (null != acceptRecord.getBeginTime() && null == acceptRecord.getEndTime()) {
				logger.info("beginDate==" + acceptRecord.getBeginTime() + "=====endDate==" + acceptRecord.getEndTime());
				String bTmpDate = DateUtils.formatDateTime(acceptRecord.getBeginTime(), "yyyy-MM-dd") + bDate;
				Date btime = DateUtils.parseDateTime(bTmpDate);
				finder.append(" and a.datetime >= :beginTime ");
				finder.setParam("beginTime", btime);
			} else if (null == acceptRecord.getBeginTime() && null != acceptRecord.getEndTime()) {
				logger.info("beginDate==" + acceptRecord.getBeginTime() + "=====endDate==" + acceptRecord.getEndTime());
				String eTmpDate = DateUtils.formatDateTime(acceptRecord.getEndTime(), "yyyy-MM-dd") + eDate;
				Date etime = DateUtils.parseDateTime(eTmpDate);
				finder.append(" and a.datetime <=:endTime ");
				finder.setParam("endTime", etime);
			}
			if (StringUtils.isNotBlank(acceptRecord.getAccount())) {
				finder.append(" and a.account like :account ");
				finder.setParam("account", "%" + acceptRecord.getAccount() + "%");
			}

		}

		finder.append(" order by a.datetime desc ");
		List<AcceptRecordDTO> datas = acceptRecordService.queryForList(finder, AcceptRecordDTO.class, null);

		try {

			WritableWorkbook book = Workbook.createWorkbook(new File(sysPath + fileName));
			WritableSheet sheet = book.createSheet("受理记录", 0);
			sheet.addCell(new Label(0, 0, "地市名称"));
			sheet.addCell(new Label(1, 0, "营业厅名称"));
			sheet.addCell(new Label(2, 0, "用户工号"));
			sheet.addCell(new Label(3, 0, "客户手机号"));
			sheet.addCell(new Label(4, 0, "商品名称"));
			sheet.addCell(new Label(5, 0, "受理时间"));

			if (datas != null && !datas.isEmpty()) {
				for (int i = 0; i < datas.size(); i++) {

					// sheet.setRowView(i+i+1, 2000);
					// sheet.setColumnView(4, 20);
					sheet.addCell(new jxl.write.Label(0, i + 1, datas.get(i).getRegionName()));
					sheet.addCell(new Label(1, i + 1, datas.get(i).getOfficeName()));
					sheet.addCell(new Label(2, i + 1, datas.get(i).getAccount()));
					sheet.addCell(new Label(3, i + 1, datas.get(i).getMobile()));
					sheet.addCell(new Label(4, i + 1, datas.get(i).getFeeName()));

					DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String str = new String();
					str = format.format(datas.get(i).getDatetime());
					sheet.addCell(new jxl.write.Label(5, i + 1, str));
				}
			}
			book.write();
			book.close();

		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}

		downFile(response, sysPath + fileName, true, true);
		logger.info("导出受理记录结束");
		return null;
	}
	/**
	 *  中奖纪录
	 * @param request
	 * @param model
	 * @param acceptRecord
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listRotate")
	public String listRotate(HttpServletRequest request, Model model, RotateDTO rotateDTO) throws Exception {
		//ReturnDatas returnObject = listjson(request, model, acceptRecord);
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();

		Page page = newPage(request);

		

		 List<RotateDTO> datas = rotateService.findRoateListByDTO(rotateDTO, page);
		 model.addAttribute("rotateDTO",rotateDTO);
		 returnObject.setPage(page);
		 returnObject.setData(datas);

		 model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/sinova/acceptrecord/listRotate";
	}
	
}
