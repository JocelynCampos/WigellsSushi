package se.edugrade.wigellssushi.services;

import se.edugrade.wigellssushi.entities.Menu;

import java.util.List;

public interface MenuServiceInterface {
    List<Menu> getAllDishes();
    Menu addDish(Menu menu);
    //Menu updateDish(Menu menu); ej krav. gör om tid finns
    void deleteDish(Integer id);
}
