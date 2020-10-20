package com.pcz.swagger.controller;

import com.pcz.swagger.common.ApiResponse;
import com.pcz.swagger.common.DataType;
import com.pcz.swagger.common.ParamType;
import com.pcz.swagger.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author picongzhi
 */
@RestController
@RequestMapping("/user")
@Api(tags = "1.0.0-SNAPSHOT", description = "用户管理", value = "用户管理")
@Slf4j
public class UserController {
    @GetMapping
    @ApiOperation(value = "条件查询(DONE)", notes = "备注")
    @ApiImplicitParams({@ApiImplicitParam(
            name = "username",
            value = "用户名",
            dataType = DataType.STRING,
            paramType = ParamType.QUERY,
            defaultValue = "xxx")})
    public ApiResponse<User> getByUserName(String username) {
        log.info("多个参数用 @ApiImplicitParams");
        return ApiResponse.<User>builder()
                .code(200)
                .message("操作成功")
                .data(new User(1, username, "JAVA"))
                .build();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "主键查询(DONE)", notes = "备注")
    @ApiImplicitParam(name = "id", value = "用户编号", dataType = DataType.INT, paramType = ParamType.PATH)
    public ApiResponse<User> get(@PathVariable Integer id) {
        log.info("单个参数用 @ApiImplicitParam");
        return ApiResponse.<User>builder()
                .code(200)
                .message("操作成功")
                .data(new User(id, "u1", "p1"))
                .build();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除用户(DONE)", notes = "备注")
    @ApiImplicitParam(name = "id", value = "用户编号", dataType = DataType.INT, paramType = ParamType.PATH)
    public void delete(@PathVariable Integer id) {
        log.info("单个参数用 ApiImplicitParam");
    }

    @PostMapping
    @ApiOperation(value = "添加用户(DONE)")
    public User post(@RequestBody User user) {
        log.info("如果是POST、PUT这种带@RequestBody的可以不写@ApiImplicitParam");
        return user;
    }

    @PostMapping("/multipart")
    @ApiOperation(value = "添加用户(DONE)")
    public List<User> multipart(@RequestBody List<User> users) {
        log.info("如果是POST、PUT这种带@RequestBody的可以不写@ApiImplicitParam");
        return users;
    }

    @PostMapping("array")
    @ApiOperation(value = "添加用户(DONE)")
    public User[] array(@RequestBody User[] users) {
        log.info("如果是POST、PUT这种带@RequestBody的可以不写@ApiImplicitParam");
        return users;
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "修改用户(DONE)")
    public void put(@PathVariable Long id, @RequestBody User user) {
        log.info("如果不想写@ApiImplicitParam，那么swagger也会使用默认的参数名作为描述信息");
    }

    @PostMapping("/{id}/file")
    @ApiOperation(value = "文件上传(DONE)")
    public String file(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        log.info(file.getContentType());
        log.info(file.getName());
        log.info(file.getOriginalFilename());
        log.info(file.getOriginalFilename());

        return file.getOriginalFilename();
    }
}
