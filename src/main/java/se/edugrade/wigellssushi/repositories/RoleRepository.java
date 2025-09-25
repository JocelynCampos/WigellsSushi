package se.edugrade.wigellssushi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.edugrade.wigellssushi.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
