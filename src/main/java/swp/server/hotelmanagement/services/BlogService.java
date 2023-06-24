package swp.server.hotelmanagement.services;

import swp.server.hotelmanagement.dtos.BlogRequest;
import swp.server.hotelmanagement.dtos.BlogResponse;

import java.util.List;

public interface BlogService {
    List<BlogResponse> getAllBlog();
    BlogResponse getBlogById(int blogId);
    BlogRequest createNewBlog(BlogRequest blogRequest);
    BlogRequest updateBlog(int blogId,BlogRequest blogRequest);
    boolean deleteBlog(int blogId);
}
