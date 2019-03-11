package com.jrwp.payMent.entity;

/**
 * 窗口设备图片对应配置表
 * 
 * @author hsl
 * 
 */
public class MAC_PHOTO_CONFIG {

	private Long id;
	private Long relationId;
	private int relationType;
	private Long photoId;
	private int dataType;

	private MAC_PHOTO macPhoto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRelationId() {
		return relationId;
	}

	public void setRelationId(Long relationId) {
		this.relationId = relationId;
	}

	public int getRelationType() {
		return relationType;
	}

	public void setRelationType(int relationType) {
		this.relationType = relationType;
	}

	public Long getPhotoId() {
		return photoId;
	}

	public void setPhotoId(Long photoId) {
		this.photoId = photoId;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public MAC_PHOTO getMacPhoto() {
		return macPhoto;
	}

	public void setMacPhoto(MAC_PHOTO macPhoto) {
		this.macPhoto = macPhoto;
	}

}
