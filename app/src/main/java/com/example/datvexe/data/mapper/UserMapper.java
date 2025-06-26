package com.example.datvexe.data.mapper;

import com.example.datvexe.data.remote.dto.UserDto;
import com.example.datvexe.domain.model.User;

public class UserMapper {

    public static User toDomainModel(UserDto dto) {
        if (dto == null)
            return null;

        return User.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .fullname(dto.getFullname())
                .phone(dto.getPhone())
                .build();
    }
}