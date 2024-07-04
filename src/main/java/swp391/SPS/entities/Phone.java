package swp391.SPS.entities;
// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
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

  @Column(name = "status")
  private Boolean status;

  @Column(name = "release_date")
  private LocalDate releaseDate;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "brand_id", referencedColumnName = "brand_id")
  private Brand brand;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "picture_id", referencedColumnName = "picture_id")
  @Nullable
  private Picture picture;

  @OneToMany(mappedBy = "phone", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<CartItem> items;

  @OneToMany(mappedBy = "phone", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<OrderItem> orderItems;


  public Phone createPhone(String productName, double price, String cpu, int ram, double memory, double display, double camera, String origin, String sim, LocalDate releaseDate, Brand brand, Picture picture) {
    return Phone.builder()
            .productName(productName)
            .price(price)
            .cpu(cpu)
            .ram(ram)
            .memory(memory)
            .display(display)
            .camera(camera)
            .origin(origin)
            .sim(sim)
            .releaseDate(releaseDate)
            .brand(brand)
            .picture(picture)
            .build();
  }

}
