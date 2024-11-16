package project.by.skillintern.services.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.by.skillintern.dto.requests.NewsDTO;
import project.by.skillintern.dto.responses.NewsResponseDTO;
import project.by.skillintern.entities.News;
import project.by.skillintern.repositories.NewsRepository;
import project.by.skillintern.services.NewsService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    private final ModelMapper modelMapper;
    private final S3Service s3Service;
    @Override
    @Transactional
    public void addNews(NewsDTO newsDTO) throws IOException {
        News news = convertToNews(newsDTO);
        String imageUrl = s3Service.uploadFile(newsDTO.getImage());
        news.setImageUrl(imageUrl);
        newsRepository.save(news);
    }

    @Override
    public List<NewsResponseDTO> allNews() {
        return newsRepository.findAll().stream().map(this::convertToNewsResponseDTO).collect(Collectors.toList());
    }

    private News convertToNews(NewsDTO newsDTO) {
        return modelMapper.map(newsDTO, News.class);
    }
    private NewsResponseDTO convertToNewsResponseDTO(News news) {
        return modelMapper.map(news, NewsResponseDTO.class);
    }
}
