package com.example.SpringMVC.Model;


import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity // This tells Hibernate to make a table out of this class

public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String title;
    private String text;
    @ManyToOne(fetch = FetchType.LAZY)
    private User author;
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public News(String title, String text, User author, String path) {
        this.title = title;
        this.text = text;
        this.author=author;
        this.path = path;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public News(int id, String title, String text, User author) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return id == news.id && Objects.equals(title, news.title) && Objects.equals(text, news.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, text);
    }

    public News(String title, String text) {
        this.title = title;
        this.text = text;
    }
    public News() {}

    @Override
    public String toString() {
        return id + title + text;
    }
}