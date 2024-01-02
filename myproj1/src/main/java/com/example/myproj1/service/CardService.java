package com.example.myproj1.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.myproj1.model.Card;
import com.example.myproj1.model.Deck;
import com.example.myproj1.repo.DeckRepo;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;

@Service
public class CardService {

    @Autowired
    DeckRepo deckRepo;

    public List<Card> searchForCards(@RequestParam(name = "nameSearch") String nameSearchString) {

        String url = UriComponentsBuilder.fromUriString("https://api.scryfall.com/cards/search")
        .queryParam("q", nameSearchString)
        .toUriString();

        RequestEntity<Void> req = RequestEntity.get(url)
                                    .build();

        RestTemplate template = new RestTemplate();

        ResponseEntity<String> res = template.exchange(req, String.class);

        JsonReader jr = Json.createReader(new StringReader(res.getBody()));
        JsonArray ja = jr.readObject().getJsonArray("data");

        List<Card> cards = new LinkedList<>();
        for (JsonValue val : ja) {
            JsonObject obj = val.asJsonObject();
            Card card = new Card();
            
            card.setId(obj.getString("id", ""));
            card.setCardName(obj.getString("name", ""));
            card.setCmc(obj.getJsonNumber("cmc").intValue());
            card.setCardText(obj.getString("oracle_text", ""));

           JsonObject prices = obj.getJsonObject("prices");
            card.setCardPrice(prices.getString("usd",""));

            JsonArray color = obj.getJsonArray("colors");
            if (color != null) {
                String colors = color.toString();
                card.setCardColor(colors);
            } else {
                String colors = "[]";
                 card.setCardColor(colors);
            }


            cards.add(card);
        }

        return cards;
    }

    public Card getCard(String id) {

        RequestEntity<Void> req = RequestEntity.get("https://api.scryfall.com/cards/" + id)
                                    .build();

        RestTemplate template = new RestTemplate();

        ResponseEntity<String> res = template.exchange(req, String.class);


        JsonReader jr = Json.createReader(new StringReader(res.getBody()));
        JsonObject obj = jr.readObject();

        Card card = new Card();
        
        card.setId(obj.getString("id", ""));
        card.setCardName(obj.getString("name", ""));
        card.setCmc(obj.getJsonNumber("cmc").intValue());
        card.setCardText(obj.getString("oracle_text", ""));

        JsonObject prices = obj.getJsonObject("prices");
        card.setCardPrice(prices.getString("usd",""));

            JsonArray color = obj.getJsonArray("colors");
            if (color != null) {
                String colors = color.toString();
                card.setCardColor(colors);
            } else {
                String colors = "[]";
                 card.setCardColor(colors);
            }

        return card;
    }

    public void saveDeck(Deck deck) {
        deckRepo.saveDeck(deck);
    }

    public Deck getDeck(String decKid) {
        return deckRepo.getDeck(decKid);
    }
}
