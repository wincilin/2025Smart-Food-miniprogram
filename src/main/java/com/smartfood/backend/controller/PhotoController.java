package com.smartfood.backend.controller;

import java.util.List;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.smartfood.backend.dto.food.FoodAnalysisResult;
import com.smartfood.backend.service.photo.PhotoAnalysisService;
import com.smartfood.backend.service.photo.PhotoRecordService;
import com.smartfood.backend.dto.photo.PhotoRecordsResponseDTO;
import com.smartfood.backend.entity.PhotoRecord;
import com.smartfood.backend.security.LoginUser;
import java.util.Base64;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/photo")
@RequiredArgsConstructor
public class PhotoController {
    
    private final PhotoAnalysisService photoAnalysisService;
    private final PhotoRecordService photoRecordService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> analyzePhoto(@RequestPart("file") MultipartFile file,@AuthenticationPrincipal LoginUser loginUser) {
        String openid = loginUser.getUser().getOpenid();
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                    "error", "File is empty",
                    "message", "Please upload a valid image file"
                ));
            }

            // 检查文件类型
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity.badRequest().body(Map.of(
                    "error", "Invalid file type",
                    "message", "Please upload an image file"
                ));
            }

            // 检查文件大小（例如限制为5MB）
            if (file.getSize() > 5 * 1024 * 1024) {
                return ResponseEntity.badRequest().body(Map.of(
                    "error", "File too large",
                    "message", "File size should be less than 5MB"
                ));
            }

            Map<String, Object> rawResult = photoAnalysisService.analyzeFoodImage(file);
            FoodAnalysisResult result = new FoodAnalysisResult();
            // 这里可以添加结果转换逻辑

            // 保存分析结果到数据库
            PhotoRecord photoRecord = new PhotoRecord();
            photoRecord.setOpenid(openid);
            photoRecord.setFoodCandidates(rawResult);
            photoRecord.setImageBase64(Base64.getEncoder().encodeToString(file.getBytes()));
            photoRecordService.savePhotoRecord(photoRecord);
            
            return ResponseEntity.ok(rawResult);
        } catch (Exception e) {
            log.error("Error analyzing photo: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Failed to analyze photo",
                "message", e.getMessage()
            ));
        }
    }

    @GetMapping("/searchHistory")
    public ResponseEntity<List<PhotoRecordsResponseDTO>> getPhotoSearchHistory(@AuthenticationPrincipal LoginUser loginUser) {
        String openid = loginUser.getUser().getOpenid();
        List<PhotoRecordsResponseDTO> searchHistory = photoRecordService.getPhotoSearchHistory(openid);
        return ResponseEntity.ok(searchHistory);
    }
}
