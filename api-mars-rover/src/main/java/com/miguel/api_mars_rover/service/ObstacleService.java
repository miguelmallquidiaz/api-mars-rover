package com.miguel.api_mars_rover.service;

import com.miguel.api_mars_rover.entity.Obstacle;
import com.miguel.api_mars_rover.repository.ObstacleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ObstacleService implements ObstacleServiceImp{
    @Autowired
    ObstacleRepository obstacleRepository;

    @Override
    public List<Obstacle> findAll() {
        return obstacleRepository.findAll();
    }

    @Override
    public void create(Obstacle obstacle) {
        obstacleRepository.save(obstacle);
    }
}
