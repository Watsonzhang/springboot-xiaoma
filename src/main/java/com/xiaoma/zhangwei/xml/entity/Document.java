package com.xiaoma.zhangwei.xml.entity;

import lombok.Data;

import java.util.List;

@Data
public class Document {

	private CommonData commonData ; // must
	private List<PageNode> pages; // must
	
	private List<OutlineElem> outlines;
	

	
}
