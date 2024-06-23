package com.miguel.api_mars_rover.service;

import com.miguel.api_mars_rover.entity.Obstacle;
import com.miguel.api_mars_rover.entity.Rover;
import com.miguel.api_mars_rover.repository.ObstacleRepository;
import com.miguel.api_mars_rover.repository.RoverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoverService implements RoverServiceImp{

    @Autowired
    RoverRepository roverRepository;
    @Autowired
    ObstacleRepository obstacleRepository;

    @Override
    public Rover get() {
        List<Rover> roverList = roverRepository.findAll();
        return roverList.isEmpty() ? null : roverList.get(0);
    }

    @Override
    public void sendCommand(String command) {
        Rover rover = get();
        if (rover == null) {
            throw new IllegalStateException("No rover found");
        }
        String message = null;
        boolean moveSuccess = false;
        for (char cmd : command.toCharArray()) {
            moveSuccess = true;
            switch (cmd) {
                case 'F':
                    moveSuccess = moveRover(rover);
                    break;
                case 'B':
                    moveSuccess = moveRoverBackwards(rover);
                    break;
                case 'R':
                    rover.turnRight();
                    break;
                case 'L':
                    rover.turnLeft();
                    break;
                default:
                    throw new IllegalArgumentException("Comando inválido: " + cmd);
            }
            if (!moveSuccess) {
                message = "¡Obstáculo detectado! Comando abortado.";
                System.out.println(message);
                rover.setMessage(message);
                break;
            }
        }
        if (moveSuccess) {
            rover.setMessage(null);
        }

        roverRepository.save(rover);
    }

    @Override
    public void createRover(Rover rover) {
        if (exists()) {
            throw new IllegalStateException("Rover already exists");
        }
        roverRepository.save(rover);
    }

    @Override
    public boolean exists() {
        return roverRepository.count() > 0;
    }

    private boolean moveRover(Rover rover) {
        int posXInicial = rover.getX();
        int posYInicial = rover.getY();
        System.out.println("Initial position: (" + posXInicial + ", " + posYInicial + ")");

        rover.move();

        if (canMoveTo(rover.getX(), rover.getY())) {
            System.out.println("Final position: (" + rover.getX() + ", " + rover.getY() + ")");
            return true;
        } else {
            rover.setX(posXInicial);
            rover.setY(posYInicial);
            System.out.println("Cannot move in direction " + rover.getDirection() + ". Obstacle found or reached boundary.");
            return false;
        }
    }

    private boolean moveRoverBackwards(Rover rover) {
        int posXInicial = rover.getX();
        int posYInicial = rover.getY();
        System.out.println("Initial position: (" + posXInicial + ", " + posYInicial + ")");

        rover.moveBackwards();

        if (canMoveTo(rover.getX(), rover.getY())) {
            System.out.println("Final position: (" + rover.getX() + ", " + rover.getY() + ")");
            return true;
        } else {
            rover.setX(posXInicial);
            rover.setY(posYInicial);
            System.out.println("Cannot move in direction " + rover.getDirection() + ". Obstacle found or reached boundary.");
            return false;
        }
    }

    private boolean canMoveTo(int newX, int newY) {
        // Check if new position is within bounds
        if (newX < Rover.MIN_X_POSITION || newX > Rover.MAX_X_POSITION ||
                newY < Rover.MIN_Y_POSITION || newY > Rover.MAX_Y_POSITION) {
            System.out.println("Reached the boundary.");
            return false;
        }

        // Check if new position has obstacles
        if (hasObstacleAt(newX, newY)) {
            System.out.println("Obstacle found at (" + newX + ", " + newY + ")");
            return false;
        }

        return true;
    }

    public boolean hasObstacleAt(int x, int y) {
        List<Obstacle> obstacles = obstacleRepository.findAll();
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getX() == x && obstacle.getY() == y) {
                return true;
            }
        }
        return false;
    }
}
