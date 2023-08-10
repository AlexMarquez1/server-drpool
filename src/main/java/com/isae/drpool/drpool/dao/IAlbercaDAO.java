package com.isae.drpool.drpool.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isae.drpool.drpool.entity.Alberca;

@Repository
public interface IAlbercaDAO extends JpaRepository<Alberca, Integer> {

}
