package com.miguel.api_mars_rover.service;

import com.miguel.api_mars_rover.entity.Rover;

public interface RoverServiceImp {
    public Rover get();
    void sendCommand(String command);
    void createRover(Rover rover);
}
