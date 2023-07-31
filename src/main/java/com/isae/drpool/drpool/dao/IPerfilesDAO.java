package com.isae.drpool.drpool.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.isae.drpool.drpool.entity.Perfile;


@Repository
public interface IPerfilesDAO extends JpaRepository<Perfile,Integer>{

}
