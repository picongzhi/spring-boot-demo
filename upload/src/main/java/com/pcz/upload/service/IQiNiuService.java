package com.pcz.upload.service;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;

import java.io.File;

/**
 * @author picongzhi
 */
public interface IQiNiuService {
    /**
     * 上传文件
     *
     * @param file 文件
     * @return Response
     * @throws QiniuException 异常
     */
    Response uploadFile(File file) throws QiniuException;
}
