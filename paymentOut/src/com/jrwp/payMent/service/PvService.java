package com.jrwp.payMent.service;

import com.jrwp.payMent.entity.PhotosAndVideos;

import java.util.List;

public interface PvService {
	void save(PhotosAndVideos photosAndVideos);

	void deleteBydeptid(Integer id);

	int delete(Integer id);

	List<PhotosAndVideos> getBydeptid(int deptid);

	PhotosAndVideos getByid(int id);

	void NextAlleqReduce(int id);

	void seqReduce(int id);

	void seqAdd(int id);

	void prevSeqAdd(int id);

	void nextseqReduce(int id);
}
