package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.BindException;
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

    public static ServerSocket newServerSocket(int socketPort) throws BindException {

        try {
            ServerSocket newSocket = new ServerSocket(socketPort);
            System.out.println("Server created successfully on port : " + socketPort);
            return newSocket;
        } catch (IllegalArgumentException e) {
            System.out.println("The port must be between 0 and 65535.");
            throw e;
        } catch (BindException b) {
            System.out.println("Port not available.");
            throw b;
        } catch (Exception c) {
            System.out.println("creating server socket failed on port: " + socketPort);
            System.out.println("Exception: " + c);
            return null;
        }

    }

    public static void closeServerSocket(ServerSocket serverSocket) throws NullPointerException {
        try {

            serverSocket.close();
            System.out.println("Closed server socket successfully on port: " + serverSocket.getLocalPort());

        } catch (NullPointerException e) {

            System.out.println("This socket is null.");
            throw e;

        } catch (Exception b) {

            System.out.println("closing server socket failed on port: " + serverSocket.getLocalPort());
            System.out.println("Exception: " + b);

        }
    }

    public static Socket newClientSocket(ServerSocket serverSocket) throws Exception {

        try {
            Socket newClientSocket = acceptSocket(serverSocket);
            System.out.println("client created successfully");
            return newClientSocket;
        } catch (Exception e) {
            System.out.println("creating new client failed");
            System.out.println("Exception : " + e);
            throw e;
        }
    }

    public static void closeClientSocket(Socket clientSocket) throws NullPointerException {
        try {

            clientSocket.close();
            System.out.println("Closed client socket successfully!");

        } catch (NullPointerException e) {

            System.out.println("This client is null.");
            throw e;

        } catch (Exception b) {

            System.out.println("closing client socket failed!");
            System.out.println("Exception: " + b);

        }
    }

    public static Socket acceptSocket(ServerSocket serverSocket) throws Exception {
        try {
            return serverSocket.accept();
        } catch (Exception e) {
            System.out.println("The socket cannot be accepted.");
            System.out.println("Exception: " + e);
            throw e;
        }
    }

    public void requestHandler(){
        try(ServerSocket serverSocket = this.newServerSocket(webServer.getPort())){

            Socket clientSocket = this.newClientSocket(serverSocket);
            clientHandler(clientSocket);
            this.closeClientSocket(clientSocket);
            this.closeServerSocket(serverSocket);

        }catch(IOException e){

            System.err.println("Could not listen on port:" + webServer.getPort());

        } catch (Exception e) {
            e.printStackTrace();
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
                if("/".equals(rawPath) || "/index.html".equals(rawPath)){
                    filePath = Paths.get(webServer.getWebsiteFilesPath(), "index/index.html");
                }else if("/index.css".equals(rawPath)){
                    filePath = Paths.get(webServer.getWebsiteFilesPath(),"index/index.css");
                }else if("/favicon.ico".equals(rawPath)){
                    filePath = Paths.get(webServer.getWebsiteFilesPath(), "favicon.ico");
                }else{
                    filePath = Paths.get(rawPath);
                }

                System.out.println("DEBUG : raw path : " + rawPath); //debug

                if(!filePath.toString().contains("index") && !filePath.toString().contains("favicon")){
                    if(webServer.pagesList.get(0).contains(rawPath.substring(1))) {
                        String aux = webServer.getWebsiteFilesPath() + '/' + "htmlFiles";
                        filePath = Paths.get(aux, rawPath);
                    }else if(webServer.pagesList.get(1).contains(rawPath.substring(1))) {
                        String aux = webServer.getWebsiteFilesPath() + '/' + "htmlFiles" + "/htmlFilesLevel2";
                        filePath = Paths.get(aux, rawPath);
                    }else if(webServer.pagesList.get(2).contains(rawPath.substring(1))) {
                        String aux = webServer.getWebsiteFilesPath() + '/' + "htmlFiles" + "/htmlFilesLevel2" + "/htmlFilesLevel3";
                        filePath = Paths.get(aux, rawPath);
                    }else{
                        filePath = Paths.get(rawPath);
                    }
                }

                System.out.println("DEBUG : file path : "+filePath); //debug

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
                        sendResponse(out, "404 Not Found", contentType, Files.readAllBytes(Paths.get(webServer.getWebsiteFilesPath(), "notfound/notfound.html")));
                    }
                }else if (webServer.getServerStatus().equals("Maintenance")) {
                    if (contentType.contains("html")) {
                        sendResponse(out, "503 Service Unavailable", contentType, Files.readAllBytes(Paths.get(webServer.getWebsiteFilesPath(), "maintenance/maintenance.html")));
                    } else {
                        sendResponse(out, "200 OK", contentType, Files.readAllBytes(filePath));
                    }
                }else  {
                    if (contentType.contains("html")) {
                        sendResponse(out, "503 Service Unavailable", contentType, Files.readAllBytes(Paths.get(webServer.getWebsiteFilesPath(), "serverdown/serverdown.html")));
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
            e.printStackTrace();
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
