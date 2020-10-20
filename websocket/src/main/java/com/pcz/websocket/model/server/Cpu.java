package com.pcz.websocket.model.server;

import cn.hutool.core.util.NumberUtil;

/**
 * @author picongzhi
 */
public class Cpu {
    /**
     * 核心数
     */
    private int cpuNum;

    /**
     * CPU总的使用率
     */
    private double total;

    /**
     * CPU系统使用率
     */
    private double sys;

    /**
     * CPU用户使用率
     */
    private double user;

    /**
     * CPU当前等待率
     */
    private double wait;

    /**
     * CPU当前空闲率
     */
    private double free;

    public int getCpuNum() {
        return cpuNum;
    }

    public void setCpuNum(int cpuNum) {
        this.cpuNum = cpuNum;
    }

    public double getTotal() {
        return NumberUtil.round(NumberUtil.mul(total, 100), 2).doubleValue();
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getSys() {
        return NumberUtil.round(NumberUtil.mul(sys / total, 100), 2).doubleValue();
    }

    public void setSys(double sys) {
        this.sys = sys;
    }

    public double getUser() {
        return NumberUtil.round(NumberUtil.mul(user / total, 100), 2).doubleValue();
    }

    public void setUser(double user) {
        this.user = user;
    }

    public double getWait() {
        return NumberUtil.round(NumberUtil.mul(wait / total, 100), 2).doubleValue();
    }

    public void setWait(double wait) {
        this.wait = wait;
    }

    public double getFree() {
        return NumberUtil.round(NumberUtil.mul(free / total, 100), 2).doubleValue();
    }

    public void setFree(double free) {
        this.free = free;
    }
}
