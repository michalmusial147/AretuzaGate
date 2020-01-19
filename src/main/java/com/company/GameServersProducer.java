package com.company;
import javax.json.*;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonGeneratorFactory;

import com.company.GameServer;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;

public class GameServersProducer {
    private ArrayList<GameServer> servers = new ArrayList<>();

    public void addServer(String name, int selectedPort, int numberOfPlayers) throws SocketException {
        GameServer newGame = new GameServer(name, selectedPort, numberOfPlayers);

        servers.add(newGame);

        Thread t = new Thread(() -> {
            try {
                newGame.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t.start();
    }
    public GameServer getGameServerByName (String name){
        for (GameServer gameServer : this.servers) {
            if (gameServer.name.equals(name)) {
                return gameServer;
            }
        }
        return null;
    }
    public String getGameServersListJson()
    {
        JsonBuilderFactory builderFactory = Json.createBuilderFactory(Collections.emptyMap());
        JsonArrayBuilder GameServerListArrayBuilder = builderFactory.createArrayBuilder();
        JsonObject GameServerObject;
        for (GameServer gs : servers)
        {
            GameServerObject = builderFactory.createObjectBuilder()
                    .add("name", gs.name)
                    .add("listeningPort", gs.listeningPort)
                    .add("maxNumberOfPlayers", gs.maxNumberOfPlayers)
                    .add("NumberOfPlayers", gs.NumberOfPlayers)
                    .build();
            GameServerListArrayBuilder.add(GameServerObject);
        }
        JsonArray GameServerListArray = GameServerListArrayBuilder.build();
        //JsonObject bufer = builderFactory.createObjectBuilder()
              //  .add("name", gs.name);
        return GameServerListArray.toString();
    }

}
