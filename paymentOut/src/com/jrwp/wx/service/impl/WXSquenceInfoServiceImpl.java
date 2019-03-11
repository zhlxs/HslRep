package com.jrwp.wx.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jrwp.wx.dao.WXSquenceInfoDao;
import com.jrwp.wx.entity.AppointmeInfoJson;
import com.jrwp.wx.entity.AppointmeInfoJsonForMachine;
import com.jrwp.wx.entity.Appointme_sync;
import com.jrwp.wx.entity.DeptGZHPic;
import com.jrwp.wx.entity.LedShow;
import com.jrwp.wx.entity.MachineConfigJson;
import com.jrwp.wx.entity.PmVisitor;
import com.jrwp.wx.entity.WXSquenceInfo;
import com.jrwp.wx.entity.WaitCountJson;
import com.jrwp.wx.entity.WarmingInfoJson;
import com.jrwp.wx.service.WXSquenceInfoService;

@Service("wXSquenceInfoService")
public class WXSquenceInfoServiceImpl implements WXSquenceInfoService{
	@Resource
	private WXSquenceInfoDao wXSquenceInfoDao;
	
	 @Override
	    public String getnextSquence(int isappointment, String date, Integer isearly, Integer deptid,int businessType) {
		 if(businessType == 4){//疑难业务
			 String squence = wXSquenceInfoDao.getYNSquence(isappointment, date, isearly, deptid);
		        System.out.println("疑难目前最大squence是：" + squence);
		        String head = "";
		        if (isappointment == 0) {
		            head = "D";
		        } else {

		            if (isearly == 1) {
		                head = "E";
		            } else {
		                head = "F";
		            }
		        }
		        int squ = 1;
		        if (squence != null && !squence.equals("")) {
		            squ = Integer.parseInt(squence.substring(1, 4)) + 1;
		        }
		        StringBuffer next = new StringBuffer(head);
		        int i = String.valueOf(squ).length();
		        for (int j = i; j < 3; j++) {
		            next.append("0");
		        }
		        next.append(squ);
		        return next.toString();
		 }else{//综合和代办业务
			 String squence = wXSquenceInfoDao.getSquence(isappointment, date, isearly, deptid);
		        System.out.println("目前最大squence是：" + squence);
		        String head = "";
		        if (isappointment == 0) {
		            head = "B";
		        } else {

		            if (isearly == 1) {
		                head = "C";
		            } else {
		                head = "A";
		            }
		        }
		        int squ = 1;
		        if (squence != null && !squence.equals("")) {
		            squ = Integer.parseInt(squence.substring(1, 4)) + 1;
		        }
		        StringBuffer next = new StringBuffer(head);
		        int i = String.valueOf(squ).length();
		        for (int j = i; j < 3; j++) {
		            next.append("0");
		        }
		        next.append(squ);
		        return next.toString();
		 }
		 	
	    }

	@Override
	@Transactional
	public Long insertSquenceInfoAndUpdateStates(WXSquenceInfo squenceInfo) {
		// TODO Auto-generated method stub
		wXSquenceInfoDao.insertSquenceInfo(squenceInfo);
		wXSquenceInfoDao.updateStates(squenceInfo.getAppointme_infoid());
		return squenceInfo.getId();
	}

	@Override
	public Long insertXcSquenceInfo(WXSquenceInfo squenceInfo) {
		// TODO Auto-generated method stub
		wXSquenceInfoDao.insertXcSquenceInfo(squenceInfo);
		
		return squenceInfo.getId();
	}

	@Override
	public List<WaitCountJson> getWaitCount(Integer deptid,String date) {
		// TODO Auto-generated method stub
		return wXSquenceInfoDao.getWaitCount(deptid,date);
	}

	@Override
	public int getWaitCountOne(Integer deptid, Date date, int businessType) {
		// TODO Auto-generated method stub
		return wXSquenceInfoDao.getWaitCountOne(deptid, date, businessType);
	}

	@Override
	public List<AppointmeInfoJsonForMachine> getAppointmeByCardNumber(String date,
			String cardNumber, Integer deptid) {
		// TODO Auto-generated method stub
		return wXSquenceInfoDao.getAppointmeByCardNumber(date, cardNumber, deptid);
	}

	@Override
	public int getWaitCountWXZS(Integer deptid, String date, int businessType) {
		// TODO Auto-generated method stub
		return wXSquenceInfoDao.getWaitCountWXZS(deptid, date, businessType);
	}

	@Override
	public void insertCheckResult(String name, String cardNumber,
			int checkResult,String xsd,byte[] b,byte[] cardb) {
		// TODO Auto-generated method stub
		wXSquenceInfoDao.insertCheckResult(name, cardNumber, checkResult,xsd,b,cardb);
	}

