public class Card {
    private String cardName;
    private CardTypes typeOfCard;

    public Card(String name, CardTypes type){
        this.cardName = name;
        this.typeOfCard = type;
    }

    public String getCardName(){
        return  this.cardName;
    }

    public CardTypes getTypeOfCard(){
        return  this.typeOfCard;
    }
}