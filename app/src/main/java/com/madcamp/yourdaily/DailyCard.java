package com.madcamp.yourdaily;

public class DailyCard {
    private String uri;
    private String title;
    private String userEmail;
    private String userNick;
    private String writerEmail;
    private String writerNick;
    private String Content;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public String getWriterEmail() {
        return writerEmail;
    }

    public void setWriterEmail(String writerEmail) {
        this.writerEmail = writerEmail;
    }

    public String getWriterNick() {
        return writerNick;
    }

    public void setWriterNick(String writerNick) {
        this.writerNick = writerNick;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public DailyCard(String uri, String title, String userEmail, String userNick, String writerEmail, String writerNick, String content) {
        this.uri = uri;
        this.title = title;
        this.userEmail = userEmail;
        this.userNick = userNick;
        this.writerEmail = writerEmail;
        this.writerNick = writerNick;
        Content = content;
    }
}
