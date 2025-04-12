package com.smartfood.backend.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import com.smartfood.backend.entity.FoodNutrition;
import com.smartfood.backend.repository.FoodNutritionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataInitializationService {

    private final FoodNutritionRepository foodNutritionRepository;
    private static final int TOTAL_FILES = 20;

    @EventListener(ApplicationReadyEvent.class)
    public void initializeData() {
        List<FoodNutrition> allFoodNutritions = new ArrayList<>();
        
        // 读取20个文件
        for (int fileNum = 1; fileNum <= TOTAL_FILES; fileNum++) {
            String fileName = "data/" + fileNum + ".xlsx";
            try {
                ClassPathResource resource = new ClassPathResource(fileName);
                log.info("Processing file: {}", fileName);
                
                try (InputStream is = resource.getInputStream();
                     Workbook workbook = new XSSFWorkbook(is)) {
                    
                    Sheet sheet = workbook.getSheetAt(0);
                    
                    // 从第二行开始读取（跳过表头）
                    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                        Row row = sheet.getRow(i);
                        if (row == null) continue;
                        
                        try {
                            FoodNutrition nutrition = new FoodNutrition();
                            
                            // 读取食物名称（第一列）
                            Cell nameCell = row.getCell(0);
                            if (nameCell != null) {
                                nameCell.setCellType(CellType.STRING);
                                nutrition.setName(nameCell.getStringCellValue().trim());
                            }
                            
                            // 读取热量（第二列）
                            Cell caloriesCell = row.getCell(1);
                            if (caloriesCell != null) {
                                caloriesCell.setCellType(CellType.NUMERIC);
                                nutrition.setCalories(caloriesCell.getNumericCellValue());
                            }
                            
                            // 只添加名称和热量都不为空的记录
                            if (nutrition.getName() != null && !nutrition.getName().isEmpty() 
                                && nutrition.getCalories() != null) {
                                allFoodNutritions.add(nutrition);
                                log.debug("Read food from file {}: {} - {} calories", 
                                    fileName, nutrition.getName(), nutrition.getCalories());
                            }
                        } catch (Exception e) {
                            log.error("Error processing row {} in file {}: {}", 
                                i, fileName, e.getMessage());
                        }
                    }
                    log.info("Completed processing file {}, current total records: {}", 
                        fileName, allFoodNutritions.size());
                    
                } catch (Exception e) {
                    log.error("Error processing file {}: {}", fileName, e.getMessage());
                }
                
            } catch (Exception e) {
                log.error("Error reading file {}: {}", fileName, e.getMessage());
            }
        }
        
        // 保存所有数据到数据库
        try {
            if (!allFoodNutritions.isEmpty()) {
                foodNutritionRepository.saveAll(allFoodNutritions);
                log.info("Successfully imported {} food nutrition records from {} files", 
                    allFoodNutritions.size(), TOTAL_FILES);
            } else {
                log.warn("No valid food nutrition records found in any of the Excel files");
            }
        } catch (Exception e) {
            log.error("Error saving data to database: {}", e.getMessage(), e);
        }
    }
} 