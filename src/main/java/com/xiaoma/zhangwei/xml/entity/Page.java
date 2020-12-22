package com.xiaoma.zhangwei.xml.entity;

import lombok.Data;

import java.util.List;

@Data
public class Page {
	
	private PageArea area;
	
	//private Set<TemplatePage> template;
	
	//private String pageRes;
	private List<Layer> content;
	//private List<Action> actions;

}
