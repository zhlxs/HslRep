package com.jrwp.wx.until;

import java.util.ArrayList;
import java.util.List;
import com.jrwp.wx.entity.PmVisitor;


public class PmSquenceHelper {
	private static PmSquenceHelper instance;
    private List<PmVisitor> taget= new ArrayList<>();

    public synchronized List<PmVisitor> dateChange(int i, List<PmVisitor> list) {
        if (i == 1) {
            return getTaget();
        } else {
            setTaget(list);
            return null;
        }
    }

    public synchronized static PmSquenceHelper getInstance() {
        if (instance == null) {
            instance = new PmSquenceHelper();
        }
        return instance;
    }

    public List<PmVisitor> getTaget() {
        synchronized (this) {
            return taget;
        }
    }

    public void setTaget(List<PmVisitor> taget) {
        synchronized (this) {
            this.taget =taget;

        }
    }
}
