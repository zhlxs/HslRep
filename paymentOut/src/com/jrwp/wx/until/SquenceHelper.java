package com.jrwp.wx.until;

import com.jrwp.wx.entity.Visitor;

import java.util.ArrayList;
import java.util.List;

public class SquenceHelper {
    private static SquenceHelper instance;
    private List<Visitor> taget= new ArrayList<>();

    public synchronized List<Visitor> dateChange(int i, List<Visitor> list) {
        if (i == 1) {
            return getTaget();
        } else {
            setTaget(list);
            return null;
        }
    }

    public synchronized static SquenceHelper getInstance() {
        if (instance == null) {
            instance = new SquenceHelper();
        }
        return instance;
    }

    public List<Visitor> getTaget() {
        synchronized (this) {
            return taget;
        }
    }

    public void setTaget(List<Visitor> taget) {
        synchronized (this) {
            this.taget =taget;

        }
    }
}
