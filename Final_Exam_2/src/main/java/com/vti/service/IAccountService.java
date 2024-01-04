package com.vti.service;

import com.vti.DTO.AccountDTO;
import com.vti.request.AccountRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.security.Principal;

public interface IAccountService extends UserDetailsService {
    Page<AccountDTO> findAll(Pageable pageable);

    ResponseEntity<?> findAccountById(Integer id);

    ResponseEntity<?> createAccount(AccountRequest accountRequest);

    ResponseEntity<?> updateAccount(AccountRequest accountRequest);

    ResponseEntity<?> deleteAccount(Integer id);

    boolean login(Principal principal);
}
