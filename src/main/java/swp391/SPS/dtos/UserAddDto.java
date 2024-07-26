package swp391.SPS.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAddDto {
    @Pattern(regexp = "^[a-zA-Z0-9_-]{3,16}$", message = "Username must be max 16 characters and not allow special character!")
    @NotEmpty(message = "Username can not be blank")
    private String username;

    private String password;
    private String repeatPassword;

    @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\\\.[A-Za-z0-9-]+)*(\\\\.[A-Za-z]{2,})$", message = "Email input invalid, try again!")
    private String email;

    String role;
}
