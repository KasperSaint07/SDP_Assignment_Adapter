package logistics;

import transport.*;

public class UsaFactory implements TransportFactory {
    @Override
    public Transport createShip() {
        return new UsaShip();
    }

    @Override
    public Transport createTruck() {
        return new UsaTruck();
    }
}
