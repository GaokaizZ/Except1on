package org.springrain.sinova.service.impl;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springrain.frame.entity.IBaseEntity;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.Page;
import org.springrain.sinova.dto.BusinessGoodsDTO;
import org.springrain.sinova.dto.UserBusinessDTO;
import org.springrain.sinova.entity.Qrcode;
import org.springrain.sinova.entity.QrcodeBusiness;
import org.springrain.sinova.service.IQrcodeBusinessService;
import org.springrain.sinova.service.IQrcodeService;
import org.springrain.system.entity.User;
import org.springrain.system.service.BaseSpringrainServiceImpl;

import com.alibaba.fastjson.JSONObject;

/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-01-09 09:13:39
 * @see org.springrain.sinova.service.impl.Qrcode
 */
@Service("qrcodeService")
public class QrcodeServiceImpl extends BaseSpringrainServiceImpl implements IQrcodeService {

    @Resource
    private IQrcodeService qrcodeService;
    @Resource
    private IQrcodeBusinessService qrcodeBusinessService;
    @Override
	public String  save(Object entity ) throws Exception{
		Qrcode qrcode=(Qrcode) entity;
		return super.save(qrcode).toString();
	}

    @Override
	public String  saveorupdate(Object entity ) throws Exception{
    	Qrcode qrcode=(Qrcode) entity;
		return super.saveorupdate(qrcode).toString();
	}

	@Override
    public Integer update(IBaseEntity entity ) throws Exception{
		Qrcode qrcode=(Qrcode) entity;
		return super.update(qrcode);
    }

    @Override
	public Qrcode findQrcodeById(Object id) throws Exception{
		return super.findById(id,Qrcode.class);
	}

	/**
	 * 列表查询,每个service都会重载,要把sql语句封装到service中,Finder只是最后的方案
	 * @param finder
	 * @param page
	 * @param clazz
	 * @param o
	 * @return
	 * @throws Exception
	 */
	@Override
    public <T> List<T> findListDataByFinder(Finder finder, Page page, Class<T> clazz, Object o) throws Exception{
		return super.findListDataByFinder(finder,page,clazz,o);
	}

	/**
	 * 根据查询列表的宏,导出Excel
	 * @param finder 为空则只查询 clazz表
	 * @param ftlurl 类表的模版宏
	 * @param page 分页对象
	 * @param clazz 要查询的对象
	 * @param o  querybean
	 * @return
	 * @throws Exception
	 */
	@Override
	public <T> File findDataExportExcel(Finder finder,String ftlurl, Page page,
			Class<T> clazz, Object o) throws Exception {
		 return super.findDataExportExcel(finder,ftlurl,page,clazz,o);
	}

	/**
     * save qrcode以及关系表
     * @param qrcode
     * @return
     * @throws Exception
     */
    public String saveQrcode(Qrcode qrcode) throws Exception {
        if(qrcode !=null){
            QrcodeBusiness qbDto =null;
            qrcodeService.save(qrcode);
            String flowNO=qrcode.getFlowNo();//流水号
            String paramStr=qrcode.getParamStr();
            UserBusinessDTO dto = JSONObject.parseObject(paramStr, UserBusinessDTO.class);
            
            List<BusinessGoodsDTO> list = dto.getBusGoodsList();
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    BusinessGoodsDTO goodsDto = list.get(i);
                    String busNo = goodsDto.getBusinessCode();

                    List<String> listTemp = goodsDto.getGoodsList();
                    if (listTemp != null && listTemp.size() > 0) {
                        for (int j = 0; j < listTemp.size(); j++) {
                            String goodsNo = listTemp.get(j);
                            qbDto=new QrcodeBusiness();
                            qbDto.setQrcodeId(flowNO);//流水号
                            qbDto.setFeeCode(goodsNo);//产品编码
                            qbDto.setBusId(busNo);//业务编码
                            qbDto.setId(null);
                            qrcodeBusinessService.save(qbDto);
                        }
                    } else if (listTemp == null || listTemp.isEmpty()) {
                        qbDto=new QrcodeBusiness();
                        qbDto.setQrcodeId(flowNO);//流水号
                        qbDto.setBusId(busNo);//业务编码
                        qbDto.setId(null);
                        qrcodeBusinessService.save(qbDto);
                    }
                }
            }
        }
        return "success";
    }

	@Override
	public Qrcode findQrcodeByFlowNo(Object flowNo) throws Exception {
		Finder finder = Finder.getSelectFinder(Qrcode.class);
		finder.append("where flow_no = :flowNo");
		finder.setParam("flowNo", flowNo);
		return this.queryForObject(finder, Qrcode.class);
	}

	/* 根据归属人查找二维码
	 * 
	 *  */
	@Override
	public List<Qrcode> findQrcodeByBelongUser(Qrcode qrcode, Page page)
			throws Exception {
		Finder finder = Finder.getSelectFinder(Qrcode.class);
		finder.append(" where belong_user = :belongUser and state ='1' order by create_date desc ");
		finder.setParam("belongUser", qrcode.getBelongUser());
		
		return this.queryForList(finder, Qrcode.class, page);
	}

	@Override
	public User findWorkNoByPhone(Object phone) throws Exception {
		Finder finder = Finder.getSelectFinder(User.class);
		finder.append(" where mobile = :mobile and state = '1' ");
		finder.setParam("mobile", phone);
		return this.queryForObject(finder, User.class);
	}

	@Override
	public List<Qrcode> findQrcodeByQrcodeName(Qrcode qrcode, Page page)
			throws Exception {
		logger.info("--------"+qrcode.getQrcodeName()+"......."+qrcode.getBelongUser());
		Finder finder = Finder.getSelectFinder(Qrcode.class);
		finder.append(" where qrcode_name like :qrcodeName ");
		finder.append("and belong_user = :belongUser");
		finder.append(" and state = '1' order by create_date desc ");
		finder.setParam("qrcodeName", "%"+qrcode.getQrcodeName()+"%");
		finder.setParam("belongUser", qrcode.getBelongUser());
		return this.queryForList(finder, Qrcode.class, page);
	}


}
