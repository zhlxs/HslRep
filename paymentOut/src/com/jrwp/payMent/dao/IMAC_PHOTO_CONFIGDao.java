package com.jrwp.payMent.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jrwp.payMent.entity.MAC_PHOTO_CONFIG;

public interface IMAC_PHOTO_CONFIGDao {

	List<MAC_PHOTO_CONFIG> list(@Param("deptId") Long deptId);

	void save(MAC_PHOTO_CONFIG maConfig);

	MAC_PHOTO_CONFIG getObjectById(Long id);

	void update(@Param("relationId") Long relationId, @Param("id") Long id);

	MAC_PHOTO_CONFIG getObjectByOtherId(@Param("relationId") Long relationId,
			@Param("photoId") Long photoId);

	void deleteById(Long id);
}
