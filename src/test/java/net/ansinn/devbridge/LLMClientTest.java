package net.ansinn.devbridge;

import io.helidon.webserver.Routing;
import io.helidon.webserver.WebServer;
import org.junit.jupiter.api.BeforeAll;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LLMClientTest {

    void testStartup() {
        var router = Routing.builder();

        router.get("/version", (req, res) -> {
            req.content().as(String.class).thenAccept(text -> {
                res.send(Map.of("version", "0.1.0"));
            });
        });

        WebServer.builder().routing(router).port(8080).build().start();

    }

}