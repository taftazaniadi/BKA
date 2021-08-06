package com.buka.amanah.utils.action_menu;

public class MenuPojo {

    // the resource ID for the imageView
    private int ivMenu;

    // TextView 1
    private String tvMenu;


    // create constructor to set the values for all the parameters of the each single view
    public MenuPojo(int IvMenu, String TvMenu) {
        ivMenu = IvMenu;
        tvMenu = TvMenu;
    }

    public int getIvMenu() {
        return ivMenu;
    }

    public String getTvMenu() {
        return tvMenu;
    }
}

