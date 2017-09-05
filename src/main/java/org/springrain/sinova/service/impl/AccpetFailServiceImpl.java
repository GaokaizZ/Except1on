package org.springrain.sinova.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springrain.frame.entity.IBaseEntity;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.Page;
import org.springrain.sinova.entity.AcceptFail;
import org.springrain.sinova.service.IAcceptFailService;
import org.springrain.system.service.BaseSpringrainServiceImpl;

@Service("acceptFailService")
public class AccpetFailServiceImpl extends BaseSpringrainServiceImpl implements IAcceptFailService{
	
	  @Override
		public String  save(Object entity ) throws Exception{
			AcceptFail acceptRecord=(AcceptFail) entity;
			return super.save(acceptRecord).toString();
		}

	    @Override
		public String  saveorupdate(Object entity ) throws Exception{
	    	AcceptFail acceptRecord=(AcceptFail) entity;
			return super.saveorupdate(acceptRecord).toString();
		}

		@Override
	    public Integer update(IBaseEntity entity ) throws Exception{
			AcceptFail acceptRecord=(AcceptFail) entity;
			return super.update(acceptRecord);
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

}
