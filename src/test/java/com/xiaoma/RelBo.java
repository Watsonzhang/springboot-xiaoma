package com.xiaoma;

import com.xiaoma.entity.TaxEntity;
import com.xiaoma.model.bo.RelDTO;
import lombok.Data;

import java.util.List;
import java.util.concurrent.Callable;

@Data
public class RelBo implements Callable<Long> {

    private  List<RelDTO> list;
    private TaxEntity entity;

    public RelBo(List<RelDTO> list, TaxEntity entity) {
        this.list = list;
        this.entity = entity;
    }

    @Override
    public Long call() throws Exception {
        List<RelDTO> list = getList();

        return null;
    }
}
