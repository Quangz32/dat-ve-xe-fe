package com.example.datvexe.data.mapper;

import com.example.datvexe.data.remote.dto.NotificationResponseDto;
import com.example.datvexe.domain.model.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationMapper {
    public static Notification toDomainModel(NotificationResponseDto dto) {
        if (dto == null) return null;

        return Notification.builder()
                .id(dto.getId())
                .type(dto.getType())
                .title(dto.getTitle())
                .message(dto.getMessage())
                .tab(dto.getTab())
                .user(dto.getUser().getId())
                .userDetail(UserMapper.toDomainModel(dto.getUser()))
                .createdAt(dto.getCreatedAt())
                .build();
    }

    public static List<Notification> toDomainModelList(List<NotificationResponseDto> dtoList) {
        if (dtoList == null) return new ArrayList<>();

        List<Notification> domainList = new ArrayList<>();
        for (NotificationResponseDto dto : dtoList) {
            Notification notification = toDomainModel(dto);
            if (notification != null) {
                domainList.add(notification);
            }
        }
        return domainList;
    }
}
