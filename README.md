# 星际相册 — NASA每日天文图

一个前后端分离 + 数据库的完整 Web 项目，调用 NASA APOD 接口展示每日天文图片，支持用户登录注册、图片收藏、中英文切换。

## 技术栈

| 层级 | 技术 | 说明 |
|------|------|------|
| 前端 | HTML + CSS + JavaScript | 原生页面，无框架依赖 |
| 前端代理 | Node.js + Express | 代理 NASA API，解决跨域和翻译 |
| 后端 | Java + Spring Boot 2.7 | RESTful API，运行在 8080 端口 |
| 持久层 | MyBatis | 注解式 SQL，操作 MySQL |
| 数据库 | MySQL 8.0 | 用户表、收藏表 |
| 第三方 API | NASA APOD | 每日天文图片数据 |

## 项目结构

```
nasa-photo-album/
├── login.html              # 登录页（前端入口，默认打开）
├── xjxc.html               # 相册主页（登录后跳转）
├── xjxc.css                # 相册页样式
├── xjxc.js                 # 相册页逻辑（NASA API 调用、收藏、翻译）
├── server.js               # Node 代理服务（3000端口，代理 NASA API）
├── package.json            # Node 依赖配置
├── .gitignore              # Git 忽略规则
│
├── backend/                # Java Spring Boot 后端
│   ├── pom.xml             # Maven 依赖配置
│   └── src/main/
│       ├── java/com/nasa/album/
│       │   ├── AlbumApplication.java    # 启动类
│       │   ├── common/Result.java       # 统一响应封装
│       │   ├── controller/UserController.java  # 登录/注册接口
│       │   ├── service/UserService.java        # 业务逻辑
│       │   ├── mapper/UserMapper.java          # MyBatis 数据访问
│       │   └── entity/User.java                # 用户实体
│       └── resources/
│           └── application.yml         # 数据库连接配置
│
└── database/               # MySQL 数据库脚本
    └── schema.sql          # 建库建表 + 测试数据
```

## 前后端联动说明

### 接口对应关系

| 前端操作 | 请求地址 | 后端方法 | 数据库操作 |
|----------|----------|----------|------------|
| 登录 | `POST http://127.0.0.1:8080/user/login` | `UserController.login()` | `SELECT * FROM user WHERE username=? AND password=?` |
| 注册 | `POST http://127.0.0.1:8080/user/register` | `UserController.register()` | `INSERT INTO user (username, password)` |
| 加载图片 | `GET http://127.0.0.1:3000/apod` | `server.js` 代理 NASA API | 无（调用第三方接口） |
| 收藏图片 | localStorage | 前端本地存储 | 可选（collect 表已预留） |

### 响应格式约定

后端统一返回：
```json
{
  "code": 200,
  "msg": "登录成功",
  "data": { "token": "xxx", "userId": 1, "username": "admin" }
}
```
- `code = 200` 成功
- `code != 200` 失败，`msg` 为错误信息

### 数据流

```
用户输入账号密码
    ↓
login.html (fetch POST /user/login)
    ↓
UserController (8080端口)
    ↓
UserService → UserMapper
    ↓
MySQL (nasa_album.user 表)
    ↓
返回 token → 前端 localStorage 保存
    ↓
跳转 xjxc.html → 调用 Node 代理 (3000端口) → NASA API
```

## 运行步骤

### 1. 初始化数据库

```bash
mysql -u root -p < database/schema.sql
```

默认测试账号：`admin / 123456`

> 如果你的 MySQL 密码不是 `root`，修改 `backend/src/main/resources/application.yml` 中的 `spring.datasource.password`。

### 2. 启动 Java 后端

```bash
cd backend
mvn spring-boot:run
```

后端启动在 `http://127.0.0.1:8080`

### 3. 启动 Node 代理服务

```bash
# 在项目根目录
npm install
node server.js
```

代理服务启动在 `http://127.0.0.1:3000`

### 4. 访问页面

浏览器打开：`http://127.0.0.1:3000`

默认进入登录页 → 输入账号密码 → 登录成功后跳转到相册页。

## 功能清单

- 用户注册 / 登录（Java 后端 + MySQL 验证）
- 默认加载今日 NASA 天文图片
- 日期选择器，查看任意日期的天文图
- 「随机一张」按钮，随机获取历史照片
- 中英文切换（调用 MyMemory 翻译接口）
- 图片弹窗放大预览
- 收藏功能（localStorage 保存，查看/删除）
- 响应式布局，适配电脑和手机

## 注意事项

- NASA API Key 在 `.env` 文件中配置（已被 gitignore），默认使用 `DEMO_KEY`，有频率限制
- 前端登录请求地址写在 `login.html` 的 `baseUrl` 变量中，默认为 `http://127.0.0.1:8080`
- 后端已配置 `@CrossOrigin` 允许跨域，前端 3000 端口可正常访问 8080 端口
