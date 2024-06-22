package com.miguel.api_mars_rover.controller;

import com.miguel.api_mars_rover.entity.Obstacle;
import com.miguel.api_mars_rover.service.ObstacleServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/obstacle")
@CrossOrigin(origins = "http://localhost")
public class ObstacleController {
    @Autowired
    private ObstacleServiceImp obstacleServiceImp;
    @PostMapping("/")
    public void create(@RequestBody Obstacle obstacle){
        obstacleServiceImp.create(obstacle);
        System.out.println(obstacle);
    }
    @GetMapping("/")
    public List<Obstacle> getAll(){
        return obstacleServiceImp.findAll();
    }
}
