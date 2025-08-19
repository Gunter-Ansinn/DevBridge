package net.ansinn.devbridge;

import io.helidon.webserver.Routing;
import io.helidon.webserver.WebServer;
import net.ansinn.devbridge.utils.versioning.VersionHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.time.Duration;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

class LLMClientTest {

    private static WebServer server;
    private static URI base;

    @BeforeAll
    static void startServer() {
        var routing = Routing.builder()
                .get("/version", (req, res) -> {
                    var v = VersionHelper.getVersion();
                    v.ifPresentOrElse(ver ->
                                    res.send(ver.toString()),
                            () -> res.send("No version data found"));
                })
                .build();

        server = WebServer.builder().addRouting(routing).port(8080).build();
        server.start();
        base = URI.create("http://localhost:8080");
    }

    @AfterAll
    static void stopServer() {
        server.shutdown().toCompletableFuture().join();
    }

    @Test
    @Timeout(3)
    void connectToVersion() throws Exception {
        try(var client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build()) {

            var req = HttpRequest.newBuilder(base.resolve("/version")).GET().build();
            var resp = client.send(req, HttpResponse.BodyHandlers.ofString());

            assertEquals(200, resp.statusCode());

            System.out.println(resp.body());

            var body = resp.body().trim();
            assertFalse(body.isBlank());

            if (body.startsWith("Version{")) {
                assertTrue(body.contains("version") || body.contains("title"));
            } else {
                assertTrue(body.contains("No version data found"));
            }
        }
    }

}