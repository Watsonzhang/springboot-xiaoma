package com.xiaoma.zhangwei.xml.entity;

import lombok.Data;

@Data
public class TextCode {
    private String text; // must
    private float x = -1;
    private float y = -1;
    private float[] deltaX;
    private float[] deltaY;
}
