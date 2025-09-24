import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import adapter.RandomTransportAdapter;
import transport.Transport;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class WebServer {
    public static void main(String[] args) throws IOException {
        // запускаем сервер на порту 8080
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/transport", new TransportHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Server started at http://localhost:8080/transport");
    }

    static class TransportHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            String query = exchange.getRequestURI().getQuery(); // country=asia&type=ship
            String response;

            if (query == null) {
                response = "Error: no parameters!";
            } else {
                String[] params = query.split("&");
                String country = "";
                String type = "";

                for (String param : params) {
                    if (param.startsWith("country=")) {
                        country = param.split("=")[1];
                    } else if (param.startsWith("type=")) {
                        type = param.split("=")[1];
                    }
                }

                RandomTransportAdapter adapter = new RandomTransportAdapter();
                Transport transport = adapter.getTransport(country, type);

                response = transport.deliver();
            }
            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }
}