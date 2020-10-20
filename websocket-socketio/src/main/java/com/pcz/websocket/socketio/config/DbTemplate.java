package com.pcz.websocket.socketio.config;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author picongzhi
 */
@Component
public class DbTemplate {
    /**
     * 模拟数据库存储 user_id -> session_id
     */
    public static final ConcurrentHashMap<String, UUID> DB = new ConcurrentHashMap<>();

    /**
     * 获取所有SessionId
     *
     * @return sessionId列表
     */
    public List<UUID> findAll() {
        return CollUtil.newArrayList(DB.values());
    }

    /**
     * 根据userId查询sessionId
     *
     * @param userId userId
     * @return sessionId
     */
    public Optional<UUID> findByUserId(String userId) {
        return Optional.ofNullable(DB.get(userId));
    }

    /**
     * 保存/更新 user_id -> session_id
     *
     * @param userId    userId
     * @param sessionId sessionId
     */
    public void save(String userId, UUID sessionId) {
        DB.put(userId, sessionId);
    }

    /**
     * 删除 user_id -> session_id
     *
     * @param userId userId
     */
    public void deleteByUserId(String userId) {
        DB.remove(userId);
    }
}
