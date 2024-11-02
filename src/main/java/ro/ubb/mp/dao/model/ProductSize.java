package ro.ubb.mp.dao.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_sizes",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name", "is_for_kids", "is_for_shoes"})
        }
)
public class ProductSize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Builder.Default
    @Column(name = "is_for_shoes")
    private boolean isForShoes = false;

    @Builder.Default
    @Column(name = "is_for_kids")
    private boolean isForKids = false;
}
