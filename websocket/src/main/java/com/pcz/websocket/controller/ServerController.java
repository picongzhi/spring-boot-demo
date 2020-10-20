package com.pcz.websocket.controller;

import cn.hutool.core.lang.Dict;
import com.pcz.websocket.model.Server;
import com.pcz.websocket.payload.ServerVo;
import com.pcz.websocket.util.ServerUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author picongzhi
 */
@RestController
@RequestMapping("/server")
public class ServerController {
    @GetMapping
    public Dict serverInfo() throws Exception {
        Server server = new Server();
        server.copyTo();
        ServerVo serverVo = ServerUtil.wrapServerVo(server);

        return ServerUtil.wrapServerDict(serverVo);
    }
}
