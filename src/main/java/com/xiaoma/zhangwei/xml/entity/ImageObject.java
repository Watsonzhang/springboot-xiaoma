package com.xiaoma.zhangwei.xml.entity;

import lombok.Data;

@Data
public class ImageObject extends Block
{
	private String resourceId; // must
	private Box boundary; // must
	private String id;
	private float miterLimit = -1;
	private double[] ctm;

}
