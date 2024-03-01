package com.example.bankingservice.config;

import com.example.bankingservice.repository.AccountRepository;
import com.example.bankingservice.repository.UserRepository;
import com.example.bankingservice.service.impl.AccountServiceImpl;
import com.example.bankingservice.service.impl.AuthServiceImpl;
import com.example.bankingservice.service.impl.UserServiceImpl;
import com.example.bankingservice.service.props.JwtProperties;
import com.example.bankingservice.web.security.JwtTokenProvider;
import com.example.bankingservice.web.security.JwtUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@TestConfiguration
@RequiredArgsConstructor
public class TestConfig {

    @Bean
    @Primary
    public BCryptPasswordEncoder testPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtProperties jwtProperties() {
        JwtProperties jwtProperties = new JwtProperties();
        jwtProperties.setSecret(
                "aGZiYmtiYWllYmNpZWFpZWJsZWNldWNlY2xhZWNhaWJlbGNhZWN3Q0VCV0VXSUM="
        );
        return jwtProperties;
    }

    @Bean
    public UserDetailsService userDetailsService(
            final UserRepository userRepository
    ) {
        return new JwtUserDetailsService(userService(userRepository));
    }

    @Bean
    public Configuration configuration() {
        return Mockito.mock(Configuration.class);
    }

    @Bean
    public JwtTokenProvider tokenProvider(
            final UserRepository userRepository
    ) {
        return new JwtTokenProvider(jwtProperties(),
                userDetailsService(userRepository),
                userService(userRepository));
    }

    @Bean
    @Primary
    public UserServiceImpl userService(
            final UserRepository userRepository
    ) {
        return new UserServiceImpl(
                userRepository,
                testPasswordEncoder()
        );
    }

    @Bean
    @Primary
    public AccountServiceImpl accountService(
            final AccountRepository accountRepository
    ) {
        return new AccountServiceImpl(accountRepository);
    }

    @Bean
    @Primary
    public AuthServiceImpl authService(
            final UserRepository userRepository,
            final AuthenticationManager authenticationManager
    ) {
        return new AuthServiceImpl(
                authenticationManager,
                userService(userRepository),
                tokenProvider(userRepository)
        );
    }

    @Bean
    public UserRepository userRepository() {
        return Mockito.mock(UserRepository.class);
    }

    @Bean
    public AccountRepository accountRepositoryRepository() {
        return Mockito.mock(AccountRepository.class);
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return Mockito.mock(AuthenticationManager.class);
    }

}
