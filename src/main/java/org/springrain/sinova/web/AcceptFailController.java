package org.springrain.sinova.web;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springrain.frame.common.SessionUser;
import org.springrain.frame.controller.BaseController;
import org.springrain.frame.util.DateUtils;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.Page;
import org.springrain.frame.util.ReturnDatas;
import org.springrain.sinova.dto.AcceptFailDTO;
import org.springrain.sinova.dto.UserOfficeDTO;
import org.springrain.sinova.entity.AcceptFail;
import org.springrain.sinova.entity.Goods;
import org.springrain.sinova.entity.Office;
import org.springrain.sinova.entity.Region;
import org.springrain.sinova.entity.UserOffice;
import org.springrain.sinova.service.IAcceptFailService;
import org.springrain.sinova.service.IRegionService;
import org.springrain.sinova.util.Const;
import org.springrain.sinova.util.Util;
import org.springrain.system.entity.User;
import org.springrain.system.entity.UserRole;
import org.springrain.system.service.IUserRoleService;
import org.springrain.system.util.PropertyFileCache;

@Controller
@RequestMapping(value = "/acceptfail")
public class AcceptFailController extends BaseController{
	
	@Resource
	private IAcceptFailService acceptFailService;
	@Resource
	private IUserRoleService userRoleService;
	@Resource
	private IRegionService regionService;
	
	private PropertyFileCache pfCache = PropertyFileCache.getInstance();
	
	private String listurl = "/sinova/acceptFail/acceptFailList";
	

