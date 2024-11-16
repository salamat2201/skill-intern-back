package project.by.skillintern.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.by.skillintern.dto.requests.NewsDTO;
import project.by.skillintern.dto.requests.UserDTO;
import project.by.skillintern.services.NewsService;
import project.by.skillintern.services.UserService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final NewsService newsService;
    @GetMapping("/allUsers")
    private ResponseEntity<List<UserDTO>> allUsers() {
        return ResponseEntity.ok(userService.allUsers());
    }
    @PostMapping("/addNews")
    private ResponseEntity<?> addNews(NewsDTO newsDTO) {
        try {
            newsService.addNews(newsDTO);
            return ResponseEntity.status(200).body("News added successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to upload image or save news");
        }
    }
}
