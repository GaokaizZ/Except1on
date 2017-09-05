package org.springrain.sinova.quartz;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.IniRealm;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springrain.frame.common.BaseLogger;
import org.springrain.frame.util.DateUtils;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.Page;
import org.springrain.sinova.dto.AcceptRecordDTO;
import org.springrain.sinova.dto.RotateDTO;
import org.springrain.sinova.entity.Rotate;
import org.springrain.sinova.interfaces.ISitechInterface;
import org.springrain.sinova.interfaces.sitech.WsGetLotteryResultInfo.LotInfo;
import org.springrain.sinova.interfaces.sitech.WsGetLotteryResultInfo.WsGetLotteryRequest;
import org.springrain.sinova.interfaces.sitech.WsGetLotteryResultInfo.WsGetLotteryResponse;
import org.springrain.sinova.service.IAcceptRecordService;
import org.springrain.sinova.service.IRotateService;
import org.springrain.sinova.util.Const;
import org.springrain.sinova.web.RotateController;
import org.springrain.system.util.PropertyFileCache;

@Component
public class MyTask extends BaseLogger {
	
	@Resource
	private ISitechInterface sitechInterFace;
	@Resource
	private IAcceptRecordService acceptRecordService;
	@Resource
	private IRotateService rotateService;
	
	private PropertyFileCache pfCache = PropertyFileCache.getInstance();

