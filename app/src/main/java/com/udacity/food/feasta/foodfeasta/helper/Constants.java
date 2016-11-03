package com.udacity.food.feasta.foodfeasta.helper;

import java.util.HashMap;

/**
 * Created by jinal on 10/22/2016.
 */

public class Constants {

    public static final int SUCCESS = 0;
    public static final int FAILURE = 1;

    public static final int FOOD_STARTER = 11;
    public static final int FOOD_MAIN_COURSE = 12;
    public static final int FOOD_DESSERT = 13;

    public static final String SELECTED_FOOD_ITEM = "selected_food_item";

    public static final HashMap<String, Integer> MENU_TYPE_HASHMAP;
    static{
        MENU_TYPE_HASHMAP = new HashMap<>();
        MENU_TYPE_HASHMAP.put("Starters", FOOD_STARTER);
        MENU_TYPE_HASHMAP.put("Main Course", FOOD_MAIN_COURSE);
        MENU_TYPE_HASHMAP.put("Desert", FOOD_DESSERT);
    }


    public static final int TABLE_ONE = 21;
    public static final int TABLE_TWO = 22;
    public static final int TABLE_THREE = 23;
    public static final int TABLE_FOUR = 24;
    public static final int TABLE_FIVE = 25;
    public static final int TABLE_SIX = 26;

    public static final HashMap<String, Integer> TABLE_MAP;
    static{
        TABLE_MAP = new HashMap<>();
        TABLE_MAP.put("Table 1", TABLE_ONE);
        TABLE_MAP.put("Table 2", TABLE_TWO);
        TABLE_MAP.put("Table 3", TABLE_THREE);
        TABLE_MAP.put("Table 4", TABLE_FOUR);
        TABLE_MAP.put("Table 5", TABLE_FIVE);
        TABLE_MAP.put("Table 6", TABLE_SIX);
    }



}
