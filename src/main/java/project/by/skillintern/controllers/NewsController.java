package project.by.skillintern.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.by.skillintern.dto.responses.NewsResponseDTO;
import project.by.skillintern.services.NewsService;

import java.util.List;

@RestController
@RequestMapping("/news")
@CrossOrigin(origins = "*")
@Tag(name="News", description="Взаймодействие с новостями")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;
    @GetMapping("/all")
    @Operation(summary = "Get all news")
    private ResponseEntity<List<NewsResponseDTO>> allNews() {
        return ResponseEntity.ok(newsService.allNews());
    }
}
