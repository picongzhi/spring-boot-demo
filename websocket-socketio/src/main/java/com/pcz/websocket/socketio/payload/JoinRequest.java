package com.pcz.websocket.socketio.payload;

import lombok.Data;

/**
 * @author picongzhi
 */
@Data
public class JoinRequest {
    /**
     * 用户id
     */
    private String userId;

    /**
     * 群id
     */
    private String groupId;
}
