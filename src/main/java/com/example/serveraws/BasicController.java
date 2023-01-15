package com.example.serveraws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController // This means that this class is a Controller
public class BasicController {

    private ScoresRepository scoresRepository;

    @Autowired
    public void setInjectedBean(ScoresRepository scoresRepository) {
        this.scoresRepository = scoresRepository;
    }


    @GetMapping("/")
    public String index() {
        return "Greeting!";
    }

    @GetMapping("/highscore")
    public ResponseEntity<Highscore> get(@RequestParam(value = "id") int id) {
        // get highscore by ID
        Optional<Highscore> userInScoresDB = scoresRepository.findById(id);
        if (userInScoresDB.isPresent()) {
            return new ResponseEntity<Highscore>(userInScoresDB.get(), HttpStatus.OK);
        }
        return new ResponseEntity("no player found with id " + id, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/highscorelist")
    public ResponseEntity<Iterable<Highscore>> getALL() {
        // get and sort all allUsersAndScores by highScore
        Iterable<Highscore> allUsersAndScores = scoresRepository.findAll(Sort.by(Sort.Direction.DESC, "highScore"));
        return new ResponseEntity<Iterable<Highscore>>(allUsersAndScores, HttpStatus.OK);
    }


    @PostMapping("/highscore")
    public ResponseEntity<Highscore> create(@RequestParam String name
            , @RequestParam Integer score) {
        // highscore user in DB
        Highscore newHighscore = new Highscore();
        newHighscore.setUsername(name);
        newHighscore.setHighScore(score);
        scoresRepository.save(newHighscore);
        return new ResponseEntity<Highscore>(newHighscore, HttpStatus.OK);
    }


    @DeleteMapping("/highscore")
    public ResponseEntity delete(@RequestParam(value = "id") int id) {

        scoresRepository.deleteById(id);
        return new ResponseEntity("Highscore gelöscht", HttpStatus.OK);
    }
}
