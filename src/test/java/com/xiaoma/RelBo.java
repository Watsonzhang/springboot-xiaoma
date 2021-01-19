package com.xiaoma;

import com.alibaba.fastjson.JSON;
import com.xiaoma.aviator.ExpressionService;
import com.xiaoma.entity.TaxEntity;
import com.xiaoma.model.bo.RelDTO;
import com.xiaoma.model.bo.RelationDTO;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.Callable;

@Data
public class RelBo implements Callable<String> {

    private  List<RelDTO> list;
    private TaxEntity entity;
    private ExpressionService service;

    public RelBo(List<RelDTO> list, TaxEntity entity, ExpressionService service) {
        this.list = list;
        this.entity = entity;
        this.service = service;
    }

    @Override
    public String call() throws Exception {
        Long execute = (Long)service.execute(this.getEntity().getExpression());
        synchronized (RelBo.class){
            modifyList(list,this.getEntity());
        }

        return JSON.toJSONString(new JsonResultBO(entity.getId(),execute));
    }

    private  void modifyList(List<RelDTO> relList, TaxEntity item) {
        relList.removeIf(i->{
            List<Long> relIds = i.getRelIds();
            relIds.removeIf(id -> id.equals(item.getId()));
            i.setRelIds(relIds);
            return CollectionUtils.isEmpty(i.getRelIds());
        });
    }
}
