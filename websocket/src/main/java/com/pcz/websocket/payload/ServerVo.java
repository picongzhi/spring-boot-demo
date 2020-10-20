package com.pcz.websocket.payload;

import com.google.common.collect.Lists;
import com.pcz.websocket.model.Server;
import com.pcz.websocket.payload.server.*;
import lombok.Data;

import java.util.List;

/**
 * @author picongzhi
 */
@Data
public class ServerVo {
    List<CpuVo> cpu = Lists.newArrayList();
    List<JvmVo> jvm = Lists.newArrayList();
    List<MemVo> mem = Lists.newArrayList();
    List<SysFileVo> sysFile = Lists.newArrayList();
    List<SysVo> sys = Lists.newArrayList();

    public ServerVo create(Server server) {
        cpu.add(CpuVo.create(server.getCpu()));
        jvm.add(JvmVo.create(server.getJvm()));
        mem.add(MemVo.create(server.getMem()));
        sysFile.add(SysFileVo.create(server.getSysFiles()));
        sys.add(SysVo.create(server.getSys()));

        return null;
    }
}
