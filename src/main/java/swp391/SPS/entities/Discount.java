package swp391.SPS.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "discount")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Data
public class Discount {
  @Id
  @Column(name = "discount_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int discountId;

  @ManyToOne
  @JoinColumn(name = "created_by", referencedColumnName = "user_id")
  @JsonIgnore
  private User createdBy;

  @Column(name = "create_on")
  private LocalDateTime createdOn;

  @ManyToOne
  @JoinColumn(name = "modify_by", referencedColumnName = "user_id")
  @JsonIgnore
  private User modifyBy;

  @Column(name = "modify_on")
  private LocalDateTime modifyOn;

  @OneToMany(mappedBy = "discount", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<Phone> phone;

  @Column(name = "quantity")
  private int quantity;

  @Column(name = "value")
  private int value;

  @Column(name = "exp_date")
  private LocalDateTime expDate;

  @Column(name = "discount_status")
  private Boolean discountStatus;

}
