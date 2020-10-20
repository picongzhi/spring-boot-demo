package com.pcz.websocket.model;

import cn.hutool.core.util.NumberUtil;
import com.pcz.websocket.model.server.*;
import com.pcz.websocket.util.IpUtil;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.CentralProcessor.TickType;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.Util;

import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * @author picongzhi
 */
public class Server {
    private static final int OSHI_WAIT_SECOND = 1000;

    /**
     * CPU信息
     */
    private Cpu cpu = new Cpu();

    /**
     * 内存信息
     */
    private Mem mem = new Mem();

    /**
     * JVM信息
     */
    private Jvm jvm = new Jvm();

    /**
     * 系统信息
     */
    private Sys sys = new Sys();

    /**
     * 硬盘信息
     */
    private List<SysFile> sysFiles = new LinkedList<>();

    public Cpu getCpu() {
        return cpu;
    }

    public void setCpu(Cpu cpu) {
        this.cpu = cpu;
    }

    public Mem getMem() {
        return mem;
    }

    public void setMem(Mem mem) {
        this.mem = mem;
    }

    public Jvm getJvm() {
        return jvm;
    }

    public void setJvm(Jvm jvm) {
        this.jvm = jvm;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public List<SysFile> getSysFiles() {
        return sysFiles;
    }

    public void setSysFiles(List<SysFile> sysFiles) {
        this.sysFiles = sysFiles;
    }

    public void copyTo() throws Exception {
        SystemInfo systemInfo = new SystemInfo();
        HardwareAbstractionLayer hardwareAbstractionLayer = systemInfo.getHardware();
        setCpuInfo(hardwareAbstractionLayer.getProcessor());
        setMemInfo(hardwareAbstractionLayer.getMemory());
        setSysInfo();
        setJvmInfo();
        setSysFiles(systemInfo.getOperatingSystem());
    }

    /**
     * 设置CPU信息
     *
     * @param processor
     */
    private void setCpuInfo(CentralProcessor processor) {
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        Util.sleep(OSHI_WAIT_SECOND);
        long[] ticks = processor.getSystemCpuLoadTicks();

        long nice = ticks[TickType.NICE.getIndex()] - prevTicks[TickType.NICE.getIndex()];
        long irq = ticks[TickType.IRQ.getIndex()] - prevTicks[TickType.IRQ.getIndex()];
        long softirq = ticks[TickType.SOFTIRQ.getIndex()] - prevTicks[TickType.SOFTIRQ.getIndex()];
        long steal = ticks[TickType.STEAL.getIndex()] - prevTicks[TickType.STEAL.getIndex()];
        long cSys = ticks[TickType.SYSTEM.getIndex()] - prevTicks[TickType.SYSTEM.getIndex()];
        long user = ticks[TickType.USER.getIndex()] - prevTicks[TickType.USER.getIndex()];
        long iowait = ticks[TickType.IOWAIT.getIndex()] - prevTicks[TickType.IOWAIT.getIndex()];
        long idle = ticks[TickType.IDLE.getIndex()] - prevTicks[TickType.IDLE.getIndex()];
        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;

        cpu.setCpuNum(processor.getLogicalProcessorCount());
        cpu.setTotal(totalCpu);
        cpu.setSys(cSys);
        cpu.setUser(user);
        cpu.setWait(iowait);
        cpu.setFree(idle);
    }

    /**
     * 设置内存信息
     *
     * @param memory
     */
    private void setMemInfo(GlobalMemory memory) {
        mem.setTotal(memory.getTotal());
        mem.setUsed(memory.getTotal() - memory.getAvailable());
        mem.setFree(memory.getAvailable());
    }

    /**
     * 设置系统信息
     */
    private void setSysInfo() {
        Properties properties = System.getProperties();
        sys.setComputerName(IpUtil.getHostName());
        sys.setComputerIp(IpUtil.getHostIp());
        sys.setOsName(properties.getProperty("os.name"));
        sys.setOsArch(properties.getProperty("os.arch"));
        sys.setUserDir(properties.getProperty("user.dir"));
    }

    /**
     * 设置Java虚拟机
     *
     * @throws UnknownHostException
     */
    private void setJvmInfo() throws UnknownHostException {
        Properties properties = System.getProperties();
        jvm.setTotal(Runtime.getRuntime().totalMemory());
        jvm.setMax(Runtime.getRuntime().maxMemory());
        jvm.setFree(Runtime.getRuntime().freeMemory());
        jvm.setVersion(properties.getProperty("java.version"));
        jvm.setHome(properties.getProperty("java.home"));
    }

    /**
     * 设置磁盘信息
     *
     * @param os
     */
    private void setSysFiles(OperatingSystem os) {
        FileSystem fileSystem = os.getFileSystem();
        OSFileStore[] osFileStores = fileSystem.getFileStores();
        for (OSFileStore osFileStore : osFileStores) {
            long free = osFileStore.getUsableSpace();
            long total = osFileStore.getTotalSpace();
            long used = total - free;

            SysFile sysFile = new SysFile();
            sysFile.setDirName(osFileStore.getMount());
            sysFile.setSysTypeName(osFileStore.getType());
            sysFile.setTypeName(osFileStore.getName());
            sysFile.setTotal(convertFileSize(total));
            sysFile.setFree(convertFileSize(free));
            sysFile.setUsed(convertFileSize(used));
            sysFile.setUsage(NumberUtil.mul(NumberUtil.div(used, total, 4), 100));
            sysFiles.add(sysFile);
        }
    }

    /**
     * 字节转换
     *
     * @param size 字节大小
     * @return 转换后的值
     */
    public String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else {
            return String.format("%d B", size);
        }
    }
}
