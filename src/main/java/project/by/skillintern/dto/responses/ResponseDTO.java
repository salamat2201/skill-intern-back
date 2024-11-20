package project.by.skillintern.dto.responses;

import lombok.Data;

@Data
public class ResponseDTO {
    private String email;
    private String username;
    public ResponseDTO(String email, String username) {
        this.email = email;
        this.username = username;
    }
}
