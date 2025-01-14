package http.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import domain.exception.AccessException;
import domain.exception.NotFoundException;

public abstract class Handler implements HttpHandler {
    protected final String emptyResult = "";

    protected abstract String handleMethods(HttpExchange t) throws Exception;

    @Override
    public void handle(HttpExchange t) throws IOException {
        try {
            processOK(t, handleMethods(t));
        } catch (NotFoundException e) {
            processNotFound(t);
        } catch (AccessException e) {
            processAcessDenied(t);
        } catch (Exception e) {
            processError(t, e.getMessage());
        }
    }

    protected static String getPathParam(URI uri, int position) {
        String[] parts = uri.getPath().split("/");

        if (parts.length > position) {
            return parts[position];
        }

        return "";
    }

    protected static void processOK(HttpExchange t, String responseString)  throws IOException {
        byte[] response = responseString.getBytes(StandardCharsets.UTF_8);

        t.getResponseHeaders().add("Content-Type", "application/json; charset=utf-8");
        t.sendResponseHeaders(200, response.length);

        OutputStream os = t.getResponseBody();
        os.write(response);
        os.close();
    }

    protected static void processNotFound(HttpExchange t)  throws IOException {
        t.sendResponseHeaders(404, 0);

        OutputStream os = t.getResponseBody();
        os.close();
    }

    protected static void processAcessDenied(HttpExchange t)  throws IOException {
        t.sendResponseHeaders(403, 0);

        OutputStream os = t.getResponseBody();
        os.close();
    }

    protected static void processInvalidRequest(HttpExchange t)  throws IOException {
        t.sendResponseHeaders(400, 0);

        OutputStream os = t.getResponseBody();
        os.close();
    }

    protected static void processError(HttpExchange t, String errorString)  throws IOException {
        byte[] response = new JSONObject(new Error(errorString)).toString().getBytes(StandardCharsets.UTF_8);

        t.getResponseHeaders().add("Content-Type", "application/json; charset=utf-8");
        t.sendResponseHeaders(500, response.length);

        OutputStream os = t.getResponseBody();
        os.write(response);
        os.close();
    }

    protected static String readBody(HttpExchange t) throws IOException {
        InputStreamReader isr =  new InputStreamReader(t.getRequestBody(),"utf-8");
        BufferedReader br = new BufferedReader(isr);

        int b;
        StringBuilder buf = new StringBuilder(512);
        while ((b = br.read()) != -1) {
            buf.append((char) b);
        }

        br.close();
        isr.close();

        return buf.toString();
    }

    protected static Date getCurrentDate() {
        return DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
    }

    public static class Error {
        private String message;

        public Error(String message) {
            this.message = message;
        }

        public String getMessage() {
            return this.message;
        }
    }


}
