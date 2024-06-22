package com.miguel.api_mars_rover.entity;

import lombok.Data;

import java.util.List;

@Data
public class Command {
    private List<String> commands;
}
