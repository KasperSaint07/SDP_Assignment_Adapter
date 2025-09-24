package logistics;

import transport.*;

public class AsiaFactory implements TransportFactory {
    @Override
    public Transport createShip() {
        return new AsiaShip();
    }

    @Override
    public Transport createTruck() {
        return new AsiaTruck();
    }
}
