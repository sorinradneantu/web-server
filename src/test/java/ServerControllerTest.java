import exceptions.WrongServerException;
import org.junit.Test;
import webserver.ServerController;
import webserver.WebServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerControllerTest {


    @Test
    public void clientHandlerOk() throws WrongServerException, IOException {
        WebServer webServer = new WebServer(8080,"src/main/java/website","Stopped");
        ServerController serverController = new ServerController(webServer);

        ServerSocket serverSocket = new ServerSocket(8080);

        Socket clientSocket = serverSocket.accept();

        serverController.clientHandler(clientSocket);

    }

    @Test
    public void clientHandlerNotOk() throws WrongServerException {
        WebServer webServer = new WebServer(8080,"src/main/java/website","Stopped");
        ServerController serverController = new ServerController(webServer);

        serverController.clientHandler(null);
        // exception thrown -> Null client object was given
    }

    @Test
    public void requestHandlerServerStoppedTest() throws WrongServerException {

        WebServer firstWebServer = new WebServer(8080,"src/main/java/website","Stopped");

        ServerController firstServerController = new ServerController(firstWebServer);

        firstServerController.requestHandler();


    }

    @Test
    public void requestHandlerServerRunningTest() throws WrongServerException {

        WebServer webserver = new WebServer(8080,"src/main/java/website","Stopped");
        webserver.setServerStatus("Running");

        ServerController firstServerController = new ServerController(webserver);

        firstServerController.requestHandler();


    }

    @Test
    public void requestHandlerServerInMaintenanceTest() throws WrongServerException {

        WebServer webserver = new WebServer(8080,"src/main/java/website","Stopped");
        webserver.setServerStatus("Maintenance");

        ServerController firstServerController = new ServerController(webserver);

        firstServerController.requestHandler();


    }


}