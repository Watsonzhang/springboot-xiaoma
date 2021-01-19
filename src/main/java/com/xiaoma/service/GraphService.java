package com.xiaoma.service;

import com.xiaoma.entity.TaxEntity;
import com.xiaoma.model.dto.TaxDTO;

import javax.swing.text.html.parser.Entity;
import java.util.List;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2021/1/14 下午2:26
 */

public interface GraphService {

    void testSave();

    void addTaxNode(TaxDTO dto);

    void addTaxNode(List<TaxDTO> dto);

    void deleteAll();

    TaxDTO findById(Long id);

    //递归计算方法
    void calculate(TaxEntity taxEntity);

    void getLeafNodes(TaxEntity entity, List<TaxEntity> list);

    List<TaxEntity> findByIds(List<Long> ids);

}
