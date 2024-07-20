package swp391.SPS.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePassDto {
    @NotEmpty(message = "Your password can not be blank")
    private String oldPass;
    @NotEmpty(message = "New password can not be blank")
    private String newPass;
    @NotEmpty(message = "Confirm password can not be blank")
    private String confirmPass;
}
