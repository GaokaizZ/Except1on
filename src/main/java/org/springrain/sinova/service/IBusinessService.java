package org.springrain.sinova.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springrain.frame.util.Page;
import org.springrain.sinova.dto.FrontListDTO;
import org.springrain.sinova.dto.TreeBusinessGoodsDTO;
import org.springrain.sinova.entity.Business;
import org.springrain.sinova.entity.Goods;
import org.springrain.sinova.entity.UserBusiness;
import org.springrain.system.service.IBaseSpringrainService;

/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version  2015-01-09 09:13:13
 * @see org.springrain.sinova.service.Business
 */
public interface IBusinessService extends IBaseSpringrainService {

	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Business findBusinessById(Object id) throws Exception;
	/**
     * 根据userId查找对应的业务
     * @param userId
     * @return
     * @throws Exception
     */
    List<Business> findBusinessByUserId(UserBusiness userBusiness, Business business, Page page) throws Exception;
    /**
     * 根据userId查找未分配的业务
     * @param userId
     * @return
     * @throws Exception
     */
    List<Business> findBusNot(UserBusiness userBusiness, String userIds, Page page, Business business) throws Exception;
    
    /**
     * 根据userId查找对应的业务
     * @param userId
     * @return
     * @throws Exception
     */
    List<Business> findBusinessByRegionId(String regionId, String busName, String busCode, Page page) throws Exception;
    /**
     * 根据regionId查找未分配的业务
     * @param userId
     * @return
     * @throws Exception
     */
    
    List<Business> findBusRegion(String regionId, Page page)throws Exception;
    /**
     * 业务展示接口
     * @param request
     * @param clazz
     * @param page
     * @return
     * @throws Exception
     */
    public List<Business> queryForBusinessList(HttpServletRequest request,Class<Business> clazz, Page page) throws Exception;

	FrontListDTO getFrontListDTOByBusCodeAndWorknoAndFeeCode(String busCode,String workno,String feeCode) throws Exception;
	
	FrontListDTO getFrontListDTOByBusCodeAndWorknoAndFeeCodeAndBusType(String busCode,String workno,String feeCode,String busType) throws Exception;
	//全业务查询
	FrontListDTO getFrontListDTOByQyw(String busCode,String workno,String feeCode)throws Exception;
	
	List<Business> findByBus(Business bus) throws Exception;
	
	void deleteBusMore(Object ids[] ) throws Exception;
	
	void upDown(Object id,Object upDown) throws Exception;
	
	/**
     * 根据userId，查找对应的业务以及产品
     * @param userId
     * @return
     * @throws Exception
     */
    List<TreeBusinessGoodsDTO> findTreeByUserId(String userId) throws Exception;
    
    /**根据地市编码，查找对应的业务及商品
     * @param regionCode
     * @return
     * @throws Exception
     */
    List<TreeBusinessGoodsDTO> findTreeByRegion(String regionCode) throws Exception;

	
	List<Business> findListDataByIds(String ids) throws Exception;
	
	List<Business> findAllBus() throws Exception;
	
	List<Goods> findGoodsByBus() throws Exception;
	
	/**
	 * 查询该用户下的某类型下所有商品及业务(活动及档次)
	 * @param workno 工号
	 * @param busType 业务类型
	 * @return
	 * @throws Exception
	 */
	List<FrontListDTO> getFrontListDTOByWorkNo(String workno, String busType) throws Exception;
	
	/**
	 * 业务表查询的重构
	 * @param request
	 * @param model
	 * @param goods
	 * @param class1
	 * @return
	 * @throws Exception
	 */
	List<Business> queryForList(HttpServletRequest request, Model model, Goods goods)throws Exception;
	
	/**
	 * 查询所属业务
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	List<Business> searchBelongBusiness(HttpServletRequest request, Model model) throws Exception;
 	
}
