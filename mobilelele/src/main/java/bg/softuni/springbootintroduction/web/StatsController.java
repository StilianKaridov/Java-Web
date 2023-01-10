package bg.softuni.springbootintroduction.web;

import bg.softuni.springbootintroduction.service.StatsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StatsController {

    private static final String VIEW_AND_OBJECT_NAME = "stats";

    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping("/statistics")
    public ModelAndView statistics() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject(VIEW_AND_OBJECT_NAME, statsService.getStats());
        modelAndView.setViewName(VIEW_AND_OBJECT_NAME);

        return modelAndView;
    }
}
