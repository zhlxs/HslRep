package com.jrwp.wx.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

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

public interface WXSquenceInfoDao {

	String getSquence(@Param("isappointment")int isappointment, @Param("nowdate")String nowdate, @Param("isearly")Integer isearly,
			@Param("deptid")Integer deptid);

	Long insertSquenceInfo(WXSquenceInfo squenceInfo);

	void updateStates(@Param("appointme_infoid")Long appointme_infoid);
	public Long insertXcSquenceInfo(WXSquenceInfo squenceInfo);
	public List<WaitCountJson> getWaitCount(@Param("deptid")Integer deptid,@Param("date")String date);
	public int getWaitCountOne(@Param("deptid")Integer deptid, @Param("date")Date date, @Param("businessType")int businessType);
	public List<AppointmeInfoJsonForMachine> getAppointmeByCardNumber(@Param("date")String date,
			@Param("cardNumber")String cardNumber, @Param("deptid")Integer deptid);
	public int getWaitCountWXZS(@Param("deptid")Integer deptid, @Param("date")String date, @Param("businessType")int businessType);
	public void insertCheckResult(@Param("name")String name, @Param("cardNumber")String cardNumber,
			@Param("checkResult")int checkResult,@Param("xsd")String xsd,@Param("b")byte[] b,@Param("cardb")byte[] cardb);
	public int getXcCount(@Param("deptid")Integer deptid, @Param("date")String date, @Param("businessType")int businessType);
	public MachineConfigJson getMachineConfig(@Param("deviceNumber")String deviceNumber);
	public AppointmeInfoJson getAppointmeInfoJson();
	public void updateIsCheck(@Param("id")Long id);
	public WarmingInfoJson getWarmingInfoJson();
	public void updateXCIsCheck(@Param("id")Long id);
	List<PmVisitor> getVisitor(@Param("nowdate") Date nowdate,@Param("deptid") Integer deptid,@Param("status") int status,@Param("seq") Integer seq);
    List<PmVisitor> getCalledVisitor(@Param("nowdate") Date nowdate,@Param("deptid") Integer deptid);
    public Integer getConfigIdByDeviceNumber(@Param("deviceNumber")String deviceNumber);
    public void insertDeviceNumber(@Param("deviceNumber")String deviceNumber);
    public MachineConfigJson getMachineConfigJsonByDeviceNumber(@Param("deviceNumber")String deviceNumber);
    public DeptGZHPic getgzhpic(@Param("deviceNumber")String deviceNumber);
    public void insertSquenceSync(@Param("squence_id")Long squence_id,@Param("status") int status, @Param("isexport")int isexport);
    public int getAbleAcceptCount(@Param("deptid")String deptid);
    public int getAlreadyAcceptCount(@Param("cardNumber")String cardNumber, @Param("proxyCardNumber")String proxyCardNumber,
    		@Param("dateString")String dateString);
    public String getBlackCardNumber(@Param("cardNumber")String cardNumber, @Param("proxyCardNumber")String proxyCardNumber);
    public Long insertDlSquenceInfo(WXSquenceInfo squenceInfo);
    public void insertWarmInfo(@Param("warmingInfo")String warmingInfo, @Param("mac_photomatchid")Long mac_photomatchid,
    		@Param("name")String name, @Param("cardnumber")String cardnumber);
    public void insertAppointWarmInfo(@Param("warmingInfo")String warmingInfo, @Param("wx_appointmeinfoid")Long wx_appointmeinfoid, @Param("name")String name,
    		@Param("cardNumber")String cardNumber, @Param("phone")String phone);
    public List<LedShow> getShow(@Param("deptid")Long deptid, @Param("status")int status);
    public Appointme_sync getWxAppintStatus();
    public void updateAppointStatus(@Param("id")long id, @Param("status")int status);
	void updateAppointIsExport(@Param("id")long id);

	String getYNSquence(@Param("isappointment")int isappointment, @Param("nowdate")String nowdate, @Param("isearly")Integer isearly,
			@Param("deptid")Integer deptid);
}
