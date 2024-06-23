package com.miguel.api_mars_rover.controller;

import com.miguel.api_mars_rover.dto.ObstacleDto;
import com.miguel.api_mars_rover.entity.Obstacle;
import com.miguel.api_mars_rover.service.ObstacleServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/obstacle")
@CrossOrigin(origins = "http://localhost")
public class ObstacleController {
    @Autowired
    private ObstacleServiceImp obstacleServiceImp;
    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody ObstacleDto obstacleDto){
        try {
            Obstacle obstacle = new Obstacle();
            obstacle.setX(obstacleDto.getX());
            obstacle.setY(obstacleDto.getY());

            obstacleServiceImp.create(obstacle);
            return ResponseEntity.status(HttpStatus.CREATED).body("Obstacle created successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating obstacle");
        }
    }
    @GetMapping("/")
    public List<Obstacle> getAll(){
        return obstacleServiceImp.findAll();
    }
}
