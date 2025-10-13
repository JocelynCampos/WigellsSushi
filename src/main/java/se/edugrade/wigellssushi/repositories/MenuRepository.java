package se.edugrade.wigellssushi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.edugrade.wigellssushi.entities.Menu;

import java.util.Optional;


public interface MenuRepository extends JpaRepository<Menu, Integer> {
    boolean existsByDishNameIgnoreCase(String name);
    Optional<Menu> findByDishNameIgnoreCase(String dishName);

}
