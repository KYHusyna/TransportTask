package com.processingData.input;

import com.controller.ControlInput;
import com.processingData.examples.CreateVaults;

import java.util.Scanner;

public class CreateStorages extends CreateVaults {
    //objects
    ControlInput controlInput = new ControlInput();
    //variables
    private static String[] storage;
    private static Integer[] storageBalances;

    //create storages and set: name, balance in storage
    private void createStorage() {
        Scanner scanner = new Scanner(System.in);
        Integer countStorage = 0;
        String enterStorageCount = "Enter storage count",
                enterStorageBalance = "Enter count things at storage",
                enterStorageName = "";

        //control correct input
        countStorage = controlInput.controlIntInput(enterStorageCount);

        storage = new String[countStorage];
        storageBalances = new Integer[countStorage];

        for (int i = 0; i < countStorage; i++) {
            enterStorageName ="Enter "+(i+1)+" storage name";
            //control correct input
            storage[i] = controlInput.controlStringInput(enterStorageName);
            storageBalances[i] = controlInput.controlIntInput(enterStorageBalance);
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
        if (storageBalances == null) {
            createStorage();
        }
        return arrayCopy(storageBalances);
    }

    //get value from vaults
    public String[] getVaultsName() {
        if (storage == null) {
            createStorage();
        }
        return arrayCopy(storage);
    }

    protected void setVaultsName(String[] newStorage) {
        storage = newStorage;
    }

    protected void setVaultsValue(Integer[] newStorageBalance) {
        storageBalances = newStorageBalance;
    }
}
