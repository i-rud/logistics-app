package com.ruddi.logiweb.service.api;

import com.ruddi.logiweb.dto.DriverDto;
import com.ruddi.logiweb.model.Driver;

import java.util.List;

public interface DriverService extends GenericService<Driver, DriverDto> {
    List<DriverDto> getResting();
    void refresh();
}
