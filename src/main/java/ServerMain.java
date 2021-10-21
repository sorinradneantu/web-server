import webserver.ServerController;
import webserver.WebServer;

public class ServerMain {

    public static void main(String[] args) {

        int port = 8080; // port
        String websiteFilesPath = "src/main/java/website"; // the path to website files
        String serverStatus = "Stopped"; // initial state -> stopped

        // Server
        WebServer webServer = new WebServer(port,websiteFilesPath,serverStatus);

        // Server Controller
        ServerController serverController = new ServerController(webServer);

        webServer.setServerStatus("Running");

        while (true) {
            serverController.requestHandler();
        }


    }
}
