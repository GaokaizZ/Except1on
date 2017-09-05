package  org.springrain.sinova.web;
import java.io.File;
import java.io.IOException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
import org.springrain.frame.common.SessionUser;
import org.springrain.frame.controller.BaseController;
import org.springrain.frame.util.ReturnDatas;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.MessageUtils;
import org.springrain.frame.util.Page;
import org.springrain.frame.util.SecUtils;

import org.springrain.sinova.dto.FrontListDTO;
import org.springrain.sinova.dto.UserOfficeDTO;
import org.springrain.sinova.entity.AdImage;
import org.springrain.sinova.service.IAdImageService;
import org.springrain.sinova.service.IUserOfficeService;
import org.springrain.system.entity.DicData;
import org.springrain.system.entity.User;
import org.springrain.system.service.IDicDataService;
import org.springrain.system.service.IUserService;


/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-05-05 10:37:53
 * @see org.springrain.sinova.web.AdImage
 */
@Controller
@RequestMapping(value = "/adimage")
public class AdImageController extends BaseController {
	@Resource
	private IAdImageService adImageService;
	@Resource
	private IUserOfficeService userOfficeService;
	@Resource
	private IUserService userService;
	@Resource
	private IDicDataService dicDataService;
	

	private String listurl = "/sinova/adimage/adimageList";
	private String manageurl = "/sinova/adimage/adimageManage";
	private String weidianurl = "/sinova/frontqrcode/weidian";

