package com.processingData.output;

import com.processingData.input.CreateStorages;
import com.processingData.input.CreateSupermarkets;

public class OutputData {
    private CreateStorages storages = new CreateStorages();
    private CreateSupermarkets supermarkets = new CreateSupermarkets();

    private Integer[] order,
            storageBalances; 

    private String[] supermarket,
            storage;

    public void matrixOutput(Integer[][] matrix, String kindOfMatrix) {
        order = supermarkets.getVaultsValue();
        storageBalances = storages.getVaultsValue();
        supermarket = supermarkets.getVaultsName();
        storage = storages.getVaultsName();

        System.out.println("\n------Matrix " + kindOfMatrix + "------");
        for (int i = 0; i < matrix[0].length; i++)
            System.out.print(supermarket[i] + " ");

        System.out.println();
        for (int i = 0; i < matrix[0].length; i++)
            System.out.print(order[i] + " ");

        System.out.println("\n----");

        for (int i = 0; i < matrix.length; i++) {
            System.out.print(storage[i] + " " + storageBalances[i] + " |");
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

}
