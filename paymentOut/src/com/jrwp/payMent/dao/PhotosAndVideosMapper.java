package com.jrwp.payMent.dao;

import com.jrwp.payMent.entity.PhotosAndVideos;

import java.util.List;

public interface PhotosAndVideosMapper {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table PHOTOSANDVIDEOS
	 * 
	 * @mbggenerated
	 */
	int deletedeptid(Integer id);

	int delete(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table PHOTOSANDVIDEOS
	 * 
	 * @mbggenerated
	 */
	int insert(PhotosAndVideos record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table PHOTOSANDVIDEOS
	 * 
	 * @mbggenerated
	 */
	int insertSelective(PhotosAndVideos record);

	List<PhotosAndVideos> getBydeptid(int deptid);

	PhotosAndVideos getByid(int id);

	void NextAlleqReduce(int id);

	void seqReduce(int id);

	void seqAdd(int id);

	void prevSeqAdd(int id);

	void nextseqReduce(int id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table PHOTOSANDVIDEOS
	 * 
	 * @mbggenerated
	 */
	PhotosAndVideos selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table PHOTOSANDVIDEOS
	 * 
	 * @mbggenerated
	 */
	int updateByPrimaryKeySelective(PhotosAndVideos record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table PHOTOSANDVIDEOS
	 * 
	 * @mbggenerated
	 */
	int updateByPrimaryKeyWithBLOBs(PhotosAndVideos record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table PHOTOSANDVIDEOS
	 * 
	 * @mbggenerated
	 */
	int updateByPrimaryKey(PhotosAndVideos record);
}