package com.smartfood.backend.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.*;
import java.security.cert.X509Certificate;

import com.alibaba.fastjson.JSONObject;
import com.smartfood.backend.model.User;
import com.smartfood.backend.repository.UserRepository;
import com.smartfood.backend.security.JwtUtil;
import com.smartfood.backend.security.LoginUser;

import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WxAuthService {

    @Value("${wx.appid}")
    private String appId;

    @Value("${wx.secret}")
    private String appSecret;

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public Map<String, Object> loginWithWxCode(String code) {
        System.out.println("接收到登录请求！");

        // mock 登录逻辑
        if ("test-wechat-code".equals(code)) {
            System.out.println("进入 MOCK 登录分支");
            String mockOpenid = "mock-openid-123";
            String token = jwtUtil.generateTokenByOpenid(mockOpenid);
            Map<String, Object> result = new HashMap<>();
            result.put("token", token);
            result.put("openid", mockOpenid);
            return result;
        }

        // 微信接口请求
        String url = String.format(
                "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",
                appId, appSecret, code
        );

        System.out.println("请求微信API地址: " + url);

        // 创建一个忽略 SSL 认证的 OkHttpClient
        OkHttpClient client = createUnsafeOkHttpClient();

        String responseStr;
        try (Response resp = client.newCall(new Request.Builder().url(url).get().build()).execute()) {
            if (!resp.isSuccessful()) {
                throw new RuntimeException("微信接口响应失败，状态码: " + resp.code());
            }
            responseStr = resp.body().string();
        } catch (IOException e) {
            throw new RuntimeException("微信登录请求失败", e);
        }

        System.out.println("微信API响应：" + responseStr);
        JSONObject data = JSONObject.parseObject(responseStr);

        // 检查 openid
        String openid = data.getString("openid");
        if (openid == null) {
            throw new RuntimeException("openid 获取失败，返回内容：" + responseStr);
        }

        // 查找或创建用户
        User user = userRepository.findByOpenid(openid).orElseGet(() -> {
            User newUser = new User();
            newUser.setOpenid(openid);
            newUser.setUserName("未命名用户");
            return userRepository.save(newUser);
        });

        // 注入登录态 
        LoginUser loginUser = new LoginUser(user);
        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 签发 JWT
        String token = jwtUtil.generateToken(user);
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("openid", openid);

        return result;
    }

    // 创建一个跳过 SSL 验证的 OkHttpClient
    private OkHttpClient createUnsafeOkHttpClient() {
        try {
            // 创建一个 TrustManager，它不做任何证书验证
            TrustManager[] trustAllCertificates = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }

                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            // 创建 SSLContext 和 SocketFactory，来跳过 SSL 验证
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCertificates, new java.security.SecureRandom());
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            // 配置 OkHttpClient 使用不验证的 SSL
            return new OkHttpClient.Builder()
                    .sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCertificates[0])
                    .hostnameVerifier((hostname, session) -> true) // 信任所有主机名
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("创建不安全的 OkHttpClient 失败", e);
        }
    }
}
