package com.smartfood.backend.dto.photo;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhotoRecordsResponseDTO {
    
    private Long id; // 图片记录的唯一标识符

    private String imageURL; // 图片内容（Base64 编码）

    private Map<String,Object> foodCandidates; // 分析结果（例如：菜名1:概率1，菜名2：概率2）

    private String createdAt; // 拍摄时间 / 上传时间
}
