package exam2023.repositories;

import exam2023.models.entities.Offer;
import exam2023.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer,Long> {
    List<Offer> findAllByCreatorNot(User user);
}
