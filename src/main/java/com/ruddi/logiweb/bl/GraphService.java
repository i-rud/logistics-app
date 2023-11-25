package com.ruddi.logiweb.bl;

import com.ruddi.logiweb.dto.CountryDto;
import com.ruddi.logiweb.dto.OrderDto;

import java.util.List;

public interface GraphService {
    List<String> getPath(OrderDto order, List<CountryDto> graph);
    int countCost(List<String> path, List<CountryDto> graph);
}
