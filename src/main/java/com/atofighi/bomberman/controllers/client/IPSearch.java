package com.atofighi.bomberman.controllers.client;

import com.atofighi.bomberman.Client;
import com.atofighi.bomberman.util.GameStatus;
import com.atofighi.bomberman.views.client.ip_select.IPSearchPanel;

import javax.swing.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class IPSearch implements Runnable {
    private Client app;
    private IPSearchPanel ipSearchPanel = new IPSearchPanel();

    public IPSearch(Client app) {
        this.app = app;

        ipSearchPanel.getSearchButton().addActionListener((e) -> {
            String[] ips = ipSearchPanel.getIpField().getText().split("\\.");
            int port = ipSearchPanel.getPortField().getValue();
            int[] ipLeft, ipRight;
            ipLeft = new int[4];
            ipRight = new int[4];
            for (int i = 0; i < 4; i++) {
                String[] range = ips[i].split("-");
                ipLeft[i] = Integer.parseInt(range[0]);
                if (range.length == 1) {
                    ipRight[i] = ipLeft[i];
                } else {
                    ipRight[i] = Integer.parseInt(range[1]);
                }

            }
            List<String> availableIps = new ArrayList<>();
            for (int i1 = ipLeft[0]; i1 <= ipRight[0]; i1++) {
                for (int i2 = ipLeft[1]; i2 <= ipRight[1]; i2++) {
                    for (int i3 = ipLeft[2]; i3 <= ipRight[2]; i3++) {
                        for (int i4 = ipLeft[3]; i4 <= ipRight[3]; i4++) {
                            String ip = i1 + "." + i2 + "." + i3 + "." + i4;
                            try {
                                Socket socket = new Socket();
                                socket.connect(new InetSocketAddress(ip, port), 10);
                                ClientProtocol clientProtocol = new ClientProtocol(socket);
                                if (clientProtocol.test()) {
                                    availableIps.add(ip);
                                }
                                socket.close();
                            } catch (IOException e1) {
                            }
                        }
                    }
                }
            }

            String ip = (String) JOptionPane.showInputDialog(
                    app.getMainFrame(),
                    "Select IP to connect:",
                    "Connect",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    availableIps.toArray(),
                    "");
            try {
                app.setProtocol(new ClientProtocol(new Socket(ip, port)));

                app.showServerGames();
            } catch (IOException e1) {
                e1.printStackTrace();
            }


        });
        ipSearchPanel.getBackButton().addActionListener((e) -> app.showHome());
    }

    @Override
    public void run() {
        app.getMainFrame().setPanel(ipSearchPanel);
    }

}
