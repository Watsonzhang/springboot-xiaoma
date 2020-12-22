package com.xiaoma.zhangwei.xml.entity;


import lombok.Data;

@Data
public class PageArea {
	private Box physicalBox; // must
	private Box applicationBox;
	private Box contentBox;
	private Box bleedBox;

}
