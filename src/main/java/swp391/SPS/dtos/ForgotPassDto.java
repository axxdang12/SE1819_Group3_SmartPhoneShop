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
public class ForgotPassDto {
    @Pattern(regexp = "^.+@.+$", message = "Email input invalid, try again!")
    @NotEmpty(message = "Can not be blank")
    private String email;
}
