package com.senyitte.accountservices.controller;

import com.senyitte.accountservices.dtos.AccountRequest;
import com.senyitte.accountservices.dtos.AccountResponse;
import com.senyitte.accountservices.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // POST /api/accounts
    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@RequestBody AccountRequest request) {
        AccountResponse response = accountService.createAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET /api/accounts
    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    // GET /api/accounts/{id}
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccount(id));
    }

    // GET /api/accounts/customer/{customerId}
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<AccountResponse>> getAccountsByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(accountService.getAccountsByCustomerId(customerId));
    }

    // PUT /api/accounts/{id}
    @PutMapping("/{id}")
    public ResponseEntity<AccountResponse> updateAccount(
            @PathVariable Long id,
            @RequestBody AccountRequest request) {
        return ResponseEntity.ok(accountService.updateAccount(id, request));
    }

    // DELETE /api/accounts/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}

