package ro.ubb.mp.dao.model;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "wishlist")
@IdClass(Wishlist.class)
public class Wishlist implements Serializable {
//    @Id
//    @ManyToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
//    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Wishlist wishlist = (Wishlist) o;
//        return Objects.equals(user, wishlist.user) &&
//                Objects.equals(product, wishlist.product);
//    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(user, product);
//    }
}
