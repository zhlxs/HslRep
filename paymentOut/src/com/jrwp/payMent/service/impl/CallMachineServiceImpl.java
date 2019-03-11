package com.jrwp.payMent.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jrwp.core.help.QueryHelper;
import com.jrwp.core.help.QueryInfo;
import com.jrwp.core.help.QueryInfoHelper;
import com.jrwp.payMent.dao.CallMachineDao;
import com.jrwp.payMent.entity.CallMachine;
import com.jrwp.payMent.service.CallMachineService;

@Service
public class CallMachineServiceImpl implements CallMachineService {

	@Resource
	private CallMachineDao callMachineDao;

	@Override
	public List<CallMachine> list(String queryinfo) {
		// TODO Auto-generated method stub
		QueryInfo queryInfo = QueryInfoHelper.forWebJson(queryinfo);
		String whereSql = QueryHelper
				.getWhereSql("", queryInfo.getWhereInfos());
		return callMachineDao.list(whereSql);
	}

	@Override
	public void save(CallMachine machine) {
		// TODO Auto-generated method stub
		if (machine.getId() == null) {
			machine.setCreatetime(new Date());
			callMachineDao.save(machine);
		} else {
			callMachineDao.update(machine);
		}
	}

	@Override
	public void update(CallMachine machine) {
		// TODO Auto-generated method stub
		callMachineDao.update(machine);
	}

	@Override
	public CallMachine getObjectById(Long id) {
		// TODO Auto-generated method stub
		return callMachineDao.getObjectById(id);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		callMachineDao.delete(id);
	}

}
