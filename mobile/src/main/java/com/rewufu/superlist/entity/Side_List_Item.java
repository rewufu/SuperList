package com.rewufu.superlist.entity;

/**
 * Created by Bell on 5/5/15.
 */
public class Side_List_Item {
    private  int icon;
    private String name;

    public int getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public Side_List_Item(int icon, String name) {

        this.icon = icon;
        this.name = name;
    }
}
