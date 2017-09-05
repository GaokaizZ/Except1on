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
import org.springrain.frame.util.ReturnDatas;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.MessageUtils;
import org.springrain.frame.util.Page;
import org.springrain.frame.util.SecUtils;

import org.springrain.sinova.entity.QrcodeBusiness;
import org.springrain.sinova.service.IQrcodeBusinessService;


/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-01-09 09:15:29
 * @see org.springrain.sinova.web.QrcodeBusiness
 */
@Controller
@RequestMapping(value = "/qrcodebusiness")
public class QrcodeBusinessController extends BaseController {
	@Resource
	private IQrcodeBusinessService qrcodeBusinessService;

	private String listurl = "/sinova/qrcodebusiness/qrcodebusinessList";

	/**
	* 进入qrcodebusiness Web页面后直接展现第一页数据,调用listjson方法,保证和app端数据统一
	*
	* @param request
	* @param model
	* @param qrcodeBusiness
	* @return
	* @author <Auto generate>
	* @version  2015-01-09 09:15:29
	*/
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model, QrcodeBusiness qrcodeBusiness) throws Exception {
		ReturnDatas returnObject = listjson(request, model, qrcodeBusiness);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return listurl;
	}
	
	/**
	* json数据,为APP提供数据
	*
	* @param request
	* @param model
	* @param qrcodeBusiness
	* @return
	* @author <Auto generate>
	* @version  2015-01-09 09:15:29
	*/
	@RequestMapping("/list/json")
	@ResponseBody
	public ReturnDatas listjson(HttpServletRequest request, Model model, QrcodeBusiness qrcodeBusiness) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		
		Page page = newPage(request);
		
		List<QrcodeBusiness> datas = qrcodeBusinessService.findListDataByFinder(null, page, QrcodeBusiness.class, qrcodeBusiness);
		returnObject.setQueryBean(qrcodeBusiness);
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
	 * @param qrcodeBusiness
	 * @throws Exception
	 */
	@RequestMapping("/list/export")
	public void listexport(HttpServletRequest request, HttpServletResponse response, Model model, QrcodeBusiness qrcodeBusiness) throws Exception {
		// ==构造分页请求
		Page page = newPage(request);

		File file = qrcodeBusinessService.findDataExportExcel(null, listurl, page, QrcodeBusiness.class, qrcodeBusiness);
		String fileName = "qrcodeBusiness" + GlobalStatic.excelext;
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
		 return "/sinova/qrcodebusiness/qrcodebusinessLook";
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
 			QrcodeBusiness qrcodeBusiness = qrcodeBusinessService.findQrcodeBusinessById(id);
 			returnObject.setData(qrcodeBusiness);
 		} else {
			returnObject.setStatus(ReturnDatas.ERROR);
		}
    	return returnObject;
    }

	/**
	* 新增/修改save操作,返回json格式数据
	*
	* @param model
	* @param qrcodeBusiness
	* @param request
	* @param response
	* @return
	* @throws Exception
	* @author <Auto generate>
	* @version  2015-01-09 09:15:29
	*/
	@RequestMapping("/update")
	@ResponseBody
	public ReturnDatas saveorupdate(Model model, QrcodeBusiness qrcodeBusiness, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		
		try {
			java.lang.String id = qrcodeBusiness.getId();
			if (StringUtils.isBlank(id)) {
				qrcodeBusiness.setId(null);
			}
			qrcodeBusinessService.saveorupdate(qrcodeBusiness);

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
		return "/sinova/qrcodebusiness/qrcodebusinessCru";
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
				qrcodeBusinessService.deleteById(id, QrcodeBusiness.class);
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
	* @version  2015-01-09 09:15:29
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
			qrcodeBusinessService.deleteByIds(ids, QrcodeBusiness.class);
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
			info = qrcodeBusinessService.saveImportExcelFile(destFile,QrcodeBusiness.class);
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
