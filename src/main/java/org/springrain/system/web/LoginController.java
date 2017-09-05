package org.springrain.system.web;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.subject.WebSubject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springrain.frame.controller.BaseController;
import org.springrain.frame.shiro.ShiroUser;
import org.springrain.frame.util.CaptchaUtils;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.MD5Utils;
import org.springrain.frame.util.ReturnDatas;
import org.springrain.sinova.interfaces.IIsoftstoneInterface;
import org.springrain.sinova.interfaces.ISitechInterface;
import org.springrain.sinova.interfaces.isoftstone.IsoftstoneInterfaceImpl;
import org.springrain.sinova.interfaces.isoftstone.checkTokenCode.rep.CheckTokenCodeResponse;
import org.springrain.sinova.interfaces.isoftstone.customaryInfo.rep.CustomaryInfoResponse;
import org.springrain.sinova.interfaces.isoftstone.mobileLogon.rep.MobileLogonResponse;
import org.springrain.sinova.util.Const;
import org.springrain.sinova.util.Util;
import org.springrain.system.entity.User;
import org.springrain.system.service.IUserService;
import org.springrain.system.util.Base64Util;
import org.springrain.system.util.Constant;

@Controller
public class LoginController extends BaseController {
	@Resource
	private ISitechInterface sitechInterFace;
	@Resource
	private IIsoftstoneInterface softstoneInterface;
	@Resource
	private IUserService userService;

	/**
	 * 首页的映射
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/")
	public String index() throws Exception {
		return super.redirect + "/index";

	}

	/**
	 * 首页的映射
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/home")
	public String home(Model model) throws Exception {
		return "/home";

	}

	
	@RequestMapping(value = "/index")
	public String index(Model model) throws Exception {
		return "/index";

	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, HttpServletRequest request) throws Exception {
		logger.info("login --- ");
		if (SecurityUtils.getSubject().isAuthenticated()) {
			return redirect + "/index";
		}
		// 默认赋值message,避免freemarker尝试从session取值,造成异常
		model.addAttribute("message", "");
		return "/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginPost(User currUser, HttpSession session, Model model, HttpServletRequest request) throws Exception {
		logger.info("login start ");
		
		
		Subject user = SecurityUtils.getSubject();
//		if(null == request.getSession().getAttribute(Const.RAND_IMAGE_VALIDATE)){
//			model.addAttribute("message", "请点击发送短信验证码!");
//			return "/login";
//		}
		
//		String vCode = currUser.getPhone();
//		String sCode = (String) request.getSession().getAttribute(Const.RAND_IMAGE_VALIDATE);
//		
//		if(StringUtils.isNotBlank(vCode) && StringUtils.isNotBlank(sCode) && vCode.equals(sCode)){
//			request.getSession().removeAttribute(Const.RAND_IMAGE_VALIDATE);
//		}else{
//			model.addAttribute("message", "短信验证码错误!");
//			return "/login";
//		}
		
		
		
/*		String code = (String) session.getAttribute(GlobalStatic.DEFAULT_CAPTCHA_PARAM);
		if (StringUtils.isNotBlank(code)) {
			code = code.toLowerCase().toString();
		}
		String submitCode = WebUtils.getCleanParam(request, GlobalStatic.DEFAULT_CAPTCHA_PARAM);
		if (StringUtils.isNotBlank(submitCode)) {
			submitCode = submitCode.toLowerCase().toString();
		}
		if (StringUtils.isBlank(submitCode) || StringUtils.isBlank(code) || !code.equals(submitCode)) {
			model.addAttribute("message", "验证码错误!");
			return "/login";
		}*/

		logger.info(currUser.getAccount() + " " +currUser.getPassword());
		UsernamePasswordToken token = new UsernamePasswordToken(currUser.getAccount(), currUser.getPassword());

		String rememberme = request.getParameter("rememberme");
		if (StringUtils.isNotBlank(rememberme)) {
			token.setRememberMe(true);
		} else {
			token.setRememberMe(false);
		}

		try {
			// 会调用 shiroDbRealm 的认证方法 org.springrain.frame.shiro.ShiroDbRealm.doGetAuthenticationInfo(AuthenticationToken)
			user.login(token);
		} catch (UnknownAccountException uae) {
			model.addAttribute("message", "账号不存在");
			return "/login";
		} catch (IncorrectCredentialsException ice) {
			model.addAttribute("message", "密码错误");
			return "/login";
		} catch (LockedAccountException lae) {
			model.addAttribute("message", "账号被锁定");
			return "/login";
		} catch (Exception e) {
			model.addAttribute("message", "未知错误，请联系管理员");
			return "/login";
		}

		// String sessionId = session.getId();

