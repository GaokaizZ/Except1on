package org.springrain.sinova.web;

import java.io.File;
import java.io.IOException;
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
import org.springrain.frame.controller.BaseController;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.MessageUtils;
import org.springrain.frame.util.Page;
import org.springrain.frame.util.ReturnDatas;
import org.springrain.sinova.entity.Brand;
import org.springrain.sinova.service.IBrandService;

/**
 * TODO 在此加入类描述
 * 
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version 2015-01-09 08:57:50
 * @see org.springrain.sinova.web.Brand
 */
@Controller
@RequestMapping(value = "/brand")
public class BrandController extends BaseController {
	@Resource
	private IBrandService brandService;

	private String listurl = "/sinova/brand/brandList";

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
	public String list(HttpServletRequest request, Model model, Brand brand) throws Exception {
		ReturnDatas returnObject = listjson(request, model, brand);
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
	public ReturnDatas listjson(HttpServletRequest request, Model model, Brand brand) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();

		Page page = newPage(request);

		List<Brand> datas = brandService.findListDataByFinder(null, page, Brand.class, brand);
		returnObject.setQueryBean(brand);
		returnObject.setPage(page);
		returnObject.setData(datas);
		return returnObject;
	}

	/**
	 * 
	 * @description 进入新增页面 <br/>
	 * @date 2015年1月20日 下午5:17:42 <br/>
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/create/pre")
	public String createpre(HttpServletRequest request, Model model) {
		return "/sinova/brand/brandCreate";
	}

	/**
	 * 
	 * @description 新增保存操作，返回json数据 <br/>
	 * @date 2015年1月20日 下午5:45:24 <br/>
	 * @param request
	 * @param model
	 * @param brand
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/create")
	@ResponseBody
	public ReturnDatas create(HttpServletRequest request, Model model, Brand brand) {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.ADD_SUCCESS);

		try {
			brand.setId(null);
			brandService.save(brand);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage(MessageUtils.ADD_ERROR);
		}
		return returnObject;
	}

	/**
	 * 
	 * @description 进入修改页面 <br/>
	 * @date 2015年1月20日 下午5:43:53 <br/>
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update/pre")
	public String updatepre(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		ReturnDatas returnObject = lookjson(request, response, model);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/sinova/brand/brandUpdate";
	}

	/**
	 * 
	 * @description 修改保存操作，返回json数据 <br/>
	 * @date 2015年1月20日 下午5:45:59 <br/>
	 * @param model
	 * @param brand
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/update")
	@ResponseBody
	public ReturnDatas update(HttpServletRequest request, HttpServletResponse response, Model model, Brand brand) {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);

		try {
			brandService.update(brand);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage(MessageUtils.UPDATE_ERROR);
		}
		return returnObject;
	}

	/**
	 * 
	 * @description 删除操作 <br/>
	 * @date 2015年1月20日 下午5:49:17 <br/>
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public ReturnDatas delete(HttpServletRequest request) {
		// 执行删除
		try {
			java.lang.String id = request.getParameter("id");
			if (StringUtils.isNotBlank(id)) {
				brandService.deleteById(id, Brand.class);
				return new ReturnDatas(ReturnDatas.SUCCESS, MessageUtils.DELETE_SUCCESS);

			} else {
				return new ReturnDatas(ReturnDatas.WARNING, MessageUtils.DELETE_WARNING);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ReturnDatas(ReturnDatas.ERROR, MessageUtils.DELETE_ERROR);
	}

	/**
	 * 
	 * @description 删除多条记录 <br/>
	 * @date 2015年1月20日 下午5:51:53 <br/>
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/delete/more")
	@ResponseBody
	public ReturnDatas deletemore(HttpServletRequest request, Model model) {
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
			brandService.deleteByIds(ids, Brand.class);
		} catch (Exception e) {
			return new ReturnDatas(ReturnDatas.ERROR, MessageUtils.DELETE_ALL_ERROR);
		}
		return new ReturnDatas(ReturnDatas.SUCCESS, MessageUtils.DELETE_ALL_SUCCESS);
	}

	/**
	 * 
	 * @description 查看对象明细 <br/>
	 * @date 2015年1月20日 下午5:13:45 <br/>
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/look")
	public String look(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		ReturnDatas returnObject = lookjson(request, response, model);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/sinova/brand/brandLook";
	}

	/**
	 * 
	 * @description 查看对象明细，返回json数据 <br/>
	 * @date 2015年1月20日 下午5:14:06 <br/>
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/look/json")
	@ResponseBody
	public ReturnDatas lookjson(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		java.lang.String id = request.getParameter("id");
		if (StringUtils.isNotBlank(id)) {
			Brand brand = brandService.findBrandById(id);
			returnObject.setData(brand);
		} else {
			returnObject.setStatus(ReturnDatas.ERROR);
		}
		return returnObject;
	}

	/**
	 * 
	 * @description 导出Excle格式的数据 <br/>
	 * @date 2015年1月20日 下午5:54:36 <br/>
	 * @param request
	 * @param response
	 * @param model
	 * @param brand
	 * @throws Exception
	 */
	@RequestMapping("/list/export")
	public void listexport(HttpServletRequest request, HttpServletResponse response, Model model, Brand brand) throws Exception {
		Page page = newPage(request);

		File file = brandService.findDataExportExcel(null, listurl, page, Brand.class, brand);
		String fileName = "brand" + GlobalStatic.excelext;
	//	downFile(response, file, fileName, true);
		return;
	}

	/**
	 * 
	 * @description 上传文件 <br/>
	 * @date 2015年1月20日 下午5:55:09 <br/>
	 * @param uploadFile
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/upload")
	@ResponseBody
	public Map<String, String> upload(@RequestParam MultipartFile uploadFile, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
			info = brandService.saveImportExcelFile(destFile, Brand.class);
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

}
