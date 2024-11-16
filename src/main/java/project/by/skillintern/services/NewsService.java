package project.by.skillintern.services;

import project.by.skillintern.dto.requests.NewsDTO;
import project.by.skillintern.dto.responses.NewsResponseDTO;

import java.io.IOException;
import java.util.List;

public interface NewsService {
    void addNews(NewsDTO newsDTO) throws IOException;
    List<NewsResponseDTO> allNews();
}
