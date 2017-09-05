package org.springrain.sinova.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springrain.frame.common.SessionUser;
import org.springrain.frame.controller.BaseController;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.MessageUtils;
import org.springrain.frame.util.Page;
import org.springrain.frame.util.ReturnDatas;
import org.springrain.sinova.dto.BusinessGoodsDTO;
import org.springrain.sinova.dto.SpecialQrcodeDTO;
import org.springrain.sinova.dto.SpecialTreeDTO;
import org.springrain.sinova.dto.UserBusinessDTO;
import org.springrain.sinova.entity.AdGoods;
import org.springrain.sinova.entity.Business;
import org.springrain.sinova.entity.Goods;
import org.springrain.sinova.entity.Qrcode;
import org.springrain.sinova.entity.SpecialQrcode;
import org.springrain.sinova.service.IAdGoodsService;
import org.springrain.sinova.service.IBusinessService;
import org.springrain.sinova.service.IGoodsService;
import org.springrain.sinova.service.IQrcodeService;
import org.springrain.sinova.service.ISpecialQrcodeService;
import org.springrain.sinova.util.Const;
import org.springrain.sinova.util.QrcodeUtils;
import org.springrain.sinova.util.Util;
import org.springrain.system.util.Constant;
import org.springrain.system.util.PropertyFileCache;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping(value = "/specialqrcode")
public class SpecialQrcodeController extends BaseController{
	
	@Resource
	private ISpecialQrcodeService specialQrcodeService;
	@Resource
	private IBusinessService businessService;
	@Resource
	private IQrcodeService qrcodeService;
	@Resource
	private IAdGoodsService adGoodsService;
	
	private PropertyFileCache pfCache = PropertyFileCache.getInstance();
	
	private String listurl = "/sinova/specialQrcode/specialQrcodeTree";
	
	/**
	 * 
	 * @description 查询列表 <br/>
	 * @date 2015年4月15日 上午10:10:51 <br/>
	 * @param request
	 * @param model
	 * @param specialQrcode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model, SpecialQrcodeDTO specialQrcode) throws Exception {
		logger.info("----------specialqrcode--list-----" );
		ReturnDatas returnObject = listjson(request, model, specialQrcode);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return listurl;
	}
	/**
	 * 
	 * @description 查询列表，返回json数据 <br/>
	 * @date 2015年4月15日 上午10:10:51 <br/>
	 * @param request
	 * @param model
	 * @param specialQrcode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list/json")
	@ResponseBody
	public ReturnDatas listjson(HttpServletRequest request, Model model, SpecialQrcodeDTO specialQrcode) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		String userId = SessionUser.getUserId();
		logger.info("----------specialqrcode--list---userId=="+userId );
		List<Business> busdata = specialQrcodeService.findBusByUserId(userId);
		logger.info("----------specialqrcode--list---busdata=="+busdata.size() );
		List<Goods> goodsdata = specialQrcodeService.findGoodsbyUserId(userId);
		logger.info("----------specialqrcode--list---goodsdata=="+goodsdata.size() );
		SpecialQrcode specQrcode = new SpecialQrcode();
		specQrcode.setBelongUser(userId);
		List<SpecialQrcode> dataDTO = specialQrcodeService.findListDataByFinder(null, null, SpecialQrcode.class, specQrcode);
		/*//第一次进入专属业务
		if(dataDTO.size()==0 && busdata.size()>0){
			logger.info("--specialqrcode--第一次进入专属业务，保存用户已绑定业务到专属业务表----start--------");	
			for(int i=0; i<goodsdata.size();i++){
				if(StringUtils.isNotBlank(specQrcode.getId())){
					specQrcode = new SpecialQrcode();
				}
				specQrcode.setBelongUser(userId);
				specQrcode.setBusId(goodsdata.get(i).getBusId());
				specQrcode.setFeeCode(goodsdata.get(i).getFeeCode());
				specQrcode.setType("1");
				specialQrcodeService.save(specQrcode);
			}
			logger.info("--specialqrcode--第一次进入专属业务，保存用户已绑定业务到专属业务表----end--------");
		}else{
			//用户有新増的业务
				try {
					logger.info("--specialqrcode--用户有新増的业务------------");
					Map<String,SpecialQrcode> map = new HashMap<String,SpecialQrcode>();
					Map<String,Goods> map2 = new HashMap<String,Goods>();
					for(int i=0; i<dataDTO.size();i++){
						map.put(dataDTO.get(i).getFeeCode(), dataDTO.get(i));
					}
					for (int j = 0; j < goodsdata.size(); j++) {
						Goods good= goodsdata.get(j);
						map2.put(good.getFeeCode(),good);
						boolean flag=map.containsKey(good.getFeeCode());
						if(!flag){
							SpecialQrcode	specQrcode1 = new SpecialQrcode();
							specQrcode1.setBelongUser(userId);
							specQrcode1.setBusId(good.getBusId());
							specQrcode1.setFeeCode(good.getFeeCode());
							specQrcode1.setType("1");
							specialQrcodeService.save(specQrcode1);
						}
					}
					
					
					for (Map.Entry<String, SpecialQrcode> entry : map.entrySet()) {
						   if(!map2.containsKey(entry.getKey())){
							   SpecialQrcode specQrcode1=entry.getValue();
							   specialQrcodeService.deleteByEntity(specQrcode1);
						   }
					}
					
					logger.info("--specialqrcode--用户有新増的业务，保存到专属业务表----end--------");
				} catch (Exception e) {
					logger.info("--specialqrcode--用户有新増的业务，保存失败------------");
					return null;
				}
		}*/
		specialQrcode.setBelongUser(userId);
		
