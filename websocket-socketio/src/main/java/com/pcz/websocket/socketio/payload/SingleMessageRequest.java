package com.pcz.websocket.socketio.payload;

import lombok.Data;

/**
 * @author picongzhi
 */
@Data
public class SingleMessageRequest {
    /**
     * 发送方id
     */
    private String fromUid;

    /**
     * 接收方id
     */
    private String toUid;

    /**
     * 消息内容
     */
    private String message;
}
