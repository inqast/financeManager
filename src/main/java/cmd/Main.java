package cmd;

import java.net.InetSocketAddress;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpServer;

import http.handler.category.CategoryHandler;
import http.handler.operation.OperationHandler;
import http.handler.user.UserHandler;
import service.CategoryRepo;
import service.OperationRepo;
import service.Service;
import service.UserRepo;


public class Main {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger("Main");

        HttpServer server;
        Service service;

        try {
            String url = System.getenv("DB_URL");
            String user = System.getenv("DB_USER");
            String passwd = System.getenv("DB_PASSWD");

            Connection con = DriverManager.getConnection(url, user, passwd);
            
            UserRepo userRepo = new repo.user.Repo(con);
            CategoryRepo categoryRepo = new repo.category.Repo(con);
            OperationRepo operationRepo = new repo.operation.Repo(con);

            service = new Service(
                MessageDigest.getInstance("SHA-256"),
                userRepo,
                operationRepo,
                categoryRepo
            );

            server = HttpServer.create(new InetSocketAddress(8000), 0);

            server.createContext("/operation", new OperationHandler(service));
            server.createContext("/category", new CategoryHandler(service));
            server.createContext("/user", new UserHandler(service));

            server.setExecutor(null); // creates a default executor
            server.start();

        } catch (Exception e) {
            logger.severe(e.getMessage());
            
            return;
        }
    }
}