package com.nasa.album.controller;

import com.nasa.album.common.Result;
import com.nasa.album.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 用户控制器
 * 提供登录、注册接口
 * 前端 baseUrl: http://127.0.0.1:8080
 */
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")  // 允许跨域，前端运行在3000端口，后端在8080
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登录接口
     * POST /user/login
     * 请求体: { "username": "xxx", "password": "xxx" }
     * 响应: { "code": 200, "msg": "登录成功", "data": { "token": "xxx" } }
     */
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");

        if (username == null || username.trim().isEmpty()) {
            return Result.error("用户名不能为空");
        }
        if (password == null || password.trim().isEmpty()) {
            return Result.error("密码不能为空");
        }

        Map<String, Object> data = userService.login(username.trim(), password.trim());
        if (data == null) {
            return Result.error(401, "用户名或密码错误");
        }
        return Result.success("登录成功", data);
    }

    /**
     * 注册接口
     * POST /user/register
     * 请求体: { "username": "xxx", "password": "xxx" }
     * 响应: { "code": 200, "msg": "注册成功", "data": null }
     */
    @PostMapping("/register")
    public Result<Void> register(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");

        if (username == null || username.trim().isEmpty()) {
            return Result.error("用户名不能为空");
        }
        if (password == null || password.trim().isEmpty()) {
            return Result.error("密码不能为空");
        }
        if (password.length() < 6) {
            return Result.error("密码长度不能少于6位");
        }

        boolean success = userService.register(username.trim(), password.trim());
        if (!success) {
            return Result.error(409, "用户名已存在");
        }
        return Result.success("注册成功", null);
    }
}
