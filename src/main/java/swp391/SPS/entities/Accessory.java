package swp391.SPS.entities;
// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "accessory")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Data
public class Accessory {
  @Id
  @Column(name = "accessory_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int accessoryId;

  @Column(name = "accessory_name")
  private String accessoryName;

  @Column(name = "accessory_description")
  private String accessoryDescription;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "category_id", referencedColumnName = "category_id")
  private Category category;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "brand_id", referencedColumnName = "brand_id")
  private Brand brand;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "picture_id", referencedColumnName = "picture_id")
  private Picture picture;

  @ManyToMany(mappedBy = "accessories")
  private List<Cart> carts;

  @ManyToMany(mappedBy = "accessories")
  private List<Order> orders;
}
