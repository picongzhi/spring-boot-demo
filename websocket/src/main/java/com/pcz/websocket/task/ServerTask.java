package com.pcz.websocket.task;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.json.JSONUtil;
import com.pcz.websocket.common.WebSocketConsts;
import com.pcz.websocket.model.Server;
import com.pcz.websocket.payload.ServerVo;
import com.pcz.websocket.util.ServerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author picongzhi
 */
@Slf4j
@Component
public class ServerTask {
    @Autowired
    private SimpMessagingTemplate wsTemplate;

    /**
     * 每个2s执行一次
     *
     * @throws Exception
     */
    @Scheduled(cron = "0/2 * * * * ?")
    public void websocket() throws Exception {
        log.info("[推送消息]开始执行: {}", DateUtil.formatDateTime(new Date()));
        Server server = new Server();
        server.copyTo();

        ServerVo serverVo = ServerUtil.wrapServerVo(server);
        Dict dict = ServerUtil.wrapServerDict(serverVo);

        wsTemplate.convertAndSend(WebSocketConsts.PUSH_SERVER, JSONUtil.toJsonStr(dict));
        log.info("[推送消息] 执行结束: {}", DateUtil.formatDateTime(new Date()));
    }
}