	@Override
	public int getXcCount(Integer deptid, String date, int businessType) {
		// TODO Auto-generated method stub
		return wXSquenceInfoDao.getXcCount(deptid, date, businessType);
	}

	@Override
	public MachineConfigJson getMachineConfig(String deviceNumber) {
		// TODO Auto-generated method stub
		return wXSquenceInfoDao.getMachineConfig(deviceNumber);
	}

	@Override
	public AppointmeInfoJson getAppointmeInfoJson() {
		// TODO Auto-generated method stub
		return wXSquenceInfoDao.getAppointmeInfoJson();
	}

	@Override
	public void updateIsCheck(Long id) {
		// TODO Auto-generated method stub
		wXSquenceInfoDao.updateIsCheck(id);
	}

	@Override
	public WarmingInfoJson getWarmingInfoJson() {
		// TODO Auto-generated method stub
		return wXSquenceInfoDao.getWarmingInfoJson();
	}

	@Override
	public void updateXCIsCheck(Long id) {
		// TODO Auto-generated method stub
		wXSquenceInfoDao.updateXCIsCheck(id);
	}

	@Override
	public List<PmVisitor> getVisitor(Date nowdate, Integer deptid, int status,
			Integer seq) {
		// TODO Auto-generated method stub
		return wXSquenceInfoDao.getVisitor(nowdate, deptid, status, seq);
	}

	@Override
	public List<PmVisitor> getCalledVisitor(Date nowdate, Integer deptid) {
		// TODO Auto-generated method stub
		return wXSquenceInfoDao.getCalledVisitor(nowdate, deptid);
	}

	@Override
	public Integer getConfigIdByDeviceNumber(String deviceNumber) {
		// TODO Auto-generated method stub
		return wXSquenceInfoDao.getConfigIdByDeviceNumber(deviceNumber);
	}

	@Override
	public void insertDeviceNumber(String deviceNumber) {
		// TODO Auto-generated method stub
		wXSquenceInfoDao.insertDeviceNumber(deviceNumber);
	}

	@Override
	public MachineConfigJson getMachineConfigJsonByDeviceNumber(
			String deviceNumber) {
		// TODO Auto-generated method stub
		return wXSquenceInfoDao.getMachineConfigJsonByDeviceNumber(deviceNumber);
	}

	@Override
	public DeptGZHPic getgzhpic(String deviceNumber) {
		// TODO Auto-generated method stub
		return wXSquenceInfoDao.getgzhpic(deviceNumber);
	}

	@Override
	public void insertSquenceSync(Long squence_id, int status, int isexport) {
		// TODO Auto-generated method stub
		wXSquenceInfoDao.insertSquenceSync(squence_id, status, isexport);
	}

	@Override
	public int getAbleAcceptCount(String deptid) {
		// TODO Auto-generated method stub
		return wXSquenceInfoDao.getAbleAcceptCount(deptid);
	}

	@Override
	public int getAlreadyAcceptCount(String cardNumber, String proxyCardNumber,
			String dateString) {
		// TODO Auto-generated method stub
		return wXSquenceInfoDao.getAlreadyAcceptCount(cardNumber, proxyCardNumber, dateString);
	}

	@Override
	public String getBlackCardNumber(String cardNumber, String proxyCardNumber) {
		// TODO Auto-generated method stub
		return wXSquenceInfoDao.getBlackCardNumber(cardNumber, proxyCardNumber);
	}

	@Override
	public Long insertDlSquenceInfo(WXSquenceInfo squenceInfo) {
		// TODO Auto-generated method stub
		wXSquenceInfoDao.insertDlSquenceInfo(squenceInfo);
		return squenceInfo.getId();
	}

	@Override
	public void insertWarmInfo(String warmingInfo, Long mac_photomatchid,
			String name, String cardnumber) {
		// TODO Auto-generated method stub
		wXSquenceInfoDao.insertWarmInfo(warmingInfo, mac_photomatchid, name, cardnumber);
	}

	@Override
	public void insertAppointWarmInfo(String warmingInfo, Long wx_appointmeinfoid, String name,
			String cardNumber, String phone) {
		// TODO Auto-generated method stub
		wXSquenceInfoDao.insertAppointWarmInfo(warmingInfo, wx_appointmeinfoid, name, cardNumber, phone);
	}

	@Override
	public List<LedShow> getShow(Long deptid, int status) {
		// TODO Auto-generated method stub
		return wXSquenceInfoDao.getShow(deptid,status);
	}

	@Override
	public Appointme_sync getWxAppintStatus() {
		// TODO Auto-generated method stub
		return wXSquenceInfoDao.getWxAppintStatus();
	}

	@Override
	@Transactional
	public void updateAppointStatus(long id, int status) {
		// TODO Auto-generated method stub
		wXSquenceInfoDao.updateAppointStatus(id,status);
		wXSquenceInfoDao.updateAppointIsExport(id);
	}


}
