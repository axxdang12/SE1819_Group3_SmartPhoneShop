package swp391.SPS.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class  EmailDetails {
  private String recipient;
  private String msgBody;
  private String subject;
}
