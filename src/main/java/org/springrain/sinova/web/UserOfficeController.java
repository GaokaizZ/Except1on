package  org.springrain.sinova.web;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springrain.frame.common.SessionUser;
import org.springrain.frame.controller.BaseController;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.ReturnDatas;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.MessageUtils;
import org.springrain.frame.util.Page;

import org.springrain.sinova.dto.OfficeUserDTO;
import org.springrain.sinova.dto.UserNameDTO;
import org.springrain.sinova.dto.UserOfficeDTO;
import org.springrain.sinova.entity.County;
import org.springrain.sinova.entity.Office;
import org.springrain.sinova.entity.Region;
import org.springrain.sinova.entity.UserOffice;
import org.springrain.sinova.service.ICountyService;
import org.springrain.sinova.service.IOfficeService;
import org.springrain.sinova.service.IRegionService;
import org.springrain.sinova.service.IUserOfficeService;
import org.springrain.system.entity.DicData;
import org.springrain.system.entity.UserRole;
import org.springrain.system.service.IDicDataService;
import org.springrain.system.service.IUserRoleService;


/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-01-09 09:16:10
 * @see org.springrain.sinova.web.UserOffice
 */
@Controller
@RequestMapping(value = "/useroffice")
public class UserOfficeController extends BaseController {
	@Resource
	private IUserOfficeService userOfficeService;
	@Resource
	private IRegionService regionService;
	@Resource
	private ICountyService countyService;
	@Resource
	private IOfficeService officeService;
	@Resource
	private IUserRoleService userRoleService;
	@Resource
	private IDicDataService dicDataService;

	
	/**
	* 进入useroffice Web页面后直接展现第一页数据,调用listjson方法,保证和app端数据统一
	*
	* @param request
	* @param model
	* @param userOffice
	* @return
	* @author <Auto generate>
	* @version  2015-01-09 09:16:10
	*/
	@RequestMapping("/list")
	public String listUser(HttpServletRequest request, Model model, OfficeUserDTO officeUserDTO) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		//列表显示岗位名称
		DicData dicData = new DicData();
		dicData.setTypekey("post");
		List<DicData> dicdata = dicDataService.findListDataByFinder(null, null, DicData.class, dicData);
		request.setAttribute("DicData", dicdata);

		logger.info("=====给营业厅绑定用户=======");
		Page page = newPage(request);
	    
	    Finder finder = new Finder();
		finder.append("select uo.id as id, u.name as userName, u.account as account, u.id as userId, r.region_code as regionCode, r.region_name as regionName, c.county_code as countyCode, c.county_name as countyName, o.office_code as officeCode, o.office_name as officeName, uo.post as postName ");
		finder.append("from t_user u, t_region r, t_county c, t_office o, t_user_office uo ");
		finder.append("where u.id = uo.user_id and uo.region_code = r.region_code and uo.county_code = c.county_code and uo.office_code = o.office_code and u.state = '1' and o.office_code = :officeCode ");
		//按条件查询时使用
		if(StringUtils.isNotBlank(officeUserDTO.getAccount())){
			finder.append("and u.account like :account");
			finder.setParam("account", "%"+officeUserDTO.getAccount()+"%");
		}

		finder.setParam("officeCode", officeUserDTO.getOfficeCode());
		List<OfficeUserDTO> datas = userOfficeService.findListDataByFinder(finder, page, OfficeUserDTO.class, officeUserDTO);
		
