package com.pcz.websocket.util;

import cn.hutool.core.lang.Dict;
import com.pcz.websocket.model.Server;
import com.pcz.websocket.payload.ServerVo;

/**
 * @author picongzhi
 */
public class ServerUtil {
    /**
     * 包装成 ServerVo
     *
     * @param server server
     * @return ServerVo
     */
    public static ServerVo wrapServerVo(Server server) {
        ServerVo serverVo = new ServerVo();
        serverVo.create(server);

        return serverVo;
    }

    /**
     * 包装成 Dict
     *
     * @param serverVo serverVo
     * @return Dict
     */
    public static Dict wrapServerDict(ServerVo serverVo) {
        return Dict.create()
                .set("cpu", serverVo.getCpu().get(0).getData())
                .set("mem", serverVo.getMem().get(0).getData())
                .set("sys", serverVo.getSys().get(0).getData())
                .set("jvm", serverVo.getJvm().get(0).getData())
                .set("sysFile", serverVo.getSysFile().get(0).getData());
    }
}
