package com.atofighi.bomberman.controllers.common;

import com.atofighi.bomberman.models.units.Bomberman;
import com.atofighi.bomberman.util.Message;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public abstract class Protocol extends Thread {
    protected final Socket socket;
    protected final Scanner scanner;
    protected final PrintWriter printer;
    protected final Object lock = new Object();
    protected GsonBuilder gson = new GsonBuilder();

    public Protocol(Socket socket) throws IOException {
        this.socket = socket;
        printer = new PrintWriter(socket.getOutputStream());
        printer.println("test");
        printer.flush();
        scanner = new Scanner(socket.getInputStream());
        scanner.nextLine();
    }


    protected synchronized void sendMessage(Message message) {
            //System.err.println("sendMessage " + message);
            String json = gson.create().toJson(message);
            printer.println(json);
            printer.flush();
    }

    protected synchronized Message getMessage() {
        Message message = gson.create().fromJson(scanner.nextLine(), Message.class);
        //System.err.println("getMessage " + message);
        return message;
    }

    public Socket getSocket() {
        return socket;
    }
}
