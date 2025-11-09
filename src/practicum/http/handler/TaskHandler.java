package practicum.http.handler;

import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.time.Duration;
import java.time.LocalDateTime;

import com.google.gson.Gson;
import practicum.http.adapter.DurationTypeAdapter;
import practicum.http.adapter.LocalDateTimeTypeAdapter;

import static practicum.http.HttpTaskServer.manager;

public class TaskHandler extends BaseHttpHandler implements HttpHandler {

    Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
            .registerTypeAdapter(Duration.class, new DurationTypeAdapter())
            .create();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        /*String response;
        String method = httpExchange.getRequestMethod();
        StringBuilder jsonTaskExample = new StringBuilder();*/
        /*switch (method) {
            case "GET":
                *//*manager.getTaskCollection().values()
                        .forEach(task -> {
                            jsonTaskExample.append(gson.toJson(task));
                        });*//*
                response = gson.toJson(manager.getTaskCollection().values());
                sendText(httpExchange, response);
                break;
            default:
                httpExchange.sendResponseHeaders(404, 0);
                httpExchange.getResponseBody().close();
                return;
        }*/
        String method = httpExchange.getRequestMethod();
        switch (method) {
            case "GET":
                String response = gson.toJson(manager.getTaskCollection().values());
                sendText(httpExchange, response);
                break;
        }



        /*try {
            String method = httpExchange.getRequestMethod();

            if ("GET".equals(method)) {
                var tasks = manager.getTaskCollection().values();
                String response = gson.toJson(tasks);
                sendText(httpExchange, response);
            } else {
                httpExchange.sendResponseHeaders(405, -1);
            }

        } catch (Exception e) {
            e.printStackTrace();
            String error = "{\"error\":\"" + e.getMessage() + "\"}";
            sendText(httpExchange, error);
        }*/

        //httpExchange.sendResponseHeaders(200, 0);

       /* try (OutputStream os = httpExchange.getResponseBody()) {
            //String response = jsonTaskExample.toString();
            os.write(response.getBytes());
        }*/

    }
}
