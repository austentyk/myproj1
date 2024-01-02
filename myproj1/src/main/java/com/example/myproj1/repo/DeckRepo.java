package com.example.myproj1.repo;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.example.myproj1.model.Card;
import com.example.myproj1.model.Deck;

import jakarta.annotation.Resource;


@Repository
public class DeckRepo {


    @Autowired
    private RedisTemplate<String, Object> template;

    public void saveDeck(Deck deck){
        template.opsForValue().set(deck.getId(), deck);
    }

    public Deck getDeck(String deckId){
        return (Deck) template.opsForValue().get(deckId);
    }
    
}
