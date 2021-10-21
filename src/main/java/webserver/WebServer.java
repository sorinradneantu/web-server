package webserver;

import exceptions.WrongServerException;

public class WebServer {

    private int port; // port
    private String websiteFilesPath; // the path to website files
    private String serverStatus; // the server's status
    private String request; // handled request

    public WebServer(int port, String websiteFilesPath, String serverStatus) throws WrongServerException {
        this.port = port;
        this.websiteFilesPath = websiteFilesPath;
        this.serverStatus = serverStatus;
        this.request = "";
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getServerStatus() {
        return serverStatus;
    }

    public void setServerStatus(String serverStatus) {
        this.serverStatus = serverStatus;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getWebsiteFilesPath() { return websiteFilesPath; }

    public void setWebsiteFilesPath(String websiteFilesPath) { this.websiteFilesPath = websiteFilesPath; }
}
