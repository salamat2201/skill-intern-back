package project.by.skillintern.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    @NotNull(message = "Username не должен быть пустым!")
    private String username;
    @Email(message = "Неверный формат email")
    @NotNull(message = "Email не должен быть пустым!")
    private String email;
    @NotNull(message = "")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&_#])(?=.*[a-z])[A-Za-z\\d@$!%*?&_#]{8,}$",
            message = "Password должен содержать как минимум одну заглавную букву, один чисел, и один символ. И должен быть длиной как минимум 8.")
    private String password;
    @NotNull(message = "Роль обязательна")
    private Boolean isEmployer;
}