	/**
	 * 
	 * @description 查询列表 <br/>
	 * @date 2015年1月20日 下午5:09:49 <br/>
	 * @param request
	 * @param model
	 * @param brand
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model, AcceptFailDTO acceptFail) throws Exception {
		ReturnDatas returnObject = listjson(request, model, acceptFail);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return listurl;
	}

	/**
	 * 
	 * @description 查询列表，返回json数据 <br/>
	 * @date 2015年1月20日 下午5:10:51 <br/>
	 * @param request
	 * @param model
	 * @param brand
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list/json")
	@ResponseBody
	public ReturnDatas listjson(HttpServletRequest request, Model model, AcceptFailDTO acceptFail) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();

		UserOfficeDTO userOfficeDTO = SessionUser.getShiroUser()
				.getUserOfficeDTO();
		String userId = SessionUser.getShiroUser().getId();
		Finder finders = new Finder("select * from ").append(
				Finder.getTableName(UserRole.class)).append(
				" where user_id =:userId");
		finders.setParam("userId", userId);
		// 查询当前用户的角色
		List<UserRole> dataz = userRoleService.queryForList(finders,
				UserRole.class);
		String roleId = "";
		
		if (null != dataz && dataz.size() > 0) {
			UserRole ur = dataz.get(0);
			roleId = ur.getRoleId();
		}
		Region region = new Region();
		List<Region> datar = regionService.findListDataByFinder(null, null, Region.class, region);
		request.setAttribute("datar", datar);
		
		Page page = newPage(request);
		
		String bDate = " 00:00:00";
		String eDate = " 23:59:59";
		Finder finder = new Finder();
		finder.append("select a.*, g.goods_Name as feeName, r.region_name as regionName, o.office_name as officeName  from ")
				.append(Finder.getTableName(AcceptFail.class)).append(" a, ");
		finder.append(Finder.getTableName(Goods.class)).append(" g, ");
		finder.append(Finder.getTableName(Region.class)).append(" r, ");
		finder.append(Finder.getTableName(UserOffice.class)).append(" uo, ");
		finder.append(Finder.getTableName(Office.class)).append(" o, ");
		finder.append(Finder.getTableName(User.class)).append(" u ");
		finder.append(" where a.fee_Code=g.fee_Code and a.workno=u.workno and u.id=uo.user_id and uo.region_code=r.region_code and uo.office_code=o.office_code");
		// 条件查询使用
		if (StringUtils.isNotBlank(acceptFail.getRegionName())) {
			finder.append(" and r.region_name like :regionName ");
			finder.setParam("regionName", "%" + acceptFail.getRegionName() + "%");
			model.addAttribute("regionName", acceptFail.getRegionName());
		}
		if (StringUtils.isNotBlank(acceptFail.getOfficeName())) {
			finder.append(" and o.office_name like :officeName ");
			finder.setParam("officeName", "%" + acceptFail.getOfficeName().trim() + "%");
			model.addAttribute("officeName", acceptFail.getOfficeName().trim());
		}
		if (StringUtils.isNotBlank(acceptFail.getMobile())) {
			finder.append(" and a.mobile like :mobile ");
			finder.setParam("mobile", "%" + acceptFail.getMobile().trim() + "%");
			model.addAttribute("mobile", acceptFail.getMobile().trim());
		}
		if (null != acceptFail.getBeginTime()
				&& null != acceptFail.getEndTime()) {
			logger.info("beginDate==" + acceptFail.getBeginTime()
					+ "=====endDate==" + acceptFail.getEndTime());
			String bTmpDate = DateUtils.formatDateTime(
					acceptFail.getBeginTime(), "yyyy-MM-dd")
					+ bDate;
			String eTmpDate = DateUtils.formatDateTime(
					acceptFail.getEndTime(), "yyyy-MM-dd")
					+ eDate;
			Date btime = DateUtils.parseDateTime(bTmpDate);
			Date etime = DateUtils.parseDateTime(eTmpDate);
			finder.append(" and a.datetime between :beginTime and :endTime ");
			finder.setParam("beginTime", btime);
			finder.setParam("endTime", etime);
			model.addAttribute("beginTime", acceptFail.getBeginTime());
			model.addAttribute("endTime", acceptFail.getEndTime());
		} else if (null != acceptFail.getBeginTime()
				&& null == acceptFail.getEndTime()) {
			logger.info("beginDate==" + acceptFail.getBeginTime()
					+ "=====endDate==" + acceptFail.getEndTime());
			String bTmpDate = DateUtils.formatDateTime(
					acceptFail.getBeginTime(), "yyyy-MM-dd")
					+ bDate;
			Date btime = DateUtils.parseDateTime(bTmpDate);
			finder.append(" and a.datetime >= :beginTime ");
			finder.setParam("beginTime", btime);
			model.addAttribute("beginTime", acceptFail.getBeginTime());
		} else if (null == acceptFail.getBeginTime()
				&& null != acceptFail.getEndTime()) {
			logger.info("beginDate==" + acceptFail.getBeginTime()
					+ "=====endDate==" + acceptFail.getEndTime());
			String eTmpDate = DateUtils.formatDateTime(
					acceptFail.getEndTime(), "yyyy-MM-dd")
					+ eDate;
			Date etime = DateUtils.parseDateTime(eTmpDate);
			finder.append(" and a.datetime <=:endTime ");
			finder.setParam("endTime", etime);
			model.addAttribute("endTime", acceptFail.getEndTime());
		}
		if (StringUtils.isNotBlank(acceptFail.getWorkno())) {
			finder.append(" and a.workno like :workno ");
			finder.setParam("workno", "%" + acceptFail.getWorkno()+ "%");
			model.addAttribute("workno", acceptFail.getWorkno());
		}
		
		// 如果是全省人员，则返回全部用户数据
		if ("admin".equals(roleId)) {
			
		}else{
			// 如果不是全省人员，判断岗位（营业厅管理员/营业员）
			String[] post = SessionUser.getShiroUser().getUserOfficeDTO()
					.getPost();
			if (post == null || post.length == 0) {
				logger.warn("当前用户岗位信息为空，返回空列表");
				model.addAttribute(GlobalStatic.returnDatas, returnObject);
				return returnObject;
			}

			// 营业厅管理员
			if (ArrayUtils.contains(post, Const.USER_OFFICE_MANAGER)) {
				finder.append(" and o.office_code =:officeCode ");
				finder.setParam("officeCode", userOfficeDTO.getOfficeCode());
			}
			// 营业厅管理员
			if (ArrayUtils.contains(post, Const.USER_OFFICE_ASSISTANT)) {
				finder.append(" and a.workno =:workno ");
				finder.setParam("workno", SessionUser.getShiroUser().getWorkno());
			}
			
		}
			
		finder.append(" order by a.datetime desc ");
		List<AcceptFailDTO> datas = acceptFailService.queryForList(
				finder, AcceptFailDTO.class, page);
		returnObject.setQueryBean(acceptFail);
		returnObject.setPage(page);
		returnObject.setData(datas);
		return returnObject;
	}
	/**
	 * 导出受理失败记录
	 * @param request
	 * @param response
	 * @param model
	 * @param acceptFail
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listexport")
	public ReturnDatas listExport(HttpServletRequest request, HttpServletResponse response, 
			Model model, AcceptFailDTO acceptFail) throws Exception{
		logger.info("导出地市受理记录报表开始.........");
		String path = pfCache.get(Const.SYSTEM_FILE, "expPath");
		String sysPath = request.getServletContext().getRealPath("/");
		String fileName = path + Util.getSendTime() + GlobalStatic.excelext;

		UserOfficeDTO userOfficeDTO = SessionUser.getShiroUser()
				.getUserOfficeDTO();
		String userId = SessionUser.getShiroUser().getId();
		Finder finders = new Finder("select * from ").append(
				Finder.getTableName(UserRole.class)).append(
				" where user_id =:userId");
		finders.setParam("userId", userId);
		// 查询当前用户的角色
		List<UserRole> dataz = userRoleService.queryForList(finders,
				UserRole.class);
		String roleId = "";
		
		if (null != dataz && dataz.size() > 0) {
			UserRole ur = dataz.get(0);
			roleId = ur.getRoleId();
		}
		
		String bDate = " 00:00:00";
		String eDate = " 23:59:59";
		
		Finder finder = new Finder();
		finder.append("select a.*, g.goods_Name as feeName, r.region_name as regionName, o.office_name as officeName  from ")
				.append(Finder.getTableName(AcceptFail.class)).append(" a, ");
		finder.append(Finder.getTableName(Goods.class)).append(" g, ");
		finder.append(Finder.getTableName(Region.class)).append(" r, ");
		finder.append(Finder.getTableName(UserOffice.class)).append(" uo, ");
		finder.append(Finder.getTableName(Office.class)).append(" o, ");
		finder.append(Finder.getTableName(User.class)).append(" u ");
		finder.append(" where a.fee_Code=g.fee_Code and a.workno=u.workno and u.id=uo.user_id and uo.region_code=r.region_code and uo.office_code=o.office_code");
		// 如果是全省人员，则返回全部用户数据
		if ("admin".equals(roleId)) {
			
		}else{
			// 如果不是全省人员，判断岗位（营业厅管理员/营业员）
			String[] post = SessionUser.getShiroUser().getUserOfficeDTO()
					.getPost();
			if (post == null || post.length == 0) {
				logger.warn("当前用户岗位信息为空");
				return null;
			}

			// 营业厅管理员
			if (ArrayUtils.contains(post, Const.USER_OFFICE_MANAGER)) {
				finder.append(" and o.office_code =:officeCode ");
				finder.setParam("officeCode", userOfficeDTO.getOfficeCode());
			}
			// 营业厅管理员
			if (ArrayUtils.contains(post, Const.USER_OFFICE_ASSISTANT)) {
				finder.append(" and a.workno =:workno ");
				finder.setParam("workno", SessionUser.getShiroUser().getWorkno());
			}
			
		}
		
		String type = request.getParameter("type");
		if(StringUtils.isNotBlank(type) && "PART".equals(type)){
			 String records = request.getParameter("records");
    	     String[] rs = records.split(",");
    	     List<String> ids = Arrays.asList(rs);
    	     finder.append(" and a.id in (:ids)");
    	     finder.setParam("ids", ids);
		}
		
		if(StringUtils.isNotBlank(type) && "ALL".equals(type)){
			// 条件查询使用
			if (StringUtils.isNotBlank(acceptFail.getRegionName())) {
				finder.append(" and r.region_name like :regionName ");
				finder.setParam("regionName", "%" + acceptFail.getRegionName() + "%");
				model.addAttribute("regionName", acceptFail.getRegionName());
			}
			if (StringUtils.isNotBlank(acceptFail.getOfficeName())) {
				finder.append(" and o.office_name like :officeName ");
				finder.setParam("officeName", "%" + acceptFail.getOfficeName().trim() + "%");
				model.addAttribute("officeName", acceptFail.getOfficeName().trim());
			}
			if (StringUtils.isNotBlank(acceptFail.getMobile())) {
				finder.append(" and a.mobile like :mobile ");
				finder.setParam("mobile", "%" + acceptFail.getMobile().trim() + "%");
				model.addAttribute("mobile", acceptFail.getMobile().trim());
			}
			if (null != acceptFail.getBeginTime()
					&& null != acceptFail.getEndTime()) {
				logger.info("beginDate==" + acceptFail.getBeginTime()
						+ "=====endDate==" + acceptFail.getEndTime());
				String bTmpDate = DateUtils.formatDateTime(
						acceptFail.getBeginTime(), "yyyy-MM-dd")
						+ bDate;
				String eTmpDate = DateUtils.formatDateTime(
						acceptFail.getEndTime(), "yyyy-MM-dd")
						+ eDate;
				Date btime = DateUtils.parseDateTime(bTmpDate);
				Date etime = DateUtils.parseDateTime(eTmpDate);
				finder.append(" and a.datetime between :beginTime and :endTime ");
				finder.setParam("beginTime", btime);
				finder.setParam("endTime", etime);
				model.addAttribute("beginTime", acceptFail.getBeginTime());
				model.addAttribute("endTime", acceptFail.getEndTime());
			} else if (null != acceptFail.getBeginTime()
					&& null == acceptFail.getEndTime()) {
				logger.info("beginDate==" + acceptFail.getBeginTime()
						+ "=====endDate==" + acceptFail.getEndTime());
				String bTmpDate = DateUtils.formatDateTime(
						acceptFail.getBeginTime(), "yyyy-MM-dd")
						+ bDate;
				Date btime = DateUtils.parseDateTime(bTmpDate);
				finder.append(" and a.datetime >= :beginTime ");
				finder.setParam("beginTime", btime);
				model.addAttribute("beginTime", acceptFail.getBeginTime());
			} else if (null == acceptFail.getBeginTime()
					&& null != acceptFail.getEndTime()) {
				logger.info("beginDate==" + acceptFail.getBeginTime()
						+ "=====endDate==" + acceptFail.getEndTime());
				String eTmpDate = DateUtils.formatDateTime(
						acceptFail.getEndTime(), "yyyy-MM-dd")
						+ eDate;
				Date etime = DateUtils.parseDateTime(eTmpDate);
				finder.append(" and a.datetime <=:endTime ");
				finder.setParam("endTime", etime);
				model.addAttribute("endTime", acceptFail.getEndTime());
			}
			if (StringUtils.isNotBlank(acceptFail.getWorkno())) {
				finder.append(" and a.workno like :workno ");
				finder.setParam("workno", "%" + acceptFail.getWorkno()+ "%");
				model.addAttribute("workno", acceptFail.getWorkno());
			}
		}
		
		finder.append(" order by a.datetime desc ");
		List<AcceptFailDTO> datas = acceptFailService.queryForList(finder, AcceptFailDTO.class, null);
		
		 try {
			   
	        	WritableWorkbook book = Workbook.createWorkbook(new File(sysPath+fileName));
	        	WritableSheet sheet = book.createSheet("受理失败记录", 0);
	        	sheet.addCell(new Label(0, 0, "地市名称"));
	        	sheet.addCell(new Label(1, 0, "营业厅名称"));
	        	sheet.addCell(new Label(2, 0, "boss工号"));
	        	sheet.addCell(new Label(3, 0, "客户手机号"));
	        	sheet.addCell(new Label(4, 0, "商品名称"));
	        	sheet.addCell(new Label(5, 0, "报文信息"));
	        	sheet.addCell(new Label(6, 0, "受理时间"));
	        	
	        	if(datas!=null && !datas.isEmpty()){
	        		for(int i=0; i<datas.size(); i++){
	        			
		    			sheet.addCell(new jxl.write.Label(0, i+1, datas.get(i).getRegionName()));
		    			sheet.addCell(new Label(1, i+1, datas.get(i).getOfficeName()));
		    			sheet.addCell(new Label(2, i+1, datas.get(i).getWorkno()));
		    			sheet.addCell(new Label(3, i+1, datas.get(i).getMobile()));
		    			sheet.addCell(new Label(4, i+1, datas.get(i).getFeeName()));
		    			sheet.addCell(new Label(5, i+1, datas.get(i).getMessage()));
		    			
		    			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    			String str = new String();
		    			str = format.format(datas.get(i).getDatetime());
		    			sheet.addCell(new jxl.write.Label(6, i+1, str));
	        		}
	        	}
	        	book.write();
	        	book.close();
				
			} catch (Exception e) {
				logger.error(e.getLocalizedMessage());
			}
		
		downFile(response, sysPath+fileName, true, true);
	    logger.info("导出受理失败记录结束。。。。。");
		return null;
	}
}
