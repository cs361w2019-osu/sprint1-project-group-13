package controllers;

import com.google.inject.Singleton;
import cs361.battleships.models.Game;
import ninja.Context;
import ninja.Result;
import ninja.Results;

@Singleton
public class ApplicationController {

    public Result index() {
        return Results.html();
    }

    public Result newGame() {
        Game g = new Game();
        return Results.json().render(g);
    }

    public Result placeShip(Context context, PlacementAction g) {
        if (g.game.placeShip(g.ally, g.enemy)) {
            return Results.json().render(g.game);
        } else {
            return Results.badRequest();
        }
    }

    public Result attack(Context context, AttackAction g) {
        if (g.game.attack(g.square)) {
            return Results.json().render(g.game);
        } else {
            return Results.badRequest();
        }
    }

    public Result sonar(Context context, AttackAction g) {
        if (g.game.sonar(g.square)) {
            return Results.json().render(g.game);
        } else {
            return Results.badRequest();
        }
    }

    public Result moveFleet(Context context, MoveAction g) {
        if (g.game.moveFleet(g.direction)) {
            return Results.json().render(g.game);
        } else {
            return Results.badRequest();
        }
    }
}
