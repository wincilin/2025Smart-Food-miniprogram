package com.smartfood.backend.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;

import com.alibaba.fastjson.JSONObject; // Ensure you have FastJSON in your dependencies
import java.io.IOException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.smartfood.backend.model.User;
import com.smartfood.backend.repository.UserRepository;
import com.smartfood.backend.security.JwtUtil;

import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
        // 1. mock 情况：用于测试接口时直接返回 token
        if ("test-wechat-code".equals(code)) {
            System.out.println("进入 MOCK 登录分支");
            String mockOpenid = "mock-openid-123";
            String token = jwtUtil.generateTokenByOpenid(mockOpenid);
            Map<String, Object> result = new HashMap<>();
            result.put("token", token);
            result.put("openid", mockOpenid);
            return result;
        }

        // 2. 正常调用微信 jscode2session
        String url = String.format(
                "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",
                appId, appSecret, code
        );

        OkHttpClient client = new OkHttpClient();
        String responseStr;

        try (Response resp = client.newCall(new Request.Builder().url(url).get().build()).execute()) {
            responseStr = resp.body().string();
        } catch (IOException e) {
            throw new RuntimeException("微信登录请求失败", e);
        }

        JSONObject data = JSONObject.parseObject(responseStr);
        String openid = data.getString("openid");
        if (openid == null) throw new RuntimeException("openid 获取失败，返回内容：" + responseStr);

        // 3. 查找或创建用户
        User user = userRepository.findByOpenid(openid).orElseGet(() -> {
            User newUser = new User();
            newUser.setOpenid(openid);
            newUser.setUserName("未命名用户");
            return userRepository.save(newUser);
        });

        // 4. 生成 token 并返回
        String token = jwtUtil.generateToken(user);

        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("openid", openid);
        return result;
    }
}

