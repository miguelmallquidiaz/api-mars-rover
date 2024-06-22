package com.miguel.api_mars_rover.service;

import com.miguel.api_mars_rover.entity.Obstacle;

import java.util.List;

public interface ObstacleServiceImp {
    public List<Obstacle> findAll();
    void create(Obstacle obstacle);
}
