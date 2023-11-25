package com.ruddi.logiweb.dao.api;

import com.ruddi.logiweb.model.Order;
import com.ruddi.logiweb.model.Truck;

public interface OrderDao extends GenericDao<Order>{
    boolean truckInOrder(Truck truck);
}
