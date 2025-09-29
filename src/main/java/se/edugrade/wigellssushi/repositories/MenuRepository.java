package se.edugrade.wigellssushi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.edugrade.wigellssushi.entities.Menu;


public interface MenuRepository extends JpaRepository<Menu, Integer> {
    boolean existsByNameIgnoreCase(String name);
}
