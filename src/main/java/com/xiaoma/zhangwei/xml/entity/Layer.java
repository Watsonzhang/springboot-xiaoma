package com.xiaoma.zhangwei.xml.entity;

import lombok.Data;

import java.util.List;

@Data
public class Layer {

    private LayerType type = LayerType.Body;
    private String drawParam;
    private List<Block> pageBlocks;

}
