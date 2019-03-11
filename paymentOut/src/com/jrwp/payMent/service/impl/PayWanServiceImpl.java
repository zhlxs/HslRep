package com.jrwp.payMent.service.impl;

import com.jrwp.core.entity.Core_Dept;
import com.jrwp.core.help.QueryHelper;
import com.jrwp.core.help.QueryInfo;
import com.jrwp.core.help.QueryInfoHelper;
import com.jrwp.payMent.dao.PayWanMapper;
import com.jrwp.payMent.entity.PayWan;
import com.jrwp.payMent.entity.PayWanInfo;
import com.jrwp.payMent.entity.WanCount;
import com.jrwp.payMent.service.PayWanService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PayWanServiceImpl implements PayWanService {
    @Resource
    private PayWanMapper payWanMapper;

    @Override
    public List<PayWanInfo> getWanList(String payStatus, String payType, String begintime, String endtime, List<String> startCodes,
                                       String configName, String ordernumber) {
        return payWanMapper.getWanList(payStatus, payType, begintime, endtime, startCodes, configName, ordernumber);
    }

    @Override
    public PayWanInfo getWanInfo(Integer id) {
        return payWanMapper.getWanInfo(id);
    }

    @Override
    public PayWan getByOrderNumber(String ordernumber) {
        return payWanMapper.getByOrderNumber(ordernumber);
    }

    @Override
    public List<Core_Dept> getMachines(Long deptId) {
        return payWanMapper.getMachines(deptId);
    }

    @Override
    public List<PayWanInfo> listBycode(String wheresql) {
        QueryInfo queryInfo = QueryInfoHelper.forWebJson(wheresql);
        String whereSql = QueryHelper.getWhereSql("", queryInfo.getWhereInfos());
        return payWanMapper.listBycode(whereSql);
    }

    @Override
    public List<WanCount> coutwanbymachine(String payType, String begintime, String endtime, List<String> startCodes, Integer[]
            macIds) {
        return payWanMapper.coutwanbymachine(payType, begintime, endtime, startCodes, macIds);
    }

    @Override
    public List<WanCount> coutwanbyconfig(String payType, String begintime, String endtime, Integer machinetype, String configname) {
        if (configname != null) {
            configname = "%" + configname + "%";
        }
        return payWanMapper.coutwanbyconfig(payType, begintime, endtime, machinetype, configname);
    }
}
