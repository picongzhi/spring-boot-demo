package com.pcz.websocket.payload.server;

import com.google.common.collect.Lists;
import com.pcz.websocket.model.server.Cpu;
import com.pcz.websocket.payload.KV;
import lombok.Data;

import java.util.List;

/**
 * @author picongzhi
 */
@Data
public class CpuVo {
    List<KV> data = Lists.newArrayList();

    public static CpuVo create(Cpu cpu) {
        CpuVo cpuVo = new CpuVo();
        cpuVo.data.add(new KV("核心数", cpu.getCpuNum()));
        cpuVo.data.add(new KV("CPU总的使用率", cpu.getTotal()));
        cpuVo.data.add(new KV("CPU系统使用率", cpu.getSys() + "%"));
        cpuVo.data.add(new KV("CPU用户使用率", cpu.getUser() + "%"));
        cpuVo.data.add(new KV("CPU当前等待率", cpu.getWait() + "%"));
        cpuVo.data.add(new KV("CPU当前空闲率", cpu.getFree() + "%"));

        return cpuVo;
    }
}
