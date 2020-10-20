package com.pcz.websocket.payload.server;

import com.google.common.collect.Lists;
import com.pcz.websocket.model.server.Mem;
import com.pcz.websocket.payload.KV;
import lombok.Data;

import java.util.List;

/**
 * @author picongzhi
 */
@Data
public class MemVo {
    List<KV> data = Lists.newArrayList();

    public static MemVo create(Mem mem) {
        MemVo memVo = new MemVo();
        memVo.data.add(new KV("内存容量", mem.getTotal() + "G"));
        memVo.data.add(new KV("已用内存", mem.getUsed() + "G"));
        memVo.data.add(new KV("剩余内存", mem.getFree() + "G"));
        memVo.data.add(new KV("使用率", mem.getUsage() + "%"));

        return memVo;
    }
}
