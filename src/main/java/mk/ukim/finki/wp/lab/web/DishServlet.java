package mk.ukim.finki.wp.lab.web;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.model.Chef;
import mk.ukim.finki.wp.lab.service.ChefService;
import mk.ukim.finki.wp.lab.service.DishService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet(name = "DishServlet", urlPatterns = {"/dish"})
public class DishServlet extends HttpServlet {
    private final DishService dishService;
    private final ChefService chefService;
    private final SpringTemplateEngine springTemplateEngine;

    public DishServlet(DishService dishService, ChefService chefService, SpringTemplateEngine springTemplateEngine) {
        this.dishService = dishService;
        this.chefService = chefService;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(request,response);

        WebContext webContext = new WebContext(webExchange);

        String getId = request.getParameter("chefId");

        Long id = Long.parseLong(getId);

        Chef chef = chefService.findById(id);
        webContext.setVariable("dishes",dishService.listDishes());
        webContext.setVariable("chef",chef);
        webContext.setVariable("chedId",getId);

        springTemplateEngine.process("dishesList.html",webContext,response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String getId = request.getParameter("chefId");
        response.sendRedirect("/dish?chefId="+getId);
    }
}