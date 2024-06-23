package com.miguel.api_mars_rover.service;

import com.miguel.api_mars_rover.entity.Obstacle;
import com.miguel.api_mars_rover.entity.Rover;
import com.miguel.api_mars_rover.repository.ObstacleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ObstacleService implements ObstacleServiceImp{
    @Autowired
    ObstacleRepository obstacleRepository;
    @Autowired
    RoverServiceImp roverService;

    @Override
    public List<Obstacle> findAll() {
        return obstacleRepository.findAll();
    }

    @Override
    public void create(Obstacle obstacle) {
        // Validar que no se pase de los límites del plano
        if (!isValidPosition(obstacle.getX(), obstacle.getY())) {
            throw new IllegalArgumentException("Coordinates out of bounds. x should be between 0 and 9, y between 0 and 5");
        }

        // Verificar si hay un obstáculo en la misma posición
        if (hasObstacleAt(obstacle.getX(), obstacle.getY())) {
            throw new IllegalArgumentException("An obstacle already exists at this position");
        }

        // Verificar si hay un rover en la posición del obstáculo
        if (roverService.exists() && roverService.get().getX() == obstacle.getX() && roverService.get().getY() == obstacle.getY()) {
            throw new IllegalArgumentException("An obstacle cannot be placed on the rover's position");
        }
        obstacleRepository.save(obstacle);
    }

    private boolean isValidPosition(int x, int y) {
        return x >= Rover.MIN_X_POSITION && x <= Rover.MAX_X_POSITION &&
                y >= Rover.MIN_Y_POSITION && y <= Rover.MAX_Y_POSITION;
    }

    private boolean hasObstacleAt(int x, int y) {
        List<Obstacle> obstacles = obstacleRepository.findAll();
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getX() == x && obstacle.getY() == y) {
                return true;
            }
        }
        return false;
    }
}
