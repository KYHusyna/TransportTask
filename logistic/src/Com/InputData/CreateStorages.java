package Com.InputData;

import java.util.Scanner;

public class CreateStorages {
    private static String[] supermarket,
            storage;
    private static int[] order,
            storageBalances;

    Scanner scanner = new Scanner(System.in);

    private void createSupermarkets() {
        System.out.println("Enter supermarkets count");
       int countSupermarkets = scanner.nextInt();

        int value;
        supermarket = new String[countSupermarkets];
        order = new int[countSupermarkets];

        for (int i = 0; i < countSupermarkets; i++) {
            System.out.println("\nEnter name supermarket");
            supermarket[i] = scanner.next();

            System.out.println("Enter order count");
            value = scanner.nextInt();
            order[i] = value;
        }
    }

    private void createStorage() {
        System.out.println("\nEnter storage count");
        int countStorage = scanner.nextInt();

        int value;
        storage = new String[countStorage];
        storageBalances = new int[countStorage];

        for (int i = 0; i < countStorage; i++) {
            System.out.println("\nEnter name storage");
            storage[i] = scanner.next();

            System.out.println("Enter count things at storage");
            value = scanner.nextInt();
            storageBalances[i] = value;
        }
    }

    //get
    private int[] getTempArray(int[] array) {
        int[] tempArray = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            tempArray[i] = array[i];
        }

        return tempArray;
    }

    private String[] getTempArray(String[] array) {
        String[] tempArray = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            tempArray[i] = array[i];
        }
        return tempArray;
    }

    public int[] getOrder() {
        if (order == null) {
            createSupermarkets();
        }
        return getTempArray(order);
    }

    public String[] getSupermarket() {
        if (supermarket == null) {
            createSupermarkets();
        }
        return getTempArray(supermarket);
    }

    public int[] getStorageBalance() {
        if (storageBalances == null) {
            createStorage();
        }
        return getTempArray(storageBalances);
    }

    public String[] getStorage() {
        if (storage == null) {
            createStorage();
        }
        return getTempArray(storage);
    }

    //set
    protected void setOrder(int[] newOrder) {
        order = newOrder;
    }

    protected void setSupermarket(String[] newSupermarket) {
        supermarket = newSupermarket;
    }

    protected void setStorageBalance(int[] newStorageBalance) {
        storageBalances = newStorageBalance;
    }

    protected void setStorage(String[] newStorage) {
        storage = newStorage;
    }
}
