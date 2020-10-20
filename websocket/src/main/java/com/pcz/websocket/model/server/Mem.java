package com.pcz.websocket.model.server;

import cn.hutool.core.util.NumberUtil;

/**
 * @author picongzhi
 */
public class Mem {
    /**
     * 内存总量
     */
    private double total;

    /**
     * 已用内存
     */
    private double used;

    /**
     * 剩余内存
     */
    private double free;

    public double getTotal() {
        return NumberUtil.div(total, (1024 * 1024 * 1024), 2);
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getUsed() {
        return NumberUtil.div(used, (1024 * 1024 * 1024), 2);

    }

    public void setUsed(double used) {
        this.used = used;
    }

    public double getFree() {
        return NumberUtil.div(free, (1024 * 1024 * 1024), 2);
    }

    public void setFree(double free) {
        this.free = free;
    }

    public double getUsage() {
        return NumberUtil.mul(NumberUtil.div(used, total, 4), 100);
    }
}
