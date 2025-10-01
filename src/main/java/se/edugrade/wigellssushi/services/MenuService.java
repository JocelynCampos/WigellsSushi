package se.edugrade.wigellssushi.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.edugrade.wigellssushi.entities.Menu;
import se.edugrade.wigellssushi.exceptions.ResourceNotFoundException;
import se.edugrade.wigellssushi.repositories.MenuRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    private static final Logger adminLogger = LoggerFactory.getLogger(MenuService.class);

    public List<Menu> getListOfDishes(){
        return menuRepository.findAll();
    }

    @Transactional
    public Menu addNewDish(Menu menu){
        if(menuRepository.existsByNameIgnoreCase(menu.getDishName())) {
            throw new IllegalArgumentException("Dish name already exists");
        }
        Menu savedMenu = menuRepository.save(menu);
        adminLogger.info("Added new dish: id={}, name={}", savedMenu.getId(), savedMenu.getDishName());
        return savedMenu;
    }

    @Transactional
    public void removeDish(Integer id){
        if (!menuRepository.existsById(id)) {
            throw new ResourceNotFoundException("Menu", "id", id);
        }
        menuRepository.deleteById(id);
        adminLogger.info("Dish with id={} removed.", id);
    }
}
