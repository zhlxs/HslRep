package com.jrwp.payMent.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jrwp.payMent.entity.MAC_PHOTO;

public interface IMAC_PHOTOService {
	// 获取上传图片 样图
	MAC_PHOTO getPhotoSample(Long samplepathid);

	Integer save(MAC_PHOTO photoSample);

	Integer update(MAC_PHOTO photoSample);

	List<MAC_PHOTO> list(@Param("whereSql") String whereSql);

	// List<PhotoSample> listofType(@Param("id") Long id,
	// @Param("whereSql") String whereSql);

	// 查询有效数据
	List<MAC_PHOTO> listValid(@Param("whereSql") String whereSql, int photoType);

	Integer delete(Long id);

	Integer updateIfPisnull(MAC_PHOTO photoSample);

	// 修改有效状态
	Integer updateValid(@Param("id") Long id, @Param("isValid") Integer isvalid);

	MAC_PHOTO getObjectById(long id);

	List<MAC_PHOTO> listofType(long id);

	MAC_PHOTO getPhotoByName(@Param("photoname") String photoname);
}
