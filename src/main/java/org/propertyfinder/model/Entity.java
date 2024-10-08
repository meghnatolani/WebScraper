package org.propertyfinder.model;

/**
 * Modal Class for Entity JSON
 * */

public class Entity implements ScrapedData {
    private String title;

    public Entity(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "title=" + title;
    }
}
