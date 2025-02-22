package swp391.SPS.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RequestSaveUserRoleDto {
    private int userId;
    private String roleName;
    private int page;
    private String search;
}
