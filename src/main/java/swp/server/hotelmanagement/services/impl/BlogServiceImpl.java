package swp.server.hotelmanagement.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import swp.server.hotelmanagement.dtos.BlogRequest;
import swp.server.hotelmanagement.dtos.BlogResponse;
import swp.server.hotelmanagement.entities.BlogEntity;
import swp.server.hotelmanagement.repositories.AccountRepository;
import swp.server.hotelmanagement.repositories.BlogRepository;
import swp.server.hotelmanagement.services.BlogService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BlogServiceImpl implements BlogService {
    private final BlogRepository blogRepository;
    private final AccountRepository accountRepository;

    @Override
    public List<BlogResponse> getAllBlog() {

        try {
            List<BlogEntity> blogEntityList = blogRepository.findAll();
            List<BlogResponse> blogResponseList = new ArrayList<>();
            blogEntityList.stream().forEach(blogEntity -> {
                BlogResponse blogResponse = new BlogResponse(
                        blogEntity.getId(), blogEntity.getTitle(),
                        blogEntity.getContent(), blogEntity.getCreatedAt(),
                        blogEntity.getThumbnailImage(),
                        blogEntity.getAccountEntity().getProfileEntity().getFirstName());
                blogResponseList.add(blogResponse);
            });
            return blogResponseList;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    @Override
    public BlogResponse getBlogById(int blogId) {
        try {
            BlogEntity blogEntity = blogRepository.getOne(blogId);
            return new BlogResponse(
                    blogEntity.getId(), blogEntity.getTitle(),
                    blogEntity.getContent(), blogEntity.getCreatedAt(),
                    blogEntity.getThumbnailImage(),
                    blogEntity.getAccountEntity().getProfileEntity().getFirstName());
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    @Override
    public BlogRequest createNewBlog(BlogRequest blogRequest) {
        try {
            BlogEntity blogEntity = new BlogEntity();
            blogEntity.setAccountEntity(accountRepository.getOne(blogRequest.getAccountId()));
            blogEntity.setContent(blogRequest.getContent());
            blogEntity.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            blogEntity.setTitle(blogRequest.getTitle());
            blogEntity.setThumbnailImage(blogRequest.getImage());
            blogRepository.save(blogEntity);
            return blogRequest;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    @Override
    public BlogRequest updateBlog(int blogId, BlogRequest blogRequest) {
        try {
            BlogEntity blogEntity = blogRepository.getOne(blogId);
            blogEntity.setContent(blogRequest.getContent());
            blogEntity.setTitle(blogRequest.getTitle());
            blogEntity.setThumbnailImage(blogRequest.getImage());
            blogRepository.save(blogEntity);
            return blogRequest;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    @Override
    public boolean deleteBlog(int blogId) {
        try {
            if (blogRepository.getOne(blogId) != null) {
                blogRepository.deleteById(blogId);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.getMessage();
            return false;
        }
    }
}
