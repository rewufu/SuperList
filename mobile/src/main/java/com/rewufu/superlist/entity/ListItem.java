package com.rewufu.superlist.entity;

/**
 * Created by Bell on 8/23/15.
 */
public class ListItem {
    private String name;
    private boolean bought;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBought() {
        return bought;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }

    public ListItem(String name, boolean bought) {

        this.name = name;
        this.bought = bought;
    }
}
