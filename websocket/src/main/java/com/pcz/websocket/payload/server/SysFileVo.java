package com.pcz.websocket.payload.server;

import com.google.common.collect.Lists;
import com.pcz.websocket.model.server.SysFile;
import com.pcz.websocket.payload.KV;
import lombok.Data;

import java.util.List;

/**
 * @author picongzhi
 */
@Data
public class SysFileVo {
    List<List<KV>> data = Lists.newArrayList();

    public static SysFileVo create(List<SysFile> sysFiles) {
        SysFileVo sysFileVo = new SysFileVo();
        for (SysFile sysFile : sysFiles) {
            List<KV> item = Lists.newArrayList();
            item.add(new KV("盘符路径", sysFile.getDirName()));
            item.add(new KV("盘符类型", sysFile.getSysTypeName()));
            item.add(new KV("文件类型", sysFile.getTypeName()));
            item.add(new KV("总大小", sysFile.getTotal()));
            item.add(new KV("剩余大小", sysFile.getFree()));
            item.add(new KV("已经使用量", sysFile.getUsed()));
            item.add(new KV("资源使用率", sysFile.getUsage() + "%"));

            sysFileVo.data.add(item);
        }

        return sysFileVo;
    }
}
