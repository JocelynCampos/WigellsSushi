package se.edugrade.wigellssushi.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import se.edugrade.wigellssushi.entities.Menu;
import se.edugrade.wigellssushi.exceptions.ResourceNotFoundException;
import se.edugrade.wigellssushi.repositories.MenuRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MenuService implements MenuServiceInterface {

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    private static final Logger adminLogger = LoggerFactory.getLogger(MenuService.class);

    @Override
    public List<Menu> getAllDishes() {
        return menuRepository.findAll();
    }

    @Override
    @Transactional
    public Menu addDish(Menu menu) {
        String dishName = menu.getDishName().trim();
        if(menuRepository.existsByDishNameIgnoreCase(menu.getDishName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Dish name already exists");
        }
        menu.setDishName(dishName);
        Menu newDish = menuRepository.save(menu);
        adminLogger.info("Added new dish: id={}, name={}", newDish.getId(), newDish.getDishName());
        return newDish;
    }

    @Override
    @Transactional
    public void deleteDish(Integer id) {

        if (!menuRepository.existsById(id)) {
            throw new ResourceNotFoundException("Menu", "id", id);
        }
        menuRepository.deleteById(id);
        adminLogger.info("Deleted dish id={}", id);

    }
/** ej kravspec. Gör om du har tid över.
    @Override
    public Menu updateDish(Menu menu) {
        return null;
    }*/
}
