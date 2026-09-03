package com.nasa.album;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 星际相册 - Spring Boot 启动类
 * 运行在 8080 端口，与前端 login.html 中的 baseUrl 对应
 */
@SpringBootApplication
@MapperScan("com.nasa.album.mapper")
public class AlbumApplication {
    public static void main(String[] args) {
        SpringApplication.run(AlbumApplication.class, args);
        System.out.println("========================================");
        System.out.println("  星际相册后端启动成功！端口: 8080");
        System.out.println("  接口地址: http://127.0.0.1:8080");
        System.out.println("========================================");
    }
}
