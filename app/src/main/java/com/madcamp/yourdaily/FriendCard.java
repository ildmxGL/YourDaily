package com.madcamp.yourdaily;

public class FriendCard {
    private String email;
    private String profileUri;
    private String card1Uri;
    private String card2Uri;
    private String card3Uri;

    public FriendCard() {
    }

    public FriendCard(String email, String profileUri, String card1Uri, String card2Uri, String card3Uri) {
        this.email = email;
        this.profileUri = profileUri;
        this.card1Uri = card1Uri;
        this.card2Uri = card2Uri;
        this.card3Uri = card3Uri;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileUri() {
        return profileUri;
    }

    public void setProfileUri(String profileUri) {
        this.profileUri = profileUri;
    }

    public String getCard1Uri() {
        return card1Uri;
    }

    public void setCard1Uri(String card1Uri) {
        this.card1Uri = card1Uri;
    }

    public String getCard2Uri() {
        return card2Uri;
    }

    public void setCard2Uri(String card2Uri) {
        this.card2Uri = card2Uri;
    }

    public String getCard3Uri() {
        return card3Uri;
    }

    public void setCard3Uri(String card3Uri) {
        this.card3Uri = card3Uri;
    }
}
