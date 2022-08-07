package com.input;

import java.util.Scanner;

public class CreateVaults {
    private static String[] supermarket,
            storage;
    private static Integer[] order,
            storageBalances;

    Scanner scanner = new Scanner(System.in);

    private void createSupermarkets() {
        System.out.println("Enter supermarkets count");
        Integer countSupermarkets = scanner.nextInt();

        supermarket = new String[countSupermarkets];
        order = new Integer[countSupermarkets];

        for (int i = 0; i < countSupermarkets; i++) {
            System.out.println("\nEnter name supermarket");
            supermarket[i] = scanner.next();

            System.out.println("Enter order count");
            order[i] = scanner.nextInt();
        }
    }

    private void createStorage() {
        System.out.println("\nEnter storage count");
        Integer countStorage = scanner.nextInt();

        storage = new String[countStorage];
        storageBalances = new Integer[countStorage];

        for (int i = 0; i < countStorage; i++) {
            System.out.println("\nEnter name storage");
            storage[i] = scanner.next();

            System.out.println("Enter count things at storage");
            storageBalances[i] = scanner.nextInt();
        }
    }

    //get
    private Integer[] arrayCopy(Integer[] array) {
        Integer[] arrayCopy = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            arrayCopy[i] = array[i];
        }
        return arrayCopy;
    }

    private String[] arrayCopy(String[] array) {
        String[] arrayCopy = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            arrayCopy[i] = array[i];
        }
        return arrayCopy;
    }

    public Integer[] getOrder() {
        if (order == null) {
            createSupermarkets();
        }
        return arrayCopy(order);
    }

    public String[] getSupermarket() {
        if (supermarket == null) {
            createSupermarkets();
        }
        return arrayCopy(supermarket);
    }

    public Integer[] getStorageBalance() {
        if (storageBalances == null) {
            createStorage();
        }
        return arrayCopy(storageBalances);
    }

    public String[] getStorage() {
        if (storage == null) {
            createStorage();
        }
        return arrayCopy(storage);
    }

    //set
    protected void setOrder(Integer[] newOrder) {
        order = newOrder;
    }

    protected void setSupermarket(String[] newSupermarket) {
        supermarket = newSupermarket;
    }

    protected void setStorageBalance(Integer[] newStorageBalance) {
        storageBalances = newStorageBalance;
    }

    protected void setStorage(String[] newStorage) {
        storage = newStorage;
    }
}
