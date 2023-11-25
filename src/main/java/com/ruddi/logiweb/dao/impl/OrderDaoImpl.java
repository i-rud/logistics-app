package com.ruddi.logiweb.dao.impl;

import com.ruddi.logiweb.dao.api.OrderDao;
import com.ruddi.logiweb.model.Order;
import com.ruddi.logiweb.model.Truck;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDaoImpl extends GenericDaoImpl<Order> implements OrderDao {
    public OrderDaoImpl() {
        init(Order.class);
    }

    @Override
    public boolean truckInOrder(Truck truck) {
        return entityManager.createQuery(
                "select a from Order a where a.truck.id = " + truck.getId()).getResultList().isEmpty();
    }
}
