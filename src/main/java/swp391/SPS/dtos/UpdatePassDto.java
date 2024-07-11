package swp391.SPS.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePassDto {
    private String oldPass;
    private String newPass;
    private String confirmPass;
}
