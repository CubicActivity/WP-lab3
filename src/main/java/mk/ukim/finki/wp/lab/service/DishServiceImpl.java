package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Chef;
import mk.ukim.finki.wp.lab.model.Dish;
import mk.ukim.finki.wp.lab.repository.ChefRepository;
import mk.ukim.finki.wp.lab.repository.DishRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishServiceImpl implements DishService { //Class 'DishServiceImpl' must either be declared abstract or implement abstract method 'create(String, String, String, int, Long)' in 'DishService'
    private final DishRepository dishRepository;
    private final ChefRepository chefRepository;

    public DishServiceImpl(DishRepository dishRepository, ChefRepository chefRepository) {
        this.dishRepository = dishRepository;
        this.chefRepository=chefRepository;
    }

    @Override
    public List<Dish> listDishes() {
        return dishRepository.findAll();
    }

    @Override
    public Dish findByDishId(String dishId) {
        return dishRepository.findByDishId(dishId);
    }

    @Override
    public Dish findById(Long id) {
        return dishRepository.findById(id).orElse(null);
    }

    @Override
    public Dish create(String dishId, String name, String cuisine, int preparationTime, Long chefId) {
        Dish dish = new Dish();
        dish.setDishId(dishId);
        dish.setName(name);
        dish.setCuisine(cuisine);
        dish.setPreparationTime(preparationTime);

        if (chefId != null) {
            Chef chef = chefRepository.findById(chefId).orElse(null);
            dish.setChef(chef);
        }
        return dishRepository.save(dish);
    }

    @Override
    public Dish update(Long id, String dishId, String name, String cuisine, int preparationTime, Long chefId) {
        Dish dish = findById(id);
        if (dish != null) {
            dish.setDishId(dishId);
            dish.setName(name);
            dish.setCuisine(cuisine);
            dish.setPreparationTime(preparationTime);

            if (chefId != null) {
                Chef chef = chefRepository.findById(chefId).orElse(null);
                dish.setChef(chef);
            }

            return dishRepository.save(dish);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        dishRepository.deleteById(id);
    }
}