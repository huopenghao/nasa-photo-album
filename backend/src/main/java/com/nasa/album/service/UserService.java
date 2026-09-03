package com.nasa.album.service;

import com.nasa.album.entity.User;
import com.nasa.album.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 用户服务层
 * 处理登录、注册业务逻辑
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 登录
     * @return 包含 token 的 map，失败返回 null
     */
    public Map<String, Object> login(String username, String password) {
        User user = userMapper.findByUsernameAndPassword(username, password);
        if (user == null) {
            return null;
        }
        // 生成简单 token（生产环境应使用 JWT）
        String token = UUID.randomUUID().toString().replace("-", "");
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("userId", user.getId());
        result.put("username", user.getUsername());
        return result;
    }

    /**
     * 注册
     * @return true 成功，false 用户名已存在
     */
    public boolean register(String username, String password) {
        // 检查用户名是否已存在
        User exist = userMapper.findByUsername(username);
        if (exist != null) {
            return false;
        }
        User user = new User(username, password);
        int rows = userMapper.insert(user);
        return rows > 0;
    }
}
