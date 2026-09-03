import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import model.Booking;
import model.Passenger;

import observer.EmailNotificationObserver;
import observer.PassengerAppObserver;
import observer.ReservationSubject;
import observer.SMSNotificationObserver;

import proxy.ReservationService;
import proxy.ReservationServiceProxy;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import java.net.InetSocketAddress;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;      
import java.nio.file.Paths;

public class WebServer {

        private static final int PORT = 8081;

        private static final String FRONTEND_FOLDER = "frontend";

        // =====================================================
        // MAIN
        // =====================================================

        public static void main(String[] args) throws Exception {

                HttpServer server = HttpServer.create(
                                new InetSocketAddress(PORT),
                                0);

                // =================================================
                // FRONTEND
                // =================================================

                server.createContext(
                                "/",
                                WebServer::serveFrontend);

                // =================================================
                // BOOKING API
                // =================================================

                server.createContext(
                                "/api/book",
                                WebServer::bookTicket);

                server.setExecutor(null);

                System.out.println();
                System.out.println("=================================");
                System.out.println(" Railway Reservation Server");
                System.out.println("=================================");
                System.out.println(
                                "Open: http://127.0.0.1:" + PORT);
                System.out.println("=================================");
                System.out.println();

                server.start();
        }

        // =====================================================
        // SERVE FRONTEND
        // =====================================================

        private static void serveFrontend(
                        HttpExchange exchange) throws IOException {

                String requestPath = exchange.getRequestURI().getPath();

                if (requestPath.equals("/")) {

                        requestPath = "/index.html";
                }

                String fileName = requestPath.substring(1);

                // Security check

                if (fileName.contains("..")) {

                        send404(exchange);

                        return;
                }

                Path filePath = Paths.get(
                                FRONTEND_FOLDER,
                                fileName);

                File file = filePath.toFile();

                if (!file.exists() || !file.isFile()) {

                        send404(exchange);

                        return;
                }

                byte[] content = Files.readAllBytes(filePath);

                String contentType = getContentType(fileName);

                exchange.getResponseHeaders()
                                .set(
                                                "Content-Type",
                                                contentType);

                exchange.sendResponseHeaders(
                                200,
                                content.length);

                try (OutputStream output = exchange.getResponseBody()) {

                        output.write(content);
                }
        }

        // =====================================================
        // CONTENT TYPE
        // =====================================================

        private static String getContentType(
                        String fileName) {

                String lower = fileName.toLowerCase();

                if (lower.endsWith(".html")) {

                        return "text/html; charset=UTF-8";
                }

                if (lower.endsWith(".css")) {

                        return "text/css; charset=UTF-8";
                }

                if (lower.endsWith(".js")) {

                        return "application/javascript; charset=UTF-8";
                }

                if (lower.endsWith(".json")) {

                        return "application/json; charset=UTF-8";
                }

                if (lower.endsWith(".png")) {

                        return "image/png";
                }

                if (lower.endsWith(".jpg") ||
                                lower.endsWith(".jpeg")) {

                        return "image/jpeg";
                }

                if (lower.endsWith(".svg")) {

                        return "image/svg+xml";
                }

                if (lower.endsWith(".ico")) {

                        return "image/x-icon";
                }

                return "application/octet-stream";
        }

        // =====================================================
        // BOOK TICKET
        // =====================================================

