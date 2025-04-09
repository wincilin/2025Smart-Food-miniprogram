package com.smartfood.backend.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeightData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Double weight;
    LocalDate date;

    //这里保证用户一定已经存在于数据库中
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
