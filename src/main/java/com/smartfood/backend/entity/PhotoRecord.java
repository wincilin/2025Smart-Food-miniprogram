package com.smartfood.backend.entity;

import java.time.LocalDateTime;
import java.util.Map;

import org.hibernate.annotations.CreationTimestamp;

import com.smartfood.backend.util.MapToJsonConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "photo_record")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhotoRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String openid;

    /**
     * 图片内容（Base64 编码）
     */
    // @Lob // 表示大文本字段，配合 @Column(columnDefinition) 可选
    // @Column(name = "image_base64", nullable = false, columnDefinition = "LONGTEXT")
    // private String imageBase64;
    @Column(name = "image_url", nullable = false)
    private String imageUrl;


    /**
     * 分析结果（例如：菜名）
     */
    @Lob
    @Convert(converter = MapToJsonConverter.class)
    @Column(name = "food_candidates", columnDefinition = "LONGTEXT")
    private Map<String, Object> foodCandidates;

    /**
     * 拍摄时间 / 上传时间
     */
    //调用 save() 把对象保存进数据库时自动填充
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
