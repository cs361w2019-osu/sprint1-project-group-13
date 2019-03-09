package controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs361.battleships.models.Game;
import cs361.battleships.models.Square;

@SuppressWarnings("WeakerAccess")
public class AttackAction {
    @JsonProperty Game game;
    @JsonProperty Square square;
}
