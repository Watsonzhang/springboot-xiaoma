package com.xiaoma.zhangwei.xml.entity;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Res {

	private String baseLoc; // must
	
	private List<ColorSpace> colorSpaces;
	private List<Font> fonts;
	private List<MultiMedia> multiMedias;
	
	private Map<String, PdfFont> fontMap = new HashMap<>();
	private Map<String, PdfImageXObject> imageMap = new HashMap<>();
	

}
