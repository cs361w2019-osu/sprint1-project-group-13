package cs361.battleships.models.ships;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import cs361.battleships.models.Square;

import java.util.List;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)

@JsonSubTypes({
        @Type(value = Minesweeper.class, name = "minesweeper"),
        @Type(value = Destroyer.class, name = "destroyer"),
        @Type(value = Battleship.class, name = "battleship"),
        @Type(value = Submarine.class, name = "Submarine")
})

public abstract class Ship {

    @JsonProperty public Square origin;
    @JsonProperty public boolean vertical;
    @JsonProperty public boolean sunk;
    @JsonProperty public boolean submerged;

    @JsonProperty
    public abstract List<Square> squares();

    @JsonIgnore
    public abstract Square getCaptainsQuarters();

    @JsonIgnore
    public abstract boolean isCaptainsReinforced();

}
