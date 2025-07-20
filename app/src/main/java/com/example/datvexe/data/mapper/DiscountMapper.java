package com.example.datvexe.data.mapper;

import com.example.datvexe.data.remote.dto.DiscountDto;
import com.example.datvexe.domain.model.Discount;

public class DiscountMapper {
    public static Discount toDomainModel(DiscountDto dto) {
        if (dto == null) return null;

        return Discount.builder()
                .id(dto.getId())
                .code(dto.getCode())
                .description(dto.getDescription())
                .percent(dto.getPercent()) // Map discount percentage to percent
                .build();
    }
} 