package com.smartfood.backend.service.photo;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.smartfood.backend.dto.photo.PhotoRecordsResponseDTO;
import com.smartfood.backend.entity.PhotoRecord;
import com.smartfood.backend.repository.PhotoRecordRepository;

@Service
public class PhotoRecordService {
    
    @Autowired
    private PhotoRecordRepository photoRecordRepository;

    public Page<PhotoRecord> getPhotoSearchHistoryPage(String openid, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PhotoRecord> photoRecordsPage = photoRecordRepository.findByOpenidOrderByCreatedAtDesc(openid, pageable);

        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // List<PhotoRecordsResponseDTO> dtoList = photoRecordsPage.getContent().stream().map(record -> new PhotoRecordsResponseDTO(
        //         record.getId(),
        //         record.getImageUrl(),
        //         record.getFoodCandidates(),
        //         record.getCreatedAt().format(formatter)
        // )).collect(Collectors.toList());

        // return new PageImpl<>(dtoList, pageable, photoRecordsPage.getTotalElements())
        ;
        return photoRecordsPage; // 返回原始的 PhotoRecord 对象列表
    }

    public void savePhotoRecord(PhotoRecord photoRecord) {
        photoRecordRepository.save(photoRecord);
    }
}