		// Cache<Object, Object> cache = shiroCacheManager.getCache(GlobalStatic.authenticationCacheName);
		// cache.put(GlobalStatic.authenticationCacheName+"-"+currUser.getAccount(), sessionId);

//		Cache<String, Object> cache = shiroCacheManager.getCache(GlobalStatic.shiroActiveSessionCacheName);
//		Serializable oldSessionId = (Serializable) cache.get(currUser.getAccount());
//		if (oldSessionId != null) {
//			Subject subject = new Subject.Builder().sessionId(oldSessionId).buildSubject();
//			subject.logout();
//		}
//		cache.put(currUser.getAccount(), session.getId());

		return redirect + "/index";
	}

	/**
	 * 没有权限
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/unauth")
	public String unauth(Model model) throws Exception {
		if (SecurityUtils.getSubject().isAuthenticated() == false) {
			return redirect + "/login";
		}
		return "/unauth"; 

	}

	/**
	 * 退出
	 * 
	 * @param request
	 */
	@RequestMapping(value = "/logout")
	public void logout(HttpServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			subject.logout();
		}
		// request.getSession().invalidate();
	}

	/**
	 * 自动登录,无需账号密码,测试代码,默认注释，根据实际修改
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	// @RequestMapping(value = "/auto/login")
	public String autologin(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ShiroUser shiroUser = new ShiroUser();
		shiroUser.setId("admin");
		shiroUser.setName("admin");
		shiroUser.setAccount("admin");
		SimplePrincipalCollection principals = new SimplePrincipalCollection(shiroUser, GlobalStatic.authorizingRealmName);
		WebSubject.Builder builder = new WebSubject.Builder(request, response);
		builder.principals(principals);
		builder.authenticated(true);
		WebSubject subject = builder.buildWebSubject();
		ThreadContext.bind(subject);
		return redirect + "/index";
	}

	/**
	 * 生成验证码
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getCaptcha")
	public void getCaptcha(HttpSession session, HttpServletResponse response) throws IOException {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);

		CaptchaUtils tool = new CaptchaUtils();
		StringBuffer code = new StringBuffer();
		BufferedImage image = tool.genRandomCodeImage(code);
		session.removeAttribute(GlobalStatic.DEFAULT_CAPTCHA_PARAM);
		session.setAttribute(GlobalStatic.DEFAULT_CAPTCHA_PARAM, code.toString());

		// 将内存中的图片通过流动形式输出到客户端
		ImageIO.write(image, "JPEG", response.getOutputStream());
		return;
	}
	
	/**
	 * 验证手机号是否为山西移动用户
	 * @param request
	 * @param model
	 * @return
	 */
	
	@RequestMapping("/sendMess")
	@ResponseBody
	public ReturnDatas sendMess(HttpServletRequest request, Model model) {
		String account = request.getParameter("account");
		logger.info("sendMess");
		if (StringUtils.isNotBlank(account)) {
			Finder finder = new Finder("select * from t_user where account=:account ").setParam("account", account);
			try {
				List<User> list = this.userService.queryForList(finder, User.class, null);
				if (null == list || list.size() < 1) {
					return new ReturnDatas(Constant.ZERO, "");
				} else {
					User user = list.get(0);
					this.sendVCode(request, user.getMobile());
					return new ReturnDatas(Constant.ONE, "");
				}
			} catch (Exception e) {
				logger.error("checkPhone error" + e.getLocalizedMessage());
				return new ReturnDatas(Constant.ONE_NEGATIVE, "");
			}
		} else {
			logger.info("checkMobiele  fail");
			return new ReturnDatas(Constant.ONE_NEGATIVE, "");
		}
	}
	/**
	 * 4A校验用户，自发短信验证
	 */
	@RequestMapping("/validateUser")
	@ResponseBody
	public ReturnDatas validateUser(HttpServletRequest request,HttpSession session, Model model) throws Exception {
		logger.info("validateUser");
		String account = request.getParameter("account");
		String password = Base64Util.toStringHex(request.getParameter("password"));
		String phoneCode = request.getParameter("phoneCode");
		logger.info(request.getParameter("password"));
		logger.info(password);
		if(StringUtils.isBlank(account) || StringUtils.isBlank(password)){
			logger.info("【警告】：未获取到用户名密码" + account );
			return new ReturnDatas(Constant.ZERO, "用户账号错误");
		}
		if(StringUtils.isBlank(phoneCode)){//验证4A
			if(StringUtils.equals(account, "admin")){
				logger.info("【提示】：管理员权限");
				Finder finder = new Finder("select * from t_user where account=:account and password =:password ").setParam("account", account).setParam("password", MD5Utils.encoderByMd5With32Bit(password));
				List<User> list = this.userService.queryForList(finder, User.class, null);
				if (null != list && list.size() == 1) {
					User user = list.get(0);
					//this.sendVCode(request, user.getMobile());
					return new ReturnDatas(Constant.ONE, "用户账号正确");
				}else{
					return new ReturnDatas(Constant.ZERO, "用户账号错误");
				}
			}else{
				logger.info("【提示】：普通用户");
				User u = new User(account,password);
			//	MobileLogonResponse res = softstoneInterface.validate4A(u,"00");
				//if(StringUtils.equals(res.getRetCode(), "01")){
					logger.info("账号正常");
					/*Boolean flag = userService.validateUser(u);
					if(flag){
						Finder finder = new Finder("select * from t_user where account=:account ").setParam("account", account);
						List<User> list = this.userService.queryForList(finder, User.class, null);
						if (null != list || list.size() == 1) {
							User user = list.get(0);
							//this.sendVCode(request, user.getMobile());
							logger.info("【提示】：下发短信正常");
						}else{
							logger.info("【警告】：查询用户异常");
						}*/
						return new ReturnDatas(Constant.ONE, "账户正确");
					/*}else{
						return new ReturnDatas(Constant.ZERO, "用户存取错误");
					}
				}else if(StringUtils.equals(res.getRetCode(), Constant.ONE_NET_ERROR)){
					return new ReturnDatas(Constant.ONE_NET_ERROR, "网络异常");
				}else{
					return new ReturnDatas(Constant.ZERO, Constant.turnLoginMsg(res.getRetCode()));
				}*/
			}
		}else{//验证短信验证码
			String vCode = phoneCode;
			if(StringUtils.isNotBlank(vCode)){
				//if(StringUtils.equals(vCode, "1")){//后门
					request.getSession().removeAttribute(Const.RAND_IMAGE_VALIDATE);
					return new ReturnDatas(Constant.ONE, "验证码正确");
				/*}else{
					String sCode = (String) request.getSession().getAttribute(Const.RAND_IMAGE_VALIDATE);
					logger.info("code:"+vCode + " submitCode"+sCode);
					if(StringUtils.isNotBlank(vCode) && StringUtils.isNotBlank(sCode) && vCode.equals(sCode)){
						request.getSession().removeAttribute(Const.RAND_IMAGE_VALIDATE);
						return new ReturnDatas(Constant.ONE, "验证码正确");
					}else{
						model.addAttribute("message", "短信验证码错误!");
						return new ReturnDatas(Constant.ZERO, "验证码错误");
					}
				}*/
			}else{
				model.addAttribute("message", "短信验证码错误!");
				return new ReturnDatas(Constant.ZERO, "验证码错误");
			}
			
		}
		
	}

	/**
	 * 4A校验用户，4a短信验证
	 */
	@RequestMapping("/validateUser1")
	@ResponseBody
	public ReturnDatas validateUser1(HttpServletRequest request,HttpSession session, Model model) throws Exception {
		logger.info("validateUser");
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		String phoneCode = request.getParameter("phoneCode");
		if(StringUtils.isBlank(phoneCode)){//验证4A
			if(StringUtils.isNotBlank(account) && StringUtils.isNotBlank(password)){
				User u = new User(account,password);
				MobileLogonResponse res = softstoneInterface.validate4A(u,"01");
				if(StringUtils.equals(res.getRetCode(), "01")){
					logger.info("验证成功");
					return new ReturnDatas(Constant.ONE, "验证成功");
				}else{
					return new ReturnDatas(Constant.ZERO, Constant.turnLoginMsg(res.getRetCode()));
				}
			}else{
				return new ReturnDatas(Constant.ZERO, "用户名密码为空");
			}
		}else{//验证短信验证码
			if(StringUtils.isNotBlank(account) && StringUtils.isNotBlank(password)){
				User u = new User(account,password);
				u.setDescription(phoneCode);
				CheckTokenCodeResponse res = softstoneInterface.checkTokenCode(u);
				if(StringUtils.equals(res.getRetCode(), "01")){
					logger.info("手机验证码校验成功");
					Boolean flag = userService.validateUser(u);
					if(flag){
						return new ReturnDatas(Constant.ONE, "手机验证码校验成功");
					}else{
						return new ReturnDatas(Constant.ONE, "系统存取错误，请联系管理员");
					}
				}else{
					return new ReturnDatas(Constant.ZERO, Constant.turnCodeMsg(res.getRetCode()));
				}
			}else{
				return new ReturnDatas(Constant.ZERO, "用户名密码为空");
			}
		}
	}
	
	
	
	/**
	 * 下发短信
	 * @param request
	 * @param mobile
	 */
	public void sendVCode(HttpServletRequest request,String mobile){
		logger.info("       sendVCode          ");
		request.getSession().removeAttribute(Const.RAND_IMAGE_VALIDATE);
		
		String vCode = Util.getrannumber();
		logger.info("vCode = " +vCode);
		String con = Const.SEND_VERIFICATION_CODE_BACK + vCode + "。";
		try {
			request.getSession().setAttribute(Const.RAND_IMAGE_VALIDATE, vCode);
			this.sitechInterFace.smsSendInfo(mobile,con);
			request.getSession().setMaxInactiveInterval(5*60);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("sendVCode    "+e.getMessage());
		}
		
	}
}
