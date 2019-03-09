package com.atofighi.bomberman.controllers.server;

import com.atofighi.bomberman.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerListener extends Thread {
    private int port;
    private Server server;

    public ServerListener(Server server, int port) {
        this.server = server;
        this.port = port;
    }

    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (!Thread.interrupted()) {
                Socket socket = serverSocket.accept();

                ServerProtocol protocol = new ServerProtocol(server, socket);
                server.getServerProtocols().add(protocol);
                protocol.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
