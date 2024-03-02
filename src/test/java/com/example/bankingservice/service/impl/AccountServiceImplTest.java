package com.example.bankingservice.service.impl;

import com.example.bankingservice.config.TestConfig;
import com.example.bankingservice.domain.account.Account;
import com.example.bankingservice.domain.exception.ResourceNotFoundException;
import com.example.bankingservice.domain.user.User;
import com.example.bankingservice.repository.AccountRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {

    @MockBean
    private AccountRepository accountRepository;

    @Autowired
    private AccountServiceImpl accountService;

    private static final Logger logger = LogManager.getLogger(AccountServiceImpl.class);

    @Test
    void getById() {
        Long id = 1L;
        Account account = new Account();
        account.setId(id);
        Mockito.when(accountRepository.findById(id))
                .thenReturn(Optional.of(account));
        Account testAccount = accountService.getById(id);
        Mockito.verify(accountRepository).findById(id);
        Assertions.assertEquals(account, testAccount);
    }

    @Test
    void getByNotExistingId() {
        Long id = 1L;

        Mockito.when(accountRepository.findById(id))
                .thenReturn(Optional.empty());
        try {
            Assertions.assertThrows(ResourceNotFoundException.class,
                    () -> accountService.getById(id));
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }

        Mockito.verify(accountRepository).findById(id);
    }

    @Test
    public void testMakeTransfer() {
        User userSender = new User();
        User userRecipient = new User();
        userSender.setId(1L);
        userRecipient.setId(2L);

        Long senderId = 1L;
        Long recipientId = 2L;
        BigDecimal amount = BigDecimal.valueOf(100);
        Account sender = new Account();
        sender.setId(senderId);
        sender.setBalance(BigDecimal.valueOf(200));
        sender.setUser(userSender);

        Account recipient = new Account();
        recipient.setId(recipientId);
        recipient.setUser(userRecipient);

        Mockito.when(accountRepository.findById(senderId)).thenReturn(Optional.of(sender));
        Mockito.when(accountRepository.findById(recipientId)).thenReturn(Optional.of(recipient));

        accountService.makeTransfer(senderId, recipientId, amount);

        Mockito.verify(accountRepository).findById(senderId);
        Mockito.verify(accountRepository).findById(recipientId);
        Mockito.verify(accountRepository).transferFunds(senderId, recipientId, amount);
    }

    @Test
    public void testMakeTransferToNonExistentAccount() {
        Long senderId = 1L;
        Long recipientId = 2L;
        BigDecimal amount = BigDecimal.valueOf(100);

        Account sender = new Account();
        sender.setId(senderId);
        sender.setBalance(BigDecimal.valueOf(200));

        Mockito.when(accountRepository.findById(senderId)).thenReturn(Optional.of(sender));
        Mockito.when(accountRepository.findById(recipientId)).thenReturn(Optional.empty());

        try {
            Assertions.assertThrows(ResourceNotFoundException.class,
                    () -> accountService.makeTransfer(senderId, recipientId, amount));
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }

        Mockito.verify(accountRepository).findById(senderId);
        Mockito.verify(accountRepository).findById(recipientId);
    }

    @Test
    public void testMakeTransferToYourself() {
        Long senderId = 1L;
        Long recipientId = 2L;
        BigDecimal amount = BigDecimal.valueOf(100);

        User user = new User();
        user.setId(senderId);

        Account sender = new Account();
        sender.setId(senderId);
        sender.setUser(user);

        Account recipient = new Account();
        recipient.setId(recipientId);
        recipient.setUser(user);

        Mockito.when(accountRepository.findById(senderId)).thenReturn(Optional.of(sender));
        Mockito.when(accountRepository.findById(recipientId)).thenReturn(Optional.of(recipient));

        IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class,
                () -> accountService.makeTransfer(senderId, recipientId, amount));

        logger.debug(exception.getMessage());

        Mockito.verify(accountRepository).findById(senderId);
        Mockito.verify(accountRepository).findById(recipientId);
        Assertions.assertEquals("You cannot make the transfer to yourself.", exception.getMessage());
    }

    @Test
    public void testMakeTransferWithInsufficientFunds() {
        User userSender = new User();
        User userRecipient = new User();
        userSender.setId(1L);
        userRecipient.setId(2L);

        Long senderId = 1L;
        Long recipientId = 2L;
        BigDecimal amount = BigDecimal.valueOf(100);

        Account sender = new Account();
        sender.setId(senderId);
        sender.setBalance(BigDecimal.valueOf(50));
        sender.setUser(userSender);

        Account recipient = new Account();
        recipient.setId(recipientId);
        recipient.setUser(userRecipient);

        Mockito.when(accountRepository.findById(senderId)).thenReturn(Optional.of(sender));
        Mockito.when(accountRepository.findById(recipientId)).thenReturn(Optional.of(recipient));

        IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class,
                () -> accountService.makeTransfer(senderId, recipientId, amount));

        logger.debug(exception.getMessage());

        Mockito.verify(accountRepository).findById(senderId);
        Mockito.verify(accountRepository).findById(recipientId);
        Assertions.assertEquals("Insufficient funds.", exception.getMessage());
    }



}
