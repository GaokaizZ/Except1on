package  org.springrain.sinova.web;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.MessageUtils;
import org.springrain.frame.util.Page;
import org.springrain.frame.util.ReturnDatas;
import org.springrain.sinova.dto.UserOfficeDTO;
import org.springrain.sinova.entity.Business;
import org.springrain.sinova.entity.Region;
import org.springrain.sinova.entity.UserBusiness;
import org.springrain.sinova.entity.UserOffice;
import org.springrain.sinova.service.IBusinessService;
import org.springrain.sinova.service.IUserBusinessService;
import org.springrain.sinova.util.Const;
import org.springrain.system.entity.User;
import org.springrain.system.entity.UserRole;
import org.springrain.system.service.IUserRoleService;
import org.springrain.system.service.IUserService;
import org.springrain.system.util.Constant;


/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-01-09 09:15:03
 * @see org.springrain.sinova.web.UserBusiness
 */
@Controller
@RequestMapping(value = "/userbusiness")
public class UserBusinessController extends BaseController {
	@Resource
	private IUserBusinessService userBusinessService;
	@Resource
	private IUserService userService;
	@Resource
	private IBusinessService businessService;
	@Resource
    private IUserRoleService userRoleService;

	private String listurl = "/sinova/userbusiness/userbusinessList";

	/**
	* 进入userbusiness Web页面后直接展现第一页数据,调用listjson方法,保证和app端数据统一
	*
	* @param request
	* @param model 
	* @param userBusiness
	* @return
	* @author <Auto generate>
	* @version  2015-01-09 09:15:03
	*/
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model, User user) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Page page = newPage(request);

		//如果是超管，则返回有营业厅的用户数据
		//获取当前登录用户的Id
		String userId = SessionUser.getShiroUser().getId();
		Finder finders = new Finder("select * from ").append(Finder.getTableName(UserRole.class)).append(" where user_id =:userId");
		finders.setParam("userId", userId);
		//查询当前用户的角色
		List<UserRole> dataz = userRoleService.queryForList(finders, UserRole.class);
		if(null!=dataz && dataz.size()>0){
			UserRole role = dataz.get(0);
			String roleId = role.getRoleId();
			//超级管理员
			if("admin".equals(roleId)){
				model.addAttribute("roleId", roleId);
				logger.info("------------超级管理员-----------");
				
				Finder finder = new Finder();
				finder.append("select u.* from ").append(Finder.getTableName(User.class)).append(" u, ");
				finder.append(Finder.getTableName(UserOffice.class)).append(" uo ");
				finder.append(" where u.state = '1' and u.id = uo.user_id ");
				
				if(StringUtils.isNotBlank(user.getAccount())){
					finder.append(" and u.account like :account");
					finder.setParam("account", "%"+user.getAccount()+"%");
				}
				if(StringUtils.isNotBlank(user.getName())){
					finder.append(" and u.name like :name");
					String name = new String(user.getName().getBytes("ISO-8859-1"), "utf-8");
					
					finder.setParam("name", "%"+name+"%");
					System.out.println(name);
					model.addAttribute("name", name);
				}
				
				finder.append(" order by u.id");
				List<User> datas = userService.queryForList(finder, User.class, page);
				returnObject.setQueryBean(user);
				returnObject.setPage(page);
				returnObject.setData(datas);
				
				model.addAttribute(GlobalStatic.returnDatas, returnObject);
				return listurl;
			}
		}
		
		UserOfficeDTO userOfficeDTO = SessionUser.getShiroUser().getUserOfficeDTO();
		logger.info("---获取当前用户信息--");
		if(userOfficeDTO == null){
			logger.warn("当前用户地市区县信息为空，返回空用户列表");
			model.addAttribute(GlobalStatic.returnDatas, returnObject);
			return listurl;
		}
		
		//营业厅管理员，返回所属营业厅下的营业员
		if(ArrayUtils.contains(userOfficeDTO.getPost(), Const.USER_OFFICE_MANAGER)){
			logger.info("-----------营业厅管理员-------------");
			Finder finder = new Finder("SELECT u.* from ").append(Finder.getTableName(User.class)).append(" u,").append(Finder.getTableName(UserOffice.class)).append("  uo where u.id=uo.user_id and uo.region_code=:regionCode and uo.county_code=:countyCode and uo.office_code=:officeCode and u.state=:state ");
			finder.setParam("regionCode", userOfficeDTO.getRegionCode());
			finder.setParam("countyCode", userOfficeDTO.getCountyCode());
			finder.setParam("officeCode", userOfficeDTO.getOfficeCode());
			finder.setParam("state", Constant.ONE);
			
			if(StringUtils.isNotBlank(user.getAccount())){
				finder.append(" and u.account like :account");
				finder.setParam("account", "%"+user.getAccount()+"%");
			}
			if(StringUtils.isNotBlank(user.getName())){
				finder.append(" and u.name like :name");
				String name = new String(user.getName().getBytes("ISO-8859-1"), "utf-8");
				finder.setParam("name", "%"+name+"%");
				System.out.println(name);
				model.addAttribute("name", name);
			}
			
			finder.append(" order by u.id");
			
			List<User> datas = userService.queryForList(finder, User.class, page);
			returnObject.setQueryBean(user);
			returnObject.setPage(page);
			returnObject.setData(datas);
			
			model.addAttribute(GlobalStatic.returnDatas, returnObject);
			return listurl;
		}
		return null;
	}
	
	/**
	* json数据,为APP提供数据
	*
	* @param request
	* @param model
	* @param userBusiness
	* @return
	* @author <Auto generate>
	* @version  2015-01-09 09:15:03
	*/
	@RequestMapping("/list/json")
	@ResponseBody
	public ReturnDatas listjson(HttpServletRequest request, Model model, UserBusiness userBusiness, User user) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		
