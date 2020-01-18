package com.company;
import com.company.GameServer;

import java.net.SocketException;
import java.util.ArrayList;

public class GameServersProducer {
    private ArrayList<GameServer> servers = new ArrayList<>();

    public void addServer(String name, int selectedPort, int numberOfPlayers) throws SocketException
    {
        GameServer newGame = new GameServer(name, selectedPort, numberOfPlayers);

        servers.add(newGame);

        Thread t = new Thread(() -> {
            try
            {
                newGame.start();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        });
        t.start();
    }


}
