import exceptions.WrongServerException;
import io.cucumber.java.sl.In;
import webserver.ServerController;
import webserver.WebServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import static java.lang.Thread.sleep;

public class GuiMain extends JFrame {

    private JPanel mainPanel;
    private JLabel currentStatusTextView;
    private JLabel currentStatusLable;
    private JLabel Title;
    private JButton startButton;
    private JButton stopButton;
    private JButton maintenanceButton;
    private JLabel portTextView;
    private JLabel portLabel;
    private JLabel hostnameTextView;
    private JLabel hostnameLabel;
    private JTextField newPortField;
    private JButton changePortButton;
    private JLabel filepathTextView;
    private JLabel rootPathLabel;
    private JTextField newRootPathLabel;
    private JButton changeRootPathButton;

    private static WebServer webServer;
    private static ServerController serverController;

    public GuiMain(String title) throws WrongServerException, UnknownHostException {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        this.setSize(850  ,750);
        this.setResizable(false);

        this.portLabel.setText(Integer.toString(webServer.getPort()));
        this.rootPathLabel.setText(webServer.getWebsiteFilesPath());
        this.currentStatusLable.setText(webServer.getServerStatus());
        this.hostnameLabel.setText(String.valueOf(InetAddress.getLocalHost()));

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                webServer.setServerStatus("Running");
                currentStatusLable.setText(webServer.getServerStatus());
                JOptionPane.showMessageDialog(null, "The server is Running !");
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                webServer.setServerStatus("Stopped");
                currentStatusLable.setText(webServer.getServerStatus());
                JOptionPane.showMessageDialog(null, "The server was Stopped !");
            }
        });

        maintenanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                webServer.setServerStatus("Maintenance");
                currentStatusLable.setText(webServer.getServerStatus());
                JOptionPane.showMessageDialog(null, "The server is in maintanance !");
            }
        });

        changePortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(webServer.getServerStatus().equals("Maintenance") || webServer.getServerStatus().equals("Stopped")){
                    if(!newPortField.getText().isEmpty()){
                        if(Pattern.matches("[0-9]+", newPortField.getText()) == true) {
                            webServer.setPort(Integer.parseInt(newPortField.getText()));
                            portLabel.setText(Integer.toString(webServer.getPort()));
                            JOptionPane.showMessageDialog(null, "The port was changed !");
                        }else{
                            JOptionPane.showMessageDialog(null, "Bad input !");
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "Empty Field !");
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "To change the port, the server must be stopped or in the maintenance mode !");
                }
            }
        });

        changeRootPathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (webServer.getServerStatus().equals("Maintenance") || webServer.getServerStatus().equals("Stopped")) {
                    if (!newRootPathLabel.getText().isEmpty()) {
                        webServer.setWebsiteFilesPath(newRootPathLabel.getText());
                        rootPathLabel.setText(webServer.getWebsiteFilesPath());
                        JOptionPane.showMessageDialog(null, "The root path was changed !");
                    } else {
                        JOptionPane.showMessageDialog(null, "Empty Field !");
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "To change the root path, the server must be stopped or in the maintenance mode !");
                }
            }
        });
    }

    public static void main(String[] args) throws WrongServerException, UnknownHostException {
        serverMainAction();
        JFrame frame = new GuiMain("VVS: WebServer Controller");
        frame.setVisible(true);
        while(true) {
            serverController.requestHandler();
        }
    }

    public static void serverMainAction() throws WrongServerException {
        int port = 8080; // port
        String websiteFilesPath = "src/main/java/website"; // the path to website files
        String serverStatus = "Stopped"; // initial state -> stopped

        // Server
        webServer = new WebServer(port,websiteFilesPath,serverStatus);
        ArrayList<String> level1 = new ArrayList<String>();
        ArrayList<String> level2 = new ArrayList<String>();
        ArrayList<String> level3 = new ArrayList<String>();
        webServer.addPageLevel(level1);
        webServer.addPageLevel(level2);
        webServer.addPageLevel(level3);
        webServer.addPageAtLevel("page1.html",0); // 0 means level1
        webServer.addPageAtLevel("page2.html",0); // 0 means level1
        webServer.addPageAtLevel("page3.html",0); // 0 means level1
        webServer.addPageAtLevel("page4.html",1); // 1 means level2
        webServer.addPageAtLevel("page5.html",2); // 2 means level3
        webServer.addPageAtLevel("pageWithStyle.html",0); // 0 means level1
        webServer.addPageAtLevel("pageWithStyle.css",0); // 0 means level1
        webServer.addPageAtLevel("upt.jpg",0); // 0 means level1
        // Server Controller
        serverController = new ServerController(webServer);
    }

}
