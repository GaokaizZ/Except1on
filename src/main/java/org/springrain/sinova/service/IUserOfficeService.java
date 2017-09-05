package org.springrain.sinova.service;

import java.util.List;

import org.springrain.frame.util.Page;
import org.springrain.sinova.dto.OfficeUserDTO;
import org.springrain.sinova.dto.UserNameDTO;
import org.springrain.sinova.dto.UserOfficeDTO;
import org.springrain.sinova.entity.UserOffice;
import org.springrain.system.entity.DicData;
import org.springrain.system.entity.User;
import org.springrain.system.service.IBaseSpringrainService;

/**
 * TODO 在此加入类描述
 * 
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version 2015-01-09 09:16:10
 * @see org.springrain.sinova.service.UserOffice
 */
public interface IUserOfficeService extends IBaseSpringrainService {

	/**
	 * 根据ID查找
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	UserOffice findUserOfficeById(Object id) throws Exception;

	/**
     * 根据营业厅管理员的userId查找该营业厅的营业员
     * @param userId
     * @return
     * @throws Exception
     */
    List<User> findUserByUserId(String userId) throws Exception;
	/**
	 * 
	 * @description 根据用户ID查询用户地市、区县、营业厅、岗位信息 <br/>
	 *              用户必须有效
	 * @date 2015年1月10日 下午6:42:40 <br/>
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public UserOfficeDTO findUserOfficeDTOById(Object id) throws Exception;
	
	/**
	 * 
	 * @description 根据用户ID查询用户地市、区县、营业厅、岗位信息 <br/>
	 *              用户必须有效
	 * @date 2015年1月10日 下午6:42:40 <br/>
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public UserOffice findUserOfficeDTOByUserId(Object id) throws Exception;
	
	
	
	/**
	 * 根据用户查询用户地市、区县、营业厅，用户必须有效
	 * @param officeuserDTO
	 * @return
	 * @throws Exception
	 */
	public List<OfficeUserDTO> findOfficeUserDTOByOffice( OfficeUserDTO officeUserDTO, Page page) throws Exception;
	
	/**
	 * 根据地市编码查询用户
	 * @param officeUserDTO
	 * @return
	 * @throws Exception
	 */
	public List<User> findOfficeUserDTOByOffice( UserOffice userOffice) throws Exception;
	
	/**
	 * 根据地市编码查询用户
	 * @param officeUserDTO
	 * @return
	 * @throws Exception
	 */
	public List<User> findOfficeUserDTOByOffice(UserOffice userOffice,Page page) throws Exception;
	
	
	/**
	 * 查找未分配营业厅的用户
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<UserNameDTO> findUserNotInOffice(Page page, UserNameDTO userNameDTO) throws Exception;

	/**
	 * 根据pathKey查询所有字典
	 * @param pathtypekey
	 * @return
	 * @throws Exception
	 */
	List<DicData> findListDicData() throws Exception;
	
	/**
	 * 根据城市编码来查询信息
	 * 王玉军 
	 * @param regionCode
	 * @return
	 * @throws Exception
	 */
	List<UserOffice> findUserOfficeByRegionCode(String regionCode) throws Exception;
	
	/**通过地市编码，营业厅等查询
	 * @param dicRoleCode 
	 * @param user
	 * @param userOffice
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<User> findOfficeUserDTOByOffice(String dicRoleCode,User user,UserOffice userOffice,Page page) throws Exception;
}
