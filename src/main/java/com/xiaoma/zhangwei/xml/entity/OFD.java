package com.xiaoma.zhangwei.xml.entity;

import lombok.Data;

import java.util.Set;

@Data
public class OFD {
    private String version;
    private String docType;
    private Set<DocBody> docBodies;

}
