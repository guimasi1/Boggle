package masi.guido.Boggle.services;

import masi.guido.Boggle.entities.Cell;
import masi.guido.Boggle.repositories.CellDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class CellService {
    @Autowired
    CellDAO cellDAO;

}

