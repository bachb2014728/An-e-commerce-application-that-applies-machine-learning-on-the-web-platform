package com.example.backend.security;

import com.example.backend.document.Account;
import com.example.backend.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findAccountByEmail(username)
                .map(account -> new User(
                        mapUserToUserDetails(account).getUsername(),
                        mapUserToUserDetails(account).getPassword(),
                        mapUserToUserDetails(account).getAuthorities()
                ))
                .orElseThrow(()->new UsernameNotFoundException("username not found"));
    }
    public UserDetails mapUserToUserDetails(Account account){
        return new CustomUserDetails(account);
    }
}
