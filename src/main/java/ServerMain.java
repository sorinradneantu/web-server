import exceptions.WrongServerException;
import webserver.ServerController;
import webserver.WebServer;

import java.util.ArrayList;

public class ServerMain {

    private static WebServer webServer;

    public static void main(String[] args) throws WrongServerException {

        int port = 8080; // port
        String websiteFilesPath = "src/main/java/website"; // the path to website files
        String serverStatus = "Stopped"; // initial state -> stopped

        // Server
        webServer = new WebServer(port,websiteFilesPath,serverStatus);
        ArrayList<String> level1 = new ArrayList<String>();
        ArrayList<String> level2 = new ArrayList<String>();
        ArrayList<String> level3 = new ArrayList<String>();
        webServer.addPageLevel(level1);
        webServer.addPageLevel(level2);
        webServer.addPageLevel(level3);
        webServer.addPageAtLevel("page1.html",0); // 0 means level1
        webServer.addPageAtLevel("page2.html",0); // 0 means level1
        webServer.addPageAtLevel("page3.html",0); // 0 means level1
        webServer.addPageAtLevel("page4.html",1); // 1 means level2
        webServer.addPageAtLevel("page5.html",2); // 2 means level3
        webServer.addPageAtLevel("pageWithStyle.html",0); // 0 means level1
        webServer.addPageAtLevel("pageWithStyle.css",0); // 0 means level1
        webServer.addPageAtLevel("upt.jpg",0); // 0 means level1
        // Server Controller
        ServerController serverController = new ServerController(webServer);

        webServer.setServerStatus("Running");

        while (true) {
            serverController.requestHandler();
        }


    }
}
