package com.smartfood.backend.dto.photo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhotoSearchHistoryPageDTO {

    private int totalPages;
    private long totalElements;
    private int currentPage;
    private List<PhotoRecordsResponseDTO> records;
}
