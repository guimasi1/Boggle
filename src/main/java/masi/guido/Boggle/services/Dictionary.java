package masi.guido.Boggle.services;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.Entity;
import lombok.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class Dictionary {
    private static final Set<String> words = new HashSet<>();
    public boolean isWordPresent(String word) {
        if(word.length() < 3) return false;
        return words.contains(word.toLowerCase());
    }

    @PostConstruct
    public static void init() {
        try {
            var resource = new ClassPathResource("parole_uniche.txt");
            var reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            String word;
            while ((word = reader.readLine()) != null) {
                words.add(word.toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printWords() {
        for (String word : words) {
            System.out.println(word);
        }
    }
}
