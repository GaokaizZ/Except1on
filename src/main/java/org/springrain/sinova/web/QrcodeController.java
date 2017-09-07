package org.springrain.sinova.web;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
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
import org.springrain.sinova.dto.BusinessGoodsDTO;
import org.springrain.sinova.dto.TreeBusinessGoodsDTO;
import org.springrain.sinova.dto.UserBusinessDTO;
import org.springrain.sinova.dto.UserOfficeDTO;
import org.springrain.sinova.entity.Business;
import org.springrain.sinova.entity.ExternalQrcode;
import org.springrain.sinova.entity.Goods;
import org.springrain.sinova.entity.Office;
import org.springrain.sinova.entity.Qrcode;
import org.springrain.sinova.entity.UserOffice;
import org.springrain.sinova.service.IBusinessService;
import org.springrain.sinova.service.IExternalQrcodeService;
import org.springrain.sinova.service.IOfficeService;
import org.springrain.sinova.service.IQrcodeService;
import org.springrain.sinova.service.IUserOfficeService;
import org.springrain.sinova.util.Const;
import org.springrain.sinova.util.QrcodeUtils;
import org.springrain.sinova.util.Util;
import org.springrain.system.entity.Role;
import org.springrain.system.entity.User;
import org.springrain.system.entity.UserRole;
import org.springrain.system.service.IRoleService;
import org.springrain.system.service.IUserRoleService;
import org.springrain.system.service.IUserService;
import org.springrain.system.util.Constant;
import org.springrain.system.util.PropertyFileCache;

import com.alibaba.fastjson.JSON;

/**
 * 二维码模块
 * 
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version 2015-01-09 09:13:39
 * @see org.springrain.sinova.web.Qrcode
 */
@Controller
@RequestMapping(value = "/qrcode")
public class QrcodeController extends BaseController {
	@Resource
	private IQrcodeService qrcodeService;

	@Resource
	private IUserOfficeService userOfficeService;

	@Resource
	private IOfficeService officeService;

	@Resource
	private IBusinessService businessService;

	@Resource
	private IUserRoleService userRoleService;

	@Resource
	private IUserService userService;
	@Resource
	private IRoleService roleService;

	@Resource
	private IExternalQrcodeService externalQrcodeService;


	
	private String listurl = "/sinova/qrcode/qrcodeList";
	private String qrcodeurl = "/sinova/qrcode/qrcodeAdd";

	private PropertyFileCache pfCache = PropertyFileCache.getInstance();

