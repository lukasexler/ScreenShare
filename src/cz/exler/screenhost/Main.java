package cz.exler.screenhost;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

/**
 * This is a simple Java exercise that tries to
 * meet following requirements:
 * <ul>
 * <li>Simple server that provides hosting of its screen
 *      (a static image taken periodically)
 * <li>There will be a chatting attached
 * </ul>
 * The resulting server is hosted on port 8080 and
 * the index web page is accessible from URI /screenHost/.
 * Example: localhost:8080/screenHost/
 * The goal of this project is to target the bare minimum
 * needed to fulfilling the required functionality.
 * Following is a list of missing/untested features:
 * <ul>
 * <li>Handle unicode char set in chat
 * <li>Register user/limit content - register user and limit access.
 *     (Now a chat name is not checked even for uniqueness)
 * <li>Improve performance
 * <li>Introduce dynamic configuration, or configuration via config file
 *     (currently lot of parameters are hardcoded)
 * <li>...
 * </ul>
 */
public class Main {
    /**
     * Initiates and starts simple HttpServer providing
     * simple screen hosting and chat functionality.
     * The services are provided on port "8080" under
     * URI /screenHost/
     *
     * @param args default "main" arguments - not used
     */
    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
            server.createContext("/screenHost/", new MyHTTPHandler());
            server.setExecutor(null); // creates a default executor
            server.start();
        }
        catch (java.io.IOException e){
            System.out.print("ERROR: " + e.getMessage());
        }
    }
}
