package com.example.bankingservice.web.mappers;

import com.example.bankingservice.domain.account.Account;
import com.example.bankingservice.web.dto.account.AccountDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountDto toDto(Account account);
    Account toEntity(AccountDto dto);
}
