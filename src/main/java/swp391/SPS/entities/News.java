package swp391.SPS.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "news")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Data
public class News {
  @Id
  @Column(name = "news_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int newsId;

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

  @Column(name = "title")
  private String title;

  @Column(name = "short_description")
  private String shortDescription;

  @Column(name = "news_detail", columnDefinition = "TEXT")
  private String newsDetail;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "news_category_id", referencedColumnName = "news_category_id")
  @JsonIgnore
  private NewsCategory newsCategory;

  @Column(name = "news_status")
  private Boolean newsStatus;

}