		List<SpecialTreeDTO> dtotype = specialQrcodeService.findTypeBusDTO(specialQrcode);
		List<SpecialTreeDTO> databus = specialQrcodeService.findBusTreeDTO(specialQrcode);
		List<SpecialTreeDTO> datagoods = specialQrcodeService.findGoodsTreeDTO(specialQrcode);
		
		List<SpecialTreeDTO> datas = new ArrayList<SpecialTreeDTO>();
		if(dtotype!= null && dtotype.size()>0){
			datas.addAll(dtotype);
		}
		if(databus!= null && databus.size()>0){
			datas.addAll(databus);
		}
		if(datagoods!= null && datagoods.size()>0){
			datas.addAll(datagoods);
		}
		//id字符串,判断是否选中（special.type为1绑定）
		//feeCode,bus_id,bus_type
		//201205081003503367,011ed7ed8b0749429dd5efa0ad668d83,200,
		StringBuilder sf = new StringBuilder(); 
		Map<String, String> mapstr = new HashMap<String, String>();
		Map<String, String> mapbus = new HashMap<String, String>();
		
		if(datagoods != null && datagoods.size()>0){
			for(int i=0; i<datagoods.size(); i++){
				if("1".equals(datagoods.get(i).getSpQrcodeType())){
					//绑定的商品放到map中(pid,id)
					mapstr.put(datagoods.get(i).getPid(), datagoods.get(i).getId());
					sf.append(datagoods.get(i).getId()+",");
				}
			}
		}
		//根据商品父id判断业务
		if(databus != null && databus.size()>0){
			for(int i=0; i<databus.size(); i++){
				if(mapstr.containsKey(databus.get(i).getId())){
					sf.append(databus.get(i).getId()+",");
					mapbus.put(databus.get(i).getPid(), databus.get(i).getId());
				}
			}
		}
		//根据业务父id判断类型
		if(dtotype != null && dtotype.size()>0){
			for(int i=0; i<dtotype.size(); i++){
				if(mapbus.containsKey(dtotype.get(i).getId())){
					if (i < dtotype.size() - 1) {
						sf.append(dtotype.get(i).getId()+",");
			           } else {
			        	   sf.append(dtotype.get(i).getId());
			           }
				}
			}
		}
		logger.info("**************id字符串,判断是否选中*****------"+sf.toString());
		model.addAttribute("namestr", sf.toString());
		model.addAttribute("checkStr", "");
		returnObject.setQueryBean(specialQrcode);
		returnObject.setData(datas);
		return returnObject;
	}

	/**专属业务修改状态
	 * @param request
	 * @param model
	 * @param specialQrcode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/update")
	@ResponseBody
	public ReturnDatas update(HttpServletRequest request, Model model, SpecialQrcode specialQrcode) throws Exception {
		logger.info("******specialQrcodeService*******update*******start***");
		String records = request.getParameter("namestr");
		String checkStr = request.getParameter("checkStr");
		
		//未选中,type设置为0
		if (StringUtils.isNotBlank(records)) {
			logger.info("*********specialQrcodeService*******update**未选中的id==="+records);
			String[] rs = records.split(",");
			try {
				List<String> rss = Arrays.asList(rs);
				if(rss != null && rss.size()>0){
					for (int i = 0; i < rss.size(); i++) {
						String feeCode = rss.get(i);
						SpecialQrcode speqrcode = new SpecialQrcode();
						
						speqrcode.setFeeCode(feeCode);
						speqrcode.setBelongUser(SessionUser.getUserId());
						speqrcode.setType("1");
						SpecialQrcode specQr = specialQrcodeService.findByFeeCode(speqrcode);
						if(specQr != null){
							if("1".equals(specQr.getType())){
								specQr.setType("0");
								specialQrcodeService.saveorupdate(specQr);
							}
						}
						
						//操作推荐
						if (StringUtils.isNotBlank(checkStr)) {
							String[] cs = checkStr.split(",");
							List<String> css = Arrays.asList(cs);
							if(!css.contains(feeCode)){
								adGoodsService.deleteAdGoods(SessionUser.getUserId(), null,null, feeCode);
							}
						}else{
							adGoodsService.deleteAdGoods(SessionUser.getUserId(),null, null, feeCode);
						}
						
						
						
					}
				}
				logger.info("******specialQrcodeService*******update*******未选中success***");
			} catch (Exception e) {
				logger.info("******specialQrcodeService*******update*******未选中error***");
				return new ReturnDatas(ReturnDatas.ERROR, MessageUtils.UPDATE_ERROR);
			}
		}
		//选中,type设置为1
		if (StringUtils.isNotBlank(checkStr)) {
			logger.info("*********specialQrcodeService*******update**选中的cs==="+checkStr);
			String[] cs = checkStr.split(",");
			try {
				List<String> css = Arrays.asList(cs);
				if(css != null && css.size()>0){
					for (int i = 0; i < css.size(); i++) {
						String feeCode = css.get(i);
						SpecialQrcode speqrcode = new SpecialQrcode();
						
						speqrcode.setFeeCode(feeCode);
						speqrcode.setBelongUser(SessionUser.getUserId());
						speqrcode.setType("0");
						SpecialQrcode specQr = specialQrcodeService.findByFeeCode(speqrcode);
						if(specQr != null){
							if("0".equals(specQr.getType())){
								specQr.setType("1");
								specialQrcodeService.saveorupdate(specQr);
							}
						}
					}
				}
				logger.info("******specialQrcodeService*******update*******选中success***");
			} catch (Exception e) {
				logger.info("******specialQrcodeService*******update*******选中error***");
				return new ReturnDatas(ReturnDatas.ERROR, MessageUtils.UPDATE_ERROR);
			}
		}
		
		
		
		
		//判断营业员是否有了自己的专属二维码
		String belongWorkNo = SessionUser.getShiroUser().getWorkno();
		Qrcode qrcode = new Qrcode();
		qrcode.setBelongUser(belongWorkNo);
		qrcode.setType("1");
		List<Qrcode> qdatas = qrcodeService.findListDataByFinder(null, null, Qrcode.class, qrcode);
		if(qdatas.size()==0){
			try {
				logger.info(" 没有专属二维码，生成---------"+SessionUser.getShiroUser().getName()+"的专属业务二维码");
				// 二维码名称
				String codeName = SessionUser.getShiroUser().getName()+"的专属业务二维码";
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
				//String param = "{busGoodsList"+":[{businessCode:101,goodsList:[BCAF9141]}],userCode"+":"+createWorkNo+"}";
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
				String type = "1";
				String filePath = request.getServletContext().getRealPath("/");

				// 生成二维码
				QrcodeUtils.encoderQRCode(qrcodeUrl + "?param=" + flowNo, filePath
						+ path + fileName, filePath + path + "logo.png");

				// save Qrcode 表
				Qrcode qrcodeDto = new Qrcode();
				qrcodeDto.setId(null);
				qrcodeDto.setType(type);
				qrcodeDto.setQrcodeName(codeName);
				qrcodeDto.setBelongUser(createWorkNo);
				qrcodeDto.setCreateUser(belongWorkNo);
				qrcodeDto.setCreateDate(new Date());
				qrcodeDto.setIconName(fileName);
				qrcodeDto.setIconUrl(path + fileName);
				qrcodeDto.setState(Constant.ONE);
				qrcodeDto.setParamStr(param);
				qrcodeDto.setFlowNo(flowNo);
				qrcodeService.saveQrcode(qrcodeDto);
				logger.info("***************生成专属二维码成功**********************");
			} catch (Exception e) {
				logger.error("***********生成专属二维码失败*******");
				return new ReturnDatas(ReturnDatas.ERROR, MessageUtils.UPDATE_ERROR);
			}
		}	
		logger.info("******specialQrcodeService*******update*******success***end****");
		return new ReturnDatas(ReturnDatas.SUCCESS, MessageUtils.UPDATE_SUCCESS);
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
}










