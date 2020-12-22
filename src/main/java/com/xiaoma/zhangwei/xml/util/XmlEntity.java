package com.xiaoma.zhangwei.xml.util;

import java.util.Map;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2020/12/21 下午4:23
 */

public class XmlEntity {
    private String node;
    private Map<String, String>  attributes;
    private String content;

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
