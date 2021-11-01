import exceptions.WrongServerException;
import org.junit.Test;
import webserver.ServerController;
import webserver.WebServer;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import static org.junit.Assert.assertTrue;

public class ServerControllerTest {

    private int port8080 = 8080;
    private String websiteFilePath = "src/main/java/website";
    private ArrayList<String> status = new ArrayList<String>(Arrays.asList("Stopped","Running","Maintenance"));


    @Test
    public void testNewServerSocketOk() throws WrongServerException {

        WebServer webServer = new WebServer(port8080,websiteFilePath,status.get(0));
        webServer.setServerStatus(status.get(1));

        try {
            ServerSocket socket = ServerController.newServerSocket(port8080);

            assertTrue(socket.isBound());

            socket.close();
        }catch(BindException e) {
            Assertions.fail(e);
        }catch(IOException b) {
            Assertions.fail(b);
        }

    }

    @Test(expected = IllegalArgumentException.class)
    public void testNewServerSocketPortNotOk() throws WrongServerException, BindException {
        WebServer webServer = new WebServer(port8080, websiteFilePath, status.get(0));
        webServer.setServerStatus(status.get(1));


        ServerSocket socket = ServerController.newServerSocket(65536); // the port must be between 0 and 65535

    }


    @Test
    public void clientHandlerOk() throws WrongServerException, IOException {
        WebServer webServer = new WebServer(port8080,websiteFilePath,status.get(0));
        ServerController serverController = new ServerController(webServer);

        ServerSocket serverSocket = new ServerSocket(port8080);

        Socket clientSocket = serverSocket.accept();

        serverController.clientHandler(clientSocket);

    }

    @Test
    public void clientHandlerNotOk() throws WrongServerException {
        WebServer webServer = new WebServer(port8080,websiteFilePath,status.get(0));
        ServerController serverController = new ServerController(webServer);

        serverController.clientHandler(null);
        // exception thrown -> Null client object was given
    }

    @Test
    public void requestHandlerServerStoppedTest() throws WrongServerException {

        WebServer firstWebServer = new WebServer(port8080,websiteFilePath,status.get(0));

        ServerController firstServerController = new ServerController(firstWebServer);

        firstServerController.requestHandler();


    }

    @Test
    public void requestHandlerServerRunningTest() throws WrongServerException {

        WebServer webserver = new WebServer(port8080,websiteFilePath,status.get(0));
        webserver.setServerStatus(status.get(1));

        ServerController firstServerController = new ServerController(webserver);

        firstServerController.requestHandler();


    }

    @Test
    public void requestHandlerServerInMaintenanceTest() throws WrongServerException {

        WebServer webserver = new WebServer(port8080,websiteFilePath,status.get(0));
        webserver.setServerStatus(status.get(2));

        ServerController firstServerController = new ServerController(webserver);

        firstServerController.requestHandler();


    }


}