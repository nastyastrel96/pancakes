package com.nastyastrel.pancakes.repository;

import com.nastyastrel.pancakes.model.pancake.Pancake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PancakeRepository extends JpaRepository<Pancake, Long> {

}
