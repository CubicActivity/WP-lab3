package mk.ukim.finki.wp.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.lab.model.Chef;
import mk.ukim.finki.wp.lab.model.Dish;
import mk.ukim.finki.wp.lab.repository.ChefRepository;
import mk.ukim.finki.wp.lab.repository.DishRepository;
import org.springframework.stereotype.Component;

@Component
public class DataHolder {

    private final ChefRepository chefRepository;
    private final DishRepository dishRepository;

    public DataHolder(ChefRepository chefRepository, DishRepository dishRepository) {
        this.chefRepository = chefRepository;
        this.dishRepository = dishRepository;
    }

    @PostConstruct
    public void init() {
        if (chefRepository.count() == 0) {
            Chef nikola = chefRepository.save(new Chef(null, "Nikola", "Nikolov", "Nikola Bio", null));
            Chef stefan = chefRepository.save(new Chef(null, "Stefan", "Stefanov", "Stefan Bio", null));
            Chef ivan = chefRepository.save(new Chef(null, "Ivan", "Ivanov", "Ivan Bio", null));
        }

        if (dishRepository.count() == 0) {
            Chef nikola = chefRepository.findById(1L).orElse(null);
            Chef stefan = chefRepository.findById(2L).orElse(null);
            Chef ivan = chefRepository.findById(3L).orElse(null);

            dishRepository.save(new Dish(null, "1D", "Tavche Gravche", "Macedonian", 15, nikola));
            dishRepository.save(new Dish(null, "2D", "Texas BBQ", "American", 55, stefan));
            dishRepository.save(new Dish(null, "3D", "Fish Stew", "Swedish", 25, ivan));
        }
    }
}