package swp391.SPS.entities;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Table(name = "newsCategory")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Data

public class NewsCategory {
    @Id
    @Column(name = "news_category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int newsCategoryId;

    @Column(name = "news_category_name")
    private String newCategoryName;

    @Column(name = "news_category_status")
    private Boolean newCategoryStatus;

    @OneToMany(mappedBy = "newsCategory", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<News> news;

}
