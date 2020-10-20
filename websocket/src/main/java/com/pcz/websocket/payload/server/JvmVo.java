package com.pcz.websocket.payload.server;

import com.google.common.collect.Lists;
import com.pcz.websocket.model.server.Jvm;
import com.pcz.websocket.payload.KV;
import lombok.Data;

import java.util.List;

/**
 * @author picongzhi
 */
@Data
public class JvmVo {
    List<KV> data = Lists.newArrayList();

    public static JvmVo create(Jvm jvm) {
        JvmVo jvmVo = new JvmVo();
        jvmVo.data.add(new KV("当前JVM占用的内存总数(M)", jvm.getTotal() + "M"));
        jvmVo.data.add(new KV("JVM最大可用内存总数(M)", jvm.getMax() + "M"));
        jvmVo.data.add(new KV("JVM空闲内存(M)", jvm.getFree() + "M"));
        jvmVo.data.add(new KV("JVM使用率", jvm.getUsage() + "%"));
        jvmVo.data.add(new KV("JDK版本", jvm.getVersion()));
        jvmVo.data.add(new KV("JDK路径", jvm.getHome()));
        jvmVo.data.add(new KV("JDK启动时间", jvm.getStartTime()));
        jvmVo.data.add(new KV("JDK运行时间", jvm.getRunTime()));

        return jvmVo;
    }
}
