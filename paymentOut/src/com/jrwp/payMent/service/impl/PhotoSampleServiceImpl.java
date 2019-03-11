package com.jrwp.payMent.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.jrwp.core.entity.Core_User;
import com.jrwp.core.help.QueryHelper;
import com.jrwp.core.help.QueryInfo;
import com.jrwp.core.help.QueryInfoHelper;
import com.jrwp.payMent.dao.PhotoSampleDao;
import com.jrwp.payMent.entity.PhotoSample;
import com.jrwp.payMent.service.PhotoSampleService;

@Service("photoSampleService")
public class PhotoSampleServiceImpl implements PhotoSampleService {
	@Resource
	private PhotoSampleDao photoSampleDao;

	@Override
	public PhotoSample getPhotoSample(Long samplepathid) {
		return photoSampleDao.getPhotoSample(samplepathid);
	}

	@Override
	public Integer save(PhotoSample photoSample) {
		Integer integer = null;
		if (photoSample.getId() == null || photoSample.getId() == 0) {
			Subject currentUser = SecurityUtils.getSubject();
			Session session = currentUser.getSession();
			Core_User user = (Core_User) session.getAttribute("user");
			photoSample.setCreator(user.getId());
			integer = photoSampleDao.save(photoSample);
		} else {
			integer = photoSampleDao.update(photoSample);
		}
		return integer;
	}

	@Override
	public Integer update(PhotoSample photoSample) {
		return photoSampleDao.update(photoSample);
	}

	@Override
	public List<PhotoSample> list(String queryinfo) {
		QueryInfo queryInfo = QueryInfoHelper.forWebJson(queryinfo);
		String whereSql = QueryHelper
				.getWhereSql("", queryInfo.getWhereInfos());
		return photoSampleDao.list(whereSql);
	}

	// @Override
	// public List<PhotoSample> listofType(Long id, String queryinfo) {
	// QueryInfo queryInfo = QueryInfoHelper.forWebJson(queryinfo);
	// String whereSql = QueryHelper
	// .getWhereSql("", queryInfo.getWhereInfos());
	// return photoSampleDao.listofType(id, whereSql);
	// }

	@Override
	public Integer delete(Long id) {
		return photoSampleDao.delete(id);
	}

	@Override
	public Integer updateValid(Long id, Integer isvalid) {
		return photoSampleDao.updateValid(id, isvalid);
	}

	@Override
	public Integer updateIfPisnull(PhotoSample photoSample) {
		return photoSampleDao.updateIfPisnull(photoSample);
	}

	@Override
	public List<PhotoSample> listValid(String queryinfo) {
		QueryInfo queryInfo = QueryInfoHelper.forWebJson(queryinfo);
		String whereSql = QueryHelper
				.getWhereSql("", queryInfo.getWhereInfos());
		return photoSampleDao.listValid(whereSql);
	}

	@Override
	public PhotoSample getObjectById(long id) {
		// TODO Auto-generated method stub
		return photoSampleDao.getObjectById(id);
	}

	@Override
	public List<PhotoSample> listofType(long id) {
		// TODO Auto-generated method stub
		return photoSampleDao.listofType(id);
	}

}
