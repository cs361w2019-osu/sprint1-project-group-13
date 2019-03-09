package controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs361.battleships.models.Game;
import cs361.battleships.models.ships.Ship;

@SuppressWarnings("WeakerAccess")
public class PlacementAction {
    @JsonProperty Game game;
    @JsonProperty Ship ally;
    @JsonProperty Ship enemy;
}
