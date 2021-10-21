import exceptions.WrongServerException;
import org.junit.BeforeClass;
import org.junit.Test;
import webserver.WebServer;

import static org.junit.Assert.assertNotNull;

public class WebServerTest {

    static WebServer webServer;

    @BeforeClass
    public static void before() throws WrongServerException {
        try{
            webServer = new WebServer(8080,"src/main/java/websites","Stopped");
        }catch(WrongServerException e){
            System.out.println("Wrong Server Inputs");
        }
    }

    @Test
    public void checkIfWebServerNotNull(){
        assertNotNull(webServer);
    }


}
