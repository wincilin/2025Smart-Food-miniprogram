package com.smartfood.backend.service;

import java.util.Map;
import java.util.Base64;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PhotoAnalysisService {

    @Value("${baidu.ai.api.key}")
    private String apiKey;

    @Value("${baidu.ai.api.secret}")
    private String apiSecret;

    @Value("${baidu.ai.api.token.url}")
    private String tokenUrl;

    @Value("${baidu.ai.api.dish.url}")
    private String dishUrl;

    @Value("${baidu.ai.api.top.num:5}")
    private int topNum;

    private final RestTemplate restTemplate;

    private String getAccessToken() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type", "client_credentials");
            params.add("client_id", apiKey);
            params.add("client_secret", apiSecret);
            
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
            Map<String, Object> response = restTemplate.postForObject(tokenUrl, request, Map.class);
            
            if (response != null && response.containsKey("access_token")) {
                return (String) response.get("access_token");
            }
            throw new RuntimeException("Failed to get access token: " + response);
        } catch (HttpClientErrorException e) {
            log.error("HTTP Error: {}", e.getStatusCode());
            log.error("Response: {}", e.getResponseBodyAsString());
            throw new RuntimeException("Failed to get access token: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("Error getting access token: {}", e.getMessage());
            throw new RuntimeException("Failed to get access token", e);
        }
    }

    public Map<String, Object> analyzeFoodImage(MultipartFile image) {
        try {
            // Get access token
            String accessToken = getAccessToken();
            String url = dishUrl + "?access_token=" + accessToken;

            // Prepare image data
            byte[] imageBytes = image.getBytes();
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            
            // Prepare request
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("image", base64Image);
            params.add("top_num", String.valueOf(topNum));
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

            // Make API call
            Map<String, Object> response = restTemplate.postForObject(
                url,
                requestEntity,
                Map.class
            );

            log.info("Food image analysis completed successfully with {} results", topNum);
            return response;
        } catch (IOException e) {
            log.error("Error reading image file: {}", e.getMessage());
            throw new RuntimeException("Failed to read image file", e);
        } catch (HttpClientErrorException e) {
            log.error("HTTP Error: {}", e.getStatusCode());
            log.error("Response: {}", e.getResponseBodyAsString());
            throw new RuntimeException("Failed to analyze food image: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("Error analyzing food image: {}", e.getMessage());
            throw new RuntimeException("Failed to analyze food image", e);
        }
    }
}
