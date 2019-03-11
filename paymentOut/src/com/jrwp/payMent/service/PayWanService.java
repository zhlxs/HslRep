package com.jrwp.payMent.service;

import com.jrwp.core.entity.Core_Dept;
import com.jrwp.payMent.entity.PayWan;
import com.jrwp.payMent.entity.PayWanInfo;
import com.jrwp.payMent.entity.WanCount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PayWanService {
    List<PayWanInfo> getWanList(String paystatus, String payType, String begintime, String endtime, List<String> startCodes, String
            configName, String ordernumber);

    PayWanInfo getWanInfo(@Param("id") Integer id);
    PayWan getByOrderNumber(String  ordernumber);
    List<Core_Dept> getMachines(Long deptId);
    List<PayWanInfo> listBycode(String wheresql);
    List<WanCount> coutwanbymachine(String payType, String begintime, String endtime, List<String> startCodes, Integer[] macIds);

    List<WanCount> coutwanbyconfig(String payType, String begintime, String endtime, Integer startCodes, String configname);
}
