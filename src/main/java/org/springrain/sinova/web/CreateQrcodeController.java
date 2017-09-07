package org.springrain.sinova.web;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.IniRealm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springrain.frame.controller.BaseController;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.MessageUtils;
import org.springrain.frame.util.Page;
import org.springrain.frame.util.ReturnDatas;
import org.springrain.sinova.dto.BusinessGoodsDTO;
import org.springrain.sinova.dto.QrcodeDto;
import org.springrain.sinova.dto.UserBusinessDTO;
import org.springrain.sinova.entity.Qrcode;
import org.springrain.sinova.service.IQrcodeService;
import org.springrain.sinova.util.Const;
import org.springrain.sinova.util.QrcodeUtils;
import org.springrain.sinova.util.Util;
import org.springrain.system.entity.User;
import org.springrain.system.service.IUserService;
import org.springrain.system.util.Constant;
import org.springrain.system.util.PropertyFileCache;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping(value = "/createQrcode")
public class CreateQrcodeController extends BaseController{
	@Resource
    private IQrcodeService qrcodeService;
	@Resource
	private IUserService userService;
	
	private PropertyFileCache pfCache = PropertyFileCache.getInstance();
	
	@RequestMapping(value = "/list")
	public String enter(HttpServletRequest request, Model model, Qrcode qrcode)
			throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/sinova/piliang/piliang";
	}
	 
	@RequestMapping("/piliang")
	@ResponseBody
    public ReturnDatas piLiang(Model model, Qrcode qrcode, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
        returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
        
		List<User> userdto = new ArrayList<User>();
		int acount = 0;
		int num = 0;
        try {
        	Finder finder  = new Finder();
    		finder.append(" select distinct(u.workno) as workno,u.name as name,u.id as id from t_user u ");
    		finder.append(" where not exists (select q.belong_user  from t_qrcode q where u.workno=q.belong_user and q.type='1') ");
    		finder.append(" and u.account<> 'admin' ");
    		
    		userdto = userService.queryForList(finder, User.class);
    		String workno = "";
    		String name = "";
    		
    		if(userdto != null && userdto.size() > 0){
    			if(userdto.size()-2000 > 0){
    				num = 2000;
    				acount = userdto.size()-2000;
    				logger.info("************没有专属二维码数量大于2000");
    			}else{
    				num = userdto.size();
    				acount = 0;
    				logger.info("************没有专属二维码数量小于等于2000");
    			}
    			for(int i=0; i<num;i++){
    				workno = userdto.get(i).getWorkno();
    				name = userdto.get(i).getName();
    				logger.info("***********workno==="+workno+"***********name===="+name);
    				if(StringUtils.isBlank(workno)){
    					continue;
    				}
    				if(StringUtils.isBlank(name)){
    					continue;
    				}
    				 //设置param
    	            String business = "108:,BCAF7936:108,";
    	            UserBusinessDTO ubDto =null;
    	            if(StringUtils.isNotEmpty(business) && StringUtils.isNotEmpty(workno)){
    	               ubDto= userBusiness(business,workno);
    	            }
    	            
    	            //二维码名称
    	            String codeName = name+"的专属二维码";
    	            String param = JSON.toJSONString(ubDto);
    		          
    				// 二维码图片保存路径
    				String path = pfCache.get(Const.SYSTEM_FILE, "qrcodePath");
    				// 图片名称
    				String fileName = Util.getUniqueSuffix() +".png";
    				// 二维码生成前面的url
    				String qrcodeUrl = pfCache.get(Const.SYSTEM_FILE, "qrcodeUrl");
    				String uuid = UUID.randomUUID().toString();
    				// 流水号
    				String flowNo = uuid.replace("-", "");
    				String filePath = request.getServletContext().getRealPath("/");
    				
    				// 生成二维码
//    				QrcodeUtils.encoderQRCode(qrcodeUrl + "?param=" + flowNo, filePath + path + fileName,filePath + path+"logo.png");

    				// save Qrcode 表
    				Qrcode qrcodeDto = new Qrcode();
    				qrcodeDto.setId(null);
    	            qrcodeDto.setQrcodeName(codeName);
    	            qrcodeDto.setBelongUser(workno);
    	            qrcodeDto.setCreateUser(workno);
    	            qrcodeDto.setCreateDate(new Date());
    	            qrcodeDto.setIconName(fileName);
    	            qrcodeDto.setIconUrl(path+fileName);
    	            qrcodeDto.setState(Constant.ONE);
    	            qrcodeDto.setParamStr(param);
    	            qrcodeDto.setFlowNo(flowNo);
    	            qrcodeDto.setType("1");
    	            qrcodeService.saveQrcode(qrcodeDto);
    				
    			}
    		}
		} catch (Exception e) {
			String errorMessage = e.getLocalizedMessage();
            logger.error(errorMessage);
            returnObject.setStatus(ReturnDatas.ERROR);
            returnObject.setMessage(MessageUtils.UPDATE_ERROR);
            return returnObject;
		}
        returnObject.setStatus(ReturnDatas.SUCCESS);
        returnObject.setMessage("批量生成成功,还有"+acount+"位用户没有专属二维码！");
		return returnObject;
    }   
	 
	 
	 
	@RequestMapping("/export")
    public ReturnDatas listexport(HttpServletRequest request, HttpServletResponse response, Model model, QrcodeDto qrcode)
            throws Exception {
    	logger.info("导出二维码开始");
    	String path = pfCache.get(Const.SYSTEM_FILE, "expPath");
     	String sysPath = request.getServletContext().getRealPath("/");
    	String fileName = path+Util.getSendTime()+GlobalStatic.excelext;
    	
        Finder finder = new Finder();
        finder.append("select q.*,u.name as name from ").append(Finder.getTableName(Qrcode.class)).append(" q left join t_user u on q.belong_user=u.workno  where q.type ='1' ");
		finder.append(" order by q.create_date desc");

    		 
    	
    	WritableWorkbook book = Workbook.createWorkbook(new File(sysPath+fileName));
    	WritableSheet sheet = book.createSheet("二维码", 0);
    	sheet.addCell(new Label(0, 0, "二维码名称"));
    	sheet.addCell(new Label(1, 0, "归属人"));
    	sheet.addCell(new Label(2, 0, "创建人"));
    	sheet.addCell(new Label(3, 0, "创建时间"));
    	sheet.addCell(new Label(4, 0, "二维码"));
    	sheet.addCell(new Label(5, 0, " 姓名"));
        try {
        	int count=0;
        	Page page=new Page();
        	page.setPageSize(3000);
        	List<QrcodeDto> datas =null;
        	for (int j = 1; j <= 1000; j++) {
        		page.setPageIndex(j);
        		datas = qrcodeService.queryForList(finder, QrcodeDto.class,page);
        		if(datas!=null && !datas.isEmpty()){
        			int cruuNum=0;
            		for(int i=count; i<count+datas.size(); i++){
            			
            			sheet.setRowView(i+i+1, 2000); 
            			sheet.setColumnView(4, 20);
            			sheet.addCell(new jxl.write.Label(0, i+i+1, datas.get(cruuNum).getQrcodeName()));
            			sheet.addCell(new Label(1, i+i+1, datas.get(cruuNum).getBelongUser()));
            			sheet.addCell(new Label(2, i+i+1, datas.get(cruuNum).getCreateUser()));
            			
            			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            			String str = new String();
            			str = format.format(datas.get(cruuNum).getCreateDate());
            			sheet.addCell(new jxl.write.Label(3, i+i+1, str));
            			String imgUrl="";
            			imgUrl="/upload/logo.png";
            			if(null==datas.get(cruuNum).getIconUrl()){
            				
            			}else{
            				imgUrl=datas.get(cruuNum).getIconUrl();
            			}
            			WritableImage image = new WritableImage(4,i+i+1,1,1,new File(sysPath+imgUrl));
            			sheet.addImage(image);
            			sheet.addCell(new Label(5, i+i+1, datas.get(cruuNum).getName()));
            			cruuNum++;
            		}
            		count+=datas.size();
            	}else{
            		break;
            	}
        		
    		}
        	book.write();
        	book.close();
			
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
    //    File file = new File(sysPath,fileName);
        downFile(response, sysPath+fileName, true, true);
        logger.info("导出二维码结束");
        return null;
    }

 /**
     * 根据tree数据转为UserBusinessDTO,
     * 返回UserBusinessDTO格式数据
     * 
     * @param business
     * @param workNo
     * @throws Exception
     * @author <yyb>
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public UserBusinessDTO userBusiness(String business,String workNo){
        UserBusinessDTO businessDto = new UserBusinessDTO();
        businessDto.setUserCode(workNo);
        String[] businessTemp =business.split(",");
        List list = new ArrayList();
        BusinessGoodsDTO goodstemp = null;
        String flag="";
        List<String> listTemp=null;
        for (int i = 0; i < businessTemp.length; i++) {
            String busTemp =businessTemp[i];
            if(StringUtils.isNotBlank(busTemp)){
                
                String id=busTemp.substring(0,busTemp.indexOf(":"));
                String pid =busTemp.substring(busTemp.indexOf(":")+1);
                if(!"".equals(flag)&&!id.equals(flag)&&StringUtils.isBlank(pid)){
                    goodstemp.setGoodsList(listTemp);
                    list.add(goodstemp);
                }
                
                if(StringUtils.isNotBlank(id)&& StringUtils.isBlank(pid)){
                    goodstemp=new BusinessGoodsDTO();
                    goodstemp.setBusinessCode(id);
                    listTemp = new ArrayList();
                    flag =id;
                } 
                if((StringUtils.isNotEmpty(flag)&&StringUtils.isNotEmpty(pid))||flag.equals(pid)){
                    listTemp.add(id);
                }
                
            }
        }
        if(businessTemp.length>0){
            goodstemp.setGoodsList(listTemp);
            list.add(goodstemp);
        }
        businessDto.setBusGoodsList(list);
        return businessDto;
    }

}
