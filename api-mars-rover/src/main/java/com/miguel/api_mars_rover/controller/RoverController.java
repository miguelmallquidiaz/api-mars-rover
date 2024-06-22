package com.miguel.api_mars_rover.controller;

import com.miguel.api_mars_rover.entity.Command;
import com.miguel.api_mars_rover.entity.Rover;
import com.miguel.api_mars_rover.service.RoverServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void create(@RequestBody Rover rover){
        roverServiceImp.createRover(rover);
        System.out.println(rover);
    }

    @PostMapping("/command/")
    public void sendCommand(@RequestBody Command commands) {
        for(String command:commands.getCommands()){
            roverServiceImp.sendCommand(command);
            System.out.println(command);
        }
    }

}
