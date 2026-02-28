package com.senyitte.accountservices.dtos;

import com.senyitte.accountservices.customer.Customer;
import com.senyitte.accountservices.models.AccountType;
import lombok.*;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponse {
    // Tous les champs qu'on veut renvoyer au client
    private Long id;
    private String accountNumber;
    private Double balance;
    private AccountType type;
    private Long customerId;
    // On inclut le customer complet (récupéré via Feign/RestClient)
    // pour que le client ait toutes les infos sans faire un 2e appel
    private Customer customer;
    private Instant createdAt;
    private Instant updatedAt;
}