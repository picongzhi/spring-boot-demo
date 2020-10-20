package com.pcz.websocket.socketio.payload;

import lombok.Data;

/**
 * @author picongzhi
 */
@Data
public class GroupMessageRequest {
    /**
     * 发送方id
     */
    private String fromUid;

    /**
     * 群组id
     */
    private String groupId;

    /**
     * 消息内容
     */
    private String message;
}
