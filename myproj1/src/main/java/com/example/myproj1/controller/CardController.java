package com.example.myproj1.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.myproj1.model.Card;
import com.example.myproj1.model.Deck;
import com.example.myproj1.service.CardService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping()
public class CardController {

    @Autowired
    private CardService cardService;


    @GetMapping()
    public String getLanding(Model model) {
        model.addAttribute("card", new Card());
        return "search";
    }

    @GetMapping("/search")
    public String getSearch(@Valid @ModelAttribute Card card, BindingResult result, Model model) {
        
        if (card.getCardName().isEmpty())
            {result.addError(new FieldError("card", "cardName", "Please enter more than 1 character"));}
        if (result.hasErrors()){
            return "search";} else {

        List<Card> cards = cardService.searchForCards(card.getCardName());
        model.addAttribute("card", card);
        model.addAttribute("cards", cards);
        return "search"; }

        
    }

    //----------------------------

    @GetMapping("/add/{id}")
    public String addCardToDeck(@PathVariable String id, Model model, HttpSession session){

        Deck deck = new Deck();
        String deckId = (String) session.getAttribute("deck");
        if (deckId == null) {
            session.setAttribute("deck", deck.getId());
        } else {
            deck = cardService.getDeck(deckId);
        }

        List<Card> cards = deck.getCards();
        if (cards == null) {
            cards = new LinkedList<>();
        }

        Card card = cardService.getCard(id);
        cards.add(card);
        deck.setCards(cards);

        cardService.saveDeck(deck);

        model.addAttribute("card", card);
        model.addAttribute("deck", deck);

        return "success";
    }

    @GetMapping("/viewdeck")
    public String viewDeckList(Model model, HttpSession session){

        String viewId = (String) session.getAttribute("deck");
        if (viewId != null) {
            Deck deck = cardService.getDeck(viewId);
            if (deck != null) {
                model.addAttribute("deck", deck);
                return "viewdeck";
            }
        }
        return "viewdeck";
    }

    @GetMapping("/createdeck")
    public String createDeckList(HttpSession session) {
        Deck deck = new Deck();
        session.setAttribute("deck", deck.getId());
        cardService.saveDeck(deck);
        return "redirect:/viewdeck";
    }

    @PostMapping("/loaddeck")
    public String loadDeckList(@RequestBody MultiValueMap<String, String> mvm,BindingResult result, HttpSession session) {
        


        String deckId = mvm.getFirst("deckId");

        Deck deck = cardService.getDeck(deckId);
        if (deck != null) {
            session.setAttribute("deck", deckId);
            return "redirect:/viewdeck";
        } 
        return "error";
    }

    @GetMapping("/unloaddeck")
    public String unloadDeckList(HttpSession session) {
        session.invalidate();
        return "redirect:/viewdeck";
    }








}
