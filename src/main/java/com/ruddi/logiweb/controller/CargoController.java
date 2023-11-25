package com.ruddi.logiweb.controller;

import com.ruddi.logiweb.dto.CargoDto;
import com.ruddi.logiweb.exception.exceptions.EntityDeleteException;
import com.ruddi.logiweb.exception.exceptions.EntityEditException;
import com.ruddi.logiweb.model.CargoStatus;
import com.ruddi.logiweb.service.api.CargoService;
import com.ruddi.logiweb.service.api.CityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * controller for cargo table
 * @author Ivan Rud
 */
@Controller
@Slf4j
public class CargoController {

    /**
     * service for working with cargo table
     */
    @Autowired
    CargoService cargoService;

    /**
     * service for working with city table
     */
    @Autowired
    CityService cityService;

    private static final String REDIRECT_PATH = "redirect:/cargos";

    /**
     * page with all cargos
     * @param model frontend model
     * @return jsp appearence
     */
    @GetMapping("/cargos")
    public String cargoStart(Model model) {

        model.addAttribute("cargoslist", cargoService.getAll());
        model.addAttribute("newCargo", new CargoDto());
        model.addAttribute("cities", cityService.getAll());

        log.info("/cargos was requested");

        return "cargosinfo";
    }

    /**
     * deleting request url
     * @param id cargo id to delete
     * @return jsp appearence
     * @throws EntityDeleteException problems with deleting entity
     */
    @GetMapping("/cargos/delete")
    public String cargoDelete(@RequestParam int id) throws EntityDeleteException {

        log.error("/cargos/delete was requested");

        try {
            cargoService.remove(id);
        } catch (Exception e) {
            throw new EntityDeleteException();
        }

        return REDIRECT_PATH;
    }

    /**
     * adding request page
     * @param cargo entity to add
     * @return jsp appearence
     */
    @PostMapping("/cargos/add")
    public String addCargo(@ModelAttribute CargoDto cargo) {
        cargoService.save(cargo);
        log.info("/cargos/add was requested");
        return REDIRECT_PATH;
    }

    /**
     * edit request page
     * @param cargo entity to edit
     * @return jsp appearence
     * @throws EntityEditException problems with editing entity
     */
    @PostMapping("/cargos/edit")
    public String editCargo(@ModelAttribute CargoDto cargo) throws EntityEditException {
        CargoDto currentCargo = cargoService.find(cargo.getId());

        log.info("/cargos/edit was requested");

        if (currentCargo.getStatus() == CargoStatus.DISPATCHED) {
            throw new EntityEditException();
        } else cargoService.update(cargo);

        return REDIRECT_PATH;
    }
}
