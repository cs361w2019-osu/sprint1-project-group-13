package controllers;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("WeakerAccess")
public class PlacementAction extends GameAction {
    @JsonProperty int size;
    @JsonProperty boolean vertical;
}
