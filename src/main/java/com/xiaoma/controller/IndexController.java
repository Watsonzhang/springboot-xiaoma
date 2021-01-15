package com.xiaoma.controller;

import com.xiaoma.service.GraphService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2021/1/14 下午2:38
 */
@RestController
@RequestMapping("/index")
@Api(value = "IndexController", tags = {"图形控制器"})
public class IndexController {
    @Autowired
    GraphService graphService;

    @GetMapping
    @ApiOperation("测试")
    public void testGraph(){
        graphService.testSave();
    }


}
