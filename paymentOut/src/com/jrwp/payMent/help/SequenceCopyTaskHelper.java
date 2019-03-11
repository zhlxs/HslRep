package com.jrwp.payMent.help;

import java.util.ArrayList;
import java.util.List;

import com.jrwp.wx.entity.Sequence_Info;

public class SequenceCopyTaskHelper {

	private static SequenceCopyTaskHelper instance;
	private List<Sequence_Info> taget = new ArrayList<>();

	public synchronized List<Sequence_Info> dateChange(int i,
			List<Sequence_Info> list) {
		if (i == 1) {
			return getTaget();
		} else {
			setTaget(list);
			return null;
		}
	}

	public synchronized static SequenceCopyTaskHelper getInstance() {
		if (instance == null) {
			instance = new SequenceCopyTaskHelper();
		}
		return instance;
	}

	public List<Sequence_Info> getTaget() {
		synchronized (this) {
			return taget;
		}
	}

	public void setTaget(List<Sequence_Info> taget) {
		synchronized (this) {
			this.taget = taget;
		}
	}
}
