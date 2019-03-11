package com.jrwp.payMent.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jrwp.payMent.dao.IDeptTimesAuthDao;
import com.jrwp.payMent.entity.AuthLog;
import com.jrwp.payMent.entity.DeptTimesAuth;
import com.jrwp.payMent.service.IDeptTimesAuthService;
import com.jrwp.payMent.until.AuthLogUtils;

@Service
public class DeptTimesAuthServiceImpl implements IDeptTimesAuthService {

	@Resource
	private IDeptTimesAuthDao deptTimesAuthDao;

	@Override
	public List<DeptTimesAuth> list() {
		// TODO Auto-generated method stub
		return deptTimesAuthDao.list();
	}

	@Override
	public DeptTimesAuth getObjectById(Long id) {
		// TODO Auto-generated method stub
		return deptTimesAuthDao.getObjectById(id);
	}

	@Override
	public void save(DeptTimesAuth deptTimesAuth) {
		// TODO Auto-generated method stub
		deptTimesAuthDao.save(deptTimesAuth);
	}

	@Override
	public void updateById(DeptTimesAuth deptTimesAuth, String idCard,
			String userName) {
		// TODO Auto-generated method stub
		AuthLog log = new AuthLog();
		log.setLogType("auth_people");// 类型
		log.setOperatname(userName);// 操作对象名称
		log.setOperator(deptTimesAuth.getOperator());// 操作人
		log.setOperatvalue(idCard);// 操作对象值
		log.setOperattime(new Date());// 操作时间
		log.setDeptid(deptTimesAuth.getDeptid());
		try {
			log.setIsAccess(1);
			deptTimesAuth.setStatus(1);//有效状态
			deptTimesAuthDao.updateById(deptTimesAuth);
		} catch (Exception e) {
			log.setIsAccess(-1);
			// TODO: handle exception
		}
		try {
			AuthLogUtils.saveLog(log, null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		deptTimesAuthDao.delete(id);
	}

}
