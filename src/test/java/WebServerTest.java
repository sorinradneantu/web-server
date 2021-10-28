import exceptions.WrongPortException;
import exceptions.WrongServerException;
import exceptions.WrongStatusException;
import exceptions.WrongWebsitePathException;
import org.junit.BeforeClass;
import org.junit.Test;
import webserver.WebServer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class WebServerTest {

    static WebServer webServer;

    @BeforeClass
    public static void before() throws WrongServerException {
        try{
            webServer = new WebServer(8080,"src/main/java/website","Stopped");
        }catch(WrongServerException e){
            System.out.println("Wrong Server Inputs");
        }
    }

    @Test
    public void checkIfWebServerNotNull(){
        assertNotNull(webServer);
    }

    @Test
    public void testPortOk() throws WrongServerException {
        webServer = new WebServer(8080,"src/main/java/website","Stopped");
    }

    @Test
    public void testPortNotOk() throws WrongServerException {
        webServer = new WebServer(8081,"src/main/java/website","Stopped");
    }

    @Test
    public void testWebsiteFilesPathOk() throws WrongServerException {
        webServer = new WebServer(8080,"src/main/java/website","Stopped");
    }

    @Test
    public void testWebsiteFilesPathNotOk() throws WrongServerException {
        webServer = new WebServer(8080,"src/main/java/webserver","Stopped");
    }

    @Test
    public void testServerStatusOk() throws WrongServerException {
        webServer = new WebServer(8080,"src/main/java/website","Stopped");
    }

    @Test
    public void testServerStatusNotOk() throws WrongServerException {
        webServer = new WebServer(8080,"src/main/java/website","Running");
    }

    // ************************* Not mandatory tests **************************

    // The following tests are not mandatory

    @Test
    public void testGetAndSetPortOk(){
        webServer = new WebServer();
        webServer.setPort(1000);
        assertEquals(1000,webServer.getPort());
    }

    @Test
    public void testGetAndSetServerStatusOk(){
        webServer = new WebServer();
        webServer.setServerStatus("test server status");
        assertEquals("test server status",webServer.getServerStatus());
    }


    @Test
    public void testGetAndSetWebsiteFilesPathOk(){
        webServer = new WebServer();
        webServer.setWebsiteFilesPath("test website path");
        assertEquals("test website path",webServer.getWebsiteFilesPath());
    }

    // ************************ /Not mandatory tests **************************


}
