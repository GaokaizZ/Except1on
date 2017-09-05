package org.springrain.sinova.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import org.springrain.sinova.entity.Business;
import org.springrain.sinova.entity.Goods;
import org.springrain.sinova.entity.GoodsAct;
import org.springrain.sinova.service.IGoodsActService;

@Controller
@RequestMapping(value = "/goodsAct")
public class GoodsActController extends BaseController{
	
	@Resource
	private IGoodsActService goodsActService;

	private String listurl = "/sinova/goods/spgl_gl";
	
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model, GoodsAct goodsAct) throws Exception {
		ReturnDatas returnObject = listjson(request, model, goodsAct);
		List<Business> bdatas = goodsActService.findBus();
		model.addAttribute("busdata", bdatas);
		
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return listurl;
	}
	
	@RequestMapping("/list/json")
	@ResponseBody
	public ReturnDatas listjson(HttpServletRequest request, Model model, GoodsAct goodsAct) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Page page = newPage(request);
		String goodsId = request.getParameter("goodsId");
		String goodsName = request.getParameter("goodsName");
		String feeCode = request.getParameter("feeCode");
		String relation = request.getParameter("relation");
		String busName = request.getParameter("busName");
		//根据地市管理员获取地市编号
		String groupId = SessionUser.getShiroUser().getUserOfficeDTO().getRegionCode();
		logger.info("当前地市管理员所属地市【"+groupId+"】");
		List<Goods> datas  = new ArrayList<Goods>();
		try {
			if(StringUtils.isNotBlank(groupId)){
				datas = goodsActService.findGoodsByGroupId(goodsId, groupId, goodsName,  feeCode,  busName, relation, page); 
			}
		} catch (Exception e) {
			logger.info("******关联商品**查询商品出错**********");
			return null;
		}
		List<GoodsAct> goodsActDto = goodsActService.findGoodsAct(goodsId,groupId);
		
		if(goodsActDto !=null && goodsActDto.size()>0){
			for(int i=0; i<goodsActDto.size(); i++){
				if(datas != null && datas.size()>0){
					for(int j=0; j<datas.size(); j++){
						if(goodsActDto.get(i).getBusId().equals(datas.get(j).getId())){
							datas.get(j).setGoodsDesc("YI_GL");
						}
					}
				}
			}
		}
		
		if(StringUtils.isNotBlank(goodsName)){
			model.addAttribute("goodsName", goodsName.trim());
		}
		if(StringUtils.isNotBlank(feeCode)){
			model.addAttribute("feeCode", feeCode.trim());
		}
		model.addAttribute("relation", relation);
		model.addAttribute("groupId", groupId);
		model.addAttribute("busName", busName);
		returnObject.setQueryBean(goodsAct);
		returnObject.setPage(page);
		returnObject.setData(datas);
		return returnObject;
	}

	@RequestMapping("/addRelation")
	@ResponseBody
	public ReturnDatas addRelation(HttpServletRequest request, Model model, GoodsAct goodsAct)throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		try {
			goodsAct.setId(null);
			goodsActService.saveorupdate(goodsAct);
			returnObject.setStatus(ReturnDatas.SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage(MessageUtils.UPDATE_ERROR);
		}
		return returnObject;
	}
	
	@RequestMapping("/delRelation")
	@ResponseBody
	public ReturnDatas delRelation(HttpServletRequest request, Model model, GoodsAct goodsAct)throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.DELETE_SUCCESS);
		try {
			goodsActService.deleteByEntity(goodsAct);
			returnObject.setStatus(ReturnDatas.SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage(MessageUtils.DELETE_ERROR);
		}
		return returnObject;
	}
	
	@RequestMapping("/plAddRelation")
	@ResponseBody
	public ReturnDatas plAddRelation(HttpServletRequest request, Model model, GoodsAct goodsAct)throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		String records = request.getParameter("records");
		if (StringUtils.isBlank(records)) {
			return new ReturnDatas(ReturnDatas.ERROR, MessageUtils.UPDATE_ERROR);
		}
		
		String[] rs = records.split(",");
		if (rs == null || rs.length < 1) {
			return new ReturnDatas(ReturnDatas.ERROR, MessageUtils.UPDATE_ERROR);
		}
		try {
			List<String> ids = Arrays.asList(rs);
			for(int i=0; i<ids.size(); i++){
				goodsAct.setId(null);
				goodsAct.setBusId(ids.get(i));
				goodsActService.saveorupdate(goodsAct);
			}
			returnObject.setStatus(ReturnDatas.SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage(MessageUtils.UPDATE_ERROR);
		}
		return returnObject;
	}
	@RequestMapping("/plDelRelation")
	@ResponseBody
	public ReturnDatas plDelRelation(HttpServletRequest request, Model model, GoodsAct goodsAct)throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.DELETE_SUCCESS);
		String records = request.getParameter("records");
		if (StringUtils.isBlank(records)) {
			return new ReturnDatas(ReturnDatas.ERROR, MessageUtils.DELETE_SUCCESS);
		}
		
		String[] rs = records.split(",");
		if (rs == null || rs.length < 1) {
			return new ReturnDatas(ReturnDatas.ERROR, MessageUtils.DELETE_SUCCESS);
		}
		try {
			List<String> ids = Arrays.asList(rs);
			for(int i=0; i<ids.size(); i++){
				goodsAct.setBusId(ids.get(i));
				goodsActService.deleteByEntity(goodsAct);
			}
			returnObject.setStatus(ReturnDatas.SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage(MessageUtils.DELETE_ERROR);
		}
		return returnObject;
	}
}














