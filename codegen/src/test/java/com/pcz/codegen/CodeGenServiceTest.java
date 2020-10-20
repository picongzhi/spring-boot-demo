package com.pcz.codegen;

import cn.hutool.core.io.IoUtil;
import cn.hutool.db.Entity;
import com.pcz.codegen.common.PageResult;
import com.pcz.codegen.entity.GenConfig;
import com.pcz.codegen.entity.TableRequest;
import com.pcz.codegen.service.CodeGenService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class CodeGenServiceTest {
    @Autowired
    private CodeGenService codeGenService;

    @Test
    public void testTablePage() {
        TableRequest request = new TableRequest();
        request.setCurrentPage(1);
        request.setPageSize(10);
        request.setPrepend("jdbc:mysql://");
        request.setUrl("127.0.0.1:3306/spring-boot-demo");
        request.setUsername("root");
        request.setPassword("123456");

        PageResult<Entity> pageResult = codeGenService.listTables(request);
        log.info("[pageResult] = {}", pageResult);
    }

    @Test
    @SneakyThrows
    public void testGenerateCode() {
        TableRequest request = new TableRequest();
        request.setCurrentPage(1);
        request.setPageSize(10);
        request.setPrepend("jdbc:mysql://");
        request.setUrl("127.0.0.1:3306/spring-boot-demo");
        request.setUsername("root");
        request.setPassword("123456");
        request.setTableName("multi_user");

        GenConfig config = new GenConfig();
        config.setRequest(request);
        config.setAuthor("picongzhi");
        config.setComments("comments");
        config.setPackageName("com.pcz");
        config.setTablePrefix("multi_");

        byte[] zip = codeGenService.generateCode(config);
        OutputStream outputStream = new FileOutputStream(
                new File("/Users/picongzhi/workspace/java/" + request.getTableName() + ".zip"));
        IoUtil.write(outputStream, true, zip);
    }
}
