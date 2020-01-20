package com.company.model;

import java.util.Collections;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;

public class Player {

    private String playerName;
    private String shipName; //ship1 , ship2
    private float positionX;
    private float positionY;
    private float positionZ;
    private float rotation;
    private boolean isShooting;

    public Player(String playerName, float positionX, float positionY, float positionZ, float rotation, boolean isShooting , String shipName)
    {
        this.playerName = playerName;
        this.positionX = positionX;
        this.positionY = positionY;
        this.positionZ = positionZ;
        this.rotation = rotation;
        this.isShooting = isShooting;
        this.shipName = shipName;
    }
    public String GetPlayerAsJson()
    {
        JsonBuilderFactory builderFactory = Json.createBuilderFactory(Collections.emptyMap());
        JsonObject playerObject = builderFactory.createObjectBuilder()
                .add("playerName", this.playerName)
                .add("positionX", this.positionX)
                .add("positionY", this.positionY)
                .add("positionZ", this.positionZ)
                .add("rotation", this.rotation)
                .add("isShooting", this.isShooting)
                .add("shipName", this.shipName)
                .build();
        return playerObject.toString();
    }


}
