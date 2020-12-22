package com.xiaoma.zhangwei.xml.entity;

import lombok.Data;

@Data
public class Color {
	private float[] value;
	private ColorSpace colorSpace=new ColorSpace(); // 对应res中的colorspace ID, 默认RGB
}
