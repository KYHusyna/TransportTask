package com.output;

import com.input.CreateVaults;

public class OutputData {
    private CreateVaults storages = new CreateVaults();

    private Integer[] order,
            storageBalances; 

    private String[] supermarket,
            storage;

    public void matrixOutput(Integer[][] matrix, String kindOfMatrix) {
        order = storages.getOrder();
        storageBalances = storages.getStorageBalance();
        supermarket = storages.getSupermarket();
        storage = storages.getStorage();

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
