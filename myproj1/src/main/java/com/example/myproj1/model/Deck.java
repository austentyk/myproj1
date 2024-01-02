package com.example.myproj1.model;

import java.util.List;
import java.util.UUID;

public class Deck {

    private final String id;
    private List<Card> cards; 

    private static String generateUuid() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    public Deck() {
        this.id = generateUuid();
    }

    public String getId() {
        return id;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    

    
}
