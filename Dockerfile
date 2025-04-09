# FROM eclipse-temurin:21-jdk-alpine
# VOLUME /tmp
# COPY target/backend-0.0.1-SNAPSHOT.jar app.jar
# ENTRYPOINT ["java", "-jar", "/app.jar"]

# 基于 Debian 的 OpenJDK 21 镜像（包含 CA 相关工具）
FROM eclipse-temurin:21-jdk

# 设置工作目录
WORKDIR /app

# 拷贝 Spring Boot 构建后的 JAR 包
COPY target/backend-0.0.1-SNAPSHOT.jar app.jar

# 将微信根证书拷贝进容器（你需要将 DigiCertGlobalRootG2.crt 放在项目根目录）
COPY DigiCertGlobalRootG2.crt /usr/local/share/ca-certificates/DigiCertGlobalRootG2.crt

# 安装证书导入工具并更新系统 CA 信任库
RUN apt-get update && \
    apt-get install -y ca-certificates && \
    update-ca-certificates && \
    apt-get clean

# 启动应用
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
