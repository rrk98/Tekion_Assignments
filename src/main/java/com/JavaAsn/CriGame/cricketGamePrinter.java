package com.JavaAsn.CriGame;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class cricketGamePrinter {
    @GetMapping("/startGame")
    public cricketGame startGame()
    {
        return new Match().startMatch();
    }
}
