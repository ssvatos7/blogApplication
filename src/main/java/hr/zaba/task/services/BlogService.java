package hr.zaba.task.services;

import hr.zaba.task.models.Blog;
import hr.zaba.task.repositories.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;

    public Optional<Blog> getById(Long id) {
        return blogRepository.findById(id);
    }

    public List<Blog> getAll() {
        return blogRepository.findAll();
    }

    public Blog save(Blog blog) {
        if (blog.getId() == null) {
            blog.setCreatedAt(LocalDateTime.now());
        }
        blog.setUpdatedAt(LocalDateTime.now());
        return blogRepository.save(blog);
    }

    public void delete(Blog blog) {
        blogRepository.delete(blog);
    }

}
