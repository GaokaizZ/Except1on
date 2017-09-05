package  org.springrain.sinova.web;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.springrain.sinova.entity.Goods;
import org.springrain.sinova.entity.Region;
import org.springrain.sinova.entity.UserBusiness;
import org.springrain.sinova.service.IBusinessService;
import org.springrain.sinova.service.IGoodsService;
import org.springrain.sinova.service.IRegionService;
import org.springrain.sinova.service.IUserBusinessService;
import org.springrain.sinova.util.SortList;
import org.springrain.system.entity.DicData;
import org.springrain.system.entity.Role;
import org.springrain.system.entity.UserRole;
import org.springrain.system.service.IDicDataService;
import org.springrain.system.service.IRoleService;
import org.springrain.system.service.IUserRoleService;
import org.springrain.system.util.Constant;

import com.sun.tools.javac.resources.javac;


/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-01-09 09:13:13
 * @see org.springrain.sinova.web.Business
 */
@Controller
@RequestMapping(value = "/business")
public class BusinessController extends BaseController {
	@Resource
	private IBusinessService businessService;
	@Resource
	private IGoodsService goodsService;
	@Resource
	private IDicDataService dicDataService;
	
	private String listurl = "/sinova/business/businessList";
	/**
	* 进入business Web页面后直接展现第一页数据,调用listjson方法,保证和app端数据统一
	*
	* @param request
	* @param model
	* @param business
	* @return
	* @author <Auto generate>
	* @version  2015-01-09 09:13:13
	*/
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model, Business business) throws Exception {
		ReturnDatas returnObject = listjson(request, model, business);
		Page page = newPage(request);
		List<Business> datas= businessService.queryForBusinessList(request,Business.class, page);
		returnObject.setQueryBean(business);
		returnObject.setPage(page);
		returnObject.setData(datas);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return listurl;
	}
	
	/**
	* json数据,为APP提供数据
	* @param request
	* @param model
	* @param business
	* @return
	* @author <Auto generate>
	* @version  2015-01-09 09:13:13
	*/
	@RequestMapping("/list/json")
	@ResponseBody
	public ReturnDatas listjson(HttpServletRequest request, Model model, Business business) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Page page = newPage(request);
		List<Business> datas = businessService.findListDataByFinder(null, page, Business.class, business);
		//根据getBusCode给业务排序
		if(null!=datas && datas.size()>0){
			SortList sortList = new SortList();
			sortList.Sort(datas, "getBusCode", null);
		}
		
		List<DicData> list = dicDataService.findListDicData("bustype");
		if(null!=list && !list.isEmpty()){
			request.setAttribute("BusTypes", list);
		}
		returnObject.setQueryBean(business);
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
	 * @param business
	 * @throws Exception
	 */
	@RequestMapping("/list/export")
	public void listexport(HttpServletRequest request, HttpServletResponse response, Model model, Business business) throws Exception {
		// ==构造分页请求
		Page page = newPage(request);

		File file = businessService.findDataExportExcel(null, listurl, page, Business.class, business);
		String fileName = "business" + GlobalStatic.excelext;
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
		 return "/sinova/business/businessLook";
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
 			Business business = businessService.findBusinessById(id);
 			returnObject.setData(business);
 		} else {
			returnObject.setStatus(ReturnDatas.ERROR);
		}
    	return returnObject;
    }

	/**
	 * 添加跳页
	 * @throws Exception 
	 */
	@RequestMapping(value = "/beforeAdd")
	public String beforeAdd(HttpServletRequest request, Model model) throws Exception{
		List<DicData> list = dicDataService.findListDicData("bustype");
		if(null!=list && !list.isEmpty()){
			request.setAttribute("BusTypes", list);
		}
		return "/sinova/business/beforeAddBusiness";
	}
	/**
	 * 修改跳页
	 * @throws Exception 
	 */
	@RequestMapping(value = "/beforeUpdate")
	public String beforeUpdate(HttpServletRequest request,Model model) throws Exception{
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			Business business = businessService.findBusinessById(id);
			if(null!=business){
				model.addAttribute("business", business);
			}
		}
		List<DicData> list = dicDataService.findListDicData("bustype");
		if(null!=list && !list.isEmpty()){
			request.setAttribute("BusTypes", list);
		}
		
		return "/sinova/business/beforeUpdateBusiness";
	}
	
	
	/**
	* 新增/修改save操作,返回json格式数据
	*
	* @param model
	* @param business
	* @param request
	* @param response
	* @return
	* @throws Exception
	* @author <Auto generate>
	* @version  2015-01-09 09:13:13
	*/
	@RequestMapping("/update")
	@ResponseBody
	public ReturnDatas saveorupdate(Model model, Business business, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage("恭喜你，操作成功！");
		
		try {
			java.lang.String id = business.getId();
			
			if (StringUtils.isBlank(id)) {//添加操作
				business.setId(null);
				business.setState(Constant.ONE);
				business.setUpDown(Constant.ZERO);
			}
			Business b = null;
			List<Business> list = new ArrayList<Business>();;
			String type = request.getParameter("type");
			if(!"1".equals(type)){//添加操作 修改不验证busCode
				if(StringUtils.isNotBlank(business.getBusCode())){
					b = new Business();
					b.setBusCode(business.getBusCode());
					b.setState(Constant.ONE);
					list= businessService.findByBus(b);
					if(null!=list && list.size()>0){
						returnObject.setStatus("codeUsed");
						return returnObject;
					}
				}
			}
			Business bb = businessService.findBusinessById(id);
			if(null!=bb){
				String bbName = bb.getBusName();
				if(!bbName.equals(business.getBusName())){//修改操作 未改动名称的不校验名称
					if(StringUtils.isNoneBlank(business.getBusName())){
						b = new Business();
						b.setBusName(business.getBusName());
						b.setState(Constant.ONE);
						list= businessService.findByBus(b);
						if(null!=list && list.size()>0){
							returnObject.setStatus("nameUsed");
							return returnObject;
						}
					}
				}
			}
			businessService.saveorupdate(business);
		} catch (Exception e) {
			String errorMessage = e.getLocalizedMessage();
			logger.error(errorMessage);
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage(MessageUtils.UPDATE_ERROR);
		}
		logger.info("================"+returnObject.getMessage());
		return returnObject;
	}

	/**
	 * 进入修改页面,APP端可以调用 lookjson 获取json格式数据
	 */
	@RequestMapping(value = "/update/pre")
	public String edit(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = lookjson(model, request, response);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/sinova/business/businessCru";
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
				businessService.findBusinessById(id);
				businessService.deleteById(id, Business.class);
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
	* @version  2015-01-09 09:13:13
	*/
	@RequestMapping("/delete/more")
	@ResponseBody
	public ReturnDatas delMultiRecords(HttpServletRequest request, Model model, Goods goods) {
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
			
			if(ids != null){
				for (int i = 0; i < ids.size(); i++) {
					String id = ids.get(i);
					Business bus = businessService.findBusinessById(id);
					String busId = bus.getId();
					goods.setBusId(busId);
					//查询该业务下的商品列表
					List<Goods> gdatas = goodsService.findListDataByFinder(null, null, Goods.class, goods);
					if(gdatas != null && gdatas.size()>0){
						for(int j = 0; j < gdatas.size(); j++){
						Goods goodsa = gdatas.get(j);
						String goodsId = goodsa.getId();
						goodsService.deleteById(goodsId, Goods.class);
						}
					}
				}
			}
			businessService.deleteByIds(ids, Business.class);
		} catch (Exception e) {
			return new ReturnDatas(ReturnDatas.ERROR, MessageUtils.DELETE_ALL_ERROR);
		}
		return new ReturnDatas(ReturnDatas.SUCCESS, MessageUtils.DELETE_ALL_SUCCESS);
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
			info = businessService.saveImportExcelFile(destFile,Business.class);
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
	
	/**
	* 操作上下架
	*
	* @param model
	* @param business
	* @param request
	* @param response
	* @return
	* @throws Exception
	* @author <Auto generate>
	* @version  2015-01-09 09:13:13
	*/
	@RequestMapping("/upDown")
	@ResponseBody
	public ReturnDatas upDown(Model model, Business business, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		try {
			java.lang.String id = business.getId();
			java.lang.String upDown = business.getUpDown();
			if(StringUtils.isBlank(id) || StringUtils.isBlank(upDown) ){
				return new ReturnDatas(ReturnDatas.ERROR, MessageUtils.DELETE_ALL_ERROR);
			}
			logger.info("id="+id+"====upDown="+upDown);
			businessService.upDown(id,upDown);
			returnObject.setMessage("操作成功！");
		} catch (Exception e) {
			String errorMessage = e.getLocalizedMessage();
			logger.error(errorMessage);
			returnObject.setMessage("操作失败！");
		}
		return returnObject;
	}
	
}
