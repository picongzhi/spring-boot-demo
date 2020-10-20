package com.pcz.codegen.controller;

import cn.hutool.core.io.IoUtil;
import com.google.common.net.HttpHeaders;
import com.pcz.codegen.common.R;
import com.pcz.codegen.constants.GenConstants;
import com.pcz.codegen.entity.GenConfig;
import com.pcz.codegen.entity.TableRequest;
import com.pcz.codegen.service.CodeGenService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @author picongzhi
 */
@RestController
@AllArgsConstructor
@RequestMapping("/generator")
public class CodeGenController {
    private final CodeGenService codeGenService;

    @GetMapping("/table")
    public R listTables(TableRequest request) {
        return R.success(codeGenService.listTables(request));
    }

    @SneakyThrows
    @PostMapping("")
    public void generateCode(@RequestBody GenConfig genConfig, HttpServletResponse response) {
        byte[] data = codeGenService.generateCode(genConfig);

        response.reset();
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s.zip", genConfig.getTableName()));
        response.addHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(data.length));
        response.setContentType("application/octet-stream; charset=UTF-8");

        IoUtil.write(response.getOutputStream(), Boolean.TRUE, data);
    }
}
