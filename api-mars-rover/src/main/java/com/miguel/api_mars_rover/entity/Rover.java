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
    @Column(name = "direction")
    private Direction direction;
    @Column(name = "message")
    private String message;

}
