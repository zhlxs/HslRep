package com.jrwp.payMent.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jrwp.core.help.QueryHelper;
import com.jrwp.core.help.QueryInfo;
import com.jrwp.core.help.QueryInfoHelper;
import com.jrwp.payMent.dao.IMAC_PHOTODao;
import com.jrwp.payMent.entity.MAC_PHOTO;
import com.jrwp.payMent.service.IMAC_PHOTOService;

@Service("macPhotoService")
public class IMAC_PHOTOServiceImpl implements IMAC_PHOTOService {

	@Resource
	private IMAC_PHOTODao macPhotoDao;

	@Override
	public MAC_PHOTO getPhotoSample(Long samplepathid) {
		// TODO Auto-generated method stub
		return macPhotoDao.getPhotoSample(samplepathid);
	}

	@Override
	public Integer save(MAC_PHOTO photoSample) {
		// TODO Auto-generated method stub
		Integer integer = null;
		if (photoSample.getId() == null || photoSample.getId() == 0) {
			integer = macPhotoDao.save(photoSample);
		} else {
			integer = macPhotoDao.update(photoSample);
		}
		return integer;
	}

	@Override
	public Integer update(MAC_PHOTO photoSample) {
		// TODO Auto-generated method stub
		return macPhotoDao.update(photoSample);
	}

	@Override
	public List<MAC_PHOTO> list(String queryinfo) {
		// TODO Auto-generated method stub
		QueryInfo queryInfo = QueryInfoHelper.forWebJson(queryinfo);
		String whereSql = QueryHelper
				.getWhereSql("", queryInfo.getWhereInfos());
		return macPhotoDao.list(whereSql);
	}

	@Override
	public List<MAC_PHOTO> listValid(String queryinfo, int photoType) {
		// TODO Auto-generated method stub
		QueryInfo queryInfo = QueryInfoHelper.forWebJson(queryinfo);
		String whereSql = QueryHelper
				.getWhereSql("", queryInfo.getWhereInfos());
		return macPhotoDao.listValid(whereSql, photoType);
	}

	@Override
	public Integer delete(Long id) {
		// TODO Auto-generated method stub
		return macPhotoDao.delete(id);
	}

	@Override
	public Integer updateIfPisnull(MAC_PHOTO photoSample) {
		// TODO Auto-generated method stub
		return macPhotoDao.updateIfPisnull(photoSample);
	}

	@Override
	public Integer updateValid(Long id, Integer isvalid) {
		// TODO Auto-generated method stub
		return macPhotoDao.updateValid(id, isvalid);
	}

	@Override
	public MAC_PHOTO getObjectById(long id) {
		// TODO Auto-generated method stub
		return macPhotoDao.getObjectById(id);
	}

	@Override
	public List<MAC_PHOTO> listofType(long id) {
		// TODO Auto-generated method stub
		return macPhotoDao.listofType(id);
	}

	@Override
	public MAC_PHOTO getPhotoByName(String photoname) {
		// TODO Auto-generated method stub
		return macPhotoDao.getPhotoByName(photoname);
	}

}
