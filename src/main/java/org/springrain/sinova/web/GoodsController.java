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
import javax.servlet.http.HttpSession;

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
import org.springrain.sinova.entity.Office;
import org.springrain.sinova.entity.Region;
import org.springrain.sinova.service.IBusinessService;
import org.springrain.sinova.service.IGoodsService;
import org.springrain.sinova.service.IRegionService;
import org.springrain.system.entity.DicData;
import org.springrain.system.entity.Role;
import org.springrain.system.service.IDicDataService;
import org.springrain.system.util.Constant;


/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-01-09 09:13:24
 * @see org.springrain.sinova.web.Goods
 */
@Controller
@RequestMapping(value = "/goods")
public class GoodsController extends BaseController {
	@Resource
	private IGoodsService goodsService;
	@Resource
	private IRegionService regionService;
	@Resource
	private IBusinessService businessService;
	@Resource
	private IDicDataService dicDataService;
	
	private String listurl = "/sinova/goods/goodsList";

	/**
	* 进入goods Web页面后直接展现第一页数据,调用listjson方法,保证和app端数据统一
	*
	* @param request
	* @param model
	* @param goods
	* @return
	* @author <Auto generate>
	* @version  2015-01-09 09:13:24
	*/
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model, Goods goods) throws Exception {
		ReturnDatas returnObject = listjson(request, model, goods);
		Page page = newPage(request);
		List<Goods> datas = goodsService.queryForList(request,goods, page);
	/*	//根据getFeeCode给商品排序
		if(null!= datas && datas.size()>0){
			SortList sortList = new SortList();
			sortList.Sort(datas,"getFeeCode",null);
		}*/
		
		List<DicData> list = dicDataService.findListDicData("bustype");
		if(null!=list && !list.isEmpty()){
			request.setAttribute("BusTypes", list);
		}
		returnObject.setQueryBean(goods);
		returnObject.setPage(page);
		returnObject.setData(datas);
		logger.info("------总页数-----"+page.getPageCount());
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		logger.info("------总页数-----"+page.getPageCount()+"-----"+returnObject.getPage().getPageCount());
		return listurl;
	}
	
	/**
	* json数据,为APP提供数据
	*
	* @param request
	* @param model
	* @param goods
	* @return
	* @author <Auto generate>
	* @version  2015-01-09 09:13:24
	*/
	@RequestMapping("/list/json")
	@ResponseBody
	public ReturnDatas listjson(HttpServletRequest request, Model model, Goods goods) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		List<Business> bdatas = businessService.queryForList(request,model,goods);
		request.setAttribute("BusDatas", bdatas);
		return returnObject;
	}
	
	//只有超级管理员有这个功能
	@RequestMapping("/search/belongBusiness")
	@ResponseBody
	public List<Business> searchBelongBusiness(HttpServletRequest request,HttpSession session, Model model) throws Exception {
		List<Business> bdatas = businessService.searchBelongBusiness(request,model);
		request.setAttribute("BusDatas", bdatas);
		model.addAttribute("BusDatas", bdatas);
		return bdatas;
	}
	/**
	 * 导出Excle格式的数据
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param goods
	 * @throws Exception
	 */
	@RequestMapping("/list/export")
	public void listexport(HttpServletRequest request, HttpServletResponse response, Model model, Goods goods) throws Exception {
		// ==构造分页请求
		Page page = newPage(request);

		File file = goodsService.findDataExportExcel(null, listurl, page, Goods.class, goods);
		String fileName = "goods" + GlobalStatic.excelext;
//		downFile(response, file, fileName, true);
		return;
	}

	/**
	 * 查看操作,调用APP端lookjson方法
	 */
	@RequestMapping(value = "/look")
	public String look(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		 ReturnDatas returnObject = lookjson(model, request, response);
		 model.addAttribute(GlobalStatic.returnDatas, returnObject);
		 return "/sinova/goods/goodsLook";
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
 			Goods goods = goodsService.findGoodsById(id);
 			returnObject.setData(goods);
 		} else {
			returnObject.setStatus(ReturnDatas.ERROR);
		}
    	return returnObject;
    }

	/**
	 * 添加跳页
	 * @throws Exception 
	 */
	@RequestMapping(value = "/gotoAdd")
	public String beforeAdd(HttpServletRequest request, Model model) throws Exception{
		logger.info("---------添加页面跳页--------------");
		//查询业务
		Finder finder = new Finder();
		finder.append("select r.* from ").append(Finder.getTableName(Business.class)).append(" r where r.state= :state and r.up_down='1' ");
		finder.setParam("state", Constant.ONE);
		finder.append(" order by r.sort");
		List<Business> bdatas = businessService.queryForList(finder, Business.class);
		request.setAttribute("BusDatas", bdatas);
		return "/sinova/goods/beforeAddGoods";
	}
	/**
	 * 修改跳页
	 * @throws Exception
	 */
	@RequestMapping(value = "/gotoUpdate")
	public String beforeUpdate(HttpServletRequest request, Model model, Goods goods) throws Exception{
		String id = goods.getId();
		logger.info("---------修改页面跳页--------------");
		
		if(StringUtils.isNotBlank(id)){
			Goods good = goodsService.findGoodsById(id);
			if(null!=good){
				model.addAttribute("good", good);
			}
		}
		Finder finder = new Finder();
		finder.append("select r.* from ").append(Finder.getTableName(Business.class)).append(" r where r.state= :state and r.up_down='1' ");
		finder.setParam("state", Constant.ONE);
		finder.append(" order by r.sort");
		List<Business> datas = businessService.queryForList(finder, Business.class);
		if(null!=datas && !datas.isEmpty()){
			request.setAttribute("BusDatas", datas);
		}
		
		return "/sinova/goods/beforeUpdateGoods";
	}
	/**
	* 新增/修改save操作,返回json格式数据
	*
	* @param model
	* @param goods
	* @param request
	* @param response
	* @return
	* @throws Exception
	* @author <Auto generate>
	* @version  2015-01-09 09:13:24
	*/
	@RequestMapping("/update")
	@ResponseBody
	public ReturnDatas saveorupdate(Model model, Goods goods, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("**********saveorupdate*****start***************");
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		try {
			java.lang.String id = goods.getId();
			if (StringUtils.isBlank(id)) {
				goods.setId(null);
				goods.setState(Constant.ONE);
				goods.setUpDown(Constant.ZERO);
			}
			
			List<Goods> listf = new ArrayList<Goods>();
			if("".equals(id) || id == null){//添加操作 验证
				if(StringUtils.isNotBlank(goods.getFeeCode())){
					Goods goodsDto = new Goods();
					goodsDto.setFeeCode(goods.getFeeCode());
					listf=goodsService.findByGood(goodsDto);
					if(null!=listf && listf.size()>0){
						returnObject.setStatus(ReturnDatas.ERROR);
						returnObject.setMessage("该资费代码已被使用,请使用其他代码!");
					}else{
						goodsService.saveorupdate(goods);
						returnObject.setMessage(MessageUtils.ADD_SUCCESS);
						returnObject.setStatus(ReturnDatas.SUCCESS);
					}
				}
			}else{
				goodsService.saveorupdate(goods);
				returnObject.setStatus(ReturnDatas.SUCCESS);
			}
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
		return "/sinova/goods/goodsCru";
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
				goodsService.deleteById(id, Goods.class);
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
	* @version  2015-01-09 09:13:24
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
			goodsService.deleteByIds(ids, Goods.class);
			//goodsService.deleteBusMore(rs);
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
			info = goodsService.saveImportExcelFile(destFile,Goods.class);
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
	public ReturnDatas upDown(Model model, Goods good, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		try {
			java.lang.String id = good.getId();
			java.lang.String upDown = good.getUpDown();
			if(StringUtils.isBlank(id) || StringUtils.isBlank(upDown) ){
				return new ReturnDatas(ReturnDatas.ERROR, MessageUtils.DELETE_ALL_ERROR);
			}
			logger.info("id="+id+"====upDown="+upDown);
			String result=goodsService.upDown(id,upDown);
			logger.info("==upDownresultresult="+result);
			if("1".equals(upDown)){
				returnObject.setMessage("操作成功！");
			}else{
				returnObject.setMessage("操作成功！");
			}
		} catch (Exception e) {
			String errorMessage = e.getLocalizedMessage();
			logger.error(errorMessage);
			returnObject.setMessage(MessageUtils.UPDATE_ERROR);
		}
		return returnObject;
	}
}