	/**
	 * 进入qrcode Web页面后直接展现第一页数据,调用listjson方法,保证和app端数据统一
	 * 
	 * @param request
	 * @param model
	 * @param qrcode
	 * @return
	 * @author <Auto generate>
	 * @version 2015-01-09 09:13:39
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model, User user,
			Qrcode qrcode) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Page page = newPage(request);
		String qrcodeName = request.getParameter("qrcodeName");
		String belongUser = request.getParameter("belongUser");
		String createDate = request.getParameter("createDate");
		String state = request.getParameter("state");
		model.addAttribute("qrcodeName", qrcodeName);
		model.addAttribute("belongUser", belongUser);
		model.addAttribute("createDate", createDate);
		// 获取当前登录用户的Id
		String userId = SessionUser.getShiroUser().getId();
		Finder finders = new Finder("select * from ").append(
				Finder.getTableName(UserRole.class)).append(
				" where user_id =:userId");
		finders.setParam("userId", userId);
		// 查询当前用户的角色
		List<UserRole> dataz = userRoleService.queryForList(finders,
				UserRole.class);
		for (int i = 0; i < dataz.size(); i++) {
			UserRole role = dataz.get(i);
			String roleId = role.getRoleId();
		//	Role roleDto = roleService.findRoleById(roleId);
			// 超级管理员
			if ("admin".equals(roleId)) {
				Finder findera = Finder.getSelectFinder(Qrcode.class);
				findera.append("where 1 = 1 ");
				if (StringUtils.isNotBlank(qrcodeName)) {
					findera.append(" and qrcode_name like :qrcodeName")
							.setParam("qrcodeName", "%" + qrcodeName + "%");
				}
				if (StringUtils.isNotBlank(belongUser)) {
					findera.append(" and belong_user = :belongUser").setParam(
							"belongUser", belongUser);
				}
				if (StringUtils.isNotBlank(state)) {
					findera.append(" and state =:state ").setParam("state",
							state);
				}

				if (StringUtils.isNotBlank(createDate)) {
					findera.append(
							" and create_date like to_date(:createDate , 'yyyy-MM-dd')")
							.setParam("createDate", createDate);
				}
				if (StringUtils.isNotBlank(qrcode.getType())) {
					findera.append(" and type =:type ").setParam("type",
							qrcode.getType());
				}
				findera.append(" order by create_date desc");
				List<Qrcode> datas = qrcodeService.queryForList(findera,
						Qrcode.class, page);
				returnObject.setQueryBean(qrcode);
				returnObject.setPage(page);
				returnObject.setData(datas);
				model.addAttribute(GlobalStatic.returnDatas, returnObject);
				return "/sinova/qrcode/qrcodeList";
			} else {
				// 先根据当前用户登录，查询当前用户下地市区县营业厅情况
				UserOfficeDTO userOfficeDTO = SessionUser.getShiroUser().getUserOfficeDTO();
				if (userOfficeDTO == null) {
					logger.warn("当前用户地市区县信息为空，返回空二维码列表");
					model.addAttribute(GlobalStatic.returnDatas, returnObject);
					return listurl;
				} else {
					
					if (StringUtils.equals(Constant.ROLE_REGION_MANAGER, SessionUser.getShiroUser().getRole().getCode())) {
						// 地市管理员
						Finder finder = new Finder("select * from ").append(Finder.getTableName(Qrcode.class)).append(" q where q.belong_user in ( select distinct u.workno from ").append(
								Finder.getTableName(User.class)).append(" u, ").append(Finder.getTableName(UserOffice.class)).append(
								" uo where u.id=uo.user_id and uo.region_code in ( select distinct uo.region_code from ").append(Finder.getTableName(User.class)).append(" u, ").append(
								Finder.getTableName(UserOffice.class)).append(" uo where u.id = uo.user_id ");
						if (StringUtils.isNoneBlank(SessionUser.getShiroUser().getWorkno())) {
							finder.append(" and u.workno=:workno").setParam("workno", SessionUser.getShiroUser().getWorkno());
						}
						if (StringUtils.isNotBlank(qrcodeName)) {
							finder.append(" and q.qrcode_name like :qrcodeName").setParam("qrcodeName", "%" + qrcodeName + "%");
						}
						if (StringUtils.isNotBlank(belongUser)) {
							finder.append(" and q.belong_user = :belongUser").setParam("belongUser", belongUser);
						}
						if (StringUtils.isNotBlank(state)) {
							finder.append(" and q.state =:state ").setParam("state", state);
						}

						if (StringUtils.isNotBlank(createDate)) {
							finder.append(" and q.create_date like to_date(:createDate , 'yyyy-MM-dd')").setParam("createDate", createDate);
						}
						if (StringUtils.isNotBlank(qrcode.getType())) {
							finder.append(" and q.type =:type ").setParam("type", qrcode.getType());
						}
						finder.append("))");
						finder.append(" order by create_date desc");
						List<Qrcode> datas = qrcodeService.queryForList(finder, Qrcode.class, page);
						returnObject.setQueryBean(qrcode);
						returnObject.setPage(page);
						returnObject.setData(datas);
						model.addAttribute(GlobalStatic.returnDatas, returnObject);
						return "/sinova/qrcode/qrcodeList";
					}
					
					
					String[] post = userOfficeDTO.getPost();
					//if (ArrayUtils.contains(post, Const.USER_OFFICE_MANAGER)) {
					if(StringUtils.equals(Constant.ROLE_OFFICE_MANAGE, SessionUser.getShiroUser().getRole().getCode())){	
						// 营业厅管理员
						Finder finder = new Finder("select * from ").append(Finder.getTableName(Qrcode.class)).append(" q where q.belong_user in ( select distinct u.workno from ").append(
								Finder.getTableName(User.class)).append(" u, ").append(Finder.getTableName(UserOffice.class)).append(
								" uo where u.id=uo.user_id and uo.office_code in ( select distinct uo.office_code from ").append(Finder.getTableName(User.class)).append(" u, ").append(
								Finder.getTableName(UserOffice.class)).append(" uo where u.id = uo.user_id ");
						if (StringUtils.isNoneBlank(SessionUser.getShiroUser().getWorkno())) {
							finder.append(" and u.workno=:workno").setParam("workno", SessionUser.getShiroUser().getWorkno());
						}
						if (StringUtils.isNotBlank(qrcodeName)) {
							finder.append(" and q.qrcode_name like :qrcodeName").setParam("qrcodeName", "%" + qrcodeName + "%");
						}
						if (StringUtils.isNotBlank(belongUser)) {
							finder.append(" and q.belong_user = :belongUser").setParam("belongUser", belongUser);
						}
						if (StringUtils.isNotBlank(state)) {
							finder.append(" and q.state =:state ").setParam("state", state);
						}

						if (StringUtils.isNotBlank(createDate)) {
							finder.append(" and q.create_date like to_date(:createDate , 'yyyy-MM-dd')").setParam("createDate", createDate);
						}
						if (StringUtils.isNotBlank(qrcode.getType())) {
							finder.append(" and q.type =:type ").setParam("type", qrcode.getType());
						}
						finder.append("))");
						finder.append(" order by create_date desc");
						List<Qrcode> datas = qrcodeService.queryForList(finder, Qrcode.class, page);
						returnObject.setQueryBean(qrcode);
						returnObject.setPage(page);
						returnObject.setData(datas);
						model.addAttribute(GlobalStatic.returnDatas, returnObject);
						return "/sinova/qrcode/qrcodeList";
					}
					// 营业员
					//if (ArrayUtils.contains(post, Const.USER_OFFICE_ASSISTANT)) {
					if(StringUtils.equals(Constant.ROLE_OFFICE, SessionUser.getShiroUser().getRole().getCode())){
						logger.info("=====营业员岗位=========" + post);
						Finder finder = new Finder("select * from ").append(Finder.getTableName(Qrcode.class)).append(" q ");
						if (StringUtils.isNoneBlank(SessionUser.getShiroUser().getWorkno())) {
							finder.append(" where q.belong_user = :workno").setParam("workno", SessionUser.getShiroUser().getWorkno());
						}
						if (StringUtils.isNotBlank(qrcodeName)) {
							finder.append(" and q.qrcode_name like :qrcodeName").setParam("qrcodeName", "%" + qrcodeName + "%");
						}
						if (StringUtils.isNotBlank(belongUser)) {
							finder.append(" and q.belong_user = :belongUser").setParam("belongUser", belongUser);
						}
						if (StringUtils.isNotBlank(state)) {
							finder.append(" and q.state =:state ").setParam("state", state);
						}

						if (StringUtils.isNotBlank(createDate)) {
							finder.append(" and q.create_date like to_date(:createDate , 'yyyy-MM-dd')").setParam("createDate", createDate);
						}
						if (StringUtils.isNotBlank(qrcode.getType())) {
							finder.append(" and q.type =:type ").setParam("type", qrcode.getType());
						}
						finder.append(" order by create_date desc");
						List<Qrcode> datas = qrcodeService.queryForList(finder, Qrcode.class, page);
						returnObject.setQueryBean(qrcode);
						returnObject.setPage(page);
						returnObject.setData(datas);
						model.addAttribute(GlobalStatic.returnDatas, returnObject);
						return "/sinova/qrcode/qrcodeList";
					}
				}
			}
		}
		return null;
	}

	/**
	 * json数据,为APP提供数据
	 * 
	 * @param request
	 * @param model
	 * @param qrcode
	 * @return
	 * @author <Auto generate>
	 * @version 2015-01-09 09:13:39
	 */
	@RequestMapping("/list/json")
	@ResponseBody
	public ReturnDatas listjson(HttpServletRequest request, Model model,
			Qrcode qrcode) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();

