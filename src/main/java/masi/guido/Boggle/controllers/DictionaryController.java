package masi.guido.Boggle.controllers;

import lombok.Getter;
import masi.guido.Boggle.services.Dictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dictionary")
public class DictionaryController {
    @Autowired
    Dictionary dictionary;

    @GetMapping("/isValid/{word}")
    public boolean isValid(@PathVariable String word) {
        return dictionary.isWordPresent(word);
    }
}
