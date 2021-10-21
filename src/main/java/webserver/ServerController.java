package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ServerController {

    private WebServer webServer;

    public ServerController(WebServer webServer) {
        this.webServer = webServer;
    }

    public void requestHandler(){
        try(ServerSocket serverSocket = new ServerSocket(webServer.getPort())){

            Socket clientSocket = serverSocket.accept();
            clientHandler(clientSocket);
            clientSocket.close();

        }catch(IOException e){

            System.err.println("Could not listen on port:" + webServer.getPort());

        }
    }

    public void clientHandler(Socket clientSocket){

        ArrayList<String> inputs = new ArrayList<String>();

        Path filePath;
        String contentType;
        String rawPath;

        try{

            OutputStream out = clientSocket.getOutputStream(); // send response to client
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); // read data from the client

            String inputString;

            while((inputString = in.readLine())!=null){

                // print the input for debug
                System.out.println("Input : " + inputString);

                //adding the lines into array to be easier to handle
                inputs.add(inputString);

                if(inputString.isEmpty()){
                    break;
                }
            }

            //handle the input
            if(inputs.isEmpty()){

            }else{
                rawPath = inputs.get(0).split(" ")[1];

                // Construct the path for file
                if("/".equals(rawPath)){
                    filePath = Paths.get(webServer.getIndexPath(), "index.html");
                }else if("/index.css".equals(rawPath)){
                    filePath = Paths.get(webServer.getIndexPath(),"index.css");
                }else if("favicon.ico".equals(rawPath)){
                    filePath = Paths.get(webServer.getIndexPath(), "favicon.ico");
                }else{
                    filePath = Paths.get(rawPath);
                }

                // extract the content type
                contentType = Files.probeContentType(filePath);

                String tmpRequest = webServer.getRequest() + " " + filePath.toString();
                webServer.setRequest(tmpRequest);

                // server status controller
                if(webServer.getServerStatus().equals("Running")){
                    // if the path exist
                    if(Files.exists(filePath)){
                        sendResponse(out,"200 OK",contentType,Files.readAllBytes(filePath));
                    }else{
                        sendResponse(out, "404 Not Found", contentType, Files.readAllBytes(Paths.get(webServer.getIndexPath(), "/notfound.html")));
                    }
                }else if (webServer.getServerStatus().equals("Maintenance")) {
                    if (contentType.contains("html")) {
                        sendResponse(out, "503 Service Unavailable", contentType, Files.readAllBytes(Paths.get(webServer.getMaintenancePath(), "/maintenance.html")));
                    } else {
                        sendResponse(out, "200 OK", contentType, Files.readAllBytes(filePath));
                    }
                }else {
                    if (contentType.contains("html")) {
                        sendResponse(out, "503 Service Unavailable", contentType, Files.readAllBytes(Paths.get(webServer.getIndexPath(), "/serverdown.html")));
                    } else {
                        sendResponse(out, "200 OK", contentType, Files.readAllBytes(filePath));
                    }
                }

            }

            in.close();
            out.close();

        }catch(NullPointerException e){
            System.err.println("Null client object was given");
        }catch(IOException e){
            System.err.println("Problem with Communication Server");
        }

    }

    // method used for sending response to client
    public void sendResponse(OutputStream out, String status, String contentType, byte[] content) throws IOException {
        out.write(("HTTP/1.1 \r\n" + status).getBytes()); //http response
        out.write(("ContentType: " + contentType + "\r\n").getBytes());
        out.write("\r\n".getBytes());
        out.write(content);
        out.write("\r\n\r\n".getBytes());
    }



}
