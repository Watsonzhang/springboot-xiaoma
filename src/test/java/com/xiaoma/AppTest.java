package com.xiaoma;

import com.google.common.collect.Lists;
import com.xiaoma.aviator.ExpressionService;
import com.xiaoma.entity.MovieEntity;
import com.xiaoma.entity.PersonEntity;
import com.xiaoma.entity.Roles;
import com.xiaoma.entity.TaxEntity;
import com.xiaoma.repository.MovieRepository;
import com.xiaoma.repository.PersonRepository;
import com.xiaoma.repository.TaxEntityRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: zhangwei
 * @Description:测试类
 * @Date:Create：2020/12/11 上午10:49
 */
@SpringBootTest(classes = {App.class})
@RunWith(SpringRunner.class)
public class AppTest {


    @Autowired
    ExpressionService expressionService;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    PersonRepository personRepository;


    @Autowired
    TaxEntityRepository taxEntityRepository;

    @Test
    public void testInsert(){
        PersonEntity director1 = PersonEntity.builder().name("导演1").id(999L).build();
        PersonEntity director2 = PersonEntity.builder().name("导演2").id(998L).build();
        PersonEntity personEntity = personRepository.findByName("个人节点");
        ArrayList<Roles> roles = Lists.newArrayList();
        roles.add(new Roles(personEntity,Arrays.asList("霸王龙","迅猛龙")));
        MovieEntity target = MovieEntity.builder().description("侏罗纪系列电影").title("侏罗纪公园")
                .actorsAndRoles(roles).directors(Lists.newArrayList(director1,director2))
                .build();
        movieRepository.save(target);
    }

    @Test
    public void recursionTax(){
        TaxEntity node4 = TaxEntity.builder().name("三级4").id(4L)
                .expression("[a]")//原子节点
                .build();
        TaxEntity node5 = TaxEntity.builder().name("三级5").id(5L)
                .expression("[b]")//原子节点
                .build();
        TaxEntity node6 = TaxEntity.builder().name("三级6").id(6L)
                .expression("[c]")//原子节点
                .build();
        TaxEntity node7 = TaxEntity.builder().name("三级7").id(7L)
                .expression("[d]").build();//原子节点

        TaxEntity node2 = TaxEntity.builder().name("二级2").id(2L)
                .children(Arrays.asList(node4,node5))
                .expression("[三级4]+[三级5]")
                .build();
        TaxEntity node3 = TaxEntity.builder().name("二级3").id(3L)
                .children(Arrays.asList(node6,node7))
                .expression("[三级6]-[三级7]")
                .build();
        TaxEntity node1 = TaxEntity.builder().name("一级1").id(1L)
                .children(Arrays.asList(node2,node3))
                .expression("[二级3]+[二级2]")
                .build();
        taxEntityRepository.save(node1);
    }

    @Test
    public  void selectRecursionTax(){
        TaxEntity taxEntity = taxEntityRepository.findById(1L).orElse(null);
        Stack<TaxEntity> leafNodes = new Stack<>();
        getLeafNodes(taxEntity,leafNodes);
        System.out.println(leafNodes);
    }

    private void getLeafNodes(TaxEntity taxEntity, Stack<TaxEntity> leafNodes) {
        if(CollectionUtils.isEmpty(taxEntity.getChildren())){
            leafNodes.add(taxEntity);
            return;
        }
        taxEntity.getChildren().forEach(item->{
            getLeafNodes(item,leafNodes);
        });
    }

    @Test
    public void testCalculate(){
        TaxEntity taxEntity = taxEntityRepository.findById(1L).orElse(null);
        calculate(taxEntity);
        System.out.println(taxEntity.getCalValue());
    }

    private void calculate(TaxEntity taxEntity) {
        if(CollectionUtils.isEmpty(taxEntity.getChildren())){
            executeExpression(taxEntity);
            return;
        }
        List<TaxEntity> children = taxEntity.getChildren();
        for (TaxEntity item:children){
            calculate(item);
            String expression = taxEntity.getExpression();
            String replace = expression.replace("["+item.getName()+"]", String.valueOf(item.getCalValue()));
            taxEntity.setExpression(replace);
        }
        executeExpression(taxEntity);
    }

    private void executeExpression(TaxEntity taxEntity) {
        if(taxEntity.getExpression().equals("[a]")){
            taxEntity.setCalValue(1);
            return;
        }
        if(taxEntity.getExpression().equals("[b]")){
            taxEntity.setCalValue(2);
            return;
        }
        if(taxEntity.getExpression().equals("[c]")){
            taxEntity.setCalValue(3);
            return;
        }
        if(taxEntity.getExpression().equals("[d]")){
            taxEntity.setCalValue(4);
            return;
        }
        Long execute = (Long)expressionService.execute(taxEntity.getExpression());
        taxEntity.setCalValue(execute.intValue());

    }
    
    @Test
    public  void testAdd(){
        TaxEntity newLevel2 = TaxEntity.builder().expression("[三级4]*[三级5]").name("这是新二级").id(88L).build();
        Set<String> strings = readExpression(newLevel2.getExpression());
        List<TaxEntity> level3= findByExpressions(strings);
        newLevel2.setChildren(level3);
        taxEntityRepository.save(newLevel2);
        TaxEntity node1 = taxEntityRepository.findById(1L).orElse(null);
        node1.setChildren(Arrays.asList(newLevel2));
        node1.setExpression(node1.getExpression()+"+"+"[这是新二级]");
        taxEntityRepository.save(node1);

    }


    @Test
    public void deleteAll(){
        taxEntityRepository.deleteAll();
    }
    private List<TaxEntity> findByExpressions(Set<String> objects) {
        ArrayList<TaxEntity> children = Lists.newArrayList();
        objects.forEach(item->{
            TaxEntity byExpression = taxEntityRepository.findByName(item);
            children.add(byExpression);
        });
        return children;
    }


    public static Set<String> readExpression(String exp) {
        Set<String> ls = new HashSet<String>();
        Pattern pattern = Pattern.compile("(?<=\\[)(.+?)(?=\\])");
        Matcher matcher = pattern.matcher(exp);
        while (matcher.find()) {
            ls.add(matcher.group());
        }
        return ls;
    }

}
