# 小虫社区-学习生活交流论坛

线上地址：http://60.205.3.154:8080/

## 网站简介

供计算机专业学生进行线上交流的论坛社区，为大家提供一个知识交流、资源分享的平台，帮助大家相互交流、相互进步。

你可以在这里向他人提问，或为其他人解答。也可以发布文章，分享你想分享的事务，和其他人自由的交流。

## 使用技术

### 后端：

- Java
- SpringBoot
- Thymeleaf
- Mybatis

### 前端：

- JavaScript、HTML、CSS
- JQuery
- BootStrap

### 数据库：

- MySQL
- Redis

## 环境依赖

- JDK1.8以上
- Maven
- Git
- Redis
- Mysql
- IntelliJ IDEA（推荐使用）

均为最新版即可

## 本地快速部署

1. clone 项目到本地：`git clone https://github.com/interlink3290/Website.git`
2. 将项目导入IDEA
3. 本地创建MySQL数据库，修改 pom.xml、application.yml、application.properties 文件中数据库连接相关信息
4. 通过执行 `mvn flyway:migrate -P dev` 自动生成数据库表
5. 注册GitHub第三方应用，修改application.yml中相关信息
6. 邮件发送功能需保证安装Redis，并去QQ邮箱中开启SMTP服务，并更新application.yml中相应信息
7. 配置完成后点击运行