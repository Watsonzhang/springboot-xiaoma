package com.xiaoma.service;

import com.xiaoma.entity.TaxEntity;
import com.xiaoma.model.dto.TaxDTO;

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

    TaxEntity findById(Long id);





}
