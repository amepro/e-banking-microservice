package com.senyitte.accountservices.services;

import com.senyitte.accountservices.customer.Customer;
import com.senyitte.accountservices.dtos.AccountRequest;
import com.senyitte.accountservices.dtos.AccountResponse;
import com.senyitte.accountservices.feign.CustomerClient;
import com.senyitte.accountservices.mappers.AccountMapper;
import com.senyitte.accountservices.models.Account;
import com.senyitte.accountservices.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final CustomerClient customerClient;

    public AccountServiceImpl(AccountRepository accountRepository,
                              AccountMapper accountMapper,
                              CustomerClient customerClient) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.customerClient = customerClient;
    }

    @Override
    public AccountResponse createAccount(AccountRequest dto) {
        // Vérifier si le numéro de compte existe déjà
        if (accountRepository.findByAccountNumber(dto.getAccountNumber()).isPresent()) {
            throw new RuntimeException("Account number already exists: " + dto.getAccountNumber());
        }

        Account account = accountMapper.toEntity(dto);
        Account savedAccount = accountRepository.save(account);
        Customer customer = customerClient.getCustomer(dto.getCustomerId());
        savedAccount.setCustomer(customer);
        return accountMapper.toDto(savedAccount);
    }

    @Override
    public AccountResponse updateAccount(Long id, AccountRequest dto) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setAccountNumber(dto.getAccountNumber());
        account.setType(dto.getType());
        account.setCustomerId(dto.getCustomerId());

        Account updatedAccount = accountRepository.save(account);
        Customer customer = customerClient.getCustomer(dto.getCustomerId());
        updatedAccount.setCustomer(customer);
        return accountMapper.toDto(updatedAccount);
    }

    @Override
    public AccountResponse getAccount(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        Customer customer = customerClient.getCustomer(account.getCustomerId());
        account.setCustomer(customer);
        return accountMapper.toDto(account);
    }

    @Override
    public List<AccountResponse> getAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(account -> {
                    Customer customer = customerClient.getCustomer(account.getCustomerId());
                    account.setCustomer(customer);
                    return accountMapper.toDto(account);
                })
                .toList();
    }

    @Override
    public List<AccountResponse> getAccountsByCustomerId(Long customerId) {
        Customer customer = customerClient.getCustomer(customerId);
        return accountRepository.findByCustomerId(customerId)
                .stream()
                .map(account -> {
                    // On réutilise le même customer pour éviter N appels Feign
                    account.setCustomer(customer);
                    return accountMapper.toDto(account);
                })
                .toList();
    }

    @Override
    public void deleteAccount(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new RuntimeException("Account not found");
        }
        accountRepository.deleteById(id);
    }
}
