package swp391.SPS.entities;
// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "picture")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Data
public class Picture {
    @Id
    @Column(name = "picture_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pictureId;

    @Column(name = "main")
    private String main;

    @Column(name = "front")
    private String front;

    @Column(name = "back")
    private String back;

    @Column(name = "side")
    private String site;

}
