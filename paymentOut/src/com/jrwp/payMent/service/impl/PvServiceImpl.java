package com.jrwp.payMent.service.impl;

import com.jrwp.payMent.dao.PhotosAndVideosMapper;
import com.jrwp.payMent.entity.PhotosAndVideos;
import com.jrwp.payMent.service.PvService;
//import com.sun.org.apache.regexp.internal.RE;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PvServiceImpl implements PvService {
	@Resource
	private PhotosAndVideosMapper photosAndVideoMapper;

	@Override
	public void save(PhotosAndVideos photosAndVideos) {
		photosAndVideoMapper.insert(photosAndVideos);
	}

	@Override
	public void deleteBydeptid(Integer id) {
		photosAndVideoMapper.deletedeptid(id);
	}

	@Override
	public int delete(Integer id) {
		return photosAndVideoMapper.delete(id);
	}

	@Override
	public List<PhotosAndVideos> getBydeptid(int deptid) {
		return photosAndVideoMapper.getBydeptid(deptid);
	}

	@Override
	public PhotosAndVideos getByid(int id) {
		return photosAndVideoMapper.getByid(id);
	}

	@Override
	public void NextAlleqReduce(int id) {
		photosAndVideoMapper.NextAlleqReduce(id);
	}

	@Override
	public void seqReduce(int id) {
		photosAndVideoMapper.seqReduce(id);
	}

	@Override
	public void seqAdd(int id) {
		photosAndVideoMapper.seqAdd(id);
	}

	@Override
	public void prevSeqAdd(int id) {
		photosAndVideoMapper.prevSeqAdd(id);
	}

	@Override
	public void nextseqReduce(int id) {
		photosAndVideoMapper.nextseqReduce(id);
	}
}
