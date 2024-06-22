package com.miguel.api_mars_rover.repository;

import com.miguel.api_mars_rover.entity.Obstacle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObstacleRepository extends JpaRepository<Obstacle, Long> {
}
