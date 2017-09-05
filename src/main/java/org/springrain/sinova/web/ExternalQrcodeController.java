package org.springrain.sinova.web;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springrain.frame.common.SessionUser;
import org.springrain.frame.controller.BaseController;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.MessageUtils;
import org.springrain.frame.util.Page;
import org.springrain.frame.util.ReturnDatas;
import org.springrain.sinova.dto.BusinessGoodsDTO;
import org.springrain.sinova.dto.ExternalQrcodeDTO;
import org.springrain.sinova.dto.UserBusinessDTO;
import org.springrain.sinova.entity.ExternalQrcode;
import org.springrain.sinova.entity.Qrcode;
import org.springrain.sinova.service.IExternalQrcodeService;
import org.springrain.sinova.service.IQrcodeService;
import org.springrain.sinova.util.Const;
import org.springrain.sinova.util.Util;
import org.springrain.system.entity.UserRole;
import org.springrain.system.service.IUserRoleService;
import org.springrain.system.util.PropertyFileCache;


@Controller
@RequestMapping(value = "/exQrcode")
public class ExternalQrcodeController extends BaseController {

	@Resource
	private IExternalQrcodeService exQrcodeService;
	@Resource
	private IQrcodeService qrcodeService;
	@Resource
    private IUserRoleService userRoleService;
	

