package adapter;

import logistics.*;
import transport.*;
import java.util.Random;

public class RandomTransportAdapter {
    private Random random = new Random();

    public Transport getTransport(String country, String type) {
        TransportFactory factory;

        if (country.equalsIgnoreCase("usa")) {
            factory = new UsaFactory();
        } else {
            factory = new AsiaFactory();
        }

        if (type != null && !type.isEmpty()) {
            if (type.equalsIgnoreCase("ship")) {
                return factory.createShip();
            } else if (type.equalsIgnoreCase("truck")) {
                return factory.createTruck();
            }
        }

        int choice = random.nextInt(2);
        return (choice == 0) ? factory.createShip() : factory.createTruck();
    }
}