package swp.server.hotelmanagement.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import swp.server.hotelmanagement.dtos.BlogRequest;
import swp.server.hotelmanagement.dtos.BlogResponse;
import swp.server.hotelmanagement.services.BlogService;

import java.util.List;

@RestController
@RequestMapping("hotel-server/api/v1")
@AllArgsConstructor
public class BlogController {
    private final BlogService blogService;

    @GetMapping("/blogs")
    public List<BlogResponse> getAllBlog() {
        return blogService.getAllBlog();
    }

    @GetMapping("/blog/{id}")
    public BlogResponse getBlogById(@PathVariable(value = "id") int blogId) {
        return blogService.getBlogById(blogId);
    }

    @PostMapping("/blog")
    public BlogRequest createNewBlog(BlogRequest blogRequest) {
        return blogService.createNewBlog(blogRequest);
    }

    @PutMapping("/updateBlog/{id}")
    public BlogRequest updateBlog(@PathVariable(value = "id") int blogId,
                                  @RequestBody BlogRequest blogRequest) {
        return blogService.updateBlog(blogId, blogRequest);
    }

    @DeleteMapping("/deleteBlog/{id}")
    public boolean deleteBlog(@PathVariable(value = "id") int blogId) {
        return blogService.deleteBlog(blogId);
    }
}
