package com.rewufu.superlist.entity;

/**
 * Created by Bell on 9/1/15.
 */
public class Goods {
    private String name;
    private String bought;
    private String listName;

    @Override
    public String toString() {
        return "Goods{" +
                "name='" + name + '\'' +
                ", bought='" + bought + '\'' +
                ", listName='" + listName + '\'' +
                '}';
    }

    public Goods(String name, String bought, String listName) {
        this.name = name;
        this.bought = bought;
        this.listName = listName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBought() {
        return bought;
    }

    public void setBought(String bought) {
        this.bought = bought;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }
}
