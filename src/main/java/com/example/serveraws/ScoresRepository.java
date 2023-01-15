package com.example.serveraws;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoresRepository extends CrudRepository<Highscore, Integer> {

    Iterable<Highscore> findAll(Sort highScore);


}
