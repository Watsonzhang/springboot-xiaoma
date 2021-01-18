package com.xiaoma;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaoma.aviator.ExpressionService;
import com.xiaoma.entity.TaxEntity;
import com.xiaoma.model.bo.GetContentTask;
import com.xiaoma.model.bo.MiddleBo;
import com.xiaoma.model.bo.RelDTO;
import com.xiaoma.model.bo.RelationDTO;
import com.xiaoma.model.dto.TaxDTO;
import com.xiaoma.repository.TaxEntityRepository;
import com.xiaoma.service.GraphService;
import org.junit.Test;
import org.junit.platform.commons.util.StringUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

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
    public void testGetLeafNodes(){
        TaxEntity taxEntity = taxEntityRepository.findById(999L).orElse(null);
        List<TaxEntity> objects = Lists.newArrayList();
        graphService.getLeafNodes(taxEntity,objects);
        System.out.println(objects);
    }

    @Test
    public void testThreadPool(){
        TaxEntity taxEntity = taxEntityRepository.findById(999L).orElse(null);
        List<TaxEntity> objects = Lists.newArrayList();
        graphService.getLeafNodes(taxEntity,objects);
        List<Runnable> task= objects.stream().map(this::apply).collect(Collectors.toList());
        ExecutorService executor = Executors.newFixedThreadPool(4);
        task.forEach(item->{
           executor.execute(item);
        });

    }


    private Runnable apply(TaxEntity item) {
        return () -> {
            Long execute = (Long)expressionService.execute(item.getExpression());
            item.setCalValue(execute.intValue());
        };
        // return () -> expressionService.execute(item.getExpression());
    }


    @Test
    public void testAlgorithm(){
        ExecutorService executor = Executors.newFixedThreadPool(4);
        TaxEntity taxEntity = taxEntityRepository.findById(999L).orElse(null);//root
        //叶子节点
        List<TaxEntity> leafNodes = Lists.newArrayList();
        graphService.getLeafNodes(taxEntity,leafNodes);
        //叶子节点去重
        List<TaxEntity> distinctLeafNodes= distinctLeafNodes(leafNodes);
        List<Runnable> task= leafNodes.stream().map(this::apply).collect(Collectors.toList());
        task.forEach(item->{
            executor.execute(item);
        });
        List<RelationDTO> relationDTOList = Lists.newArrayList();
        iteratorTaxEntity(taxEntity,relationDTOList,0L);
        System.out.println(relationDTOList);
        List<RelationDTO> distinct= distinctList(relationDTOList);
        List<RelationDTO> synList = Collections.synchronizedList(distinct);
        TaxEntity middle = removeLeafNodeAndResStruct(taxEntity, leafNodes);
        //维护所有子到父节的关系映射 ① 例如 节点8->节点2

        //并发计算叶子节点并存储计算结果 ②
        //摘除叶子节点 内存形成新树 值带入表达式 ③
        //再次计算新树叶子节点②③



    }

    @Test
    public void testFinal(){
        //叶子节点
        TaxEntity taxEntity = taxEntityRepository.findById(999L).orElse(null);//root
        List<TaxEntity> leafNodes = Lists.newArrayList();
        graphService.getLeafNodes(taxEntity,leafNodes);
        List<TaxEntity> relationDTOList = Lists.newArrayList();


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


    private void addToQueue(TaxEntity entity,ArrayBlockingQueue<TaxEntity> queue){
        queue.add(entity);
        if(hasChild(entity)){
           for(TaxEntity item:entity.getChildren()){
               addToQueue(item,queue);
           }
        }
    }

    private void addToList(TaxEntity entity,List<TaxEntity> list){
        list.add(entity);
        if(hasChild(entity)){
            for(TaxEntity item:entity.getChildren()){
                addToList(item,list);
            }
        }
    }

    private boolean hasChild(TaxEntity entity){
        return  !CollectionUtils.isEmpty(entity.getChildren());
    }

    @Test
    public void  test(){
        //根据树生成计算单元 计算单元为深度为1的类树结构
        TaxEntity taxEntity = taxEntityRepository.findById(999L).orElse(null);
        ExecutorService executor = Executors.newFixedThreadPool(4);
        List<RelDTO> relList = Lists.newArrayList();
        ArrayList<Long> l1 = new ArrayList<>();
        l1.add(995L);
        l1.add(998L);
        ArrayList<Long> l2 = new ArrayList<>();
        l2.add(997L);
        l2.add(992L);
        ArrayList<Long> l3 = new ArrayList<>();
        l3.add(996L);
        l3.add(995L);
        ArrayList<Long> l4 = new ArrayList<>();
        l4.add(993L);
        l4.add(994L);
        relList.add(RelDTO.builder().id(999L).relIds(l1).build());
        relList.add(RelDTO.builder().id(998L).relIds(l2).build());
        relList.add(RelDTO.builder().id(997L).relIds(l3).build());
        relList.add(RelDTO.builder().id(995L).relIds(l4).build());
        while(!CollectionUtils.isEmpty(relList)){
            //可根据树或者计算单元容器获取树的叶子节点
            List<TaxEntity> leafNodes = getLeafNodeByRelList(relList);
            //并发执行叶子节点取数逻辑 取数成功后修改计算单元列表如果子节点都计算完毕这删除这个计算单元
            List<Runnable> task= leafNodes.stream().map(item->getResultAndModify(item,relList)).collect(Collectors.toList());
            task.forEach(executor::execute);
            System.out.println(relList);

        }


    }

    @Test
    public void restRemove(){
        List<RelDTO> relList = Lists.newArrayList();
        ArrayList<Long> l1 = new ArrayList<>();
        l1.add(995L);
        l1.add(998L);
        ArrayList<Long> l2 = new ArrayList<>();
        l2.add(997L);
        l2.add(992L);
        ArrayList<Long> l3 = new ArrayList<>();
        l3.add(996L);
        l3.add(995L);
        ArrayList<Long> l4 = new ArrayList<>();
        l4.add(993L);
        l4.add(994L);
        relList.add(RelDTO.builder().id(999L).relIds(l1).build());
        relList.add(RelDTO.builder().id(998L).relIds(l2).build());
        relList.add(RelDTO.builder().id(997L).relIds(l3).build());
        relList.add(RelDTO.builder().id(995L).relIds(l4).build());
        Iterator<RelDTO> iterator = relList.iterator();
        while (iterator.hasNext()) {
            RelDTO next = iterator.next();
            if(next.getId().equals(998L)){
                iterator.remove();
            }
        }
        System.out.println(relList);
    }

    @Test//有点东西
    public void testArrayListRemove(){
        List<Integer> integers = Arrays.asList(1, 2, 4);
        integers.remove(1);
    }



    private Runnable getResultAndModify(TaxEntity item, List<RelDTO> relList) {
        return () -> {
            Long execute = (Long)expressionService.execute(item.getExpression());
            item.setCalValue(execute.intValue());
            modifyList(relList,item);
        };
    }

    private synchronized void modifyList(List<RelDTO> relList, TaxEntity item) {
        relList.removeIf(i->{
            List<Long> relIds = i.getRelIds();
            relIds.removeIf(id -> id.equals(item.getId()));
            i.setRelIds(relIds);
            return CollectionUtils.isEmpty(i.getRelIds());
        });
        System.out.println(relList);
    }


    private List<TaxEntity> getLeafNodeByRelList(List<RelDTO> relList) {
        List<TaxEntity> leafNodes = Lists.newArrayList();
        TaxEntity taxEntity = taxEntityRepository.findById(999L).orElse(null);
        graphService.getLeafNodes(taxEntity,leafNodes);
        return leafNodes;
    }
}
