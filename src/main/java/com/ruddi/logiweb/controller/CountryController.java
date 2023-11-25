package com.ruddi.logiweb.controller;

import com.ruddi.logiweb.dto.CityDto;
import com.ruddi.logiweb.dto.CountryDto;
import com.ruddi.logiweb.service.api.CityService;
import com.ruddi.logiweb.service.api.CountryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * controller for distances table
 * @author Ivan Rud
 */
@Controller
@Slf4j
public class CountryController {

    /**
     * service for working with distances table
     */
    @Autowired
    CountryService countryService;

    /**
     * service for working with city table
     */
    @Autowired
    CityService cityService;

    private static final String REDIRECT_PATH = "redirect:/cities";

    /**
     * page with all cities
     * @param model frontend model
     * @return jsp appearence
     */
    @GetMapping("/cities")
    public String citiesStart(Model model) {

        model.addAttribute("routes", countryService.getAll());
        model.addAttribute("cities", cityService.getAll());
        model.addAttribute("newCity", new CityDto());
        model.addAttribute("newRoute", new CountryDto());

        log.info("/cities was requested");

        return "citiesroutes";
    }

    /**
     * adding distance pair request url
     * @param country entity to add
     * @return jsp appearence
     */
    @PostMapping("/cities/add")
    public String citiesAdd(@ModelAttribute CountryDto country) {
        countryService.save(country);

        log.info("/cities/add was requested");

        return REDIRECT_PATH;
    }

    /**
     * adding city request url
     * @param city entity to add
     * @return jsp appearence
     */
    @PostMapping("/cities/newcity")
    public String cityAdd(@ModelAttribute CityDto city) {

        cityService.save(city);

        log.info("/cities/newcity was requested");

        return REDIRECT_PATH;
    }

    /**
     * deleting entity request url
     * @param id entity id to delete
     * @return jsp appearence
     */
    @GetMapping("/cities/delete")
    public String deleteRoute(@RequestParam int id) {

        countryService.remove(id);
        log.info("/cities/delete was requested");

        return REDIRECT_PATH;
    }

    /**
     * entity edit request url
     * @param country distance pair to edit
     * @return jsp appearence
     */
    @PostMapping("/cities/edit")
    public String citiesUpdate(@ModelAttribute CountryDto country) {

        countryService.update(country);
        log.info("/cities/edit was requested");

        return REDIRECT_PATH;
    }
}
