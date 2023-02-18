package shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import shop.models.entities.Category;
import shop.models.entities.Product;

import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Set<Product> getAllByUserId(Long id);

//    @Modifying
//    @Query("UPDATE Product p " +
//            "SET p.name = :#{#product.name}, " +
//            "p.description = :#{#product.description}, " +
//            "p.price = :#{#product.price}, " +
//            "p.expirationDate = :#{#product.expirationDate} " +
//            "WHERE p.id = :productId AND p.user.id = :#{#product.user.id}")
//    void updateProductById(@Param("productId") Long productId, @Param("product") Product product);

}
