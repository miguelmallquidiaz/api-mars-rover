package com.miguel.api_mars_rover.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table (name = "rover")
public class Rover {
    public static final int MIN_X_POSITION = 0;
    public static final int MAX_X_POSITION = 9;
    public static final int MIN_Y_POSITION = 0;
    public static final int MAX_Y_POSITION = 5;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "posx")
    private int x;

    @Column(name = "posy")
    private int y;

    @Enumerated(EnumType.STRING)
    @Column(name = "direction")
    private Direction direction;

    @Column(name = "message")
    private String message;

    // Métodos para mover el rover y girar
    public void move() {
        switch (direction) {
            case NORTH:
                if (y < MAX_Y_POSITION) y++;
                break;
            case EAST:
                if (x < MAX_X_POSITION) x++;
                break;
            case SOUTH:
                if (y > MIN_Y_POSITION) y--;
                break;
            case WEST:
                if (x > MIN_X_POSITION) x--;
                break;
        }
    }

    public void turnLeft() {
        switch (direction) {
            case NORTH:
                direction = Direction.WEST;
                break;
            case EAST:
                direction = Direction.NORTH;
                break;
            case SOUTH:
                direction = Direction.EAST;
                break;
            case WEST:
                direction = Direction.SOUTH;
                break;
        }
    }

    public void turnRight() {
        switch (direction) {
            case NORTH:
                direction = Direction.EAST;
                break;
            case EAST:
                direction = Direction.SOUTH;
                break;
            case SOUTH:
                direction = Direction.WEST;
                break;
            case WEST:
                direction = Direction.NORTH;
                break;
        }
    }

    public void moveBackwards() {
        switch (direction) {
            case NORTH:
                if (y > MIN_Y_POSITION) y--;
                break;
            case EAST:
                if (x > MIN_X_POSITION) x--;
                break;
            case SOUTH:
                if (y < MAX_Y_POSITION) y++;
                break;
            case WEST:
                if (x < MAX_X_POSITION) x++;
                break;
        }
    }
}
