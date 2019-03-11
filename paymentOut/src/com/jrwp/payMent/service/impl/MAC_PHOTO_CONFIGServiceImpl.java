package com.jrwp.payMent.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jrwp.payMent.dao.IMAC_PHOTO_CONFIGDao;
import com.jrwp.payMent.entity.MAC_PHOTO_CONFIG;
import com.jrwp.payMent.service.IMAC_PHOTO_CONFIGService;

@Service
public class MAC_PHOTO_CONFIGServiceImpl implements IMAC_PHOTO_CONFIGService {

	@Resource
	private IMAC_PHOTO_CONFIGDao maConfigDao;

	@Override
	public List<MAC_PHOTO_CONFIG> list(Long deptId) {
		// TODO Auto-generated method stub
		return maConfigDao.list(deptId);
	}

	@Override
	public void save(MAC_PHOTO_CONFIG maConfig) {
		// TODO Auto-generated method stub
		maConfigDao.save(maConfig);
	}

	@Override
	public MAC_PHOTO_CONFIG getObjectById(Long id) {
		// TODO Auto-generated method stub
		return maConfigDao.getObjectById(id);
	}

	@Override
	public void update(Long relationId, Long id) {
		// TODO Auto-generated method stub
		maConfigDao.update(relationId, id);
	}

	@Override
	public MAC_PHOTO_CONFIG getObjectByOtherId(Long relationId, Long photoId) {
		// TODO Auto-generated method stub
		return maConfigDao.getObjectByOtherId(relationId, photoId);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		maConfigDao.deleteById(id);
	}

}
