package com.pcz.websocket.socketio.payload;

import lombok.Data;

/**
 * @author picongzhi
 */
@Data
public class BroadcastMessageRequest {
    /**
     * 消息内容
     */
    private String message;
}
