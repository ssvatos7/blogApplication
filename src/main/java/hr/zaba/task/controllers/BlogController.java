package hr.zaba.task.controllers;

import hr.zaba.task.models.Account;
import hr.zaba.task.models.Blog;
import hr.zaba.task.services.AccountService;
import hr.zaba.task.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/")
    public String home(Model model) {
        List<Blog> blogs = blogService.getAll();
        model.addAttribute("blogs", blogs);
        return "home";
    }

    @GetMapping("/blogs/{id}")
    public String getBlog(@PathVariable Long id, Model model, Principal principal) {

        // find post by id
        Optional<Blog> optionalPost = this.blogService.getById(id);

        // if post exists put it in model
        if (optionalPost.isPresent() && principal != null) {
            Blog blog = optionalPost.get();
            // puts entity in model
            model.addAttribute("blog", blog);
            model.addAttribute("authUsername", principal.getName());
            return "blog";
        } else {
            return "404";
        }
    }

    @PostMapping("/blogs/{id}")
    @PreAuthorize("isAuthenticated()")
    public String updateBlog(@PathVariable Long id, Blog blog, BindingResult result, Model model) {

        Optional<Blog> optionalPost = blogService.getById(id);
        if (optionalPost.isPresent()) {
            Blog existingBlog = optionalPost.get();

            existingBlog.setTitle(blog.getTitle());
            existingBlog.setBody(blog.getBody());

            blogService.save(existingBlog);
        }

        return "redirect:/blogs/" + blog.getId();
    }

    @GetMapping("/blogs/new")
    @PreAuthorize("isAuthenticated()")
    public String createNewBlog(Model model, Principal principal) {

        String authUsername = "anonymousUser";
        if (principal != null) {
            authUsername = principal.getName();
        }

        Optional<Account> optionalAccount = accountService.findOneByEmail(authUsername);
        if (optionalAccount.isPresent()) {
            Blog blog = new Blog();
            blog.setAccount(optionalAccount.get());
            model.addAttribute("blog", blog);
            return "blog_new";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/blogs/new")
    @PreAuthorize("isAuthenticated()")
    public String createNewBlog(@ModelAttribute Blog blog, Principal principal) {

        blogService.save(blog);
        return "redirect:/blogs/" + blog.getId();
    }

    @GetMapping("/blogs/{id}/edit")
    @PreAuthorize("isAuthenticated()")
    public String getBlogForEdit(@PathVariable Long id, Model model) {

        // find post by id
        Optional<Blog> optionalPost = blogService.getById(id);
        // if post exist put it in model
        if (optionalPost.isPresent()) {
            Blog blog = optionalPost.get();
            model.addAttribute("blog", blog);
            return "blog_edit";
        } else {
            return "404";
        }
    }

    @GetMapping("/blogs/{id}/delete")
    @PreAuthorize("isAuthenticated()")
    public String deleteBlog(@PathVariable Long id) {

        // find post by id
        Optional<Blog> optionalBlog = blogService.getById(id);
        if (optionalBlog.isPresent()) {
            Blog blog = optionalBlog.get();
            blogService.delete(blog);
            return "redirect:/";
        } else {
            return "404";
        }
    }

}
