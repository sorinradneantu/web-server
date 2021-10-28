import exceptions.WrongServerException;
import org.junit.BeforeClass;
import org.junit.Test;
import webserver.ServerController;
import webserver.WebServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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


}