		returnObject.setQueryBean(officeUserDTO);
		returnObject.setPage(page);
		returnObject.setData(datas);
		model.addAttribute("officeCode", officeUserDTO.getOfficeCode());
		model.addAttribute("regionCode", officeUserDTO.getRegionCode());
		model.addAttribute("countyCode", officeUserDTO.getCountyCode());
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		model.addAttribute("ouDTO", officeUserDTO);
		logger.info("=====给营业厅绑定用户结束=======");
		return "/sinova/useroffice/userofficeList";
	}
	
	/**
	* json数据,为APP提供数据
	*
	* @param request
	* @param model
	* @param userOffice
	* @return
	* @author <Auto generate>
	* @version  2015-01-09 09:16:10
	 * @throws Exception 
	*/
	@RequestMapping("/list/json")
	@ResponseBody
	public ReturnDatas listjson(HttpServletRequest request, Model model, OfficeUserDTO officeUserDTO) throws Exception{
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		return returnObject;
	}
	
	/**
	 * 查找未分配营业厅的用户
	 * @param request
	 * @param model
	 * @param userNameDTO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findUser")
	public String findUserNoInOffice(HttpServletRequest request, Model model, UserNameDTO userNameDTO) throws Exception {
		ReturnDatas returnObject = jsonuser(request, model, userNameDTO);
		logger.info("查询未分配营业厅的用户-----------------------------");
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/sinova/useroffice/findUser";
	}
	@RequestMapping("/findUser/json")
	@ResponseBody
	public ReturnDatas jsonuser(HttpServletRequest request, Model model, UserNameDTO userNameDTO) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		
		Page page = newPage(request);
		
		List<UserNameDTO> datas = userOfficeService.findUserNotInOffice(page, userNameDTO);
		returnObject.setQueryBean(userNameDTO);
		returnObject.setPage(page);
		returnObject.setData(datas);
		model.addAttribute("unameDTO", userNameDTO);
		return returnObject;
	}
	
	/**
	 * 查找岗位
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/postList")
	public String postList(HttpServletRequest request, Model model, HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = jsonpost(request, model, response);
		
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/sinova/useroffice/postList";
	}
	@RequestMapping("/postList/jsonpost")
	@ResponseBody
	public ReturnDatas jsonpost(HttpServletRequest request, Model model, HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		
		String id = request.getParameter("id");

		model.addAttribute("id", id);
		
		List<DicData> datas  =  userOfficeService.findListDicData();
		returnObject.setData(datas);
		
		return returnObject;
	}
	
	/**
	 * 查看操作,调用APP端lookjson方法
	 */
	@RequestMapping(value = "/look")
	public String look(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		 ReturnDatas returnObject = lookjson(model, request, response);
		 model.addAttribute(GlobalStatic.returnDatas, returnObject);
		 return "/sinova/useroffice/userofficeLook";
	}
	
	/**
	 * 查看的Json格式数据,为APP端提供数据
	 */
	@RequestMapping(value = "/look/json")
	@ResponseBody
    public ReturnDatas lookjson(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
 	    java.lang.String id = request.getParameter("id");
 	    if (StringUtils.isNotBlank(id)) {
 			UserOffice userOffice = userOfficeService.findUserOfficeById(id);
 			returnObject.setData(userOffice);
 		} else {
			returnObject.setStatus(ReturnDatas.ERROR);
		}
    	return returnObject;
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
				userOfficeService.deleteById(id, UserOffice.class);
				return new ReturnDatas(ReturnDatas.SUCCESS, MessageUtils.DELETE_SUCCESS);
			} else {
			    return new ReturnDatas(ReturnDatas.WARNING, MessageUtils.DELETE_WARNING);
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
	* @version  2015-01-09 09:16:10
	*/
	@RequestMapping("/delete/more")
	@ResponseBody
	public ReturnDatas delMultiRecords(HttpServletRequest request, Model model) {
		String records = request.getParameter("records");
		if (StringUtils.isBlank(records)) {
			return new ReturnDatas(ReturnDatas.ERROR, MessageUtils.DELETE_ALL_ERROR);
		}
		
		String[] rs = records.split(",");
		if (rs == null || rs.length < 1) {
			return new ReturnDatas(ReturnDatas.ERROR, MessageUtils.DELETE_NULL_FAIL);
		}
		try {
			List<String> ids = Arrays.asList(rs);
			userOfficeService.deleteByIds(ids, UserOffice.class);
		} catch (Exception e) {
			return new ReturnDatas(ReturnDatas.ERROR, MessageUtils.DELETE_ALL_ERROR);
		}
		return new ReturnDatas(ReturnDatas.SUCCESS, MessageUtils.DELETE_ALL_SUCCESS);
	}
	/**
	 * 进入修改页面,APP端可以调用 lookjson 获取json格式数据
	 */
	@RequestMapping(value = "/update/more")
	@ResponseBody
	public ReturnDatas edit(UserOffice userOffice, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		
		String post = request.getParameter("post");
		
		String records = request.getParameter("records");
		userOffice = userOfficeService.findUserOfficeById(records);
		
		try {
			userOffice.setPost(post);
			userOfficeService.saveorupdate(userOffice);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage(MessageUtils.UPDATE_ERROR);
		}
		return new ReturnDatas(ReturnDatas.SUCCESS, MessageUtils.UPDATE_SUCCESS);
	}
	/**
	* 新增/修改save操作,返回json格式数据
	*给营业厅添加用户
	* @param model
	* @param userOffice
	* @param request
	* @param response
	* @return
	* @throws Exception
	* @author <Auto generate>
	* @version  2015-01-09 09:16:10
	*/
	@RequestMapping("/save")
	@ResponseBody
	public ReturnDatas saveorupdate(Model model, UserOffice userOffice, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.ADD_SUCCESS);
		
		String records = request.getParameter("records");
		
		if (StringUtils.isBlank(records)) {
			return new ReturnDatas(ReturnDatas.ERROR, MessageUtils.ADD_ERROR);
		}
		
		String[] rs = records.split(",");
		if (rs == null || rs.length < 1) {
			return new ReturnDatas(ReturnDatas.ERROR, MessageUtils.ADD_ERROR);
		}
		try {
				
			List<String> userIds = Arrays.asList(rs);
			for(int i=0;i<userIds.size();i++){
				userOffice.setUserId(userIds.get(i));
				userOffice.setId(null);
				userOfficeService.save(userOffice);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage(MessageUtils.ADD_ERROR);
		}
		return new ReturnDatas(ReturnDatas.SUCCESS, MessageUtils.ADD_SUCCESS);
	}
	
	/**
	* 进入office Web页面后直接展现第一页数据,调用listjson方法,保证和app端数据统一
	*
	* @param request
	* @param model
	* @param office
	* @return
	* @author <Auto generate>
	* @version  2015-01-09 09:13:01
	*/
	@RequestMapping("/listoffice")
	public String list(HttpServletRequest request, Model model, Office office, Region region,County county) throws Exception {
		ReturnDatas returnObject = listjson(request, model, office, region,county);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/sinova/useroffice/officeList";
	}
	
	@RequestMapping("/listoffice/json")
	@ResponseBody
	public ReturnDatas listjson(HttpServletRequest request, Model model, Office office, Region region,County county) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Page page = newPage(request);
		
		//获取当前登录用户的Id
		String userId = SessionUser.getShiroUser().getId();
		Finder finders = new Finder("select * from ").append(Finder.getTableName(UserRole.class)).append(" where user_id =:userId");
		finders.setParam("userId", userId);
		//查询当前用户的角色
		List<UserRole> dataz = userRoleService.queryForList(finders, UserRole.class);
			for (int i = 0; i < dataz.size(); i++) {
				UserRole role = dataz.get(i);
				String roleId = role.getRoleId();
				//超级管理员
				if("admin".equals(roleId)){
					//查询全部地市
					List<Region> dataRegion = regionService.findRegion(region.getRegionName());
					if(dataRegion == null || dataRegion.size()== 0){
						return returnObject;
					}
					//查询全部区县
					List<County> dataCounty = countyService.findCountyByRegionCode(region.getRegionName(), county.getCountyName(),null);
					if(dataCounty == null || dataCounty.size()== 0){
						return returnObject;
					}
					//查询全部营业厅
					if(StringUtils.isNotBlank(region.getRegionName())||StringUtils.isNotBlank(county.getCountyName()) ){
						Finder finder = new Finder();
						finder.append("select * from ").append(Finder.getTableName(Office.class)).append(" where state = '1' ");
						if(dataRegion != null && dataRegion.size()>0){
							if(dataCounty != null && dataCounty.size()>0){
								String str = "";
								for(int x = 0;x<dataCounty.size();x++){
									String countyCode = dataCounty.get(x).getCountyCode();
									if(x==0){
										str = "'" + countyCode + "'";
									}else{
										str = str + ",'" + countyCode + "'";
									}
								}
								finder.append(" and county_code in ("+ str +") ");
							}
						}
						List<Office> datas = userOfficeService.queryForList(finder, Office.class, page);
						returnObject.setData(datas);
						returnObject.setQueryBean(office);
						returnObject.setPage(page);
						model.addAttribute("region", region);
						model.addAttribute("county", county);
						model.addAttribute("office", office);
					}else{
						//查询全部营业厅
						Finder finder = new Finder();
						finder.append("select * from ").append(Finder.getTableName(Office.class));
						finder.append(" where state='1' ");
						if(StringUtils.isNotBlank(office.getOfficeName())){
							finder.append(" and office_name like :officeName");
							finder.setParam("officeName", "%"+office.getOfficeName()+"%");
						}
						List<Office> datas = userOfficeService.queryForList(finder, Office.class, page);
						returnObject.setQueryBean(office);
						returnObject.setPage(page);
						returnObject.setData(datas);
						
					}
					//区县表数据返回页面
					model.addAttribute("dataCounty",dataCounty);
					//地市表数据返回页面
					model.addAttribute("dataRegion",dataRegion);
					return returnObject;
				}else{
					//先根据当前用户登录，查询当前用户下地市区县营业厅情况
					UserOfficeDTO userOfficeDTO = SessionUser.getShiroUser().getUserOfficeDTO();
					if (userOfficeDTO == null) {
						logger.warn("当前用户地市区县信息为空，返回空营业厅列表");
						return returnObject;
					}else{
						//查询全部地市
						List<Region> dataRegion = regionService.findRegion(region.getRegionName());
						if(dataRegion == null || dataRegion.size()== 0){
							return returnObject;
						}
						//查询全部区县
						List<County> dataCounty = countyService.findCountyByRegionCode(region.getRegionName(), county.getCountyName(),null);
						if(dataCounty == null || dataCounty.size()== 0){
							return returnObject;
						}
						//营业厅管理员查询自己的营业厅
							
						Finder finder = new Finder("select * from ").append(Finder.getTableName(Office.class));
						finder.append(" where state ='1' and office_code in ( select office_code from ").append(Finder.getTableName(UserOffice.class)).append(" where user_id =:userId )");
						finder.setParam("userId", userId);
						logger.info("---------userId====="+userId);
						if(StringUtils.isNotBlank(office.getOfficeName())){
							finder.append(" and office_name like :officeName");
							finder.setParam("officeName", office.getOfficeName()+"%");
						}
							
							
						List<Office> datas = officeService.findListDataByFinder(finder, page, Office.class, office);
						returnObject.setQueryBean(office);
						returnObject.setPage(page);
						returnObject.setData(datas);
						
						//区县表数据返回页面
						model.addAttribute("dataCounty",dataCounty);
						//地市表数据返回页面
						model.addAttribute("dataRegion",dataRegion);
						return returnObject;
					}
				}
			}
		return null;
	}
	
}
