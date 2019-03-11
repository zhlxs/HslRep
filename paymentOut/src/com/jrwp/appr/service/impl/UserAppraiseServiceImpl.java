package com.jrwp.appr.service.impl;

import com.jrwp.appr.dao.UserAppraiseDao;
import com.jrwp.appr.dao.UserHotwordsDao;
import com.jrwp.appr.entity.UserAppraise;
import com.jrwp.appr.entity.UserHotwords;
import com.jrwp.appr.service.UserAppraiseService;
import com.jrwp.wx.dao.AppointInfoDao;
import com.jrwp.wx.entity.AppointInfo;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;

@Service
public class UserAppraiseServiceImpl implements UserAppraiseService{

    @Resource
    private UserAppraiseDao userAppraiseDao;
    @Resource
    private UserHotwordsDao uHotwordsDao;
    @Resource
    private AppointInfoDao appointInfoDao;

    @Override
    @Transactional(rollbackFor = { Exception.class })//如果在目标catch块捕捉到异常则回滚事务
    public void saveAppr(UserAppraise userAppraise,Long appointmeId) {
    	try {
    		/**
    		 * 保存评价结果
    		 */
    		userAppraiseDao.saveAppr(userAppraise);
            //Long Apprid = userAppraise.getAppraisedetailid();
    		/**
    		 * 保存热词
    		 */
        	String hotwords = userAppraise.getHotwords();
        	if(hotwords != null&&!hotwords.equals("")){
        		String[] words = hotwords.split(",");
            	for(int i=0;i<words.length;i++){
            		UserHotwords userHotwords = new UserHotwords();
            		userHotwords.setHotword(words[i]);
            		userHotwords.setUserapprid(userAppraise.getId());
            		uHotwordsDao.save(userHotwords);
            	}
        	}
        	/**
        	 * 更新评价状态
        	 */
        	AppointInfo info = new AppointInfo();
        	info.setId(appointmeId);
        	info.setStatus(4);//已经评价
        	appointInfoDao.updateState(info);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
		}
    }
}
