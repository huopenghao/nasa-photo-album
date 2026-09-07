# 星际相册 — NASA每日天文图

前端项目，调用 NASA APOD 接口展示每日天文图片，支持用户登录注册、图片收藏、中英文切换。

## 技术栈

| 层级 | 技术 | 说明 |
|------|------|------|
| 前端 | HTML + CSS + JavaScript | 原生页面，无框架依赖 |
| 前端代理 | Node.js | 代理 NASA API，解决跨域和翻译，运行在 3000 端口 |
| 后端 | Java + Spring Boot | 登录注册接口，运行在 8080 端口（logindemo 项目） |
| 数据库 | MySQL | 用户表，运行在 3306 端口 |

## 项目结构

```
nasa-photo-album/
├── login.html          # 登录页（默认入口）
├── xjxc.html           # 相册主页（登录后跳转）
├── xjxc.css            # 相册页样式
├── xjxc.js             # 相册页逻辑
├── server.js           # Node 代理服务（3000端口）
├── package.json        # Node 依赖配置
└── README.md
```

## 前后端联动说明

### 接口对应关系

| 前端操作 | 请求地址 | 后端 | 数据库表 |
|----------|----------|------|----------|
| 登录 | `POST http://127.0.0.1:8080/user/login` | logindemo 项目 | `xjxc.user` |
| 注册 | `POST http://127.0.0.1:8080/user/register` | logindemo 项目 | `xjxc.user` |
| 加载图片 | `GET http://127.0.0.1:3000/apod` | server.js 代理 | NASA API |

### 数据在哪看

注册的用户数据存在 **MySQL 的 `xjxc` 数据库的 `user` 表**中。

用 DBeaver / Navicat 等工具连接 `localhost:3306` 后执行：
```sql
SELECT * FROM xjxc.user;
```
就能看到所有注册的账号。

### 响应格式

```json
{
  "code": 200,
  "msg": "登录成功",
  "data": { "token": "xxx", "username": "xxx" }
}
```

## 运行步骤

### 1. 启动 MySQL

确保 MySQL 服务运行在 3306 端口，包含 `xjxc` 数据库和 `user` 表。

### 2. 启动 Java 后端（logindemo 项目）

启动后运行在 `http://127.0.0.1:8080`，提供 `/user/login` 和 `/user/register` 接口。

### 3. 启动 Node 代理服务

```bash
npm install
node server.js
```

运行在 `http://127.0.0.1:3000`

### 4. 访问页面

浏览器打开：`http://127.0.0.1:3000`

默认进入登录页 → 注册/登录 → 跳转到相册页。

## 功能清单

- 用户注册 / 登录（Java 后端 + MySQL 验证）
- 退出登录（清除 token，跳转登录页）
- 登录态校验（未登录自动跳转登录页）
- 默认加载今日 NASA 天文图片
- 日期选择器，查看任意日期的天文图
- 「随机一张」按钮，随机获取历史照片
- 中英文切换（调用 MyMemory 翻译接口）
- 图片弹窗放大预览
- 收藏功能（localStorage 保存，查看/删除）
- 响应式布局，适配电脑和手机
