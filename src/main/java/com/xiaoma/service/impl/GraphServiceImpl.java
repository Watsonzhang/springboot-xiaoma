package com.xiaoma.service.impl;

import com.google.common.collect.Lists;
import com.xiaoma.aviator.ExpressionService;
import com.xiaoma.entity.PersonEntity;
import com.xiaoma.entity.TaxEntity;
import com.xiaoma.model.dto.TaxDTO;
import com.xiaoma.repository.MovieRepository;
import com.xiaoma.repository.PersonRepository;
import com.xiaoma.repository.TaxEntityRepository;
import com.xiaoma.service.GraphService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2021/1/14 下午2:26
 */

@Service
public class GraphServiceImpl  implements GraphService {

    private static Stack<Long> idStock=new Stack<Long>();

    @Autowired
    TaxEntityRepository taxEntityRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    ExpressionService expressionService;

     static {
         for (long i = 0; i < 1000; i++) {
             assert idStock != null;
             idStock.add(i);
         }
     }


    public void testSave() {
        PersonEntity build = PersonEntity.builder().name("个人节点").id(1000L).build();
        personRepository.save(build);
    }

    @Override
    public void addTaxNode(TaxDTO dto) {
        List<TaxEntity> list = Lists.newArrayList();
        TaxEntity taxEntity = buildRecurseTax(dto,list);
        taxEntityRepository.save(taxEntity);
        System.out.println(taxEntity);
    }

    private TaxEntity buildRecurseTax(TaxDTO dto,List<TaxEntity> temp) {
        List<TaxDTO> children = dto.getChildren();
        TaxEntity build;
        Optional<TaxEntity> any = temp.stream().filter(item -> item.getName().equals(dto.getName())).findAny();
        if(!any.isPresent()){
            build = TaxEntity.builder().name(dto.getName()).id(getId())
                    .expression(dto.getExpression()).build();
            temp.add(build);
        }else {
            build=any.get();
        }
        if(CollectionUtils.isEmpty(children)){
            return build;
        }
        List<TaxEntity> list = Lists.newArrayList();
        children.forEach(item->{
            TaxEntity taxEntity = buildRecurseTax(item,temp);
            list.add(taxEntity);
        });
        build.setChildren(list);
        return build;
    }

    @Override
    public void addTaxNode(List<TaxDTO> dtos) {
        List<TaxEntity> collect = dtos.stream().map(item -> TaxEntity.builder().name(item.getName())
                .id(getId()).expression(item.getExpression()).build()).collect(Collectors.toList());
        taxEntityRepository.saveAll(collect);
    }

    @Override
    public void deleteAll() {
        taxEntityRepository.deleteAll();
    }

    @Override
    public TaxEntity findById(Long id) {
        return taxEntityRepository.findById(id).orElse(null);
    }

    @Override
    public void calculate(TaxEntity taxEntity) {
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

    @Override
    public void getLeafNodes(TaxEntity entity, List<TaxEntity> list) {
        if(CollectionUtils.isEmpty(entity.getChildren())){
            list.add(entity);
        }
        entity.getChildren().forEach(item->{
            getLeafNodes(item,list);
        });
    }


    public Long getId(){
        return idStock.pop();
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


}
