package project.by.skillintern.dto.responses;

import lombok.Data;

@Data
public class ResponseDTO {
    private Long id;
    private String email;
    private String username;
    public ResponseDTO(Long id, String email, String username) {
        this.email = email;
        this.username = username;
    }
}
