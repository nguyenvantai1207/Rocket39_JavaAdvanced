package com.vti.service;

import com.vti.DTO.AccountDTO;
import com.vti.entity.Account;
import com.vti.entity.Group;
import com.vti.repository.AccountRepository;
import com.vti.repository.GroupRepository;
import com.vti.request.AccountRequest;
import org.apache.commons.text.WordUtils;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.Type;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AccountService implements IAccountService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> opAccount = accountRepository.findByUsername(username);

        if (opAccount.isEmpty()) {
            throw new UsernameNotFoundException("Account not found with username: " + username);
        }

        Account authAccount = opAccount.get();

        return new User(username, authAccount.getPassword(), AuthorityUtils.createAuthorityList("ROLE_" + authAccount.getRole()));
    }


    public Page<AccountDTO> findAll(Pageable pageable) {
        Page<Account> accountList = accountRepository.findAll(pageable);

        Type listType = new TypeToken<List<AccountDTO>>() {
        }.getType();

        //page mapping
        Page<AccountDTO> accountDTOPage = new PageImpl<>(
                modelMapper.map(accountList.getContent(), listType),
                pageable,
                accountList.getTotalElements()
        );

        return accountDTOPage;
    }


    public ResponseEntity<?> findAccountById(Integer id) {
        Optional<Account> opAccount = accountRepository.findById(id);
        if (opAccount.isPresent()) {
            Account account = opAccount.get();

            AccountDTO accountDTO = modelMapper.map(account, AccountDTO.class);

            return ResponseEntity.ok(accountDTO);
        }
        return new ResponseEntity<>("Account not found with id: " + id, HttpStatus.BAD_REQUEST);
    }


    public ResponseEntity<?> createAccount(AccountRequest accountRequest) {
        Optional<Account> existingAccount = accountRepository.findByUsername(accountRequest.getUsername());
        if (existingAccount.isEmpty()) {
            Account newAccount = modelMapper.map(accountRequest, Account.class);

            newAccount.setPassword(passwordEncoder.encode(newAccount.getPassword()));
            newAccount.setRole(WordUtils.capitalize(newAccount.getRole()));

            Optional<Group> opGroupById = groupRepository.findById(accountRequest.getGroupId());
            if (opGroupById.isEmpty()) {
                return new ResponseEntity<>("Group not found with id: " + accountRequest.getGroupId(), HttpStatus.BAD_REQUEST);
            }

            accountRepository.save(newAccount);

            return ResponseEntity.ok("Account created successfully with username: " + newAccount.getUsername());
        }
        return new ResponseEntity<>("Account already existed: " + accountRequest.getUsername(), HttpStatus.BAD_REQUEST);
    }


    public ResponseEntity<?> updateAccount(AccountRequest accountRequest) {

        Optional<Account> opAccountById = accountRepository.findById(accountRequest.getId());
        if (opAccountById.isPresent()) {
            Account newAccount = modelMapper.map(accountRequest, Account.class);

            newAccount.setPassword(passwordEncoder.encode(newAccount.getPassword()));

            accountRepository.save(newAccount);

            return ResponseEntity.ok("Update Account Successfully!");
        }
        return new ResponseEntity<>("Account not found with id: ", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> deleteAccount(Integer id) {
        Optional<Account> opAccountById = accountRepository.findById(id);
        if (opAccountById.isPresent()) {
            accountRepository.deleteAccountById(id);

            return new ResponseEntity<>("Delete Account Successfully with id: " + id, HttpStatus.OK);
        }
        return new ResponseEntity<>("Account not found with id: " + id, HttpStatus.BAD_REQUEST);
    }

    public boolean login(Principal principal) {
        Optional<Account> optionalAccount = accountRepository.findByUsername(principal.getName());
        return optionalAccount.isPresent();
    }

}
