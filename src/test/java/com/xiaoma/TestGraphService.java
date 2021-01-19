package com.xiaoma;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;
import com.xiaoma.aviator.ExpressionService;
import com.xiaoma.entity.TaxEntity;
import com.xiaoma.model.bo.GetContentTask;
import com.xiaoma.model.bo.MiddleBo;
import com.xiaoma.model.bo.RelDTO;
import com.xiaoma.model.bo.RelationDTO;
import com.xiaoma.model.dto.TaxDTO;
import com.xiaoma.repository.TaxEntityRepository;
import com.xiaoma.service.GraphService;
import com.xiaoma.util.ListUtils;
import org.junit.Test;
import org.junit.platform.commons.util.StringUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import javax.swing.text.html.parser.Entity;
import java.util.*;
import java.util.concurrent.*;
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

    @Autowired
    TaxEntityRepository taxEntityRepository;
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
                .expression("[a]")//原子节点
                .build();
        TaxDTO node4 = TaxDTO.builder().name("三级4")
                .expression("MAX([四级1],[二级3])")//
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
    public void testSelect(){
        TaxEntity taxEntity = taxEntityRepository.findById(999L).orElse(null);
        System.out.println(JSON.toJSONString(taxEntity, SerializerFeature.DisableCircularReferenceDetect));
    }

    @Test
    public void testCalculate(){
        TaxEntity taxEntity = taxEntityRepository.findById(999L).orElse(null);
        graphService.calculate(taxEntity);
        System.out.println(taxEntity.getCalValue());
        String str = JSONObject.toJSONString(taxEntity);
        System.out.println(str);
    }

    @Test
    public void testMAx(){
        String s="MAX(1,2)";
        Long execute = (Long)expressionService.execute(s);
        System.out.println(execute);
    }

    @Test
    public void testTPool(){
        ExecutorService executorService = Executors.newCachedThreadPool();
        CompletionService<String> completionService = new ExecutorCompletionService(executorService);
        // 十个
        long startTime = System.currentTimeMillis();
        int count = 0;
        for (int i = 0;i < 10;i ++) {
            count ++;
            GetContentTask getContentTask = new GetContentTask("micro" + i, 10);
            completionService.submit(getContentTask);
        }
        System.out.println("提交完任务，主线程空闲了, 可以去做一些事情。");
        // 假装做了8秒种其他事情
        try {
            Thread.sleep(8000);
            System.out.println("主线程做完了，等待结果");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            // 做完事情要结果
            for (int i = 0;i < count;i ++) {
                Future<String> result = completionService.take();
                System.out.println(result.get());
            }
            long endTime = System.currentTimeMillis();
            System.out.println("耗时 : " + (endTime - startTime) / 1000);
        }  catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private List<TaxEntity> distinctLeafNodes(List<TaxEntity> leafNodes) {
        return leafNodes;
    }


    //去重算法
    private List<RelationDTO> distinctList(List<RelationDTO> relationDTOList) {
        Set<RelationDTO> set = new TreeSet<>((origin, target) -> {
            int compareToResult = 1;//==0表示重复
            if(origin.getId().equals(target.getId()) && origin.getParentId().equals(target.getParentId())) {
                compareToResult = 0;
            }
            return compareToResult;
        });
        set.addAll(relationDTOList);
        return new ArrayList<>(set);
    }


    private TaxEntity removeLeafNodeAndResStruct(TaxEntity entity,List<TaxEntity> entities){
        /*if(hasChild(entity)){
            for(TaxEntity i:entity.getChildren()){
                if(!hasChild(i)){
                    entity.setChildren(null);
                    continue;
                }
                removeLeafNodeAndResStruct(i,entities);
            }
        }*/
        return entity;
    }
    private void iteratorTaxEntity(TaxEntity entity,List<RelationDTO> list,Long parentId){
        if(!hasChild(entity)){
            RelationDTO build = RelationDTO.builder().id(entity.getId()).parentId(parentId).build();
            list.add(build);
            return;
        }
        for(TaxEntity item:entity.getChildren()){
            RelationDTO build = RelationDTO.builder().id(item.getId()).parentId(entity.getId()).build();
            list.add(build);
            iteratorTaxEntity(item,list,entity.getId());
        }
    }



    private boolean hasChild(TaxEntity entity){
        return  !CollectionUtils.isEmpty(entity.getChildren());
    }

    @Test
    public void  test() throws InterruptedException, ExecutionException {
        //根据树生成计算单元 计算单元为深度为1的类树结构
        TaxEntity taxEntity = taxEntityRepository.findById(999L).orElse(null);
        ExecutorService executor = Executors.newFixedThreadPool(4);
        List<RelDTO> emptyList = Lists.newArrayList();
        generateRelList(taxEntity,emptyList);
        List<RelDTO> relList=emptyList.stream().distinct().collect(Collectors.toList());
        List<RelDTO> copyRelList = ListUtils.deepCopy(relList);
        long start = System.currentTimeMillis();
        System.out.println("开始");
        while(!CollectionUtils.isEmpty(relList)){
            //可根据树或者计算单元容器获取树的叶子节点
            List<TaxEntity> leafNodes = getLeafNodeByRelList(relList,taxEntity);
            //并发执行叶子节点取数逻辑 取数成功后修改计算单元列表如果子节点都计算完毕这删除这个计算单元
            List<TaxEntity> targetLeaf = leafNodes.stream().distinct().collect(Collectors.toList());
            Set<Callable<String>> callableSet = new HashSet<>();
            targetLeaf.forEach(item->{
                callableSet.add(new RelBo(relList,item,expressionService));
            });
            List<Future<String>> futures = executor.invokeAll(callableSet);
            for(Future<String> stringFuture : futures) {
                JsonResultBO jsonResultBO = JSON.parseObject(stringFuture.get(), JsonResultBO.class);
                changeTreeTemplate(taxEntity,jsonResultBO);
                changeParent(jsonResultBO,taxEntity,copyRelList);
            }

        }
        assert taxEntity != null;
        Long value = (Long)expressionService.execute(taxEntity.getExpression());
        taxEntity.setCalValue(value.intValue());
        System.out.println(JSON.toJSONString(taxEntity,SerializerFeature.DisableCircularReferenceDetect));
        long stop = System.currentTimeMillis();
        long via= stop-start;
        System.out.println("共耗时"+via);
    }

    private void generateRelList(TaxEntity taxEntity, List<RelDTO> relList) {
       if(hasChild(taxEntity)){
           List<Long> collect = taxEntity.getChildren().stream().map(TaxEntity::getId).collect(Collectors.toList());
           RelDTO build = RelDTO.builder().id(taxEntity.getId()).relIds(collect).build();
           relList.add(build);
           for(TaxEntity item:taxEntity.getChildren()){
               generateRelList(item,relList);
           }
       }

    }

    private void changeParent(JsonResultBO bo, TaxEntity taxEntity, List<RelDTO> copyRelList) {
        List<Long> parentIds= findParentIds(bo.getId(),copyRelList);
        TaxEntity child = taxEntityRepository.findById(bo.getId()).orElse(null);
        changeParentByParentIds(child,parentIds,taxEntity,bo);
    }

    private void changeParentByParentIds(TaxEntity child, List<Long> parentIds, TaxEntity taxEntity,JsonResultBO bo) {
        if(parentIds.contains(taxEntity.getId())){
            String replace = taxEntity.getExpression().replace("["+child.getName()+"]", String.valueOf(bo.getValue()));
            taxEntity.setExpression(replace);
        }
        if(hasChild(taxEntity)){
            for(TaxEntity item:taxEntity.getChildren()){
                changeParentByParentIds(child,parentIds,item,bo);
            }
        }
    }

    private List<Long> findParentIds(Long id, List<RelDTO> copyRelList) {
        List<Long> parentIds = Lists.newArrayList();
        copyRelList.forEach(item->{
            if(item.getRelIds().contains(id)){
                parentIds.add(item.getId());
            }
        });
        return parentIds;
    }

    private void changeTreeTemplate(TaxEntity taxEntity, JsonResultBO bo) {
       if(taxEntity.getId().equals(bo.getId())){
           taxEntity.setCalValue(bo.getValue().intValue());
           return;
       }
        if(hasChild(taxEntity)){
            List<TaxEntity> children = taxEntity.getChildren();
            for (TaxEntity t:children){
                changeTreeTemplate(t,bo);
            }
        }
    }
    @Test//有点东西
    public void testArrayListRemove(){
        List<Integer> integers = Arrays.asList(1, 2, 4);
        integers.remove(1);
    }

    private List<TaxEntity> getLeafNodeByRelList(List<RelDTO> relList,TaxEntity entity) {
        List<Long> leafIds = Lists.newArrayList();
        relList.forEach(item-> leafIds.addAll(item.getRelIds()));
        List<Long> parentIds = relList.stream().map(RelDTO::getId).collect(Collectors.toList());
        List<Long> collect = leafIds.stream().distinct().collect(Collectors.toList());
        collect.removeIf(parentIds::contains);
        List<TaxEntity> leaf = Lists.newArrayList();
        collect.forEach(item->{
           searchNodeById(item,entity,leaf);
        });
        return leaf;
    }

    private void searchNodeById(Long id, TaxEntity entity,List<TaxEntity> list) {
        if(entity.getId().equals(id)){
            list.add(entity);
        }
        if(hasChild(entity)){
            List<TaxEntity> children = entity.getChildren();
            for (TaxEntity t:children){
                if(t.getId().equals(id)){
                    list.add(t);
                    break;
                }
                 searchNodeById(id,t,list);
            }
        }
    }

}
