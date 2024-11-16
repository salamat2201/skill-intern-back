package project.by.skillintern.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.by.skillintern.dto.requests.NewsDTO;
import project.by.skillintern.dto.responses.NewsResponseDTO;
import project.by.skillintern.services.NewsService;

import java.util.List;

@RestController
@RequestMapping("/news")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;
    @GetMapping("/all")
    private ResponseEntity<List<NewsResponseDTO>> allNews() {
        return ResponseEntity.ok(newsService.allNews());
    }
}
