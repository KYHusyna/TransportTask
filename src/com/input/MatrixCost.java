package com.input;

import java.util.Random;
import java.util.Scanner;

public class MatrixCost {

    private CreateVaults storages = new CreateVaults();
    private String[] order = storages.getSupermarket(),
            storage = storages.getStorage();

    private static Integer[][] matrixCost;
    Scanner scanner = new Scanner(System.in);
    Random rand = new Random();

    private void createCostMatrix() {
         matrixCost = new Integer[storage.length][order.length];
        System.out.println("\nHow you want to fill delivery matrix? \nEnter 0 if random and 1 if input");

        Integer choice = scanner.nextInt();

        while (choice != 0 && choice != 1) {
            System.out.println("Input correct choice");
            choice = scanner.nextInt();
        }

         if (choice == 1) {
        //input delivery value
        for (int i = 0; i < storage.length; i++) {
            for (int j = 0; j < order.length; j++) {

                System.out.println("Enter count delivery from  storage " + storage[i] +
                        " to " + order[j] + " supermarket");
                matrixCost[i][j] = scanner.nextInt();
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

    public Integer[][] getMatrixCost() {
        if (matrixCost == null) {
            System.out.println("Matrix null");
            createCostMatrix();
        }
            return matrixCost;
    }

    protected void setMatrixCost(Integer[][] newDeliveryMatrix){
        matrixCost = new Integer[newDeliveryMatrix.length][newDeliveryMatrix[0].length];
        for (int i = 0; i < matrixCost.length; i++) {
            for (int j = 0; j < matrixCost[0].length; j++) {
                matrixCost[i][j] = newDeliveryMatrix[i][j];
            }
        }
    }
}
