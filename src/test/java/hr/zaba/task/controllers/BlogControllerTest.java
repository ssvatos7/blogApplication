package hr.zaba.task.controllers;

import hr.zaba.task.config.ApplicationConfig;
import hr.zaba.task.models.Account;
import hr.zaba.task.models.Blog;
import hr.zaba.task.repositories.AccountRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BlogControllerTest {

    private static Account account = new Account();
    private static final String password = "ssvatos7";
    private static Blog blog = new Blog();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @BeforeAll
    public static void beforeTests() {
        account.setEmail("ssvatos7@gmail.com");
        account.setPassword(ApplicationConfig.passwordEncoder().encode(password));
        account.setFirstName("Snježana");
        account.setLastName("Križanić");
        blog.setTitle("Nulla dies sine linea");
        blog.setBody("Lorem ipsum dolor sit amet, per legimus oporteat et, an eum viderer quaestio. Cu cum ferri atqui consectetuer, his utinam consul omittam an, ad malis platonem cotidieque has. Ei mel vidisse nominavi. Vim eu aperiri commune laboramus. Id minimum detracto mea. Est cu iisque deseruisse, sea cu iudicabit molestiae, nisl hinc his cu.");
    }

    @BeforeEach
    public void beforeTest() {
        if (!accountRepository.findOneByEmailIgnoreCase(account.getEmail()).isPresent()) {
            account = accountRepository.save(account);
            blog.setAccount(account);
        }
    }

    @Test
    void createBlog_shouldBeRedirectedToLoginFormWhenUnauthenticated() throws Exception {

        mockMvc.perform(
                        post("/blogs/new")
                                .param("title", blog.getTitle())
                                .param("body", blog.getBody())
                                .param("account", blog.getAccount().getId().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    void createBlog_shouldBeAbleToCreateBlogWhenAuthenticated() throws Exception {

        MvcResult mvcResult = mockMvc.perform(formLogin()
                        .user("email",account.getEmail())
                        .password("password",password))
                .andExpect(authenticated())
                .andReturn();
        MockHttpSession session = (MockHttpSession) mvcResult.getRequest().getSession(false);
        mockMvc.perform(
                post("/blogs/new")
                        .session(session)
                        .param("title", blog.getTitle())
                        .param("body", blog.getBody())
                        .param("account", blog.getAccount().getId().toString()))
                        .andExpect(status().is3xxRedirection())
                        .andExpect(redirectedUrl("/blogs/1"));
    }

}