	//@Scheduled(cron="0/10 *  *  * * ? ")
	@Scheduled(cron="0 0 8 * * ?")
//	@Scheduled(cron="0 32 22 * * ?")
	public void taskCycle() throws Exception{
		String bDate=" 00:00:00";
		String eDate=" 23:59:59";
		Calendar calendar = Calendar.getInstance();
		
		String bTmpDate=DateUtils.convertDate2String(DateUtils.addDay(-1, new Date()))+bDate;
		String eTmpDate=DateUtils.convertDate2String(DateUtils.addDay(-1, new Date()))+eDate;

		if(DateUtils.convertDate2String(new Date()).equals(DateUtils.convertDate2String(DateUtils.getMinDate(calendar)))){
			calendar.add(Calendar.MONTH, -1);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
		}
		String minMDate=DateUtils.convertDate2String(DateUtils.getMinDate(calendar))+bDate;
		Date btime=DateUtils.parseDateTime(bTmpDate);
		Date etime=DateUtils.parseDateTime(eTmpDate);
		Date mtime=DateUtils.parseDateTime(minMDate);
		String daCount = "0";
		String accCount = "0";
		String offCount = "0";
		String moCount = "0";
		String offAllCount = "0";
		String cityCount = "";
		String cjAllCount = "0";
		String cjCount = "0";
		String allAccount="0";
		String mobiles = pfCache.get(Const.SYSTEM_FILE, "mobile");
		
		//日新增业务量
		Finder finder1 = new Finder();
		finder1.append("select count(*) as id from t_accept_record aa where datetime between :beginTime and :endTime ");
		finder1.setParam("beginTime", btime);
		finder1.setParam("endTime", etime);
		List<AcceptRecordDTO> data1 = acceptRecordService.queryForList(finder1, AcceptRecordDTO.class);
		if(data1 != null && data1.size()>0){
			AcceptRecordDTO dto1 = data1.get(0);
			daCount = dto1.getId();
		}
		
		//日新增营业员量
		Finder finder2 = new Finder();
		finder2.append("select count(*) as id from (SELECT aa.ACCOUNT, MIN(aa.datetime) dt FROM t_accept_record aa  GROUP BY aa.ACCOUNT) a");
		finder2.append(" where a.dt between :beginTime and :endTime ");
		finder2.setParam("beginTime", btime);
		finder2.setParam("endTime", etime);
		List<AcceptRecordDTO> data2 = acceptRecordService.queryForList(finder2, AcceptRecordDTO.class);
		if(data2 != null && data2.size()>0){
			AcceptRecordDTO dto2 = data2.get(0);
			accCount  = dto2.getId();
		}
		
		//日新增营业厅量
		Finder finder3 = new Finder();
		finder3.append("select count(*) as id from ( SELECT   ff.office_code, MIN (aa.datetime) AS dt FROM t_accept_record aa, t_user_office oo,t_goods gg,t_user uu,");
		finder3.append("t_office ff,t_region rr WHERE aa.ACCOUNT = uu.workno AND gg.fee_code = aa.fee_code AND uu.ID = oo.user_id AND ff.office_code = oo.office_code ");
		finder3.append("AND rr.region_code = oo.region_code GROUP BY ff.office_code) a where a.dt between :beginTime and :endTime ");
		finder3.setParam("beginTime", btime);
		finder3.setParam("endTime", etime);
		List<AcceptRecordDTO> data3 = acceptRecordService.queryForList(finder3, AcceptRecordDTO.class);
		if(data3 != null && data3.size()>0){
			AcceptRecordDTO dto3 = data3.get(0);
			offCount = dto3.getId();
		}
/*		
		//日地市办理量
		
		Finder finder4 = new Finder();
		finder4.append("select r1.region_name as id,NVL(a1.cou,0) as mobile from t_region r1 left join (select a.rcode rc,a.rname,sum(a.cou) cou from ");
		finder4.append("(SELECT   ff.office_name oname, rr.region_code rcode,rr.region_name rname,count(*) cou FROM t_accept_record aa, t_user_office oo, t_goods gg,");
		finder4.append(" t_user uu,t_office ff, t_region rr WHERE aa.ACCOUNT = uu.workno AND gg.fee_code = aa.fee_code AND uu.ID = oo.user_id AND ff.office_code = oo.office_code ");
		finder4.append("AND rr.region_code = oo.region_code  AND aa.datetime between :beginTime and :endTime  GROUP BY ff.office_name,rr.region_name,rr.region_code) a ");
		finder4.append("group by a.rcode,a.rname) a1 on r1.region_code = a1.rc");
		finder4.setParam("beginTime", btime);
		finder4.setParam("endTime", etime);
		List<AcceptRecordDTO> data4 = acceptRecordService.queryForList(finder4, AcceptRecordDTO.class);
		if(data4 != null && data4.size()>0){
			cityCount="其中：{";
			for(int i =0 ; i < data4.size();i++){
				AcceptRecordDTO dto4= data4.get(i);
				cityCount = cityCount+"\n"+dto4.getId() +":"+dto4.getMobile()+",";
			}
			cityCount = cityCount.substring(0,cityCount.length()-1)+"}";
		}*/
		
		//地市日，月
		Finder finder4 = new Finder();
		finder4.append("select aaa.a1 as id, aaa.a2 as mobile, bbb.b2 as ip from (select r1.region_name a1, NVL(a1.cou, 0) a2 from t_region r1 left join (select a.rcode rc, a.rname, sum(a.cou) cou ");
		finder4.append("from (SELECT ff.office_name oname, rr.region_code rcode,rr.region_name rname, count(*) cou FROM t_accept_record aa,  t_user_office   oo,t_goods gg, t_user uu, t_office ff,");
		finder4.append("t_region rr WHERE aa.ACCOUNT = uu.workno AND gg.fee_code = aa.fee_code AND uu.ID = oo.user_id AND ff.office_code = oo.office_code AND rr.region_code = oo.region_code AND aa.datetime ");
		finder4.append(" between :beginTime and :endTime  GROUP BY ff.office_name,rr.region_name,rr.region_code) a ");
		finder4.append("group by a.rcode,a.rname) a1 on r1.region_code = a1.rc ) aaa, (select r1.region_name b1, NVL(a1.cou, 0) b2  from t_region r1 left join (select a.rcode rc, a.rname, sum(a.cou) cou ");
		finder4.append("from (SELECT ff.office_name oname, rr.region_code rcode,rr.region_name rname, count(*) cou FROM t_accept_record aa,  t_user_office   oo,t_goods gg, t_user uu, t_office ff,");
		finder4.append("t_region rr WHERE aa.ACCOUNT = uu.workno AND gg.fee_code = aa.fee_code AND uu.ID = oo.user_id AND ff.office_code = oo.office_code AND rr.region_code = oo.region_code AND aa.datetime ");
		finder4.append(" between :beginMTime and :endTime  GROUP BY ff.office_name,rr.region_name,rr.region_code) a group by a.rcode,a.rname) a1 on r1.region_code = a1.rc ) bbb  where aaa.a1 = bbb.b1");
		finder4.setParam("beginTime", btime);
		finder4.setParam("beginMTime", mtime);
		finder4.setParam("endTime", etime);
		List<AcceptRecordDTO> data4 = acceptRecordService.queryForList(finder4, AcceptRecordDTO.class);
		if(data4 != null && data4.size()>0){
			cityCount=",其中:{";
			for(int i =0 ; i < data4.size();i++){
				AcceptRecordDTO dto4= data4.get(i);
				cityCount = cityCount+"\n"+dto4.getId() +":月办理"+dto4.getIp()+",日新增"+dto4.getMobile()+",";
			}
			cityCount = cityCount.substring(0,cityCount.length()-1)+"}";
		}
		
		//月办理量
		Finder finder5 = new Finder();
		finder5.append("select count(*) as id from t_accept_record aa where datetime between :beginTime and :endTime ");
		finder5.setParam("beginTime", mtime);
		finder5.setParam("endTime", etime);
		List<AcceptRecordDTO> data5 = acceptRecordService.queryForList(finder5, AcceptRecordDTO.class);
		if(data5 != null && data5.size()>0){
			AcceptRecordDTO dto5 = data5.get(0);
			moCount = dto5.getId();
		}
		
		//营业厅总量
		Finder finder6 = new Finder();
		finder6.append("select count(*) as id from (SELECT ff.office_code from (SELECT a.ACCOUNT ACCOUNT  FROM t_accept_record a GROUP BY ");
		finder6.append("a.ACCOUNT) aa,t_user_office ff, t_user uu where uu.ID = ff.user_id  and aa.ACCOUNT = uu.workno group by ff.office_code)");
		List<AcceptRecordDTO> data6 = acceptRecordService.queryForList(finder6, AcceptRecordDTO.class);
		if(data6 != null && data6.size()>0){
			AcceptRecordDTO dto6 = data6.get(0);
			offAllCount = dto6.getId();
		}
		
		//日新增cj业务量
		Finder finder7 = new Finder();
		finder7.append("select count(*) as id from T_ROTATE aa where CREATE_TIME between :beginTime and :endTime ");
		finder7.setParam("beginTime", btime);
		finder7.setParam("endTime", etime);
		List<AcceptRecordDTO> data7 = acceptRecordService.queryForList(finder7, AcceptRecordDTO.class);
		if(data7 != null && data7.size()>0){
			AcceptRecordDTO dto7 = data7.get(0);
			cjCount = dto7.getId();
		}
		
		
		//月cj业务量
		Finder finder8 = new Finder();
		finder8.append("select count(*) as id from T_ROTATE aa where CREATE_TIME between :beginTime and :endTime ");
		finder8.setParam("beginTime", mtime);
		finder8.setParam("endTime", etime);
		List<AcceptRecordDTO> data8 = acceptRecordService.queryForList(finder8, AcceptRecordDTO.class);
		if(data8 != null && data8.size()>0){
			AcceptRecordDTO dto8 = data8.get(0);
			cjAllCount = dto8.getId();
		}
		
		//营业员总量
		Finder finder9 = new Finder();
//		finder9.append("select sum(count(*)) as id from  t_accept_record aa group by account ");
		finder9.append("select count(*) as id from (select count(*) from  t_accept_record aa group by account ) ");
		List<AcceptRecordDTO> data9 = acceptRecordService.queryForList(finder9, AcceptRecordDTO.class);
		if(data9 != null && data9.size()>0){
			AcceptRecordDTO dto9 = data9.get(0);
			allAccount = dto9.getId();
		}
		
		if(!StringUtils.equals(daCount, "0")){
			StringBuffer content = new StringBuffer("{山西移动“快扫”业务日报}");
			content.append(DateUtils.convertDate2String(DateUtils.addDay(-1, new Date())));
			content.append("：月办理:").append(moCount).append(",日新增").append(daCount).append(cityCount).append(",新增营业员:").append(accCount);
			//	content.append(",部署营业厅:").append(offAllCount);
			content.append(",累计:").append(allAccount);
			content.append(",新增营业厅:").append(offCount);
			content.append(",累计:").append(offAllCount).append(".");
//		content.append("抽奖活动月参与:").append(cjAllCount).append(",日抽奖:").append(cjCount).append(".");
			//logger.info(content.toString());
			String[] phones = mobiles.split(",");
			for(int i=0;i<phones.length;i++){
				this.sitechInterFace.smsSendInfo(phones[i],content.toString());
			}
		}
		
	}
	@Scheduled(cron="0 0/60  *  * * ? ")
	//@Scheduled(cron="0 0/5 * * * ?")
	public void taskRoate() throws Exception{
		logger.info("MyTask=taskRoate=======Start=");
		Realm r = new IniRealm();
		DefaultSecurityManager s = new DefaultSecurityManager(r);
		SecurityUtils.setSecurityManager(s);
		Page page=new Page();
		page.setPageSize(999999);
		RotateDTO rotate=new RotateDTO();
		rotate.setState("3");
		rotate.setBatCode(RotateController.BAT_CODE);
		List<Rotate> list=rotateService.findRoateByDTO(rotate, page);
		logger.info("MyTask=taskRoate=======list="+list);
		if(null!=list && list.size()>0){
			logger.info("MyTask=taskRoate=======listSize="+list.size());
			for (int i = 0; i < list.size(); i++) {
				Rotate tmpRotate=list.get(i);
				WsGetLotteryRequest request=new WsGetLotteryRequest();
				request.setActId(RotateController.ACT_ID);
				request.setPhoneNo(tmpRotate.getMobile());
				WsGetLotteryResponse response=sitechInterFace.getLottery(request);
				String returnCode=response.getReturnCode();
				logger.info("MyTask=taskRoate=======returnCode="+returnCode+"===mobile="+tmpRotate.getMobile());
				if(StringUtils.isNotBlank(returnCode)){
					String count=response.getCountLot();
					logger.info("MyTask=taskRoate=======getCountLot="+count+"===mobile="+tmpRotate.getMobile());
					if(StringUtils.isNotBlank(count)){
						if(count.equals("0")){
							tmpRotate.setState("0");
							tmpRotate.setPrize("0");
						}else if(count.equals("1")){
							LotInfo lnfo=response.getLotList().get(0);
							String dataStr=lnfo.getPrizeDate();
							dataStr=dataStr.substring(0, 8);
							String tmpdateStr=DateUtils.formatDateTime(tmpRotate.getCreateTime(),DateUtils.DATE_FORMAT2);
							logger.info("MyTask=taskRoate====count1===tmpdateStr="+tmpdateStr+"===dataStr=="+dataStr+"===mobile="+tmpRotate.getMobile());
							if(tmpdateStr.equals(dataStr)){
								tmpRotate.setState("1");
								tmpRotate.setPrize(lnfo.getPrizeLevel());
							}else{
								tmpRotate.setState("0");
								tmpRotate.setPrize("0");
							}
						}else{
							String tmpdateStr=DateUtils.formatDateTime(tmpRotate.getCreateTime(),DateUtils.DATE_FORMAT2);
							boolean flag=true;
							for (int j = 0; j < response.getLotList().size(); j++) {
								LotInfo lnfo=response.getLotList().get(j);
								String dataStr=lnfo.getPrizeDate();
								dataStr=dataStr.substring(0, 8);
								logger.info("MyTask=taskRoate====count2===tmpdateStr="+tmpdateStr+"===dataStr=="+dataStr+"===mobile="+tmpRotate.getMobile());
								if(tmpdateStr.equals(dataStr)){
									flag=false;
									tmpRotate.setState("1");
									tmpRotate.setPrize(lnfo.getPrizeLevel());
								}
							}
							if(flag){
								tmpRotate.setState("0");
								tmpRotate.setPrize("0");
							}
							
						}
						try {
							rotateService.update(tmpRotate);
						} catch (Exception e) {
							logger.info("MyTask=taskRoate===SAVEERROR====="+e.getMessage()+"===mobile="+tmpRotate.getMobile());
						}
					}
				}
			}
		}
		logger.info("MyTask=taskRoate=======END=");
	}
	public static void main(String[] args) {
//		String bDate=" 00:00:00";
//		String eDate=" 23:59:59";
//		String bTmpDate=DateUtils.convertDate2String(DateUtils.addDay(-1, new Date()))+bDate;
//		String eTmpDate=DateUtils.convertDate2String(DateUtils.addDay(-1, new Date()))+eDate;
//		System.out.println(bTmpDate);
//		System.out.println(eTmpDate);
		 
	}
}
