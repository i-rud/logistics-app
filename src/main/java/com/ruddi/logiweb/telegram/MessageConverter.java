package com.ruddi.logiweb.telegram;

import com.ruddi.logiweb.dto.DriverDto;
import com.ruddi.logiweb.dto.OrderDto;

/**
 * message converter for sending to telegram chat
 * @author Ivan Rud
 */
public class MessageConverter {
    /* Emojis */
    private final static String BOX = "\uD83D\uDCE6";
    private final static String TRUCK = "\uD83D\uDE9A";
    private final static String DRIVER = "\uD83D\uDC68\uD83C\uDFFB";
    private final static String PATH = "\uD83C\uDF0E";
    private final static String ESTIMATED_TIME = "⏰";
    private final static String ROAD_LENGTH = "\uD83C\uDFC1";
    private final static String STATUS = "\uD83D\uDD35";
    private final static String STARTED = "\uD83D\uDFE2";
    private final static String COMPLETED = "✅";

    public static String orderPosted(OrderDto order) {
        return BOX + " *New order" +
                "* _(POSTED)_\n" +
                "-----------------------------\n\n" +
                PATH + " Route: _" + order.getPath() + "_\n" +
                ESTIMATED_TIME + " Estimated time: _" + order.getTripTime() + "h_\n" +
                ROAD_LENGTH + " Distance: _" + order.getDistance() + "km_\n" +
                STATUS + " Status: _" + order.getStatus().getStatus() + "_\n";
    }

    public static String orderTruckAdded(OrderDto order) {
        return BOX + " *Order #" +
                order.getId() +
                "* _(TRUCK ADDED)_\n" +
                "-----------------------------\n\n" +
                PATH + " Route: _" + order.getPath() + "_\n" +
                ESTIMATED_TIME + " Estimated time: _" + order.getTripTime() + "h_\n" +
                ROAD_LENGTH + " Distance: _" + order.getDistance() + "km_\n" +
                STATUS + " Status: _" + order.getStatus().getStatus() + "_\n" +
                TRUCK + " Truck: _" + order.getTruck().getPlate() + "_\n";
    }

    public static String orderDriversAdded(OrderDto order) {
        StringBuilder sb = new StringBuilder(BOX + " *Order #" +
                order.getId() +
                "* _(DRIVERS ADDED)_\n" +
                "-----------------------------\n\n" +
                PATH + " Route: _" + order.getPath() + "_\n" +
                ESTIMATED_TIME + " Estimated time: _" + order.getTripTime() + "h_\n" +
                ROAD_LENGTH + " Distance: _" + order.getDistance() + "km_\n" +
                STATUS + " Status: _" + order.getStatus().getStatus() + "_\n" +
                TRUCK + " Truck: _" + order.getTruck().getPlate() + "_\n");

        sb.append(DRIVER + " Drivers: _");
        for (DriverDto driver : order.getDrivers())
            sb.append(driver.getFirstName() + " " + driver.getSecondName() + " (@"
                    + driver.getTelegram() + "), ");
        sb.delete(sb.length() - 2, sb.length() - 1);
        sb.append("_\n");

        return sb.toString();
    }

    public static String orderStarted(OrderDto order) {
        String buf = orderDriversAdded(order);
        buf = buf.replace("DRIVERS ADDED", "STARTED " + STARTED);
        buf = buf.replace("-----------------------------",
                "-----------------------------\n" +
                        "Route: [Google Maps](http://ruddi-logiweb.herokuapp.com/routes/display?id=" + order.getId() + ")");
        return buf;
    }

    public static String orderCompleted(OrderDto order) {
        String buf = orderDriversAdded(order);
        return buf.replace("DRIVERS ADDED", "COMPLETED " + COMPLETED);
    }

    public static String orderDeleted(int id) {
        return BOX + " *Order #" +
                id +
                "* _(DELETED)_\n";
    }
}
