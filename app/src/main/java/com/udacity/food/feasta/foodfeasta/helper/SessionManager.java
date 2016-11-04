package com.udacity.food.feasta.foodfeasta.helper;

import java.util.ArrayList;

/**
 * Created by jinal on 11/4/2016.
 */

public class SessionManager implements SessionManagerInterface{

    private String selectedTable;

    public String getSelectedTable() {
        return selectedTable;
    }

    public void setSelectedTable(String selectedTable) {
        this.selectedTable = selectedTable;
    }
}
