package com.miguel.api_mars_rover.controller;

import com.miguel.api_mars_rover.dto.RoverDto;
import com.miguel.api_mars_rover.entity.Command;
import com.miguel.api_mars_rover.entity.Direction;
import com.miguel.api_mars_rover.entity.Rover;
import com.miguel.api_mars_rover.service.RoverServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rover")
@CrossOrigin(origins = "http://localhost:8080")
public class RoverController {
    @Autowired
    RoverServiceImp roverServiceImp;

    @GetMapping("/")
    public Rover get(){
        return roverServiceImp.get();
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody RoverDto roverDto){
        try {
            // Validar y transformar los datos del DTO
            int x = roverDto.getX();
            int y = roverDto.getY();
            Direction direction = Direction.valueOf(roverDto.getDirection().toUpperCase()); // Convertir a mayúsculas

            // Validar dirección
            if (!isValidDirection(direction)) {
                return ResponseEntity.badRequest().body("Invalid direction. Accepted values: NORTH, EAST, SOUTH, WEST");
            }

            // Validar límites de coordenadas
            if (!isValidPosition(x, y)) {
                return ResponseEntity.badRequest().body("Coordinates out of bounds. x should be between 0 and 9, y between 0 and 5");
            }

            // Crear y guardar el rover
            Rover rover = new Rover();
            rover.setX(x);
            rover.setY(y);
            rover.setDirection(direction);

            // Verificar si ya existe un rover
            if (roverServiceImp.exists()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Rover already exists");
            }

            // Verificar si hay un obstáculo en las coordenadas especificadas
            if (roverServiceImp.hasObstacleAt(x, y)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot create rover at obstacle position");
            }

            roverServiceImp.createRover(rover);

            return ResponseEntity.status(HttpStatus.CREATED).body("Rover created successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid direction. Accepted values: NORTH, EAST, SOUTH, WEST");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating rover");
        }
    }

    @PostMapping("/command/")
    public void sendCommand(@RequestBody Command commands) {
        for(String command:commands.getCommands()){
            roverServiceImp.sendCommand(command);
            System.out.println(command);
        }
    }

    // Método para validar dirección
    private boolean isValidDirection(Direction direction) {
        return direction != null;
    }

    // Método para validar límites de coordenadas
    private boolean isValidPosition(int x, int y) {
        return x >= Rover.MIN_X_POSITION && x <= Rover.MAX_X_POSITION &&
                y >= Rover.MIN_Y_POSITION && y <= Rover.MAX_Y_POSITION;
    }

}
