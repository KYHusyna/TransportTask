package com.processingData.input;

import com.controller.ControlInput;

import java.util.Random;
import java.util.Scanner;

public class DeliveryCost {
    //objects
    private CreateStorages storages = new CreateStorages();
    private CreateSupermarkets supermarkets = new CreateSupermarkets();
    private ControlInput controlInput = new ControlInput();
    Scanner scanner = new Scanner(System.in);
    //variables
    private String[] order,
            storage;

    private static Integer[][] matrixCost;
    private Random rand = new Random();

    //set delivery cost for each storage-supermarket
    private void createDeliveryCost() {
        order = supermarkets.getVaultsName();
        storage = storages.getVaultsName();
        matrixCost = new Integer[storage.length][order.length];

        String chooseInput = "\nHow you want to fill delivery matrix? \nEnter 0 if random and 1 if input",
                deliveryValue = "";
        Integer choice = controlInput.controlIntInput(chooseInput);

        while (choice != 0 && choice != 1) {
            System.out.println("Input correct choice");
            choice = scanner.nextInt();
        }

        if (choice == 1) {
            //input delivery value
            for (int i = 0; i < storage.length; i++) {
                for (int j = 0; j < order.length; j++) {

                    deliveryValue = "Enter count delivery from  storage " + storage[i] +
                            " to " + order[j] + " supermarket";
                    matrixCost[i][j] = controlInput.controlIntInput(deliveryValue);
                }
            }
        } else {
            //random delivery value for testing
            for (int i = 0; i < storage.length; i++) {
                for (int j = 0; j < order.length; j++) {
                    matrixCost[i][j] = rand.nextInt(1, 10);
                }
            }
        }
    }

    public Integer[][] getDeliveryCost() {
        if (matrixCost == null) {
            createDeliveryCost();

        }
        return matrixCost;
    }

    protected void setDeliveryCost(Integer[][] newDeliveryMatrix) {
        matrixCost = new Integer[newDeliveryMatrix.length][newDeliveryMatrix[0].length];
        for (int i = 0; i < matrixCost.length; i++) {
            for (int j = 0; j < matrixCost[0].length; j++) {
                matrixCost[i][j] = newDeliveryMatrix[i][j];
            }
        }
    }
}
