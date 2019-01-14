package com.madcamp.yourdaily;

public class DailyCard {
    private String ImageUri;
    private String Title;
    private String UserEmail;
    private String UserNick;
    private String WriterEmail;
    private String WriterNick;
    private String Content;

    public DailyCard() {
    }

    public DailyCard(String imageUri, String title, String userEmail, String userNick, String writerEmail, String writerNick, String content) {
        ImageUri = imageUri;
        Title = title;
        UserEmail = userEmail;
        UserNick = userNick;
        WriterEmail = writerEmail;
        WriterNick = writerNick;
        Content = content;
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

    public String getWriterEmail() {
        return WriterEmail;
    }

    public void setWriterEmail(String writerEmail) {
        WriterEmail = writerEmail;
    }

    public String getWriterNick() {
        return WriterNick;
    }

    public void setWriterNick(String writerNick) {
        WriterNick = writerNick;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
