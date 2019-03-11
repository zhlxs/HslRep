package com.jrwp.payMent.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jrwp.payMent.entity.PhotoSample;

public interface PhotoSampleDao {
	// 获取上传图片 样图
	PhotoSample getPhotoSample(Long samplepathid);

	Integer save(PhotoSample photoSample);

	Integer update(PhotoSample photoSample);

	Integer updateIfPisnull(PhotoSample photoSample);

//	List<PhotoSample> listofType(@Param("id") Long id,
//			@Param("whereSql") String whereSql);

	List<PhotoSample> list(@Param("whereSql") String whereSql);

	// 查询有效数据
	List<PhotoSample> listValid(@Param("whereSql") String whereSql);

	Integer delete(Long id);

	// 修改有效状态
	Integer updateValid(@Param("id") Long id, @Param("isvalid") Integer isvalid);
	
	PhotoSample getObjectById(long id);
	
	List<PhotoSample> listofType(long id);
}
