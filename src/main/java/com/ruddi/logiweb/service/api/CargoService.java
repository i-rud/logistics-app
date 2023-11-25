package com.ruddi.logiweb.service.api;

import com.ruddi.logiweb.dto.CargoDto;
import com.ruddi.logiweb.model.Cargo;

import java.util.List;

public interface CargoService extends GenericService<Cargo, CargoDto>{
    List<CargoDto> getPrepared();
}
