package com.codergeek.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codergeek.entities.Profile;

public interface ProfileRepo  extends JpaRepository<Profile, Integer>{

}