        private static void bookTicket(
                        HttpExchange exchange) throws IOException {

                // -------------------------------------------------
                // ONLY POST ALLOWED
                // -------------------------------------------------

                if (!exchange.getRequestMethod()
                                .equalsIgnoreCase("POST")) {

                        sendJson(
                                        exchange,
                                        405,
                                        "{\"success\":false,\"message\":\"POST required\"}");

                        return;
                }

                try {

                        // =================================================
                        // READ JSON FROM FRONTEND
                        // =================================================

                        String json = new String(
                                        exchange.getRequestBody()
                                                        .readAllBytes(),
                                        StandardCharsets.UTF_8);

                        System.out.println();
                        System.out.println(
                                        "========== NEW WEB BOOKING ==========");

                        System.out.println(json);

                        // =================================================
                        // READ VALUES
                        // =================================================

                        String passengerName = getJsonValue(
                                        json,
                                        "passenger_name");

                        String ageString = getJsonValue(
                                        json,
                                        "age");

                        String gender = getJsonValue(
                                        json,
                                        "gender");

                        String mobile = getJsonValue(
                                        json,
                                        "mobile");

                        String email = getJsonValue(
                                        json,
                                        "email");

                        String trainName = getJsonValue(
                                        json,
                                        "train_name");

                        String source = getJsonValue(
                                        json,
                                        "source");

                        String destination = getJsonValue(
                                        json,
                                        "destination");

                        String travelClass = getJsonValue(
                                        json,
                                        "travel_class");

                        String paymentMode = getJsonValue(
                                        json,
                                        "payment_mode");

                        String fareString = getJsonValue(
                                        json,
                                        "fare");

                        // =================================================
                        // BASIC VALIDATION
                        // =================================================

                        if (passengerName.isEmpty()) {

                                sendJson(
                                                exchange,
                                                400,
                                                "{\"success\":false,\"message\":\"Passenger name required\"}");

                                return;
                        }

                        if (ageString.isEmpty()) {

                                sendJson(
                                                exchange,
                                                400,
                                                "{\"success\":false,\"message\":\"Age required\"}");

                                return;
                        }

                        int age = Integer.parseInt(ageString);

                        double fare = fareString.isEmpty()
                                        ? 1500.0
                                        : Double.parseDouble(fareString);

                        // =================================================
                        // CREATE PASSENGER OBJECT
                        // =================================================

                        Passenger passenger = new Passenger(
                                        passengerName,
                                        age,
                                        gender,
                                        mobile,
                                        email);

                        // =================================================
                        // CREATE BOOKING OBJECT
                        // =================================================

                        Booking booking = new Booking(
                                        passenger,
                                        trainName,
                                        source,
                                        destination,
                                        travelClass,
                                        paymentMode,
                                        fare);

                        System.out.println();
                        System.out.println(
                                        "[WebServer] Booking object created:");

                        System.out.println(booking);

                        // =================================================
                        // OBSERVER PATTERN
                        // =================================================

                        ReservationSubject subject = new ReservationSubject();

                        subject.attach(
                                        new PassengerAppObserver());

                        subject.attach(
                                        new SMSNotificationObserver());

                        subject.attach(
                                        new EmailNotificationObserver());

                        // =================================================
                        // PROXY PATTERN
                        // =================================================

                        ReservationService reservationService = new ReservationServiceProxy(subject);

                        System.out.println();
                        System.out.println(
                                        "[WebServer] Sending booking to Proxy...");

                        // =================================================
                        // RESERVATION
                        //
                        // Proxy
                        // ↓
                        // Chain of Responsibility
                        // ↓
                        // RealReservationService
                        // ↓
                        // Singleton Database
                        // ↓
                        // SQLite
                        //
                        // Then Observer notification
                        // =================================================

                        boolean success = reservationService.reserve(booking);

                        // =================================================
                        // SUCCESS
                        // =================================================

                        if (success) {

                                int pnr = booking.getPnr();

                                System.out.println();
                                System.out.println(
                                                "=================================");

                                System.out.println(
                                                "       BOOKING SUCCESSFUL");

                                System.out.println(
                                                "=================================");

                                System.out.println(
                                                "PNR       : " + pnr);

                                System.out.println(
                                                "Passenger : " +
                                                                booking.getPassenger().getName());

                                System.out.println(
                                                "Train     : " +
                                                                booking.getTrainName());

                                System.out.println(
                                                "Route     : " +
                                                                booking.getSource() +
                                                                " -> " +
                                                                booking.getDestination());

                                System.out.println(
                                                "Class     : " +
                                                                booking.getTravelClass());

                                System.out.println(
                                                "Fare      : Rs." +
                                                                booking.getFare());

                                System.out.println(
                                                "Status    : " +
                                                                booking.getStatus());

                                System.out.println(
                                                "=================================");

                                // =================================================
                                // SEND PNR TO FRONTEND
                                // =================================================

                                String response = "{"
                                                + "\"success\":true,"
                                                + "\"pnr\":" + pnr + ","
                                                + "\"message\":\"Booking successful\""
                                                + "}";

                                sendJson(
                                                exchange,
                                                200,
                                                response);

                        } else {

                                // =================================================
                                // BOOKING REJECTED
                                // =================================================

                                System.out.println();
                                System.out.println(
                                                "[WebServer] Booking rejected by validation chain.");

                                String response = "{"
                                                + "\"success\":false,"
                                                + "\"message\":\"Booking rejected by validation chain\""
                                                + "}";

                                sendJson(
                                                exchange,
                                                400,
                                                response);
                        }

                } catch (Exception e) {

                        e.printStackTrace();

                        String message = e.getMessage();

                        if (message == null) {

                                message = "Unknown server error";
                        }

                        String response = "{"
                                        + "\"success\":false,"
                                        + "\"message\":\""
                                        + escapeJson(message)
                                        + "\""
                                        + "}";

                        sendJson(
                                        exchange,
                                        500,
                                        response);
                }
        }

