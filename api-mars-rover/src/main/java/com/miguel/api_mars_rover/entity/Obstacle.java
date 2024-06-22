package com.miguel.api_mars_rover.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="obstacle")
public class Obstacle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "posx")
    private int x;
    @Column(name = "posy")
    private int y;
}
