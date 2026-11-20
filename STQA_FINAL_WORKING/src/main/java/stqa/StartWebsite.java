package stqa;

import com.sun.net.httpserver.HttpServer;
import java.awt.Desktop;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Executors;

public class StartWebsite {
    public static void main(String[] args) throws Exception {
        Path siteFolder = Path.of("src", "main", "resources", "site").toAbsolutePath();

        if (!Files.exists(siteFolder.resolve("index.html"))) {
            System.out.println("Website files not found.");
            System.out.println("Open the STQA_FINAL_WORKING folder (the one containing pom.xml) in VS Code.");
            return;
        }

        HttpServer server;
        int port;
        try {
            port = 8080;
            server = HttpServer.create(new InetSocketAddress("127.0.0.1", port), 0);
        } catch (java.net.BindException e) {
            port = 8081;
            server = HttpServer.create(new InetSocketAddress("127.0.0.1", port), 0);
        }

        server.createContext("/", exchange -> {
            try {
                String requestPath = exchange.getRequestURI().getPath();
                if (requestPath.equals("/")) requestPath = "/index.html";

                Path requestedFile = siteFolder.resolve(requestPath.substring(1)).normalize();
                if (!requestedFile.startsWith(siteFolder) || !Files.exists(requestedFile) || Files.isDirectory(requestedFile)) {
                    byte[] notFound = "404 - File not found".getBytes();
                    exchange.sendResponseHeaders(404, notFound.length);
                    exchange.getResponseBody().write(notFound);
                    exchange.close();
                    return;
                }

                String fileName = requestedFile.getFileName().toString();
                String contentType = "text/plain; charset=UTF-8";
                if (fileName.endsWith(".html")) contentType = "text/html; charset=UTF-8";
                else if (fileName.endsWith(".css")) contentType = "text/css; charset=UTF-8";
                else if (fileName.endsWith(".js")) contentType = "application/javascript; charset=UTF-8";

                byte[] content = Files.readAllBytes(requestedFile);
                exchange.getResponseHeaders().set("Content-Type", contentType);
                exchange.getResponseHeaders().set("Cache-Control", "no-cache");
                exchange.sendResponseHeaders(200, content.length);
                exchange.getResponseBody().write(content);
                exchange.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                exchange.close();
            }
        });

        server.setExecutor(Executors.newCachedThreadPool());
        server.start();

        String url = "http://localhost:" + port;
        System.out.println();
        System.out.println("==============================================");
        System.out.println(" ShopEase India is running successfully!");
        System.out.println(" Website: " + url);
        System.out.println(" Username: student");
        System.out.println(" Password: shop123");
        System.out.println(" Keep this terminal open while using the site.");
        System.out.println(" Press Ctrl+C to stop the website.");
        System.out.println("==============================================");

        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(URI.create(url));
        }
    }
}
