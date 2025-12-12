package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.service.ChefService;
import mk.ukim.finki.wp.lab.service.DishService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DishController {
    private final DishService dishService;
    private final ChefService chefService;

    public DishController(DishService dishService, ChefService chefService) {
        this.dishService = dishService;
        this.chefService = chefService;
    }

    @GetMapping("/dishes")
    public String getDishesPage(@RequestParam(required = false) String error, Model model) {

        model.addAttribute("dishes",dishService.listDishes());

        return "listDishes";
    }

    @PostMapping("/dishes/add")
    public String saveDish(@RequestParam String dishId,
                           @RequestParam String name,
                           @RequestParam String cuisine,
                           @RequestParam int preparationTime,
                           @RequestParam Long chefId) {
        dishService.create(dishId, name, cuisine, preparationTime, chefId);
        return "redirect:/dishes";
    }

    @PostMapping("/dishes/edit/{id}")
    public String editDish(@PathVariable Long id,
                           @RequestParam String dishId,
                           @RequestParam String name,
                           @RequestParam String cuisine,
                           @RequestParam int preparationTime,
                           @RequestParam Long chefId) {
        dishService.update(id, dishId, name, cuisine, preparationTime, chefId);
        return "redirect:/dishes";
    }

    @PostMapping("/dishes/delete/{id}")
    public String deleteDish(@PathVariable Long id) {
        dishService.delete(id);

        return "redirect:/dishes";
    }

    @GetMapping("/dishes/dish-form")
    public String getAddDishPage(Model model) {
        model.addAttribute("chefs", chefService.listChefs()); // or through ChefService
        return "dish-form";
    }

    @GetMapping("/dishes/dish-form/{id}")
    public String getEditDishForm(@PathVariable Long id, Model model) {
        model.addAttribute("dish", dishService.findById(id));
        model.addAttribute("chefs", chefService.listChefs());
        return "dish-form";
    }
}