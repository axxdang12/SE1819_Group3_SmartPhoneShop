package swp391.SPS.entities;
// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "phone")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Data
public class Phone {
  @Id
  @Column(name = "phone_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int phoneId;

  @Column(name = "product_name")
  private String productName;

  @Column(name = "price")
  private double price;

  @Column(name = "cpu")
  private String cpu;

  @Column(name = "ram")
  private int ram;

  @Column(name = "memory")
  private double memory;

  @Column(name = "display")
  private double display;

  @Column(name = "camera")
  private double camera;

  @Column(name = "origin")
  private String origin;

  @Column(name = "sim")
  private String sim;

  @Column(name = "release_date")
  private LocalDate releaseDate;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "category_id", referencedColumnName = "category_id")
  private Category category;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "brand_id", referencedColumnName = "brand_id")
  private Brand brand;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "picture_id", referencedColumnName = "picture_id")
  @Nullable
  private Picture picture;

  @ManyToMany(mappedBy = "phones")
  @Nullable
  private List<Cart> carts;

  @ManyToMany(mappedBy = "phones")
  @Nullable
  private List<Order> orders;
}
