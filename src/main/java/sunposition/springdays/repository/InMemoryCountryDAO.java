package sunposition.springdays.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sunposition.springdays.model.Country;

import java.util.Optional;


@Repository
public interface InMemoryCountryDAO extends JpaRepository<Country, Long> {
    Optional<Country> findByName(String name);
}
