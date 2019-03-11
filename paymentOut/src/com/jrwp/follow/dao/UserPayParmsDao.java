package com.jrwp.follow.dao;

import com.jrwp.follow.entity.PayConfig;
import com.jrwp.follow.entity.QrCodeParms;
import com.jrwp.payMent.entity.PayWan;
import org.apache.ibatis.annotations.Param;

public interface UserPayParmsDao {
    /**
     * 查找最近的一条支付参数
     *
     * @param openid
     * @return
     */
    QrCodeParms getByopenid(@Param("openid") String openid);

    /**
     * 保存存支付参数
     */
    void saveSync(QrCodeParms parms);

    Long findWordIDRECORD(String workid);

    Long findWordIDSYNC(Long workid);

    PayWan isPay(Long id);

    /**
     * 支付完成以后更新支付状态 增加订单编号
     *
     * @param paystatus
     * @param ordernumber
     */
    void updateStatus(@Param("paystatus") int paystatus, @Param("ordernumber") String ordernumber);

    void updatePayid(@Param("localorders") String localorders, @Param("id") Long id);
    void updatesyncPayid(QrCodeParms parms);

    Long saveRecord(QrCodeParms parms);
}
