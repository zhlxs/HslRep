package com.jrwp.wx.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class ChoseDept {
    private Long id ;
    private String value;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ChoseDept> childs;
    private Long parentid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<ChoseDept> getChilds() {
        return childs;
    }

    public void setChilds(List<ChoseDept> childs) {
        this.childs = childs;
    }

    public Long getParentid() {
        return parentid;
    }

    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }
}
