package com.madcamp.yourdaily;

public class Daily {
    private String ImageUri;
    private String Hashtag;
    private String Title;
    private String UserEmail;
    private String UserNick;

    public Daily() {
    }

    public Daily(String imageUri, String hashtag, String title, String userEmail, String userNick) {
        ImageUri = imageUri;
        Hashtag = hashtag;
        Title = title;
        UserEmail = userEmail;
        UserNick = userNick;
    }

    public String getImageUri() {
        return ImageUri;
    }

    public void setImageUri(String imageUri) {
        ImageUri = imageUri;
    }

    public String getHashtag() {
        return Hashtag;
    }

    public void setHashtag(String hashtag) {
        Hashtag = hashtag;
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

}