		Page page = newPage(request);
		page.setOrder("CREATE_DATE");
		page.setSort("desc");

		List<Qrcode> datas = qrcodeService.findListDataByFinder(null, page,
				Qrcode.class, qrcode);
		returnObject.setQueryBean(qrcode);
		returnObject.setPage(page);
		returnObject.setData(datas);
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
			HttpServletResponse response, Model model, Qrcode qrcode)
			throws Exception {
		logger.info("导出二维码开始");
		String path = pfCache.get(Const.SYSTEM_FILE, "expPath");
		String sysPath = request.getServletContext().getRealPath("/");
		String fileName = path + Util.getSendTime() + GlobalStatic.excelext;

		String types = request.getParameter("types");
		Finder finder = new Finder();
		finder.append("select * from ")
				.append(Finder.getTableName(Qrcode.class))
				.append(" q where 1=1");
		if (StringUtils.isNotBlank(types) && "PART".equals(types)) {
			String records = request.getParameter("records");
			String[] rs = records.split(",");
			List<String> ids = Arrays.asList(rs);
			finder.append(" and q.id in (:ids)");
			finder.setParam("ids", ids);
			finder.append(" order by create_date desc");
		}

		if (StringUtils.isNotBlank(types) && "ALL".equals(types)) {
			// 获取当前登录用户的Id
			String qrcodeName = request.getParameter("qrcodeName");
			String belongUser = request.getParameter("belongUser");
			String state = request.getParameter("state");
			String createDate = request.getParameter("createDate");
			String userId = SessionUser.getShiroUser().getId();
			Finder finders = new Finder("select * from ").append(
					Finder.getTableName(UserRole.class)).append(
					" where user_id =:userId");
			finders.setParam("userId", userId);
			// 查询当前用户的角色
			List<UserRole> dataz = userRoleService.queryForList(finders,
					UserRole.class);
			for (int i = 0; i < dataz.size(); i++) {
				UserRole role = dataz.get(i);
				String roleId = role.getRoleId();
				// 超级管理员
				if ("admin".equals(roleId)) {
					// finder.append(" order by create_date desc");
				} else {
					UserOfficeDTO userOfficeDTO = SessionUser.getShiroUser()
							.getUserOfficeDTO();
					if (userOfficeDTO == null) {
						logger.warn("当前用户地市区县信息为空");
						return null;
					} else {
						String[] post = userOfficeDTO.getPost();
						if (ArrayUtils
								.contains(post, Const.USER_OFFICE_MANAGER)) {
							logger.info("=====管理员岗位=========");
							// 营业厅管理员
							finder.append(
									" and q.belong_user in ( select distinct u.workno from ")
									.append(Finder.getTableName(User.class))
									.append(" u, ")
									.append(Finder
											.getTableName(UserOffice.class))
									.append(" uo where u.id=uo.user_id and uo.office_code in ( select distinct uo.office_code from ")
									.append(Finder.getTableName(User.class))
									.append(" u, ")
									.append(Finder
											.getTableName(UserOffice.class))
									.append(" uo where u.id = uo.user_id ");

							if (StringUtils.isNoneBlank(SessionUser
									.getShiroUser().getWorkno())) {
								finder.append(" and u.workno=:workno")
										.setParam(
												"workno",
												SessionUser.getShiroUser()
														.getWorkno());
							}
							finder.append("))");
						}
						// 营业员
						if (ArrayUtils.contains(post,
								Const.USER_OFFICE_ASSISTANT)) {
							logger.info("=====营业员岗位=========");
							if (StringUtils.isNoneBlank(SessionUser
									.getShiroUser().getWorkno())) {
								finder.append(" and q.belong_user = :workno")
										.setParam(
												"workno",
												SessionUser.getShiroUser()
														.getWorkno());
							}
						}
					}
				}
				if (StringUtils.isNotBlank(qrcodeName)) {
					finder.append(" and q.qrcode_Name like :qrcodeName  ");
					finder.setParam("qrcodeName", "%" + qrcodeName + "%");
				}
				if (StringUtils.isNotBlank(belongUser)) {
					finder.append(" and q.belong_User=:belongUser  ");
					finder.setParam("belongUser", belongUser);
				}
				if (StringUtils.isNotBlank(state)) {
					finder.append(" and q.state=:state  ");
					finder.setParam("state", state);
				}
				if (StringUtils.isNotBlank(createDate)) {
					finder.append(" and q.create_date like to_date(:createDate , 'yyyy-MM-dd')");
					finder.setParam("createDate", createDate);
				}
				if (StringUtils.isNotBlank(qrcode.getType())) {
					finder.append(" and q.type =:type ").setParam("type",
							qrcode.getType());
				}
				finder.append(" order by create_date desc");
			}

		}

		WritableWorkbook book = Workbook.createWorkbook(new File(sysPath
				+ fileName));
		WritableSheet sheet = book.createSheet("二维码", 0);
		sheet.addCell(new Label(0, 0, "二维码名称"));
		sheet.addCell(new Label(1, 0, "归属人"));
		sheet.addCell(new Label(2, 0, "创建人"));
		sheet.addCell(new Label(3, 0, "创建时间"));
		sheet.addCell(new Label(4, 0, "二维码"));
		try {
			int count = 0;
			Page page = new Page();
			page.setPageSize(3000);
			List<Qrcode> datas = null;
			for (int j = 1; j <= 1000; j++) {
				page.setPageIndex(j);
				datas = qrcodeService.queryForList(finder, Qrcode.class, page);
				if (datas != null && !datas.isEmpty()) {
					int cruuNum = 0;
					for (int i = count; i < count + datas.size(); i++) {

						sheet.setRowView(i + i + 1, 2000);
						sheet.setColumnView(4, 20);
						sheet.addCell(new jxl.write.Label(0, i + i + 1, datas
								.get(cruuNum).getQrcodeName()));
						sheet.addCell(new Label(1, i + i + 1, datas
								.get(cruuNum).getBelongUser()));
						sheet.addCell(new Label(2, i + i + 1, datas
								.get(cruuNum).getCreateUser()));

						DateFormat format = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						String str = new String();
						str = format.format(datas.get(cruuNum).getCreateDate());
						sheet.addCell(new jxl.write.Label(3, i + i + 1, str));
						String imgUrl = "";
						imgUrl = "/upload/logo.png";
						if (null == datas.get(cruuNum).getIconUrl()) {

						} else {
							imgUrl = datas.get(cruuNum).getIconUrl();
						}
						WritableImage image = new WritableImage(4, i + i + 1,
								1, 1, new File(sysPath + imgUrl));
						sheet.addImage(image);
						cruuNum++;
					}
					count += datas.size();
				} else {
					break;
				}

			}
			book.write();
			book.close();

		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
		// File file = new File(sysPath,fileName);
		downFile(response, sysPath + fileName, true, true);
		logger.info("导出二维码结束");
		return null;
	}

	/**
	 * 查看操作,调用APP端lookjson方法
	 */
	@RequestMapping(value = "/look")
	public String look(Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = lookjson(model, request, response);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/sinova/qrcode/qrcodeLook";
	}

	/**
	 * 查看的Json格式数据,为APP端提供数据
	 */
	@RequestMapping(value = "/look/json")
	@ResponseBody
	public ReturnDatas lookjson(Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		java.lang.String id = request.getParameter("id");
		if (StringUtils.isNotBlank(id)) {
			Qrcode qrcode = qrcodeService.findQrcodeById(id);
			returnObject.setData(qrcode);
		} else {
			returnObject.setStatus(ReturnDatas.ERROR);
		}
		return returnObject;
	}

	/**
	 * 修改跳页
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/beforeUpdate")
	public String beforeUpdate(HttpServletRequest request, Model model)
			throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isNotBlank(id)) {
			Qrcode qrcode = qrcodeService.findQrcodeById(id);
			model.addAttribute("qrcode", qrcode);
			return "/sinova/qrcode/updateQrcode";
		}
		return null;
	}

	/**
	 * 新增/修改save操作,返回json格式数据
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
	public ReturnDatas saveorupdate(Model model, Qrcode qrcode,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);

		try {
			String state = request.getParameter("state");
			String id = request.getParameter("id");
			Qrcode qrcodes = qrcodeService.findQrcodeById(id);
			qrcodes.setState(state);

			if (StringUtils.isBlank(state)) {
				response.getOutputStream().print("1");
				logger.info("============资料不全==============");
			} else {

				qrcodeService.saveorupdate(qrcodes);
				response.getOutputStream().print("0");
				logger.info("============修改成功==============");
			}

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
	 * 进入修改页面,APP端可以调用 lookjson 获取json格式数据
	 */
	@RequestMapping(value = "/update/pre")
	public String edit(Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = lookjson(model, request, response);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/sinova/qrcode/qrcodeCru";
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
				qrcodeService.deleteById(id, Qrcode.class);
				return new ReturnDatas(ReturnDatas.SUCCESS,
						MessageUtils.DELETE_SUCCESS);
			} else {
				return new ReturnDatas(ReturnDatas.WARNING,
						MessageUtils.DELETE_WARNING);
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
	 * @version 2015-01-09 09:13:39
	 * @throws IOException
	 */
	@RequestMapping("/delete/more")
	@ResponseBody
	public ReturnDatas delMultiRecords(HttpServletRequest request, Model model,
			HttpServletResponse response) throws IOException {
		String records = request.getParameter("records");
		try {
			if (StringUtils.isBlank(records)) {
				response.getOutputStream().print("1");
			}

			String[] rs = records.split(",");
			if (rs == null || rs.length < 1) {
				response.getOutputStream().print("1");
			}

			List<String> ids = Arrays.asList(rs);
			qrcodeService.deleteByIds(ids, Qrcode.class);
			response.getOutputStream().print("0");
		} catch (Exception e) {
			response.getOutputStream().print("1");
		}
		response.getOutputStream().flush();
		response.getOutputStream().close();
		return null;
	}

	/**
	 * 上传文件
	 * 
	 * @param uploadFile
	 * @param request
	 * @param model
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/upload")
	@ResponseBody
	public Map<String, String> upload(@RequestParam MultipartFile uploadFile,
			Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String info = "";
		File destFile = null;

		try {
			String uploadDirPath = request.getSession().getServletContext()
					.getRealPath("/upload");
			File dir = new File(uploadDirPath);
			if (dir.exists() == false) {
				dir.mkdirs();
			}
			destFile = new File(uploadDirPath + "/"
					+ uploadFile.getOriginalFilename());
			FileCopyUtils.copy(uploadFile.getBytes(), destFile);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			info = "上传失败,文件处理异常.";
		}

		try {
			info = qrcodeService.saveImportExcelFile(destFile, Qrcode.class);
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

	/**
	 * 跳往页面操作
	 */
	@RequestMapping(value = "/add")
	public String beforeAddQrcode(Model model, HttpServletRequest request,
 HttpServletResponse response) throws Exception {

		ReturnDatas returnObject = ReturnDatas.getErrorReturnDatas();
		// 获取当前登录人
		String userId = SessionUser.getUserId();
		if (StringUtils.isBlank(userId)) {
			logger.error("---用户信息为空---");
			returnObject.setMessage("用户信息为空！");
			model.addAttribute(GlobalStatic.returnDatas, returnObject);
			return qrcodeurl;
		}

		// 获取用户岗位编码
		UserOfficeDTO dto = SessionUser.getShiroUser().getUserOfficeDTO();
		if (dto == null) {
			logger.warn("---你的身份不具有该功能！---");
			returnObject.setMessage("你的身份不具有该功能！");
			model.addAttribute(GlobalStatic.returnDatas, returnObject);
			return qrcodeurl;
		}
		// 获取用户营业厅是否有效
		String officeCode = SessionUser.getShiroUser().getUserOfficeDTO().getOfficeCode();
		Office office = new Office();
		if (StringUtils.isNotBlank(officeCode)) {
			office.setOfficeCode(officeCode);
			List<Office> officeData = officeService.findListDataByFinder(null, null, Office.class, office);
			String state = officeData.get(0).getState();
			if (StringUtils.isNotBlank(state)) {
				if ("0".equals(state) || state == "0") {
					logger.warn("---你的营业厅无效！---");
					returnObject.setMessage("你的营业厅无效！");
					model.addAttribute(GlobalStatic.returnDatas, returnObject);
					return qrcodeurl;
				}
			} else {
				logger.warn("---你的营业厅无效！---");
				returnObject.setMessage("你的营业厅无效！");
				model.addAttribute(GlobalStatic.returnDatas, returnObject);
				return qrcodeurl;
			}
		}
		
//		String[] post = SessionUser.getShiroUser().getUserOfficeDTO().getPost();
		// 营业厅管理员
		if(StringUtils.equals(Constant.ROLE_OFFICE_MANAGE, SessionUser.getShiroUser().getRole().getCode())){
			List<User> dates = userOfficeService.findUserByUserId(userId);
			returnObject.setStatus(ReturnDatas.SUCCESS);// 成功
			returnObject.setData(dates);
			model.addAttribute(GlobalStatic.returnDatas, returnObject);
			return qrcodeurl;
		}
		// 营业员	
		if(StringUtils.equals(Constant.ROLE_OFFICE, SessionUser.getShiroUser().getRole().getCode())){
			User userDto = new User(); 
			userDto.setId(SessionUser.getUserId());
			userDto.setName(SessionUser.getUserName());
			List<User> dates = new ArrayList<User>();
			dates.add(userDto);

			returnObject.setStatus(ReturnDatas.SUCCESS);// 成功
			returnObject.setData(dates);
			model.addAttribute(GlobalStatic.returnDatas, returnObject);
			return qrcodeurl;
		}
		logger.error("---获取用户与营业厅标志为空---");
		returnObject.setMessage("获取用户与营业厅标志为空");
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return qrcodeurl;
	}

	/**
	 * tree展示,返回json格式数据
	 * 
	 * @author <yyb>
	 */
	@RequestMapping("/look/tree")
	public @ResponseBody
	ReturnDatas listTreeJson(HttpServletRequest request, Model model,
			TreeBusinessGoodsDTO tree) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();

		String userId = request.getParameter("userId");
		if (StringUtils.isBlank(userId)) {
			logger.error("---用户信息为空---");
			return null;
		}

		List<TreeBusinessGoodsDTO> datas = businessService
				.findTreeByUserId(userId);
		List<TreeBusinessGoodsDTO> datasTemp = new ArrayList<TreeBusinessGoodsDTO>();
		String businessCode = "";
		/*
		 * 说明：根据userId获取对应的业务以及产品，并对数据分子父关系操作，数据结构类似T_MENU表的id,name,pid.
		 * 以便页面显示（ztree）用
		 */
		if (datas != null && datas.size() > 0) {
			for (int i = 0; i < datas.size(); i++) {
				TreeBusinessGoodsDTO dto = datas.get(i);
				if (!businessCode.equals(dto.getBusinessCode())) {
					TreeBusinessGoodsDTO temp = new TreeBusinessGoodsDTO();
					temp.setGoodsCode(dto.getBusinessCode());
					temp.setGoodsName(dto.getBusinessName());
					datasTemp.add(temp);
				}
				TreeBusinessGoodsDTO tempDto = new TreeBusinessGoodsDTO();
				tempDto.setBusinessCode(dto.getBusinessCode());
				tempDto.setGoodsCode(dto.getGoodsCode());
				tempDto.setGoodsName(dto.getGoodsName());
				datasTemp.add(tempDto);

				businessCode = dto.getBusinessCode();
			}
		}
		returnObject.setQueryBean(tree);
		returnObject.setData(datasTemp);
		return returnObject;
	}

	/**
	 * 二维码save操作,返回json格式数据
	 * 
	 * @param model
	 * @param qrcode
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author <yyb>
	 */
	@RequestMapping("/save")
	@ResponseBody
	public ReturnDatas save(Model model, Qrcode qrcode,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);

		List<Qrcode> qdatas = qrcodeService.findListDataByFinder(null, null,
				Qrcode.class, qrcode);
		if (qdatas.size() > 0) {
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage("二维码名称不能重复!");
			return returnObject;
		}

		try {
			// 二维码创建人(得到创建人的工号)
			String belongUserId = SessionUser.getUserId();
			User belongUser = userService.findUserById(belongUserId);
			String belongWorkNo = belongUser.getWorkno();
			// 二维码名称
			String codeName = request.getParameter("qrcodeName");
			// 归属人
			String userId = request.getParameter("userId");
			// 得到tree选中的值（业务编码以及产品编码），格式为：子级:父级,子级:父级,
			String business = request.getParameter("business");
			User user = userService.findUserById(userId);
			// 得到对应的用户工号
			String createWorkNo = user.getWorkno();
			UserBusinessDTO ubDto = null;
			if (StringUtils.isNotEmpty(business)
					&& StringUtils.isNotEmpty(createWorkNo)) {
				ubDto = userBusiness(business, createWorkNo);
			}

			String param = JSON.toJSONString(ubDto);
			// 二维码图片保存路径
			String path = pfCache.get(Const.SYSTEM_FILE, "qrcodePath");
			// 图片名称
			String fileName = Util.getUniqueSuffix() + ".png";
			// 二维码生成前面的url
			String qrcodeUrl = pfCache.get(Const.SYSTEM_FILE, "qrcodeUrl");
			String uuid = UUID.randomUUID().toString();
			// 流水号
			String flowNo = uuid.replace("-", "");
			String filePath = request.getServletContext().getRealPath("/");

			// JSONObject paramsJson = new JSONObject();
			// paramsJson.put("param", param);
			// GenerateParam gen = new GenerateParam(qrcodeUrl);
			// String url = gen.generateStringArg(paramsJson);

			// 生成二维码
			/*QrcodeUtils.encoderQRCode(qrcodeUrl + "?param=" + flowNo, filePath
					+ path + fileName, filePath + path + "logo.png");
*/
			// save Qrcode 表
			Qrcode qrcodeDto = new Qrcode();
			qrcodeDto.setId(null);
			qrcodeDto.setQrcodeName(codeName);
			qrcodeDto.setBelongUser(createWorkNo);
			qrcodeDto.setCreateUser(belongWorkNo);
			qrcodeDto.setCreateDate(new Date());
			qrcodeDto.setIconName(fileName);
			qrcodeDto.setIconUrl(path + fileName);
			qrcodeDto.setState(Constant.ONE);
			qrcodeDto.setParamStr(param);
			qrcodeDto.setFlowNo(flowNo);
			qrcodeDto.setType("0");
			qrcodeService.saveQrcode(qrcodeDto);
			String picPath = path + fileName;
			String picturePath = picPath.replace("\\", "/");

			returnObject.setData(picturePath);
		} catch (Exception e) {
			String errorMessage = e.getLocalizedMessage();
			logger.error(errorMessage);
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage(MessageUtils.UPDATE_ERROR);
		}
		return returnObject;
	}

	/**
	 * 全业务二维码
	 * 
	 * @param model
	 * @param qrcode
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author <yyb>
	 */
	@RequestMapping("/saveAllQrcode")
	@ResponseBody
	public ReturnDatas saveAllQrcode(Model model, Qrcode qrcode,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);

		List<Qrcode> qdatas = qrcodeService.findListDataByFinder(null, null,
				Qrcode.class, qrcode);
		if (qdatas.size() > 0) {
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage("二维码名称不能重复!");
			return returnObject;
		}
		try {
			// 二维码创建人()
			String belongUserId = SessionUser.getUserId();
			User belongUser = userService.findUserById(belongUserId);
			String belongWorkNo = belongUser.getWorkno();
			if ("admin".equals(belongUser.getAccount())) {
				returnObject.setStatus(ReturnDatas.ERROR);
				returnObject.setMessage("超级管理员不能生成二维码!");
				return returnObject;
			}
			// 二维码名称
			String codeName = request.getParameter("qrcodeName");
			// 得到tree选中的值（业务编码以及产品编码），格式为：子级:父级,子级:父级,
			List<Business> buss = businessService.findAllBus();
			List<Goods> goods = businessService.findGoodsByBus();
			String business = "";
			String stro = "";
			String busStr1 = "";
			if (buss.size() > 0 && goods.size() > 0) {
				for (int i = 0; i < buss.size(); i++) {
					for (int j = 0; j < goods.size(); j++) {
						if (j == 0) {
							stro = buss.get(i).getBusCode() + ":" + ",";
						}
						if (goods.get(j).getBusId().equals(buss.get(i).getId())) {
							busStr1 = busStr1 + goods.get(j).getFeeCode() + ":"
									+ buss.get(i).getBusCode() + ",";
						}
					}
					business = business + stro + busStr1;
					stro = "";
					busStr1 = "";
				}
			}

			// 得到对应的用户工号
			String createWorkNo = belongWorkNo;
			UserBusinessDTO ubDto = null;
			if (StringUtils.isNotEmpty(business)
					&& StringUtils.isNotEmpty(createWorkNo)) {
				ubDto = userBusiness(business, createWorkNo);
			}

			String param = JSON.toJSONString(ubDto);

			// 二维码图片保存路径
			String path = pfCache.get(Const.SYSTEM_FILE, "qrcodePath");
			// 图片名称
			String fileName = Util.getUniqueSuffix() + ".png";
			// 二维码生成前面的url
			String qrcodeUrl = pfCache.get(Const.SYSTEM_FILE, "qrcodeUrl");
			String uuid = UUID.randomUUID().toString();
			// 流水号
			String flowNo = uuid.replace("-", "");
			flowNo = flowNo + "qyw999";
			String filePath = request.getServletContext().getRealPath("/");

			// 生成二维码
			/*QrcodeUtils.encoderQRCode(qrcodeUrl + "?param=" + flowNo, filePath
					+ path + fileName, filePath + path + "logo.png");
*/
			// save Qrcode 表
			Qrcode qrcodeDto = new Qrcode();
			qrcodeDto.setId(null);
			qrcodeDto.setQrcodeName(codeName);
			qrcodeDto.setBelongUser(createWorkNo);
			qrcodeDto.setCreateUser(belongWorkNo);
			qrcodeDto.setCreateDate(new Date());
			qrcodeDto.setIconName(fileName);
			qrcodeDto.setIconUrl(path + fileName);
			qrcodeDto.setState(Constant.ONE);
			qrcodeDto.setParamStr(param);
			qrcodeDto.setFlowNo(flowNo);
			qrcodeDto.setType("0");
			qrcodeService.saveQrcode(qrcodeDto);
			String picPath = path + fileName;
			String picturePath = picPath.replace("\\", "/");

			returnObject.setData(picturePath);
		} catch (Exception e) {
			String errorMessage = e.getLocalizedMessage();
			logger.error(errorMessage);
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage(MessageUtils.UPDATE_ERROR);
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
	 * 外部连接二维码跳页
	 */
	@RequestMapping(value = "/otherQrcode")
	public String other(Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = lookjson(model, request, response);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/sinova/exQrcode/qrcodeOtherAdd";
	}

	/**
	 * 外部连接二维码生成
	 */
	@RequestMapping("/otherQrcodeAdd")
	@ResponseBody
	public ReturnDatas otherQrcodeAdd(Model model, ExternalQrcode exqrcode,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);

		// 二维码名称
		String codeName = request.getParameter("qrcodeName");
		// 链接名称
		String addrName = request.getParameter("addrName");
		String[] rs = addrName.split(",");
		if (rs == null || rs.length < 1) {
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage("链接名称不能为空!");
			return returnObject;
		}
		List<String> rsList = Arrays.asList(rs);
		// 外部链接地址
		String qrcodeUrl = request.getParameter("urlName");
		String[] url = qrcodeUrl.split(",");
		if (url == null || url.length < 1) {
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage("链接地址不能为空!");
			return returnObject;
		}
		List<String> urlList = Arrays.asList(url);

		exqrcode.setExName(codeName);

		List<ExternalQrcode> qdatas = qrcodeService.findListDataByFinder(null,
				null, ExternalQrcode.class, exqrcode);
		if (qdatas.size() > 0) {
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage("二维码名称不能重复!");
			return returnObject;
		}

		try {
			// 二维码创建人(得到创建人的工号)
			String belongUserId = SessionUser.getUserId();
			User belongUser = userService.findUserById(belongUserId);
			String belongWorkNo = belongUser.getWorkno();

			if (StringUtils.isBlank(belongWorkNo)) {
				returnObject.setStatus(ReturnDatas.ERROR);
				returnObject.setMessage("当前登录用户工号为空!");
				return returnObject;
			}

			// 二维码图片保存路径
			String path = pfCache.get(Const.SYSTEM_FILE, "qrcodePath");
			// 图片名称
			String fileName = Util.getUniqueSuffix() + ".png";
			String uuid = UUID.randomUUID().toString();
			// 流水号
			String flowNo = uuid.replace("-", "");
			// 二维码生成前面的url
			String qrcodeurl = pfCache.get(Const.SYSTEM_FILE, "exqrcodeUrl");
			String filePath = request.getServletContext().getRealPath("/");
			// 生成二维码
			/*QrcodeUtils.encoderQRCode(qrcodeurl + "?param=" + flowNo, filePath
					+ path + fileName, filePath + path + "logo.png");*/

			String exUrl = "";
			String spare1 = "";
			for (int i = 0; i < rsList.size(); i++) {
				exUrl = rsList.get(i);
				spare1 = urlList.get(i);
				// save externalqrcode 表
				ExternalQrcode exqrcodeDto = new ExternalQrcode();
				exqrcodeDto.setId(null);
				exqrcodeDto.setExName(codeName);
				exqrcodeDto.setCreateTime(new Date());
				exqrcodeDto.setExUrl(spare1);
				exqrcodeDto.setState(Constant.ONE);
				exqrcodeDto.setImageUrl(path + fileName);
				exqrcodeDto.setImageName(fileName);
				exqrcodeDto.setBelongUser(belongWorkNo);
				exqrcodeDto.setSpare1(exUrl);
				exqrcodeDto.setSpare2(flowNo);
				externalQrcodeService.save(exqrcodeDto);
			}
			String picPath = path + fileName;
			String picturePath = picPath.replace("\\", "/");

			returnObject.setData(picturePath);
		} catch (Exception e) {
			String errorMessage = e.getLocalizedMessage();
			logger.error(errorMessage);
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage(MessageUtils.UPDATE_ERROR);
		}
		return returnObject;
	}

	/**
	 * 厅店二维码生成页面
	 * 
	 * @return
	 *//*
	@RequestMapping(value = "/create")
	public String create(TreeBusinessGoodsDTO tree, HttpServletRequest request,
			Model model) throws Exception {
		
		return "/sinova/officeqrcode/qrcodeadd";
	}

	*//**
	 * tree展示,返回json格式数据
	 * 
	 * @author <yyb>
	 *//*
	@RequestMapping("/officetree")
	@ResponseBody
	public ReturnDatas listOfficeTreeJson(HttpServletRequest request, Model model, TreeBusinessGoodsDTO tree) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();

		UserOfficeDTO uodto = SessionUser.getShiroUser().getUserOfficeDTO();
		if (StringUtils.isBlank(uodto.getOfficeCode())) {
			logger.error("---用户信息为空---");
			return null;
		}
		try {
			List<TreeBusinessGoodsDTO> datas = businessService.findTreeByRegion(uodto.getRegionCode());
			List<TreeBusinessGoodsDTO> datasTemp = new ArrayList<TreeBusinessGoodsDTO>();
			String businessCode = "";
			
			 * 说明：根据userId获取对应的业务以及产品，并对数据分子父关系操作，数据结构类似T_MENU表的id,name,pid.
			 * 以便页面显示（ztree）用
			 
			if (datas != null && datas.size() > 0) {
				for (int i = 0; i < datas.size(); i++) {
					TreeBusinessGoodsDTO dto = datas.get(i);
					if (!businessCode.equals(dto.getBusinessCode())) {
						TreeBusinessGoodsDTO temp = new TreeBusinessGoodsDTO();
						temp.setGoodsCode(dto.getBusinessCode());
						temp.setGoodsName(dto.getBusinessName());
						datasTemp.add(temp);
					}
					TreeBusinessGoodsDTO tempDto = new TreeBusinessGoodsDTO();
					tempDto.setBusinessCode(dto.getBusinessCode());
					tempDto.setGoodsCode(dto.getGoodsCode());
					tempDto.setGoodsName(dto.getGoodsName());
					datasTemp.add(tempDto);

					businessCode = dto.getBusinessCode();
				}
			}
			returnObject.setQueryBean(tree);
			returnObject.setData(datasTemp);
			returnObject.setStatus(ReturnDatas.SUCCESS);
		} catch (Exception e) {
			returnObject.setMessage("没有相关业务商品信息！");
			returnObject.setStatus(ReturnDatas.ERROR);
		}
		
		return returnObject;
	}

	*//**
	 * 厅店二维码生成操作
	 * 
	 * @param model
	 * @param qrcode
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author <yyb>
	 *//*
	@RequestMapping("/officeqrcode")
	@ResponseBody
	public ReturnDatas officeqrcode(Model model, Qrcode qrcode,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);

		List<Qrcode> qdatas = qrcodeService.findListDataByFinder(null, null,
				Qrcode.class, qrcode);
		if (qdatas.size() > 0) {
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage("二维码名称不能重复!");
			return returnObject;
		}

		try {
			// 二维码创建人(得到创建人的工号)
			String belongUserId = SessionUser.getUserId();
			User belongUser = userService.findUserById(belongUserId);
			String belongWorkNo = belongUser.getWorkno();
			// 二维码名称
			String codeName = request.getParameter("qrcodeName");
			
			// 得到tree选中的值（业务编码以及产品编码），格式为：子级:父级,子级:父级,
			String business = request.getParameter("business");
			
			// 得到对应的用户工号
			String createWorkNo = belongWorkNo;
			UserBusinessDTO ubDto = null;
			if (StringUtils.isNotEmpty(business)
					&& StringUtils.isNotEmpty(createWorkNo)) {
				ubDto = userBusiness(business, createWorkNo);
			}

			String param = JSON.toJSONString(ubDto);
			// 二维码图片保存路径
			String path = pfCache.get(Const.SYSTEM_FILE, "qrcodePath");
			// 图片名称
			String fileName = Util.getUniqueSuffix() + ".png";
			// 二维码生成前面的url
			String qrcodeUrl = pfCache.get(Const.SYSTEM_FILE, "qrcodeUrl");
			String uuid = UUID.randomUUID().toString();
			// 流水号
			String flowNo = uuid.replace("-", "");
			String filePath = request.getServletContext().getRealPath("/");

			// 生成二维码
			QrcodeUtils.encoderQRCode(qrcodeUrl + "?param=" + flowNo, filePath
					+ path + fileName, filePath + path + "logo.png");

			// save Qrcode 表
			Qrcode qrcodeDto = new Qrcode();
			qrcodeDto.setId(null);
			qrcodeDto.setQrcodeName(codeName);
			qrcodeDto.setBelongUser(createWorkNo);
			qrcodeDto.setCreateUser(belongWorkNo);
			qrcodeDto.setCreateDate(new Date());
			qrcodeDto.setIconName(fileName);
			qrcodeDto.setIconUrl(path + fileName);
			qrcodeDto.setState(Constant.ONE);
			qrcodeDto.setParamStr(param);
			qrcodeDto.setFlowNo(flowNo);
			qrcodeDto.setType("2");
			qrcodeService.saveQrcode(qrcodeDto);
			String picPath = path + fileName;
			String picturePath = picPath.replace("\\", "/");

			returnObject.setData(picturePath);
		} catch (Exception e) {
			String errorMessage = e.getLocalizedMessage();
			logger.error(errorMessage);
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage(MessageUtils.UPDATE_ERROR);
		}
		return returnObject;
	}
*/
}
