package com.pcz.websocket.payload.server;

import com.google.common.collect.Lists;
import com.pcz.websocket.model.server.Sys;
import com.pcz.websocket.payload.KV;
import lombok.Data;

import java.util.List;

/**
 * @author picongzhi
 */
@Data
public class SysVo {
    List<KV> data = Lists.newArrayList();

    public static SysVo create(Sys sys) {
        SysVo sysVo = new SysVo();
        sysVo.data.add(new KV("服务器名称", sys.getComputerName()));
        sysVo.data.add(new KV("服务器IP", sys.getComputerIp()));
        sysVo.data.add(new KV("项目路径", sys.getUserDir()));
        sysVo.data.add(new KV("操作系统", sys.getOsName()));
        sysVo.data.add(new KV("系统架构", sys.getOsArch()));

        return sysVo;
    }
}
