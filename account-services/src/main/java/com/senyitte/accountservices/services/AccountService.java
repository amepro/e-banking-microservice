package com.senyitte.accountservices.services;

import com.senyitte.accountservices.dtos.AccountRequest;
import com.senyitte.accountservices.dtos.AccountResponse;

import java.util.List;

public interface AccountService {

    AccountResponse createAccount(AccountRequest dto);
    AccountResponse updateAccount(Long id, AccountRequest dto);
    AccountResponse getAccount(Long id);
    List<AccountResponse> getAllAccounts();
    // Récupérer tous les comptes d'un client spécifique
    List<AccountResponse> getAccountsByCustomerId(Long customerId);
    void deleteAccount(Long id);
}