package hr.zaba.task.controllers;

import hr.zaba.task.models.Account;
import hr.zaba.task.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/register")
    public String getRegisterForm(Model model) {

        Account account = new Account();
        model.addAttribute("account", account);
        return "account_register";
    }

    @PostMapping("/register")
    public String registerNewAccount(@ModelAttribute Account account) {
        accountService.save(account);
        return "redirect:/";
    }

    @GetMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public String getActiveAccountForEdit(Model model, Principal principal) {

        // find account by id
        Optional<Account> optionalAccount = accountService.findOneByEmail(principal.getName());
        // if account exists, pass the data to the model
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            model.addAttribute("account", account);
            return "account_edit";
        } else {
            return "404";
        }
    }

    @PostMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public String editAccount(@ModelAttribute Account accountIn, Principal principal) {

        // find account by id
        Optional<Account> optionalAccount = accountService.findOneByEmail(principal.getName());
        // if account exists, update the info
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            account.setFirstName(accountIn.getFirstName());
            account.setLastName(accountIn.getLastName());
            accountService.save(account);
            return "redirect:/";
        } else {
            return "404";
        }
    }

}
