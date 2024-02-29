package com.example.bankingservice.web.mappers;

import com.example.bankingservice.domain.user.User;
import com.example.bankingservice.web.dto.user.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto dto);
}
