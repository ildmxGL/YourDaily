package com.madcamp.yourdaily;

public class PreDailyCard{
    private String ImageUri;
    private String Title;
    private String UserEmail;
    private String UserNick;
    private String Hashtag;

    public PreDailyCard() {
    }

    public PreDailyCard(String imageUri, String title, String userEmail, String userNick, String hashtag) {
        ImageUri = imageUri;
        Title = title;
        UserEmail = userEmail;
        UserNick = userNick;
        Hashtag = hashtag;
    }

    public String getImageUri() {
        return ImageUri;
    }

    public void setImageUri(String imageUri) {
        ImageUri = imageUri;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getUserNick() {
        return UserNick;
    }

    public void setUserNick(String userNick) {
        UserNick = userNick;
    }

    public String getHashtag() {
        return Hashtag;
    }

    public void setHashtag(String hashtag) {
        Hashtag = hashtag;
    }

}
