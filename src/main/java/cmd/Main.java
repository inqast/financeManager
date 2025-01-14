package cmd;

import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpServer;

import service.Service;


public class Main {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger("Main");

        HttpServer server;
        Connection con;

        try {
            String url = System.getenv("DB_URL");
            String user = System.getenv("DB_USER");
            String passwd = System.getenv("DB_PASSWD");

            con = DriverManager.getConnection(url, user, passwd);

            server = HttpServer.create(new InetSocketAddress(8000), 0);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            
            return;
        }
        
        Service service = new Service();
    }
}