package com.jrwp.payMent.service.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jrwp.payMent.dao.BusinessconfigStuffDao;
import com.jrwp.payMent.entity.BusinessconfigStuff;
import com.jrwp.payMent.service.BusinessconfigStuffService;

@Service
public class BusinessconfigStuffServiceImpl implements
		BusinessconfigStuffService {

	@Resource
	private BusinessconfigStuffDao businessconfigStuffDao;

	@Override
	public List<BusinessconfigStuff> listChildren(Long id) {
		// TODO Auto-generated method stub
		return businessconfigStuffDao.listChildren(id);
	}

	@Override
	public void save(BusinessconfigStuff businessconfigStuff) {
		// TODO Auto-generated method stub
		String maxCode = businessconfigStuffDao.getMaxCode();
		if (businessconfigStuff.getId() == null) {
			if (maxCode == null) {
				businessconfigStuff.setOrdercode("00001");
			} else {
				StringBuffer sBuffer = new StringBuffer();
				sBuffer.append(String.valueOf(Integer.parseInt(maxCode) + 1));
				while (sBuffer.length() < 5) {
					sBuffer.insert(0, "0");
				}
				businessconfigStuff.setOrdercode(sBuffer.toString());
			}
			businessconfigStuffDao.save(businessconfigStuff);
		} else {// 修改
			businessconfigStuffDao.update(businessconfigStuff);
		}
	}

	@Override
	public void update(BusinessconfigStuff businessconfigStuff) {
		// TODO Auto-generated method stub
		businessconfigStuffDao.update(businessconfigStuff);
	}

	@Override
	public BusinessconfigStuff getObjectById(long id) {
		// TODO Auto-generated method stub
		return businessconfigStuffDao.getObjectById(id);
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		businessconfigStuffDao.delete(id);
	}

	@Override
	public void move(long id, int movetype) throws Exception {
		// TODO Auto-generated method stub
		String startOrderCode = getStartOrderCode(id);
		String endOrderCode = getEndOrderCode(id);
		String orderCode = getOrderCodeById(id);
		BusinessconfigStuff stuff = new BusinessconfigStuff();
		if(movetype == 1){//向上移动
			if (orderCode.equals(startOrderCode)) {
				throw new Exception("已经排在此类型分类第一了！");
			}
			stuff = getnextOrderCodeById(id);
			String code = stuff.getOrdercode();
			//换码
			businessconfigStuffDao.updateordercode(code,id);
			businessconfigStuffDao.updateordercode(orderCode, stuff.getId());
		}else{
			if (orderCode.equals(endOrderCode)) {
				throw new Exception("已经排在此类型分类末尾了！");
			}
			stuff = getbefOrderCodeById(id);
			String code = stuff.getOrdercode();
			//换码
			businessconfigStuffDao.updateordercode(code,id);
			businessconfigStuffDao.updateordercode(orderCode, stuff.getId());
		}
	}

	// 获取操作对象排序码
	public String getOrderCodeById(long id) {
		BusinessconfigStuff we = businessconfigStuffDao.getObjectById(id);
		List<BusinessconfigStuff> list = businessconfigStuffDao.listChildren(we
				.getApplytypeid());
		for (BusinessconfigStuff weBusinessconfigstr : list) {
			if (weBusinessconfigstr.getId().equals(id)) {
				return weBusinessconfigstr.getOrdercode();
			}
		}
		return null;
	}

	// 获取下一个操作对象
	public BusinessconfigStuff getnextOrderCodeById(long id) {
		BusinessconfigStuff we = businessconfigStuffDao.getObjectById(id);
		List<BusinessconfigStuff> list = businessconfigStuffDao.listChildren(we
				.getApplytypeid());
		for (int i = list.size() - 1; i >= 0; i--) {
			if (list.get(i).getOrdercode().equals(we.getOrdercode())) {
				if (i == 0) {
					return list.get(i);
				} else {
					return list.get(i - 1);
				}
			}
		}
		return null;
	}

	// 获取上一个操作对象
	public BusinessconfigStuff getbefOrderCodeById(long id) {
		BusinessconfigStuff we = businessconfigStuffDao.getObjectById(id);
		List<BusinessconfigStuff> list = businessconfigStuffDao.listChildren(we
				.getApplytypeid());
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getOrdercode().equals(we.getOrdercode())) {
				if (i == list.size() - 1) {
					return list.get(i);
				} else {
					return list.get(i + 1);
				}
			}
		}
		return null;
	}

	// 获取最小排序码
	private String getStartOrderCode(long id) {
		BusinessconfigStuff we = businessconfigStuffDao.getObjectById(id);
		List<BusinessconfigStuff> list = businessconfigStuffDao.listChildren(we
				.getApplytypeid());
		String orderCode = getOrderCodeById(id);
		for (BusinessconfigStuff weBusinessconfigstr : list) {
			if (orderCode.compareTo(weBusinessconfigstr.getOrdercode()) > 0) {
				orderCode = weBusinessconfigstr.getOrdercode();
			}
		}
		return orderCode;
	}

	// 获取最大排序码
	private String getEndOrderCode(long id) {
		BusinessconfigStuff we = businessconfigStuffDao.getObjectById(id);
		List<BusinessconfigStuff> list = businessconfigStuffDao
				.listChildren(we.getApplytypeid());
		String orderCode = getOrderCodeById(id);
		for (BusinessconfigStuff weBusinessconfigstr : list) {
			if (orderCode.compareTo(weBusinessconfigstr.getOrdercode()) < 0) {
				orderCode = weBusinessconfigstr.getOrdercode();
			}
		}
		return orderCode;
	}
	
	public static void main(String[] args) {
		String s = "00003";
		String ss = "00002";
		System.out.println(s.compareTo(ss));
	}
}
