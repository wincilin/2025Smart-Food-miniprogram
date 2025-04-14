package com.smartfood.backend.service.photo;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartfood.backend.dto.photo.PhotoRecordsResponseDTO;
import com.smartfood.backend.entity.PhotoRecord;
import com.smartfood.backend.repository.PhotoRecordRepository;

@Service
public class PhotoRecordService {
    
    @Autowired
    private PhotoRecordRepository photoRecordRepository;

    public List<PhotoRecordsResponseDTO> getPhotoSearchHistory(String openid) {
        List<PhotoRecord> photoRecords = photoRecordRepository.findByOpenidOrderByCreatedAtDesc(openid);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return photoRecords.stream().map(record -> new PhotoRecordsResponseDTO(
                record.getId(),
                record.getImageBase64(),
                record.getFoodCandidates(),
                record.getCreatedAt().format(formatter) // LocalDateTime -> String
        )).collect(Collectors.toList());

    }

    public void savePhotoRecord(PhotoRecord photoRecord) {
        photoRecordRepository.save(photoRecord);
    }
}
