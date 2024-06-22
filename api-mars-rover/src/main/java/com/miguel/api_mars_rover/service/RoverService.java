package com.miguel.api_mars_rover.service;

import com.miguel.api_mars_rover.entity.Direction;
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
                    moveSuccess = moveRover(rover, true);
                    break;
                case 'B':
                    moveSuccess = moveRover(rover, false);
                    break;
                case 'R':
                    turnRover(rover, true);
                    break;
                case 'L':
                    turnRover(rover, false);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid command: " + cmd);
            }
            if (!moveSuccess) {
                message = "Obstacle detected! Command aborted.";
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
        roverRepository.save(rover);
    }

    private void turnRover(Rover rover, boolean isRight) {
        Direction direction = rover.getDirection();
        Direction finalDirection = null;

        if (isRight) {
            switch (direction) {
                case NORTH:
                    finalDirection = Direction.EAST;
                    break;
                case EAST:
                    finalDirection = Direction.SOUTH;
                    break;
                case SOUTH:
                    finalDirection = Direction.WEST;
                    break;
                case WEST:
                    finalDirection = Direction.NORTH;
                    break;
            }
        } else {
            switch (direction) {
                case NORTH:
                    finalDirection = Direction.WEST;
                    break;
                case WEST:
                    finalDirection = Direction.SOUTH;
                    break;
                case SOUTH:
                    finalDirection = Direction.EAST;
                    break;
                case EAST:
                    finalDirection = Direction.NORTH;
                    break;
            }
        }
        rover.setDirection(finalDirection);
    }

    private boolean moveRover(Rover rover, boolean isForward) {
        int posXInicial = rover.getX();
        int posYInicial = rover.getY();
        System.out.println("Initial position: (" + posXInicial + ", " + posYInicial + ")");

        Direction direction = rover.getDirection();
        int moveValue = isForward ? direction.getValue() : -direction.getValue();

        int posXFinal = posXInicial;
        int posYFinal = posYInicial;

        // Calculate new positions if movement is possible
        if (canMoveTo(posXInicial, posYInicial, moveValue, direction)) {
            if (Direction.EAST.equals(direction) || Direction.WEST.equals(direction)) {
                posXFinal += moveValue;
            } else if (Direction.NORTH.equals(direction) || Direction.SOUTH.equals(direction)) {
                posYFinal -= moveValue;
            }
        } else {
            System.out.println("Cannot move in direction " + direction + ". Obstacle found or reached boundary.");
            return false;
        }

        // Apply position updates
        rover.setX(posXFinal);
        rover.setY(posYFinal);

        System.out.println("Final position: (" + posXFinal + ", " + posYFinal + ")");
        return true;
    }


    private boolean canMoveTo(int currentX, int currentY, int moveValue, Direction direction) {
        int newX = currentX;
        int newY = currentY;

        if (Direction.EAST.equals(direction) || Direction.WEST.equals(direction)) {
            newX += moveValue;
        } else if (Direction.NORTH.equals(direction) || Direction.SOUTH.equals(direction)) {
            newY -= moveValue;
        }

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
