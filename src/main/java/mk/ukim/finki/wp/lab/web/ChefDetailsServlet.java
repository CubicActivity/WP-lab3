package mk.ukim.finki.wp.lab.web;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.model.Chef;
import mk.ukim.finki.wp.lab.model.Dish;
import mk.ukim.finki.wp.lab.service.ChefService;
import mk.ukim.finki.wp.lab.service.DishService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet(name = "ChefDetailsServlet", urlPatterns = {"/chefDetails"})
public class ChefDetailsServlet extends HttpServlet {
    private final SpringTemplateEngine springTemplateEngine;
    private final ChefService chefService;
    private final DishService dishService;

    public ChefDetailsServlet(SpringTemplateEngine springTemplateEngine, ChefService chefService, DishService dishService) {
        this.springTemplateEngine = springTemplateEngine;
        this.chefService = chefService;
        this.dishService = dishService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(request,response);

        WebContext webContext = new WebContext(webExchange);

        String getChef = request.getParameter("chefId");

        Long id = Long.parseLong(getChef);

        Chef chef = chefService.findById(id);

        webContext.setVariable("chef",chef);

        springTemplateEngine.process("chefDetails.html", webContext, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String getChefId = request.getParameter("chefId");
        String getDishId = request.getParameter("dishId");

        Long chefId = Long.parseLong(getChefId);

        chefService.addDishToChef(chefId,getDishId);

        response.sendRedirect("/chefDetails?chefId=" + getChefId);
    }
}