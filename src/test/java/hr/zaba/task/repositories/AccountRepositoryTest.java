package hr.zaba.task.repositories;

import hr.zaba.task.models.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void shouldCreateAccountTest() {
        Account account = new Account();
        account.setEmail("ivo.ivic@acme.hr");
        account.setPassword("12345");
        account.setFirstName("Ivo");
        account.setLastName("Ivić");
        accountRepository.save(account);
        Optional<Account> optionalAccount = accountRepository.findOneByEmailIgnoreCase(account.getEmail());
        Assertions.assertTrue(optionalAccount.isPresent());
        Assertions.assertEquals(account.getEmail(), optionalAccount.get().getEmail());
        Assertions.assertEquals(account.getFirstName(), optionalAccount.get().getFirstName());
        Assertions.assertEquals(account.getLastName(), optionalAccount.get().getLastName());
    }

}
