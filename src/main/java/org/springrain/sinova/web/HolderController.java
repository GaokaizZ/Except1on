package  org.springrain.sinova.web;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springrain.frame.controller.BaseController;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.MD5Utils;
import org.springrain.frame.util.ReturnDatas;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.MessageUtils;
import org.springrain.frame.util.Page;
import org.springrain.frame.util.SecUtils;

import org.springrain.sinova.entity.Holder;
import org.springrain.sinova.service.IHolderService;


/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-04-20 11:59:16
 * @see org.springrain.sinova.web.Holder
 */
@Controller
@RequestMapping(value = "/holder")
public class HolderController extends BaseController {
	@Resource
	private IHolderService holderService;

	private String listurl = "/sinova/holder/holderList";

	/**
	* 进入holder Web页面后直接展现第一页数据,调用listjson方法,保证和app端数据统一
	*
	* @param request
	* @param model
	* @param holder
	* @return
	* @author <Auto generate>
	* @version  2015-04-20 11:59:16
	*/
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model, Holder holder) throws Exception {
		ReturnDatas returnObject = listjson(request, model, holder);
		Page page = newPage(request);
		String name = request.getParameter("name");
		String sourceNo = request.getParameter("sourceNo");
		String state = request.getParameter("state");
		model.addAttribute("name", name);
		Finder finder = Finder.getSelectFinder(Holder.class);
		finder.append("where 1 = 1 ");
		if(StringUtils.isNotBlank(name)){
			finder.append(" and name like :name").setParam("name", "%"+name.trim()+"%");
		}
		if(StringUtils.isNotBlank(state)){
			finder.append(" and state = :state").setParam("state", state);
		}
		if(StringUtils.isNotBlank(sourceNo)){
			finder.append(" and SOURCE_NO = :sourceNo").setParam("sourceNo", sourceNo.trim());
		}
		finder.append(" order by create_date desc");
		List<Holder> datas = holderService.queryForList(finder, Holder.class, page);
		returnObject.setQueryBean(holder);
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
	* @param holder
	* @return
	* @author <Auto generate>
	* @version  2015-04-20 11:59:16
	*/
	@RequestMapping("/list/json")
	@ResponseBody
	public ReturnDatas listjson(HttpServletRequest request, Model model, Holder holder) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		
		Page page = newPage(request);
		
		List<Holder> datas = holderService.findListDataByFinder(null, page, Holder.class, holder);
		returnObject.setQueryBean(holder);
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
	 * @param holder
	 * @throws Exception
	 */
	@RequestMapping("/list/export")
	public void listexport(HttpServletRequest request, HttpServletResponse response, Model model, Holder holder) throws Exception {
		// ==构造分页请求
		Page page = newPage(request);

		File file = holderService.findDataExportExcel(null, listurl, page, Holder.class, holder);
		String fileName = "holder" + GlobalStatic.excelext;
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
		 return "/sinova/holder/holderLook";
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
 			Holder holder = holderService.findHolderById(id);
 			returnObject.setData(holder);
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
		return "/sinova/holder/beforeAddHolder";
	}
	
	@RequestMapping("/save")
	public @ResponseBody ReturnDatas save(Holder holder, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.ADD_SUCCESS);
		String sourceNo = holder.getSourceNo();
//		String token = holder.getToken();
		//验证是否存在
		Holder queryHolder = new Holder();
		queryHolder.setSourceNo(sourceNo);
		List<Holder> holderList = holderService.findByHolder(queryHolder);
		if (null!=holderList && holderList.size()>0) {
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage("该厂商已存在!");
			return returnObject;
		}
		holder.setToken(MD5Utils.encoderByMd5With32Bit(holder.getToken()));
		holder.setCreateDate(new Date());
		holder.setSourceIp(request.getRemoteAddr());
		holderService.save(holder);
		returnObject.setStatus(ReturnDatas.SUCCESS);
		logger.info("添加厂商成功");
		return returnObject;
	}
	
	/**
	 * 修改跳页
	 * @throws Exception 
	 */
	@RequestMapping(value = "/beforeUpdate")
	public String beforeUpdate(HttpServletRequest request,Model model) throws Exception{
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			Holder holder = holderService.findHolderById(id);
			model.addAttribute("holder", holder);
			return "/sinova/holder/beforeUpdateHolder";
		}
		return null;
	}
	

	/**
	* 新增/修改save操作,返回json格式数据
	*
	* @param model
	* @param holder
	* @param request
	* @param response
	* @return
	* @throws Exception
	* @author <Auto generate>
	* @version  2015-04-20 11:59:16
	*/
	@RequestMapping("/update")
	@ResponseBody
	public ReturnDatas saveorupdate(Model model, Holder holder, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		
		try {
			java.lang.String id = holder.getId();
			if (StringUtils.isBlank(id)) {
				holder.setId(null);
			}
			if (!StringUtils.isBlank(holder.getToken())) {
				holder.setToken(MD5Utils.encoderByMd5With32Bit(holder.getToken()));
			} else {
				Holder holder2 = holderService.findHolderById(id);
				holder.setToken(holder2.getToken());
			}
			holderService.saveorupdate(holder);

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
		return "/sinova/holder/holderCru";
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
				holderService.deleteById(id, Holder.class);
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
	* @version  2015-04-20 11:59:16
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
			holderService.deleteByIds(ids, Holder.class);
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
			info = holderService.saveImportExcelFile(destFile,Holder.class);
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
	
}
