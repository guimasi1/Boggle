package masi.guido.Boggle;

import masi.guido.Boggle.services.Dictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.support.WindowIterator;
import org.springframework.stereotype.Component;

import static masi.guido.Boggle.services.Dictionary.init;

@Component
public class MyRunner implements CommandLineRunner {

    @Autowired
    Dictionary dictionary;


    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hello!");
    }

}
