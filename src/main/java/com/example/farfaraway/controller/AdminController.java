package com.example.farfaraway.controller;

import com.example.farfaraway.entity.*;
import com.example.farfaraway.model.request.UpsertBlogRequest;
import com.example.farfaraway.repository.BookingRepository;
import com.example.farfaraway.repository.CommentRepository;
import com.example.farfaraway.repository.DepartureDateRepository;
import com.example.farfaraway.security.CustomUserDetails;
import com.example.farfaraway.service.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private final TourService tourService;
    @Autowired
    private final CategoryService categoryService;
    @Autowired
    private final BlogService blogService;
    @Autowired
    private DepartureDateRepository departureDateRepository;
    @Autowired
    private CommentService commentService;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private UserService userService;
    @Autowired
    private ImageService imageService;

    //Quản lý Blogs
    @GetMapping("/blogs")
    public String getBlogPage(@RequestParam(required = false, defaultValue = "1") Integer page,
                              @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                              Model model) {
        Page<Blog> pageData = blogService.getAllBlogs(page, pageSize);
        model.addAttribute("pageData", pageData);
        model.addAttribute("currentPage", page);
        return "admin/blog/index";
    }

    // Danh sách bài viết của tôi
    @GetMapping("/blogs/own-blogs")
    public String getOwnBlogPage(@RequestParam(required = false, defaultValue = "1") Integer page,
                                 @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                 Model model) {
        Page<Blog> pageData = blogService.getAllBlogsOfUserById(page, pageSize);
        model.addAttribute("pageData", pageData);
        model.addAttribute("currentPage", page);
        return "admin/blog/own-blog";
    }
    // Tìm kiếm Blogs
    @GetMapping("/blogs/search")
    public String searchBlog(@RequestParam("title") String title,
                              @RequestParam(required = false, defaultValue = "1") Integer page,
                              @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                              Model model) {
        Page<Blog> searchResults = blogService.searchByTitlePaged(title, PageRequest.of(page -1, pageSize));
        System.out.println(searchResults);
        model.addAttribute("pageData", searchResults);
        return "admin/blog/search-results";
    }

    // Tạo bài viết
    @GetMapping("/blogs/create")
    public String getBlogCreatePage(Model model) {
        return "admin/blog/create";
    }


    // Chi tiết bài viết
    @GetMapping("/blogs/{id}/detail")
    public String getBlogDetailPage(@PathVariable Integer id, Model model) {
        Blog blog = blogService.getBlogById(id);
        model.addAttribute("blog", blog);
        model.addAttribute("imageList", imageService.getFilesOfCurrentUser());
        return "admin/blog/detail";
    }
    //Xóa Blog
    @DeleteMapping("/blog/{id}")
    public ResponseEntity<?> deleteBlog(@PathVariable Integer id) {
        try {
            blogService.deleteBlog(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Xóa bài viết thất bại: " + e.getMessage());
        }
    }
    //Update Blog
    @PutMapping ("/blog/{id}")
    public ResponseEntity<?> updateBlog(@PathVariable Integer id, @RequestBody Blog updatedBlog){
        Blog existingBlog = blogService.findById(id);
        existingBlog.setTitle(updatedBlog.getTitle());
        existingBlog.setDescription(updatedBlog.getDescription());
        existingBlog.setContent(updatedBlog.getContent());
        existingBlog.setStatus(updatedBlog.getStatus());
        existingBlog.setThumbnail(updatedBlog.getThumbnail());

        // Lưu thay đổi vào cơ sở dữ liệu
        blogService.saveBlog(existingBlog);

        return new ResponseEntity<>("Cập nhật bài viết thành công", HttpStatus.OK);
    }
    // Tạo Blog mới
    @PostMapping ("/blogs")
    public ResponseEntity<?> createBlog(@RequestBody UpsertBlogRequest blog){
        return new ResponseEntity<>(blogService.createBlog(blog), HttpStatus.OK);
    }

    //Tất cả users
    @GetMapping("/users")
    public String getUsersPage(@RequestParam(required = false, defaultValue = "1") Integer page,
                              @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                              Model model) {
        Page<User> pageData = userService.getAllUsersByPage(page , pageSize);
        model.addAttribute("pageData", pageData);
        model.addAttribute("currentPage", page);
        return "admin/user/index";
    }
    // Tạo User
    @GetMapping("/user/create")
    public String getUserCreatePage() {
        return "admin/user/create";
    }

    // Chi tiết bài viết
    @GetMapping("/user/{id}/detail")
    public String getUserDetailPage(@PathVariable Integer id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("imageList", imageService.getFilesOfCurrentUser());
        return "admin/user/detail";
    }
    // Tất cả comment
    @GetMapping("/comments")
    public String getToursPage(@RequestParam(required = false, defaultValue = "1") Integer page,
                               @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                               Model model) {
        Page<Comment> pageData = commentService.getAllCommentsByPage(1,10);
        model.addAttribute("pageData", pageData);
        model.addAttribute("currentPage", page);
        return "admin/comment/index";
    }
    // Chi tiết comment
    @GetMapping("/comment/{id}/detail")
    public String getTourDetailPage(@PathVariable Integer id, Model model) {
        Comment comment = commentService.getCommentById(id);
        model.addAttribute("comment", comment);
        model.addAttribute("imageList", imageService.getFilesOfCurrentUser());
        return "admin/tour/detail";
    }
    // Tất cả booking
    @GetMapping("/bookings")
    public String getBookingsPage(@RequestParam(required = false, defaultValue = "1") Integer page,
                               @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                               Model model) {
        Page<Booking> pageData = bookingService.getAllBookingByPage(1,10);
        model.addAttribute("pageData", pageData);
        model.addAttribute("currentPage", page);
        return "admin/booking/index";
    }
    // Chi tiết booking
    @GetMapping("/booking/{id}/detail")
    public String getBookingDetailPage(@PathVariable Integer id, Model model) {
        Booking booking = bookingService.getBookingById(id);
        model.addAttribute("booking", booking);
        model.addAttribute("imageList", imageService.getFilesOfCurrentUser());
        return "admin/tour/detail";
    }
}
