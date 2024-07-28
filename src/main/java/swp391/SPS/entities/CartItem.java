package swp391.SPS.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "cartItem")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

public class CartItem {
    @Id
    @Column(name = "cartItem_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartItemId;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "total")
    private double total;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", referencedColumnName = "cart_id")
    @JsonIgnore
    private Cart cart;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "phone_id", referencedColumnName = "phone_id")
    @JsonIgnore
    private Phone phone;

    @Transient
    public double getTotalPrice() {
        return this.quantity * phone.getPrice();
    }
}
