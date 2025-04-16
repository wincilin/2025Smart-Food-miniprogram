package com.smartfood.backend.service.food;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import com.smartfood.backend.entity.FoodNutrition;
import com.smartfood.backend.repository.food.FoodNutritionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataInitializationService {

    private final FoodNutritionRepository foodNutritionRepository;
    private static final int TOTAL_FILES = 20;

    @EventListener(ApplicationReadyEvent.class)
    public void initializeData() {
        List<FoodNutrition> allFoodNutritions = new ArrayList<>();
        int totalProcessedFiles = 0;
        int totalProcessedRecords = 0;

        // 获取已有食物名称，用于批量查重
        Set<String> existingNames = foodNutritionRepository.findAll()
                .stream()
                .map(FoodNutrition::getName)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

            String fileName = "data/" + "merge_result" + ".xlsx";
            try {
                ClassPathResource resource = new ClassPathResource(fileName);

                try (InputStream is = resource.getInputStream();
                     Workbook workbook = new XSSFWorkbook(is)) {

                    Sheet sheet = workbook.getSheetAt(0);
                    int fileRecords = 0;

                    System.out.println("表格总行数："+sheet.getLastRowNum());
                    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                        Row row = sheet.getRow(i);
                        if (row == null) continue;

                        try {
                            FoodNutrition nutrition = new FoodNutrition();

                            // 食物名称
                            Cell nameCell = row.getCell(0);
                            if (nameCell != null) {
                                String name = nameCell.toString().trim(); // 通用读取，不依赖单元格类型
                                nutrition.setName(name);
                                log.info("读取名称：{}", name);
                            }
                            // 热量
                            Cell caloriesCell = row.getCell(1);
                            if (caloriesCell != null) {
                                try {
                                    double calories = Double.parseDouble(caloriesCell.toString().trim());
                                    nutrition.setCalories(calories);
                                } catch (NumberFormatException e) {
                                    log.warn("热量解析失败：{}", caloriesCell.toString());
                                }
                            }

                            System.out.println("读取第 " + i + " 行：" +
                                "食物名称=" + nutrition.getName() +
                                ", 热量=" + nutrition.getCalories());


                            // 校验后加入列表（名称不为空、热量不为空、数据库中不存在）
                            if (nutrition.getName() != null && !nutrition.getName().isEmpty()
                                    && nutrition.getCalories() != null
                                    && !existingNames.contains(nutrition.getName())) {

                                
                                allFoodNutritions.add(nutrition);
                                fileRecords++;
                                existingNames.add(nutrition.getName()); // 避免本轮中重复添加
                            }

                        } catch (Exception e) {
                            log.error("Error processing row {} in file {}: {}", i, fileName, e.getMessage());
                        }
                    }

                    totalProcessedFiles++;
                    totalProcessedRecords += fileRecords;
                    log.info("Processed file {}: {} new records", fileName, fileRecords);

                } catch (Exception e) {
                    log.error("Error processing file {}: {}", fileName, e.getMessage());
                }

            } catch (Exception e) {
                log.error("Error reading file {}: {}", fileName, e.getMessage());
            }

        // 批量插入
        try {
            if (!allFoodNutritions.isEmpty()) {
                foodNutritionRepository.saveAll(allFoodNutritions);
                log.info("=== Import Summary ===");
                log.info("Total files processed: {}/{}", totalProcessedFiles, TOTAL_FILES);
                log.info("Total new records imported: {}", totalProcessedRecords);
                log.info("====================");
            } else {
                log.warn("No new food nutrition records found to import.");
            }
        } catch (Exception e) {
            log.error("Error saving data to database: {}", e.getMessage(), e);
        }
    }
}
