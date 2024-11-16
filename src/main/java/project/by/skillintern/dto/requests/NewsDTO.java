package project.by.skillintern.dto.requests;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class NewsDTO {
    private String title;
    private String description;
    private MultipartFile image;
}
