package com.example.myproj1.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class Card {

    @Size(min=3, message="Please enter a card name with more than 3 characters")
    private String cardName;
    @Min(0)
    private Integer cmc = 0;
    private String cardText;
    private String cardColor;
    private String cardType;
    @Min(0)
    private String cardPrice;
    private String id;

    
    public String getId() {
        return id;
    }
    public String getCardName() {
        return cardName;
    }
    public void setCardName(String cardName) {
        this.cardName = cardName;
    }
    public Integer getCmc() {
        return cmc;
    }
    public void setCmc(Integer cmc) {
        this.cmc = cmc;
    }
    public String getCardText() {
        return cardText;
    }
    public void setCardText(String cardText) {
        this.cardText = cardText;
    }
    public String getCardColor() {
        return cardColor;
    }
    public void setCardColor(String cardColor) {
        this.cardColor = cardColor;
    }
    public String getCardType() {
        return cardType;
    }
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
    public String getCardPrice() {
        return cardPrice;
    }
    public void setCardPrice(String cardPrice) {
        this.cardPrice = cardPrice;
    }
    public void setId(String id) {
        this.id = id;
    }
}
