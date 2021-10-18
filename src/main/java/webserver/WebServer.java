package webserver;

public class WebServer {

    private int port; // port
    private String indexPath; // the path to index.html
    private String maintenancePath; // the path to maintanance.html
    private String serverStatus; // the server's status

    public WebServer(int port, String indexPath, String maintenancePath, String serverStatus) {
        this.port = port;
        this.indexPath = indexPath;
        this.maintenancePath = maintenancePath;
        this.serverStatus = serverStatus;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getMaintenancePath() {
        return maintenancePath;
    }

    public void setMaintenancePath(String maintenancePath) {
        this.maintenancePath = maintenancePath;
    }

    public String getServerStatus() {
        return serverStatus;
    }

    public void setServerStatus(String serverStatus) {
        this.serverStatus = serverStatus;
    }

}
