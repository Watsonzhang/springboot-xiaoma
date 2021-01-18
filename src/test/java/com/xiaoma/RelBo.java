package com.xiaoma;

import com.xiaoma.model.bo.RelDTO;

import java.util.List;
import java.util.concurrent.Callable;

public class RelBo implements Callable<Long> {
    private volatile List<RelDTO> list;

    public RelBo(List<RelDTO> list) {
        this.list = list;
    }

    @Override
    public Long call() throws Exception {

        return null;
    }
}
