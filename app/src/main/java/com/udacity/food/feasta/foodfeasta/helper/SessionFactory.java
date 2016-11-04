package com.udacity.food.feasta.foodfeasta.helper;

/**
 * Created by jinal on 11/4/2016.
 */
public class SessionFactory {
    private static SessionManagerInterface ourInstance = new SessionManager();

    public static SessionManagerInterface getInstance() {
        return ourInstance;
    }

}
