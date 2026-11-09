package com.ejemplo.tmdb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

public class server_api {

    private final conexion_tmdb tmdb;

    private final ObjectMapper mapper = new ObjectMapper();

    public server_api(conexion_tmdb tmdb) {
        this.tmdb = tmdb;
    }

    public void start(int port) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", this::handle);
        server.setExecutor(Executors.newFixedThreadPool(8));
        server.start();
        System.out.println("============================================");
        System.out.println(" Cinemex API escuchando en:");
        System.out.println("   http://localhost:" + port);
        System.out.println("============================================");
    }

    private void handle(HttpExchange ex) throws IOException {
        String path = ex.getRequestURI().getPath();
        String method = ex.getRequestMethod();

        try {
            if (!"GET".equalsIgnoreCase(method)) {
                sendError(ex, 405, "Metodo no permitido");
                return;
            }

            if (path.equals("/") || path.equals("/health")) {
                Map<String, Object> body = new LinkedHashMap<>();
                body.put("servicio", "Cinemex API");
                body.put("status", "ok");
                body.put("endpoints", List.of(
                        "/peliculas",
                        "/peliculas/populares",
                        "/peliculas/cartelera",
                        "/peliculas/proximos-estrenos",
                        "/peliculas/{id}",
                        "/peliculas/{id}/reparto"));
                sendJson(ex, 200, body);
                return;
            }

            if (!path.startsWith("/peliculas")) {
                sendError(ex, 404, "Ruta no encontrada");
                return;
            }

            String resto = path.substring("/peliculas".length());
            if (resto.startsWith("/")) resto = resto.substring(1);
            if (resto.endsWith("/"))   resto = resto.substring(0, resto.length() - 1);

            int page = extraerPage(ex);

            switch (resto) {
                case ""                  -> enviarLista(ex, tmdb.populares(page));
                case "populares"         -> enviarLista(ex, tmdb.populares(page));
                case "cartelera"         -> enviarLista(ex, tmdb.cartelera(page));
                case "proximos-estrenos" -> enviarLista(ex, tmdb.proximosEstrenos(page));
                default -> {
                    String[] partes = resto.split("/");

                    if (partes.length == 1) {
                        int id = parseId(partes[0]);
                        pelicula p = tmdb.detalle(id);
                        String clas = tmdb.clasificacion(id);
                        sendJson(ex, 200, pelicula_dto.from(p, clas));
                        return;
                    }

                    if (partes.length == 2 && "reparto".equals(partes[1])) {
                        int id = parseId(partes[0]);
                        sendJson(ex, 200, tmdb.reparto(id));
                        return;
                    }

                    sendError(ex, 404, "Ruta no encontrada");
                }
            }

        } catch (NumberFormatException e) {
            sendError(ex, 400, "ID de pelicula invalido");
        } catch (tmdb_status e) {
            sendError(ex, e.getStatusCode(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            sendError(ex, 500, "Error interno: " + e.getMessage());
        }
    }

    private int parseId(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("ID invalido: " + s);
        }
    }

    private int extraerPage(HttpExchange ex) {
        String q = ex.getRequestURI().getQuery();
        if (q != null) {
            for (String par : q.split("&")) {
                if (par.startsWith("page=")) {
                    try { return Math.max(1, Integer.parseInt(par.substring(5))); }
                    catch (NumberFormatException ignored) { }
                }
            }
        }
        return 1;
    }

    private void enviarLista(HttpExchange ex, tmdb_page pagina) throws IOException {
        List<pelicula_dto> dtos = pagina.getResults().stream()
                .map(pelicula_dto::from)
                .toList();

        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("pagina", pagina.getPage());
        respuesta.put("totalPaginas", pagina.getTotalPages());
        respuesta.put("totalResultados", pagina.getTotalResults());
        respuesta.put("peliculas", dtos);

        sendJson(ex, 200, respuesta);
    }

    private void sendJson(HttpExchange ex, int status, Object body) throws IOException {
        ex.getResponseHeaders().add("Content-Type", "application/json; charset=utf-8");
        ex.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        byte[] bytes = mapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(body);
        ex.sendResponseHeaders(status, bytes.length);
        try (OutputStream os = ex.getResponseBody()) {
            os.write(bytes);
        }
    }

    private void sendError(HttpExchange ex, int status, String mensaje) throws IOException {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", status);
        body.put("error", mensaje);
        sendJson(ex, status, body);
    }

    public static void main(String[] args) throws Exception {
        int puerto = 8080;
        if (args.length > 0) {
            puerto = Integer.parseInt(args[0]);
        }

        conexion_tmdb tmdb = new conexion_tmdb();
        server_api srv = new server_api(tmdb);

        Runtime.getRuntime().addShutdownHook(new Thread(() ->
                System.out.println("\nApagando servidor...")));

        srv.start(puerto);
    }
}
