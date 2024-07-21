package swp391.SPS.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "orderItem")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

public class OrderItem {
    @Id
    @Column(name = "orderItem_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderItemId;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "total")
    private double total;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    @JsonIgnore
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "phone_id", referencedColumnName = "phone_id")
    @JsonIgnore
    private Phone phone;

}
