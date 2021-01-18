package com.xiaoma;

import com.xiaoma.model.bo.RelDTO;
import lombok.Data;

import java.util.List;
import java.util.concurrent.Callable;

@Data
public class RelBo implements Callable<Long> {
    private volatile List<RelDTO> list;

    public RelBo(List<RelDTO> list) {
        this.list = list;
    }

    @Override
    public Long call() throws Exception {
        List<RelDTO> list = getList();

        return null;
    }
}