	private PropertyFileCache pfCache = PropertyFileCache.getInstance();

	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model,
			ExternalQrcode exQrcode) throws Exception {
		ReturnDatas returnObject = listjson(request, model, exQrcode);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/sinova/exQrcode/qrcodeOtherList";
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
	public ReturnDatas listjson(HttpServletRequest request, Model model,
			ExternalQrcode exQrcode) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();

		Page page = newPage(request);

		if("admin".equals(SessionUser.getShiroUser().getAccount())){
			logger.info("*******admin************");
		}else{
			String workno = SessionUser.getShiroUser().getWorkno();
			exQrcode.setBelongUser(workno);
			logger.info("*************belongUser********"+workno);
		}
		

		List<ExternalQrcode> datas = exQrcodeService.findExternalQrcode(
				exQrcode, page);
		model.addAttribute("exQrcode", exQrcode);
		returnObject.setQueryBean(exQrcode);
		returnObject.setPage(page);
		returnObject.setData(datas);
		return returnObject;
	}

	/**
	 * 删除多条记录
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @author <Auto generate>
	 * @version 2015-01-09 09:13:39
	 * @throws IOException
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public ReturnDatas delQrcodeRecords(HttpServletRequest request,
			Model model, HttpServletResponse response) throws IOException {
		String records = request.getParameter("records");
		logger.info("*********delQrcodeRecords**********"+records);
		try {
			if (StringUtils.isBlank(records)) {
				response.getOutputStream().print("1");
			}

			String[] rs = records.split(",");
			if (rs == null || rs.length < 1) {
				response.getOutputStream().print("1");
			}

			List<String> sp2 = Arrays.asList(rs);
			List<String> ids = new ArrayList<String>();
			for(int i=0; i<sp2.size(); i++){
				String spare2 = sp2.get(i);
				ExternalQrcode externalQrcode = new ExternalQrcode();
				externalQrcode.setSpare2(spare2);
				List<ExternalQrcode> exqrcode = exQrcodeService.findListDataByFinder(null, null, ExternalQrcode.class, externalQrcode);
				if(exqrcode!=null){
					for(int j=0; j<exqrcode.size(); j++){
						ids.add(exqrcode.get(j).getId());
					}
				}
				exQrcodeService.deleteByIds(ids, ExternalQrcode.class);
			}
			response.getOutputStream().print("0");
		} catch (Exception e) {
			response.getOutputStream().print("1");
		}
		response.getOutputStream().flush();
		response.getOutputStream().close();
		logger.info("*********delQrcodeRecords********success**");
		return null;
	}

	/**
	 * 修改跳页
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/beforeUpdate")
	public String beforeUpdate(HttpServletRequest request, Model model)
			throws Exception {
		String spare2 = request.getParameter("id");
		logger.info("---------exqrcode.spare2----" + spare2);
		if (StringUtils.isNotBlank(spare2)) {
			ExternalQrcode externalQrcode = new ExternalQrcode();
			externalQrcode.setSpare2(spare2);
			List<ExternalQrcode> exqrcode = exQrcodeService.findListDataByFinder(null, null, ExternalQrcode.class, externalQrcode);
			model.addAttribute("spare2", exqrcode.get(0).getSpare2());
			model.addAttribute("exName", exqrcode.get(0).getExName());
			return "/sinova/exQrcode/UpdateOtherQrcode";
		}
		return null;
	}

	/**
	 * 修改save操作,返回json格式数据
	 * 
	 * @param model
	 * @param qrcode
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author <Auto generate>
	 * @version 2015-01-09 09:13:39
	 */
	@RequestMapping("/update")
	@ResponseBody
	public ReturnDatas saveorupdate(Model model, ExternalQrcode exQrcode,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		
		try {
			String spare2 = exQrcode.getId();
			ExternalQrcode externalQrcode = new ExternalQrcode();
			externalQrcode.setSpare2(spare2);
			List<ExternalQrcode> exqrcode = exQrcodeService.findListDataByFinder(null, null, ExternalQrcode.class, externalQrcode);
			
			externalQrcode.setExName(exQrcode.getExName());
			externalQrcode.setSpare2("");
			List<ExternalQrcode> exqrcodedto = exQrcodeService.findListDataByFinder(null, null, ExternalQrcode.class, externalQrcode);
			if(exqrcodedto.size()>0 ){
				if(!exqrcodedto.get(0).getSpare2().equals(spare2)){
					response.getOutputStream().print("1");
					response.getOutputStream().flush();
					response.getOutputStream().close();
					return returnObject;
				}
			}
			
			if(exqrcode!=null || exqrcode.size()>0){
				for(int i=0; i<exqrcode.size(); i++){
					exqrcode.get(i).setExName(exQrcode.getExName());
					exQrcodeService.saveorupdate(exqrcode.get(i));
				}
			}

			response.getOutputStream().print("0");
			logger.info("============修改成功==============");

		} catch (Exception e) {
			String errorMessage = e.getLocalizedMessage();
			logger.error(errorMessage);
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage(MessageUtils.UPDATE_ERROR);
		}
		response.getOutputStream().flush();
		response.getOutputStream().close();
		return returnObject;
	}

	/**
	 * 导出w格式的数据
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param qrcode
	 * @throws Exception
	 */
	@RequestMapping("/list/export")
	public ReturnDatas listexport(HttpServletRequest request,
			HttpServletResponse response, Model model, ExternalQrcode exQrcode)
			throws Exception {
		logger.info("导出外部链接二维码开始");
		String path = pfCache.get(Const.SYSTEM_FILE, "expPath");
		String sysPath = request.getServletContext().getRealPath("/");
		String fileName = path + Util.getSendTime() + GlobalStatic.excelext;

		String workno = SessionUser.getShiroUser().getWorkno();
		String type = request.getParameter("type");
		Finder finder = new Finder();
		finder.append(" select distinct spare2 as spare2,ex_name as exName, belong_user as belongUser, image_url as imageUrl,  ")
		.append(" image_name as imageName ,create_time as createTime from t_externalqrcode ");
		
		if ("admin".equals(SessionUser.getShiroUser().getAccount())) {
			finder.append("  where 1=1 ");
		} else {
			finder.append("  where belong_user =:belongUser ");
			finder.setParam("belongUser", workno);
		}
		logger.info("daochuleixing=====" + type);
		if (StringUtils.isNotBlank(type) && "PART".equals(type)) {
			String records = request.getParameter("records");
			String[] rs = records.split(",");
			List<String> ids = Arrays.asList(rs);
			finder.append(" and spare2 in (:ids) ");
			finder.setParam("ids", ids);
		}else{
			if(StringUtils.isNotBlank(exQrcode.getExName())){
				finder.append(" and ex_name like:exName ");
				finder.setParam("exName", "%"+exQrcode.getExName()+"%");
			}
		}
		
		finder.append(" order by create_time desc");

		List<ExternalQrcode> datas = exQrcodeService.queryForList(finder, ExternalQrcode.class);

		try {

			WritableWorkbook book = Workbook.createWorkbook(new File(sysPath + fileName));
			WritableSheet sheet = book.createSheet("外部链接二维码", 0);
			sheet.addCell(new Label(0, 0, "二维码名称"));
			sheet.addCell(new Label(1, 0, "归属人"));
			sheet.addCell(new Label(2, 0, "创建时间"));
			sheet.addCell(new Label(3, 0, "二维码"));

			if (datas != null && !datas.isEmpty()) {
				for (int i = 0; i < datas.size(); i++) {

					sheet.setRowView(i + i + 1, 2000);
					sheet.setColumnView(3, 20);
					sheet.addCell(new jxl.write.Label(0, i + i + 1, datas
							.get(i).getExName()));
					sheet.addCell(new Label(1, i + i + 1, datas.get(i)
							.getBelongUser()));
					DateFormat format = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					String str = new String();
					str = format.format(datas.get(i).getCreateTime());
					sheet.addCell(new jxl.write.Label(2, i + i + 1, str));
					WritableImage image = new WritableImage(3, i + i + 1, 1, 1,
							new File(sysPath + datas.get(i).getImageUrl()));
					sheet.addImage(image);
				}
			}
			book.write();
			book.close();

		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			return null;
		}

		downFile(response, sysPath + fileName, true, true);
		logger.info("导出外部链接二维码结束");
		return null;
	}

	/**
	 * 二维码列表入口
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/enterQrcode")
	public String enter(HttpServletRequest request, Model model, Qrcode qrcode)
			throws Exception {
		ReturnDatas returnObject = enterjson(request, model, qrcode);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/sinova/exQrcode/enterQrcode";
	}

	@RequestMapping("/enterQrcode/json")
	@ResponseBody
	public ReturnDatas enterjson(HttpServletRequest request, Model model,
			Qrcode qrcode) throws Exception {
		logger.info("------------二维码列表入口------------------");
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		
		String account = SessionUser.getShiroUser().getAccount();
		if("admin".equals(account)){
			logger.info("admin没有专属二维码！");
			model.addAttribute("iconName", "admin");
			return returnObject;
		}
		//判断营业员是否有了自己的专属二维码
		String belongWorkNo = SessionUser.getShiroUser().getWorkno();
		qrcode.setBelongUser(belongWorkNo);
		qrcode.setType("1");
		List<Qrcode> qdatas = qrcodeService.findListDataByFinder(null, null, Qrcode.class, qrcode);
		if (qdatas.size() > 0) {
			logger.info("该营业员已经有了自己的专属二维码！");
			model.addAttribute("iconName", qdatas.get(0).getIconName());
			return returnObject;
		}else{
			logger.info("没有专属二维码！");
			model.addAttribute("iconName", "admin");
		}

		return returnObject;
	}

	/**
	 * 根据tree数据转为UserBusinessDTO, 返回UserBusinessDTO格式数据
	 * 
	 * @param business
	 * @param workNo
	 * @throws Exception
	 * @author <yyb>
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public UserBusinessDTO userBusiness(String business, String workNo) {
		UserBusinessDTO businessDto = new UserBusinessDTO();
		businessDto.setUserCode(workNo);
		String[] businessTemp = business.split(",");
		List list = new ArrayList();
		BusinessGoodsDTO goodstemp = null;
		String flag = "";
		List<String> listTemp = null;
		for (int i = 0; i < businessTemp.length; i++) {
			String busTemp = businessTemp[i];
			if (StringUtils.isNotBlank(busTemp)) {

				String id = busTemp.substring(0, busTemp.indexOf(":"));
				String pid = busTemp.substring(busTemp.indexOf(":") + 1);
				if (!"".equals(flag) && !id.equals(flag)
						&& StringUtils.isBlank(pid)) {
					goodstemp.setGoodsList(listTemp);
					list.add(goodstemp);
				}

				if (StringUtils.isNotBlank(id) && StringUtils.isBlank(pid)) {
					goodstemp = new BusinessGoodsDTO();
					goodstemp.setBusinessCode(id);
					listTemp = new ArrayList();
					flag = id;
				}
				if ((StringUtils.isNotEmpty(flag) && StringUtils
						.isNotEmpty(pid)) || flag.equals(pid)) {
					listTemp.add(id);
				}

			}
		}
		if (businessTemp.length > 0) {
			goodstemp.setGoodsList(listTemp);
			list.add(goodstemp);
		}
		businessDto.setBusGoodsList(list);
		return businessDto;
	}
	/**
	 * 批量外链二维码报表
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/report")
	public String report(HttpServletRequest request, Model model, ExternalQrcodeDTO exQrcode)throws Exception {
		ReturnDatas returnObject = listjson(request, model, exQrcode);
		Page page = newPage(request);
		String workno = SessionUser.getShiroUser().getWorkno();
		String userId = SessionUser.getShiroUser().getId();
		Finder finders = new Finder("select * from ").append(Finder.getTableName(UserRole.class)).append(" where user_id =:userId");
		finders.setParam("userId", userId);
		//查询当前用户的角色
		List<UserRole> dataz = userRoleService.queryForList(finders, UserRole.class);
		if(dataz!=null && dataz.size()>0){
			UserRole role = dataz.get(0);
			String roleId = role.getRoleId();
			if("admin".equals(roleId)){
				exQrcode.setBelongUser(roleId);
			}else{
				exQrcode.setBelongUser(workno);
			}
		}	
		List<ExternalQrcodeDTO> exQrcodeDto = exQrcodeService.findByUser(exQrcode, page);
		returnObject.setData(exQrcodeDto);
		returnObject.setPage(page);
		returnObject.setQueryBean(exQrcode);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/sinova/exQrcode/exQrcodeReport";
	}
	/**
	 * 批量外链二维码wap列表
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/forUrl")
	public String forUrl(HttpServletRequest request, Model model)throws Exception {
		logger.info("*****************批量外链二维码wap列表*************** start******************");
		String flowNo  = request.getParameter("param");
		logger.info("*************flowNo*************"+flowNo);
		Finder finder = new Finder();
		finder.append(" select * from ");
		finder.append(Finder.getTableName(ExternalQrcode.class));
		finder.append(" where state='1' and spare2 =:spare2 order by spare1  ");
		finder.setParam("spare2", flowNo);
		List<ExternalQrcode> datas =  exQrcodeService.queryForList(finder, ExternalQrcode.class);
		if(datas!=null && datas.size()>0){
			String qrcodeName = datas.get(0).getExName();
			model.addAttribute("qrcodeName", qrcodeName);
		}else{
			logger.info("******二维码不存在*********");
			return "/sinova/exQrcode/errorWap";
		}
		model.addAttribute("datas", datas);
		logger.info("*****************批量外链二维码wap列表*************** end******************");
		return "/sinova/exQrcode/exQrcodeWap";
	}
	
	/**
	 * 链接点击量
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/count")
	@ResponseBody
	public void count(HttpServletRequest request, Model model, HttpServletResponse response, ExternalQrcode externalQrcode)throws Exception {
		logger.info("*****************链接点击量*************** start******************");
		String id = request.getParameter("id");
		logger.info("*************id*************"+id);
		Finder finder = new Finder();
		finder.append(" select * from ");
		finder.append(Finder.getTableName(ExternalQrcode.class));
		finder.append(" where id=:id  ");
		finder.setParam("id", id);
		ExternalQrcode exQrcode =  exQrcodeService.queryForObject(finder, ExternalQrcode.class);
		try {
			
			int count = Integer.parseInt(exQrcode.getSpare3());
			logger.info("**********链接点击量*****第   "+count+"  次点击*******");
			exQrcode.setSpare3(String.valueOf(count+1));
			
		} catch (Exception e) {
			logger.info("**********链接点击量*****第一次点击*******");
			exQrcode.setSpare3(String.valueOf(1));
		}
		exQrcodeService.saveorupdate(exQrcode);
		logger.info("*****************链接点击量*************** end******************");
	}
}














