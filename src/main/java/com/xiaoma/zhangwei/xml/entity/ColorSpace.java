package com.xiaoma.zhangwei.xml.entity;

import lombok.Data;

@Data
public class ColorSpace {

	private String type="RGB"; // must
	private String id;
	private int bitsPerComponent=8;
}
