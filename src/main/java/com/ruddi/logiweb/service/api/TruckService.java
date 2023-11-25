package com.ruddi.logiweb.service.api;

import com.ruddi.logiweb.dto.TruckDto;
import com.ruddi.logiweb.model.Truck;

import java.util.List;

public interface TruckService extends GenericService<Truck, TruckDto>{
    List<TruckDto> getAvailable();
}
