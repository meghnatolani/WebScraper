package org.propertyfinder.model;

/**
 * Modal Class for Product JSON
 * */

public class Product {
    private String id;
    private String title;

    public Product(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", title=" + title + "]";
    }
}
