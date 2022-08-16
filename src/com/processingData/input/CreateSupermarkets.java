package com.processingData.input;

import com.controller.ControlInput;
import com.processingData.examples.CreateVaults;

import java.util.Scanner;

public class CreateSupermarkets extends CreateVaults {
    //objects
    private ControlInput controlInput = new ControlInput();
    //variables
    private static String[] supermarket;
    private static Integer[] order;

    //create supermarkets and set: name, order
    private void createSupermarkets() {
        Scanner scanner = new Scanner(System.in);
        Integer countSupermarkets = 0;
        String enterSupermarketCount = "Enter supermarkets count",
                enterOrder = "Enter order value",
                enterSupermarketName = "";

        //control correct input
        countSupermarkets = controlInput.controlIntInput(enterSupermarketCount);


        supermarket = new String[countSupermarkets];
        order = new Integer[countSupermarkets];

        for (int i = 0; i < countSupermarkets; i++) {
            enterSupermarketName ="Enter "+(i+1)+" supermarket name";
            //control correct input
            supermarket[i] = controlInput.controlStringInput(enterSupermarketName);
            order[i] = controlInput.controlIntInput(enterOrder);
        }
    }

    //return temporary array
    private Integer[] arrayCopy(Integer[] array) {
        Integer[] arrayCopy = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            arrayCopy[i] = array[i];
        }
        return arrayCopy;
    }

    //return temporary array
    private String[] arrayCopy(String[] array) {
        String[] arrayCopy = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            arrayCopy[i] = array[i];
        }
        return arrayCopy;
    }

    //get value from vaults
    public Integer[] getVaultsValue() {
        if (order == null) {
            createSupermarkets();
        }
        return arrayCopy(order);
    }

    //get names of vaults
    public String[] getVaultsName() {
        if (supermarket == null) {
            createSupermarkets();
        }
        return arrayCopy(supermarket);
    }

    protected void setVaultsName(String[] newSupermarket) {
        supermarket = newSupermarket;
    }

    protected void setVaultsValue(Integer[] newOrder) {
        order = newOrder;
    }

}
