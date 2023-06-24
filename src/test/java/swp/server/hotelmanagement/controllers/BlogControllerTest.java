package swp.server.hotelmanagement.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import swp.server.hotelmanagement.dtos.BlogRequest;
import swp.server.hotelmanagement.dtos.BlogResponse;
import swp.server.hotelmanagement.services.BlogService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BlogControllerTest {
    @InjectMocks
    BlogController blogController;
    @Mock
    BlogService blogService;

    @Test
    public void getAllBlogTest() {
        BlogResponse blogResponse = new BlogResponse(1, "Hnay là thứ 2", "Thứ 2 là ngày đầu tuần"
                , null, "thu2.image", "VyVy");
        BlogResponse blogResponse1 = new BlogResponse(2, "Hnay là thứ 3", "Thứ 3 là tiểu tứ"
                , null, "thu3.image", "CCCCC");
        List<BlogResponse> blogResponseList = new ArrayList<>();
        blogResponseList.add(blogResponse);
        blogResponseList.add(blogResponse1);
        when(blogService.getAllBlog()).thenReturn(blogResponseList);
        assertThat(blogController.getAllBlog().size()).isEqualTo(2);
        assertThat(blogController.getAllBlog().get(0).getAccountName()).isEqualTo("VyVy");
    }

    @Test
    public void getByIdTest() {
        BlogResponse blogResponse = new BlogResponse(1, "Hnay là thứ 2", "Thứ 2 là ngày đầu tuần"
                , null, "thu2.image", "VyVy");
        when(blogService.getBlogById(1)).thenReturn(blogResponse);
        assertThat(blogController.getBlogById(1)).isEqualTo(blogResponse);
        assertThat(blogController.getBlogById(1).getBlogId()).isEqualTo(1);
    }

    @Test
    public void createNewBlogTest() {
        BlogRequest blogRequest = new BlogRequest("Hnay là thứ 5", "Thứ  4 hum qua", "thu4.image", 2);
        when(blogService.createNewBlog(blogRequest)).thenReturn(blogRequest);
        assertThat(blogController.createNewBlog(blogRequest).getContent().length()).isGreaterThan(10);
        assertThat(blogController.createNewBlog(blogRequest).getAccountId()).isGreaterThan(0);
    }

    @Test
    public void updateBlogTest() {
        BlogRequest blogRequest = new BlogRequest("Hnay là thứ 5", "Thứ  4 hum qua", "thu4.image", 2);
        when(blogService.updateBlog(1, blogRequest)).thenReturn(blogRequest);
        assertThat(blogController.updateBlog(1, blogRequest).getContent().length()).isGreaterThan(0);
        assertThat(blogController.updateBlog(1, blogRequest).getAccountId()).isEqualTo(2);
    }

    @Test
    public void deleteBlog() {
        BlogResponse blogResponse = new BlogResponse(1, "Hnay là thứ 2", "Thứ 2 là ngày đầu tuần"
                , null, "thu2.image", "VyVy");
        when(blogService.deleteBlog(blogResponse.getBlogId())).thenReturn(true);
        assertThat(blogController.deleteBlog(1)).isTrue();
    }
}
