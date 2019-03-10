package controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs361.battleships.models.Game;

public class MoveAction{
    @JsonProperty private Game game;
    @JsonProperty private int x;
    @JsonProperty private int y;

    public Game getGame(){
        return this.game;
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
}
