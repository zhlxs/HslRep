package com.jrwp.JSPay.dao;

import com.jrwp.JSPay.entity.PayRefund;
import com.jrwp.JSPay.entity.RefundApply;
import com.jrwp.JSPay.entity.RefundDetails;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface PayRefundDao {

    void saveRefund(PayRefund payRefund);
    void saveRefundDetails(RefundDetails refundDetails);

    void saveBillRefund(PayRefund payRefund);

    void updateStatus(PayRefund payRefund);

    void updateApplyByid(RefundApply refundApply);

    void savaApplySelective(RefundApply refundApply);

    RefundApply selectApplyByordernumber(String ordernumber);
    RefundApply selectApplyById(Integer id);

    RefundApply getRefundInfo(Long id);

    List<RefundApply> selectApplyByUserId(@Param("userid") Long userid, @Param("whereSql") String whereSql);

    List<RefundApply> selectWaitApply(@Param("userid") Long userid, @Param("whereSql") String whereSql);

    List<PayRefund> getPayRefund(Date adate);

    PayRefund getPayRefundByRefundNumber(String refundnumber);
}
