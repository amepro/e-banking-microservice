package com.senyitte.accountservices.dtos;

import com.senyitte.accountservices.models.AccountType;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountRequest {
    // Seuls les champs que le client doit fournir
    // Pas d'id, pas de dates, pas de balance (initialisée à 0 par défaut)
    private String accountNumber;
    private AccountType type;
    private Long customerId;
}