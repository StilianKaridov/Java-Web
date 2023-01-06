package bg.softuni.springbootintroduction.repository;

import bg.softuni.springbootintroduction.domain.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
}
