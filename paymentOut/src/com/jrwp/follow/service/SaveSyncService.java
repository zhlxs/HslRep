package com.jrwp.follow.service;

import com.jrwp.core.entity.Core_Dept;
import com.jrwp.core.service.IDeptService;
import com.jrwp.core.utils.LogUtil;
import com.jrwp.core.utils.MsgManager;
import com.jrwp.follow.dao.DeptPayConfigDao;
import com.jrwp.follow.dao.UserPayParmsDao;
import com.jrwp.follow.entity.PayConfig;
import com.jrwp.follow.entity.QrCodeParms;
import com.jrwp.payMent.entity.PayWan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class SaveSyncService {
    @Resource
    private DeptPayConfigDao deptPayConfigDao;
    @Resource
    private UserPayParmsDao payParmsDao;
    @Resource
    private IDeptService deptService;

    @Transactional
    public String saveSync(QrCodeParms parms, PayConfig config) {
        String order = "";
        try {
            PayWan payWan = payParmsDao.isPay(parms.getId());
            if (payWan == null) {
                Core_Dept dept = deptService.getBydeptCode(parms.getDeptCode());
                parms.setConfigid(config.getId().longValue());
                parms.setDeptid(dept.getId());
                Long payid;
                //先存入record 获取payid
                payParmsDao.saveRecord(parms);
                payid = parms.getPayid();
                order = config.getMarkname() + payid;
                payParmsDao.updatePayid(order, payid);
                //是否存在这条记录
                Long sync = payParmsDao.findWordIDSYNC(parms.getId());
                parms.setPayid(payid);
                if (sync != null) {
                    //更新payid
                    payParmsDao.updatesyncPayid(parms);
                } else {
                    //存入sync表
                    payParmsDao.saveSync(parms);
                }
            } else {
                order = config.getMarkname() + payWan.getPayid();
                return order;
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.getLogger().error(e.getMessage(), e);
            throw new RuntimeException();
        }
        return order;
    }

    public Boolean ispay(Long id) {
        boolean f = false;
        try {
            PayWan payWan = payParmsDao.isPay(id);
            if (payWan != null && payWan.getPaystatus() == 2) {
                f = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.getLogger().error(e.getMessage(), e);
        }

        return f;
    }
}