	/**
	* 进入adimage Web页面后直接展现第一页数据,调用listjson方法,保证和app端数据统一
	*
	* @param request
	* @param model
	* @param adImage
	* @return
	* @author <Auto generate>
	* @version  2015-05-05 10:37:53
	*/
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model, AdImage adImage) throws Exception {
		ReturnDatas returnObject = listjson(request, model, adImage);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return listurl;
	}
	
	/**
	* json数据,为APP提供数据
	*
	* @param request
	* @param model
	* @param adImage
	* @return
	* @author <Auto generate>
	* @version  2015-05-05 10:37:53
	*/
	@RequestMapping("/list/json")
	@ResponseBody
	public ReturnDatas listjson(HttpServletRequest request, Model model, AdImage adImage) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		
		Page page = newPage(request);
		
		List<AdImage> datas = adImageService.findListDataByFinder(null, page, AdImage.class, adImage);
		returnObject.setQueryBean(adImage);
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
	 * @param adImage
	 * @throws Exception
	 */
	@RequestMapping("/list/export")
	public void listexport(HttpServletRequest request, HttpServletResponse response, Model model, AdImage adImage) throws Exception {
		// ==构造分页请求
		Page page = newPage(request);

		File file = adImageService.findDataExportExcel(null, listurl, page, AdImage.class, adImage);
		String fileName = "adImage" + GlobalStatic.excelext;
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
		 return "/sinova/adimage/adimageLook";
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
 			AdImage adImage = adImageService.findAdImageById(id);
 			returnObject.setData(adImage);
 		} else {
			returnObject.setStatus(ReturnDatas.ERROR);
		}
    	return returnObject;
    }

	/**
	* 新增/修改save操作,返回json格式数据
	*
	* @param model
	* @param adImage
	* @param request
	* @param response
	* @return
	* @throws Exception
	* @author <Auto generate>
	* @version  2015-05-05 10:37:53
	*/
	@RequestMapping(value="/update",produces="text/html;charset=UTF-8")
	@ResponseBody
	public ReturnDatas saveorupdate(@RequestParam(required=false) MultipartFile uploadFile,Model model, AdImage adImage, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("开始添加广告位");
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		
		try {
			java.lang.String id = adImage.getId();
			boolean hasImage = false;
			if (uploadFile!=null) {
				hasImage = (uploadFile.getSize()>0);
			}
			String newName = "";
			String userId = SessionUser.getUserId();
	        AdImage adImage2 = adImageService.findAdImageByUserId(userId);
			
			if (StringUtils.isBlank(id) && adImage2==null) {
				adImage.setId(null);
				adImage.setCreateTime(new Date());
				id = (String) adImageService.save(adImage);
				if (hasImage) {
					adImage = adImageService.findAdImageById(id);
					String fileName = uploadFile.getOriginalFilename();
					String extensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
					newName = id + "." + extensionName;
					adImage.setImage(newName);
				}
			}
			
			if (hasImage) {
				String uploadDirPath = request.getSession().getServletContext().getRealPath("/upload");
				File dir = new File(uploadDirPath);
				if (dir.exists() == false) {
					dir.mkdirs();
				}
//				adImage = adImageService.findAdImageById(id);
				String fileName = uploadFile.getOriginalFilename();
				String extensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
				newName = id + "." + extensionName;
				File destFile = new File(uploadDirPath + "/" + newName);
				FileCopyUtils.copy(uploadFile.getBytes(), destFile);
			}
			
			adImageService.saveorupdate(adImage);
		} catch (Exception e) {
			e.printStackTrace();
			String errorMessage = e.getLocalizedMessage();
			logger.error(errorMessage);
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage(MessageUtils.UPDATE_ERROR);
		}
		return returnObject;
	}
	
	 /**
     * 跳往页面操作
     */
    @RequestMapping(value = "/manage")
    public String manageAdImage(Model model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	ReturnDatas returnObject = ReturnDatas.getErrorReturnDatas();
        // 获取当前登录人
        String userId = SessionUser.getUserId();
        if (StringUtils.isBlank(userId)) {
            logger.error("---用户信息为空---");
            returnObject.setMessage("用户信息为空！");
            model.addAttribute(GlobalStatic.returnDatas, returnObject);
            return manageurl;
        }
        AdImage adImage = adImageService.findAdImageByUserId(userId);
        model.addAttribute("adImage", adImage);
        User user = userService.findUserById(userId);
    	UserOfficeDTO userOfficeDto = userOfficeService.findUserOfficeDTOById(user.getId());
		if (null!=userOfficeDto) {
			model.addAttribute("wdName",userOfficeDto.getOfficeName()+"营业员"+user.getName()+"的店铺");
		}
        
        model.addAttribute("userId", userId);
    	return manageurl;
    }
    
    /**
     * 跳往页面操作
     */
    @RequestMapping(value = "/lookdemo")
    public String lookDemo(Model model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	String id = request.getParameter("id");
    	if (null!=id && !"".equals(id)) {
    		AdImage adImage = adImageService.findAdImageById(id);
        	model.addAttribute("adImage",adImage);
    	}
    	
    	List<DicData> dicList = dicDataService.findListDicData("bustype");
    	Comparator<Object> com = Collator.getInstance(java.util.Locale.CHINA);
    	Map<String,DicData> typeMap = new TreeMap<String,DicData>(com);
		Map<String,DicData> displayMap = new TreeMap<String,DicData>(com);
    	if (null!=dicList && dicList.size()!=0) {
			for (DicData dicData : dicList) {
				if ("1".equals(dicData.getState())) {
					displayMap.put(dicData.getName(), dicData);
				}
			}
			model.addAttribute("typeMap", typeMap);
			model.addAttribute("displayMap", displayMap);
		}
    	
    	String userId = SessionUser.getUserId();
    	User user = userService.findUserById(userId);
    	UserOfficeDTO userOfficeDto = userOfficeService.findUserOfficeDTOById(user.getId());
		if (null!=userOfficeDto) {
			model.addAttribute("wdName",userOfficeDto.getOfficeName()+"营业员"+user.getName()+"的店铺");
		}
    	
    	model.addAttribute("hashd",false);
		model.addAttribute("hastc",false);
		model.addAttribute("random", Math.random());
    	return weidianurl;
    }

	/**
	 * 进入修改页面,APP端可以调用 lookjson 获取json格式数据
	 */
	@RequestMapping(value = "/update/pre")
	public String edit(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = lookjson(model, request, response);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/sinova/adimage/adimageCru";
	}

	/**
	 * 删除操作
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public ReturnDatas destroy(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 执行删除
		try {
			java.lang.String id = request.getParameter("id");
			if (StringUtils.isNotBlank(id)) {
				AdImage adImage = adImageService.findAdImageById(id);
				String image = adImage.getImage();
				adImageService.deleteById(id, AdImage.class);
				
				String uploadDirPath = request.getSession().getServletContext().getRealPath("/upload");
				File destFile = new File(uploadDirPath + "/" + image);
				destFile.delete();
				response.getOutputStream().print("0");
			} else {
				response.getOutputStream().print("1");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			response.getOutputStream().print("1");
		}
		response.getOutputStream().flush();
		response.getOutputStream().close();
        return null;
	}

	/**
	* 删除多条记录
	*
	* @param request
	* @param model
	* @return
	* @author <Auto generate>
	* @version  2015-05-05 10:37:53
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
			adImageService.deleteByIds(ids, AdImage.class);
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
			info = adImageService.saveImportExcelFile(destFile,AdImage.class);
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
