package logistics;

import transport.Transport;

public interface TransportFactory {
    Transport createShip();
    Transport createTruck();
}
