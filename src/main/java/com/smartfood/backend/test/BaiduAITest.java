package com.smartfood.backend.test;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Base64;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class BaiduAITest {
    
    // 请替换为你的实际API密钥
    private static final String API_KEY = "DB1NiyLGHukpaz9lUosw6Lvt";
    private static final String SECRET_KEY = "hBWWvae8z7NXuFFtdVzXMjmKsH6cHWD8";
    private static final String TOKEN_URL = "https://aip.baidubce.com/oauth/2.0/token";
    private static final String DISH_URL = "https://aip.baidubce.com/rest/2.0/image-classify/v2/dish";
    
    private static RestTemplate createRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<org.springframework.http.converter.HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        messageConverters.add(new FormHttpMessageConverter());
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        restTemplate.setMessageConverters(messageConverters);
        return restTemplate;
    }
    
    private static String getAccessToken(RestTemplate restTemplate) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type", "client_credentials");
            params.add("client_id", API_KEY);
            params.add("client_secret", SECRET_KEY);
            
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
            Map<String, Object> response = restTemplate.postForObject(TOKEN_URL, request, Map.class);
            
            if (response != null && response.containsKey("access_token")) {
                return (String) response.get("access_token");
            }
            throw new RuntimeException("Failed to get access token: " + response);
        } catch (HttpClientErrorException e) {
            System.err.println("HTTP Error: " + e.getStatusCode());
            System.err.println("Response: " + e.getResponseBodyAsString());
            throw new RuntimeException("Failed to get access token: " + e.getMessage(), e);
        } catch (Exception e) {
            System.err.println("Error getting access token: " + e.getMessage());
            throw new RuntimeException("Failed to get access token", e);
        }
    }
    
    private static void analyzeImage(RestTemplate restTemplate, String accessToken, String imagePath) {
        try {
            // Read image file
            File file = new File(imagePath);
            if (!file.exists()) {
                throw new RuntimeException("File not found: " + imagePath);
            }
            
            byte[] imageBytes = Files.readAllBytes(file.toPath());
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            
            // Prepare request
            String url = DISH_URL + "?access_token=" + accessToken;
            
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("image", base64Image);
            params.add("top_num", "1");
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
            
            // Make API call
            Map<String, Object> response = restTemplate.postForObject(url, requestEntity, Map.class);
            
            // Print result
            System.out.println("Analysis result for " + imagePath + ":");
            System.out.println(response);
            
        } catch (IOException e) {
            System.err.println("Error reading image file: " + e.getMessage());
        } catch (HttpClientErrorException e) {
            System.err.println("HTTP Error: " + e.getStatusCode());
            System.err.println("Response: " + e.getResponseBodyAsString());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error analyzing image: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java BaiduAITest <image_path>");
            return;
        }
        
        try {
            RestTemplate restTemplate = createRestTemplate();
            String accessToken = getAccessToken(restTemplate);
            System.out.println("Got access token: " + accessToken);
            
            for (String imagePath : args) {
                analyzeImage(restTemplate, accessToken, imagePath);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 