//		Page page = newPage(request);
//		
//		List<UserBusiness> datas = userBusinessService.findListDataByFinder(null, page, UserBusiness.class, userBusiness);
//		returnObject.setQueryBean(userBusiness);
//		returnObject.setPage(page);
//		returnObject.setData(datas);
		return returnObject;
	}
	
	
	@RequestMapping("/buslist")
	public String buslist(HttpServletRequest request, Model model, UserBusiness userBusiness, Business business) throws Exception {
		ReturnDatas returnObject = buslistjson(request, model, userBusiness, business);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/sinova/userbusiness/businessList";
	}
	
	

	@RequestMapping("/buslist/json")
	@ResponseBody
	public ReturnDatas buslistjson(HttpServletRequest request, Model model, UserBusiness userBusiness, Business business) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();

		Page page = newPage(request);
		
		String userId = request.getParameter("userId");
		
		List<Business> datas = businessService.findBusinessByUserId(userBusiness, business, page);
		returnObject.setQueryBean(business);
		returnObject.setPage(page);
		returnObject.setData(datas);
		
		String account = request.getParameter("account");
		model.addAttribute("account", account);
		model.addAttribute("userId", userId);
		if(StringUtils.isNotBlank(business.getBusCode()) && !"undefined".equals(business.getBusCode())){
			model.addAttribute("busCode", business.getBusCode());
		}
		if(StringUtils.isNotBlank(business.getBusName()) && !"undefined".equals(business.getBusName())){
			model.addAttribute("busName", business.getBusName());
		}
		return returnObject;
	}
	@RequestMapping("/findBus")
	public String findbusjson(HttpServletRequest request, Model model, UserBusiness userBusiness, Business business) throws Exception {
		ReturnDatas returnObject = findbus(request, model, userBusiness, business);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/sinova/userbusiness/findBus";
	}
	@RequestMapping("/findBus/json")
	@ResponseBody
	public ReturnDatas findbus(HttpServletRequest request, Model model, UserBusiness userBusiness, Business business) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();

		Page page = newPage(request);

		String userId = request.getParameter("userId");
		String account = request.getParameter("account");
		
		Finder finder = new Finder();
		finder.append("select * from t_user_business t where t.user_id =:userId ");
		finder.setParam("userId", userId);
		List<UserBusiness> userBus = userBusinessService.queryForList(finder, UserBusiness.class); 
		String userIds = null;
		//如果userBus不为空，则查询用户与业务表
		if(userBus.size()>0){
			userIds = userId;
		}
		
		String busCode = request.getParameter("busCode");
		String busName = request.getParameter("busName");
		if(StringUtils.isNotBlank(busCode)){
			business.setBusCode(busCode.trim());
		}
		if(StringUtils.isNotBlank(busName)){
			business.setBusName(busName.trim());
		}
		
		List<Business> datas = businessService.findBusNot(userBusiness, userIds, page, business);
		returnObject.setQueryBean(business);
		returnObject.setPage(page);
		returnObject.setData(datas);
		
		model.addAttribute("account", account);
		model.addAttribute("userId", userId);
		model.addAttribute("busCode", busCode);
		model.addAttribute("busName", busName);
		return returnObject;
	}
	
	/**
	 * 导出Excle格式的数据
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param userBusiness
	 * @throws Exception
	 */
	@RequestMapping("/list/export")
	public void listexport(HttpServletRequest request, HttpServletResponse response, Model model, UserBusiness userBusiness) throws Exception {
		// ==构造分页请求
		Page page = newPage(request);

		File file = userBusinessService.findDataExportExcel(null, listurl, page, UserBusiness.class, userBusiness);
		String fileName = "userBusiness" + GlobalStatic.excelext;
	//	downFile(response, file, fileName, true);
		return;
	}

	/**
	 * 查看操作,调用APP端lookjson方法
	 */
	@RequestMapping(value = "/look")
	public String look(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		 ReturnDatas returnObject = lookjson(model, request, response);
		 model.addAttribute(GlobalStatic.returnDatas, returnObject);
		 return "/sinova/userbusiness/userbusinessLook";
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
 			UserBusiness userBusiness = userBusinessService.findUserBusinessById(id);
 			returnObject.setData(userBusiness);
 		} else {
			returnObject.setStatus(ReturnDatas.ERROR);
		}
    	return returnObject;
    }

	/**
	* 新增/修改save操作,返回json格式数据
	*
	* @param model
	* @param userBusiness
	* @param request
	* @param response
	* @return
	* @throws Exception
	* @author <Auto generate>
	* @version  2015-01-09 09:15:03
	*/
	@RequestMapping("/update")
	@ResponseBody
	public ReturnDatas saveorupdate(Model model, UserBusiness userBusiness, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		
		try {
			java.lang.String id = userBusiness.getId();
			if (StringUtils.isBlank(id)) {
				userBusiness.setId(null);
			}
			userBusinessService.saveorupdate(userBusiness);

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
	public String edit(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = lookjson(model, request, response);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/sinova/userbusiness/userbusinessCru";
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
				userBusinessService.deleteById(id, UserBusiness.class);
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
	* @version  2015-01-09 09:15:03
	*/
	@RequestMapping("/deletebus")
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
			userBusinessService.deleteByIds(ids, UserBusiness.class);
		} catch (Exception e) {
			return new ReturnDatas(ReturnDatas.ERROR, MessageUtils.DELETE_ALL_ERROR);
		}
		return new ReturnDatas(ReturnDatas.SUCCESS, MessageUtils.DELETE_ALL_SUCCESS);
	}
	
	@RequestMapping("/savebus")
	@ResponseBody
	public ReturnDatas updates(Model model, UserBusiness userBusiness, HttpServletRequest request, HttpServletResponse response,String busIds) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		String records = request.getParameter("records");
	
		if (StringUtils.isBlank(records)) {
			return new ReturnDatas(ReturnDatas.ERROR, MessageUtils.UPDATE_ERROR);
		}
		
		String[] rs = records.split(",");
		if (rs == null || rs.length < 1) {
			return new ReturnDatas(ReturnDatas.ERROR, MessageUtils.UPDATE_ERROR);
		}
		try {
		for(int i = 0; i<rs.length; i++){
			String id = userBusiness.getId();
			if(StringUtils.isNotBlank(id)){
				userBusiness.setId(null);
			}
			String busid = rs[i];
			userBusiness.setBusId(busid);
			userBusinessService.save(userBusiness);
		}
		returnObject.setStatus(ReturnDatas.SUCCESS);
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
			
		} catch (Exception e) {
			String errorMessage = e.getLocalizedMessage();
			logger.error(errorMessage);
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage(MessageUtils.UPDATE_ERROR);
		}
		return returnObject;
	}

	/**
	 *  上传文件
	 * @param uploadFile
	 * @param request
	 * @param model
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/upload")
	@ResponseBody
	public Map<String,String> upload(@RequestParam MultipartFile uploadFile, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String info = "";
		File destFile = null;

		try {
			String uploadDirPath = request.getSession().getServletContext().getRealPath("/upload");
			File dir = new File(uploadDirPath);
			if (dir.exists() == false) {
				dir.mkdirs();
			}
			destFile = new File(uploadDirPath + "/" + uploadFile.getOriginalFilename());
			FileCopyUtils.copy(uploadFile.getBytes(), destFile);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			info = "上传失败,文件处理异常.";
		}

		try {
			info = userBusinessService.saveImportExcelFile(destFile,UserBusiness.class);
		} catch (Exception e) {
		    logger.error(e.getMessage(), e);
			info = e.getMessage();
		}
		if (StringUtils.isBlank(info)) {
			info = "数据导入成功....";
		}
		Map<String,String> map = new HashMap<String,String>();
		map.put("msg", info);
		return map;
	}
	
	@RequestMapping("/userPage")
	public String userPage(HttpServletRequest request, Model model) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/sinova/userbusiness/regionTree";
	}
	
	@RequestMapping("/userPage/json")
	@ResponseBody
	public ReturnDatas userPagejson(HttpServletRequest request, Model model, Region region) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		List<Region> datas = userService.findListDataByFinder(null, null, Region.class, region);
		returnObject.setQueryBean(region);
		returnObject.setData(datas);
		return returnObject;
	}

	
}
