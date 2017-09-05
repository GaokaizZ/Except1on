package org.springrain.sinova.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springrain.frame.entity.IBaseEntity;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.Page;
import org.springrain.sinova.dto.SpecialQrcodeDTO;
import org.springrain.sinova.dto.SpecialTreeDTO;
import org.springrain.sinova.entity.Business;
import org.springrain.sinova.entity.Goods;
import org.springrain.sinova.entity.SpecialQrcode;
import org.springrain.sinova.entity.UserBusiness;
import org.springrain.sinova.service.ISpecialQrcodeService;
import org.springrain.system.entity.DicData;
import org.springrain.system.service.BaseSpringrainServiceImpl;



@Service("specialQrcodeService")
public class SpecialQrcodeServiceImpl extends BaseSpringrainServiceImpl implements ISpecialQrcodeService{
	
	 @Override
		public String  save(Object entity ) throws Exception{
		 SpecialQrcode speQrcodecial=(SpecialQrcode) entity;
			return super.save(speQrcodecial).toString();
		}

	    @Override
		public String  saveorupdate(Object entity ) throws Exception{
	    	SpecialQrcode speQrcodecial=(SpecialQrcode) entity;
			return super.saveorupdate(speQrcodecial).toString();
		}

		@Override
	    public Integer update(IBaseEntity entity ) throws Exception{
			SpecialQrcode speQrcodecial=(SpecialQrcode) entity;
			return super.update(speQrcodecial);
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

		/* 
		 * 根据用户ID查询绑定的业务
		 */
		@Override
		public List<Business> findBusByUserId(String userId) throws Exception {
			logger.info("-----specialQrcodeServiceImpl------findBusByUserId---start---userId----"+userId);
			logger.info("-----specialQrcodeServiceImpl------findBusByUserId----根据用户ID查询绑定的业务------");
			if(StringUtils.isBlank(userId)){
				logger.info("-----specialQrcodeServiceImpl------findBusByUserId---1111---userId----"+userId);
				return null;
			}
			logger.info("-----specialQrcodeServiceImpl------findBusByUserId---222---userId----"+userId);
			Finder	finder = new Finder("select b.* from ");
			finder.append(Finder.getTableName(UserBusiness.class)).append(" ub, " );
			finder.append(Finder.getTableName(Business.class)).append(" b ");
			finder.append("where ub.bus_id = b.id and b.state='1' and b.up_down ='1' ");
			finder.append(" and ub.user_id = :userId ");
			finder.setParam("userId", userId);
			
			finder.append(" order by b.bus_code desc ");
			logger.info("-----specialQrcodeServiceImpl------findBusByUserId---end----");
			return queryForList(finder, Business.class);
		}

		@Override
		public List<Goods> findGoodsbyUserId(String userId) throws Exception {
			logger.info("-----specialQrcodeServiceImpl------findGoodsbyUserId---start---userId----"+userId);
			if(StringUtils.isBlank(userId)){
				logger.info("-----specialQrcodeServiceImpl------findGoodsbyUserId---userId不为空----");
				return null;
			}
			logger.info("-----specialQrcodeServiceImpl------findGoodsbyUserId---start---userId----"+userId);
			Finder	finder = new Finder("select distinct  g.id as goodsId , g.goods_name as goodsName, g.fee_code as feeCode, g.bus_id as busId from ");
			finder.append(Finder.getTableName(UserBusiness.class)).append(" ub, " );
			finder.append(Finder.getTableName(Business.class)).append(" b, ");
			finder.append(Finder.getTableName(Goods.class)).append(" g ");
			finder.append("where ub.bus_id = b.id and b.state='1' and b.up_down ='1' ");
			finder.append(" and g.bus_id = b.id and g.state='1' and g.up_down = '1' ");
			finder.append(" and ub.user_id = :userId ");
			finder.setParam("userId", userId);
			finder.append(" order by g.fee_code desc ");
			logger.info("-----specialQrcodeServiceImpl------findGoodsbyUserId---end---");
			return queryForList(finder, Goods.class);
		}

		@Override
		public List<SpecialQrcodeDTO> findSpecialQrcodeDTO(SpecialQrcodeDTO specialQrcode, Page page) throws Exception {
			if(StringUtils.isBlank(specialQrcode.getBelongUser())){
				return null;
			}
			Finder finder = new Finder();
			finder.append(" select s.*, b.bus_name as busName, b.bus_code as busCode, g.goods_name as goodsName,g.fee_code as feeCode from ");
			finder.append(Finder.getTableName(SpecialQrcode.class)).append(" s, ");
			finder.append(Finder.getTableName(Business.class)).append(" b, ");
			finder.append(Finder.getTableName(Goods.class)).append(" g ");
			finder.append(" where s.belong_user =:belongUser ");
			finder.setParam("belongUser", specialQrcode.getBelongUser());
			finder.append(" and s.bus_id=b.id and s.fee_code=g.fee_code and b.id = g.bus_id and b.state='1' and b.up_down='1' and g.state='1' and g.up_down = '1' ");
			if(StringUtils.isNotBlank(specialQrcode.getBusCode())){
				finder.append(" and b.bus_code =:busCode ");
				finder.setParam("busCode", specialQrcode.getBusCode().trim());
			}
			if(StringUtils.isNotBlank(specialQrcode.getBusName())){
				finder.append(" and b.bus_name like :busName ");
				finder.setParam("busName", "%"+specialQrcode.getBusName().trim()+"%");
			}
			if(StringUtils.isNotBlank(specialQrcode.getType())){
				finder.append(" and s.type =:type ");
				finder.setParam("type", specialQrcode.getType());
			}
			finder.append(" order by g.fee_code  ");
			return queryForList(finder, SpecialQrcodeDTO.class, page);
		}

		@Override
		public List<Business> findBus(SpecialQrcodeDTO specialQrcode, Page page) throws Exception {
			if(StringUtils.isBlank(specialQrcode.getBelongUser())){
				return null;
			}
			Finder	finder = new Finder("select distinct b.* from ");
			finder.append(Finder.getTableName(UserBusiness.class)).append(" ub, " );
			finder.append(Finder.getTableName(SpecialQrcode.class)).append(" s, ");
			finder.append(Finder.getTableName(Business.class)).append(" b, ");
			finder.append(Finder.getTableName(Goods.class)).append(" g ");
			finder.append("where ub.bus_id = b.id and b.state='1' and b.up_down ='1' and s.bus_id=b.id and s.fee_code=g.fee_code and s.belong_user=ub.user_id ");
			finder.append(" and ub.user_id = :userId ");
			finder.setParam("userId", specialQrcode.getBelongUser());
			if(StringUtils.isNotBlank(specialQrcode.getBusCode())){
				finder.append(" and b.bus_code =:busCode ");
				finder.setParam("busCode", specialQrcode.getBusCode().trim());
			}
			if(StringUtils.isNotBlank(specialQrcode.getBusName())){
				finder.append(" and b.bus_name like :busName ");
				finder.setParam("busName", "%"+specialQrcode.getBusName().trim()+"%");
			}
			if(StringUtils.isNotBlank(specialQrcode.getType())){
				finder.append(" and s.type =:type ");
				finder.setParam("type", specialQrcode.getType());
			}
			finder.append(" order by b.bus_code desc ");
			return queryForList(finder, Business.class, page);
		}

		@Override
		public void deleteByBelongUserId(String userId) throws Exception {
			Finder finder = new Finder();
			finder.append(" delete from ").append(Finder.getTableName(SpecialQrcode.class));
			finder.append(" s where s.belong_user =:userId ");
			finder.setParam("userId", userId);
			
		}

		/* 
		 * 业务类型树(id,name)
		 */
		@Override
		public List<SpecialTreeDTO> findTypeBusDTO(SpecialQrcodeDTO specialQrcode) throws Exception {
			if(StringUtils.isBlank(specialQrcode.getBelongUser())){
				return null;
			}
			Finder finder = new Finder();
			finder.append(" select  distinct(dd.remark) as id, dd.name as name from ");
			finder.append(Finder.getTableName(SpecialQrcode.class)).append(" s, ");
			finder.append(Finder.getTableName(Business.class)).append(" b, ");
			finder.append(Finder.getTableName(Goods.class)).append(" g, ");
			finder.append(Finder.getTableName(DicData.class)).append(" dd ");
			finder.append(" where s.belong_user =:belongUser ");
			finder.setParam("belongUser", specialQrcode.getBelongUser());
			finder.append(" and s.bus_id=b.id and s.fee_code=g.fee_code and b.id = g.bus_id and b.state='1' and b.up_down='1' and g.state='1' and g.up_down = '1' ");
			finder.append("  and dd.typekey = 'bustype' and dd.remark = b.bus_type and dd.state = '1'");
			return queryForList(finder, SpecialTreeDTO.class);
		}

		/* 
		 * 业务树(id,name,pid)
		 */
		@Override
		public List<SpecialTreeDTO> findBusTreeDTO(SpecialQrcodeDTO specialQrcode) throws Exception {
			if(StringUtils.isBlank(specialQrcode.getBelongUser())){
				return null;
			}
			Finder finder = new Finder();
			finder.append(" select  distinct(s.bus_id) as id, b.bus_name as name, b.bus_type as pid from ");
			finder.append(Finder.getTableName(SpecialQrcode.class)).append(" s, ");
			finder.append(Finder.getTableName(Business.class)).append(" b, ");
			finder.append(Finder.getTableName(Goods.class)).append(" g, ");
			finder.append(Finder.getTableName(DicData.class)).append(" dd ");
			finder.append(" where s.belong_user =:belongUser ");
			finder.setParam("belongUser", specialQrcode.getBelongUser());
			finder.append(" and s.bus_id=b.id and s.fee_code=g.fee_code and b.id = g.bus_id and b.state='1' and b.up_down='1' and g.state='1' and g.up_down = '1' ");
			finder.append("  and dd.typekey = 'bustype' and dd.remark = b.bus_type and dd.state = '1'");
			return queryForList(finder, SpecialTreeDTO.class);
		}

		/* 
		 * 商品树(id,name,pid)
		 */
		@Override
		public List<SpecialTreeDTO> findGoodsTreeDTO(SpecialQrcodeDTO specialQrcode) throws Exception {
			if(StringUtils.isBlank(specialQrcode.getBelongUser())){
				return null;
			}
			Finder finder = new Finder();
			finder.append(" select  s.fee_code as id, s.bus_id as pid, g.goods_name as name, s.type as spQrcodeType from ");
			finder.append(Finder.getTableName(SpecialQrcode.class)).append(" s, ");
			finder.append(Finder.getTableName(Business.class)).append(" b, ");
			finder.append(Finder.getTableName(Goods.class)).append(" g, ");
			finder.append(Finder.getTableName(DicData.class)).append(" dd ");
			finder.append(" where s.belong_user =:belongUser ");
			finder.setParam("belongUser", specialQrcode.getBelongUser());
			finder.append(" and s.bus_id=b.id and s.fee_code=g.fee_code and b.id = g.bus_id and b.state='1' and b.up_down='1' and g.state='1' and g.up_down = '1' ");
			finder.append("  and dd.typekey = 'bustype' and dd.remark = b.bus_type and dd.state = '1'");
			return queryForList(finder, SpecialTreeDTO.class);
		}

		@Override
		public SpecialQrcode findByFeeCode(SpecialQrcode specialQrcode) throws Exception {
			if(StringUtils.isBlank(specialQrcode.getFeeCode())){
				return null;
			}
			if(StringUtils.isBlank(specialQrcode.getBelongUser())){
				return null;
			}
			Finder finder = new Finder();
			finder.append(" select s.* from ");
			finder.append(Finder.getTableName(SpecialQrcode.class)).append(" s ");
			finder.append(" where s.belong_user =:belongUser and s.fee_code =:feeCode and s.type=:type ");
			finder.setParam("belongUser", specialQrcode.getBelongUser());
			finder.setParam("feeCode", specialQrcode.getFeeCode());
			finder.setParam("type", specialQrcode.getType());
			return queryForObject(finder, SpecialQrcode.class);
		}


		

	

}