        // =====================================================
        // SIMPLE JSON READER
        // =====================================================

        private static String getJsonValue(
                        String json,
                        String key) {

                String search = "\"" + key + "\"";

                int keyPosition = json.indexOf(search);

                if (keyPosition == -1) {

                        return "";
                }

                int colon = json.indexOf(
                                ":",
                                keyPosition);

                if (colon == -1) {

                        return "";
                }

                int start = colon + 1;

                while (start < json.length()
                                &&
                                Character.isWhitespace(
                                                json.charAt(start))) {

                        start++;
                }

                // =================================================
                // STRING
                // =================================================

                if (start < json.length()
                                &&
                                json.charAt(start) == '"') {

                        start++;

                        StringBuilder value = new StringBuilder();

                        boolean escaped = false;

                        for (int i = start; i < json.length(); i++) {

                                char c = json.charAt(i);

                                if (escaped) {

                                        value.append(c);

                                        escaped = false;

                                } else if (c == '\\') {

                                        escaped = true;

                                } else if (c == '"') {

                                        break;

                                } else {

                                        value.append(c);
                                }
                        }

                        return value.toString();
                }

                // =================================================
                // NUMBER / BOOLEAN
                // =================================================

                int end = start;

                while (end < json.length()
                                &&
                                json.charAt(end) != ','
                                &&
                                json.charAt(end) != '}') {

                        end++;
                }

                return json.substring(
                                start,
                                end).trim();
        }

        // =====================================================
        // JSON RESPONSE
        // =====================================================

        private static void sendJson(
                        HttpExchange exchange,
                        int status,
                        String response) throws IOException {

                byte[] data = response.getBytes(
                                StandardCharsets.UTF_8);

                exchange.getResponseHeaders()
                                .set(
                                                "Content-Type",
                                                "application/json; charset=UTF-8");

                exchange.sendResponseHeaders(
                                status,
                                data.length);

                try (OutputStream output = exchange.getResponseBody()) {

                        output.write(data);
                }
        }

        // =====================================================
        // 404
        // =====================================================

        private static void send404(
                        HttpExchange exchange) throws IOException {

                String response = "404 - File Not Found: "
                                +
                                exchange.getRequestURI()
                                                .getPath();

                byte[] data = response.getBytes(
                                StandardCharsets.UTF_8);

                exchange.sendResponseHeaders(
                                404,
                                data.length);

                try (OutputStream output = exchange.getResponseBody()) {

                        output.write(data);
                }
        }

        // =====================================================
        // ESCAPE JSON
        // =====================================================

        private static String escapeJson(
                        String value) {

                if (value == null) {

                        return "Unknown error";
                }

                return value
                                .replace("\\", "\\\\")
                                .replace("\"", "\\\"")
                                .replace("\n", " ")
                                .replace("\r", " ");
        }
}