package com.miguel.api_mars_rover.repository;

import com.miguel.api_mars_rover.entity.Rover;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoverRepository extends JpaRepository<Rover, Long> {
}
