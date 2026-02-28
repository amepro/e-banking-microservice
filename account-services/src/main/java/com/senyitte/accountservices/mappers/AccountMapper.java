package com.senyitte.accountservices.mappers;

import com.senyitte.accountservices.dtos.AccountRequest;
import com.senyitte.accountservices.dtos.AccountResponse;
import com.senyitte.accountservices.models.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    // Entity → DTO (pour les réponses API)
    public AccountResponse toDto(Account entity) {
        return AccountResponse.builder()
                .id(entity.getId())
                .accountNumber(entity.getAccountNumber())
                .balance(entity.getBalance())
                .type(entity.getType())
                .customerId(entity.getCustomerId())
                .customer(entity.getCustomer()) // peut être null si pas encore chargé
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    // DTO → Entity (pour les créations)
    public Account toEntity(AccountRequest dto) {
        return Account.builder()
                .accountNumber(dto.getAccountNumber())
                .type(dto.getType())
                .customerId(dto.getCustomerId())
                // balance non incluse → prendra la valeur par défaut 0.0 de l'entité
                .build();
    }
}
