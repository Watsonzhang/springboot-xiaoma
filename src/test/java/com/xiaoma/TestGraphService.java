package com.xiaoma;

import com.google.common.collect.Lists;
import com.xiaoma.aviator.ExpressionService;
import com.xiaoma.entity.TaxEntity;
import com.xiaoma.model.dto.TaxDTO;
import com.xiaoma.service.GraphService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2021/1/15 下午1:46
 */
@SpringBootTest(classes = {App.class})
@RunWith(SpringRunner.class)
public class TestGraphService {

    @Autowired
    GraphService graphService;

    @Autowired
    ExpressionService expressionService;

    @Test
    public void  testAddTaxNode(){
        TaxDTO leafA = TaxDTO.builder().name("叶子节点a").expression("[a]").build();
        TaxDTO leafB = TaxDTO.builder().name("叶子节点b").expression("[b]").build();
        TaxDTO leafC = TaxDTO.builder().name("叶子节点c").expression("[c]").build();
        TaxDTO leafD = TaxDTO.builder().name("叶子节点d").expression("[d]").build();
        graphService.addTaxNode(Arrays.asList(leafA,leafB,leafC,leafD));
    }

    @Test
    public void  testDeleteAll(){
        graphService.deleteAll();
    }

    @Test
    public void testSingleAdd(){
        TaxDTO node4 = TaxDTO.builder().name("三级4")
                .expression("[a]")//原子节点
                .build();
        graphService.addTaxNode(node4);
    }

    @Test
    public void testAddMiddleNode() {
        TaxDTO node8 = TaxDTO.builder().name("四级1")
                .expression("[e]")//原子节点
                .build();
        TaxDTO node4 = TaxDTO.builder().name("三级4")
                .expression("[a]")//原子节点
                .build();
        TaxDTO node5 = TaxDTO.builder().name("三级5")
                .expression("[b]")//原子节点
                .build();
        TaxDTO node6 = TaxDTO.builder().name("三级6")
                .expression("[c]")//原子节点
                .build();
        TaxDTO node7 = TaxDTO.builder().name("三级7")
                .expression("[d]").build();//原子节点
        TaxDTO node2 = TaxDTO.builder().name("二级2")
                .children(Arrays.asList(node4,node5))
                .expression("[三级4]+[三级5]")
                .build();
        TaxDTO node3 = TaxDTO.builder().name("二级3")
                .children(Arrays.asList(node6,node7))
                .expression("[三级6]-[三级7]")
                .build();
        node4.setChildren(Arrays.asList(node8,node3));
        TaxDTO node1 = TaxDTO.builder().name("一级1").id(1L)
                .children(Arrays.asList(node2,node3))
                .expression("[二级3]+[二级2]")
                .build();
        graphService.addTaxNode(node1);
    }

    @Test
    public void testCalculate(){
        TaxEntity taxEntity = graphService.findById(999L);
        graphService.calculate(taxEntity);
        System.out.println(taxEntity.getCalValue());
    }


    @Test
    public void testGetLeafNodes(){
        TaxEntity taxEntity = graphService.findById(999L);
        List<TaxEntity> objects = Lists.newArrayList();
        graphService.getLeafNodes(taxEntity,objects);
        System.out.println(objects);
    }

    @Test
    public void testThreadPool(){
        TaxEntity taxEntity = graphService.findById(999L);
        List<TaxEntity> objects = Lists.newArrayList();
        graphService.getLeafNodes(taxEntity,objects);
        List<Runnable> task= objects.stream().map(this::apply).collect(Collectors.toList());
        ExecutorService executor = Executors.newFixedThreadPool(4);
        task.forEach(item->{
           executor.execute(item);
        });
    }


    private Runnable apply(TaxEntity item) {
        return new Runnable() {
            @Override
            public void run() {
                expressionService.execute(item.getExpression());
            }
        };
    }
}
