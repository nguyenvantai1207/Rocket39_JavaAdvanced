package com.vti.controller;

import com.vti.DTO.AccountDTO;
import com.vti.request.AccountRequest;
import com.vti.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin("*")
@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("account")
    public Page<AccountDTO> findAllAccounts(Pageable pageable) {
        return accountService.findAll(pageable);
    }

    @GetMapping("/account/login")
    public ResponseEntity<?> login(Principal principal) {
        return new ResponseEntity<>(accountService.login(principal), HttpStatus.OK);
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<?> findAccountById(@PathVariable Integer id) {
        return accountService.findAccountById(id);
    }

    @PostMapping("/account")
    public ResponseEntity<?> createAccount(@RequestBody AccountRequest accountRequest) {
        return accountService.createAccount(accountRequest);
    }

    @PutMapping("/account")
    public ResponseEntity<?> updateAccount(@RequestBody AccountRequest accountRequest) {
        return accountService.updateAccount(accountRequest);
    }

    @DeleteMapping("/account/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Integer id) {
        return accountService.deleteAccount(id);
    }
}
