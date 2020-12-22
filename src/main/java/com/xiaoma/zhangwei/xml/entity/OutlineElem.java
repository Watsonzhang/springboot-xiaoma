package com.xiaoma.zhangwei.xml.entity;

import lombok.Data;

import java.util.List;

@Data
public class OutlineElem {
    private String title; // must
    private int count;
    private boolean expanded;
    private List<Action> actions;
    private List<OutlineElem> outlineElem;
}
