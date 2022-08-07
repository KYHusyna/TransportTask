package com.calculating.algorithms;

import com.input.AddFictiveField;
import com.input.CreateVaults;
import com.input.MatrixCost;
import com.output.OutputData;

public class DemandSupplyComparison {

    //object
    private CreateVaults storages = new CreateVaults();
    private AddFictiveField addFictiveField = new AddFictiveField();
    private OutputData outputData = new OutputData();
    private MatrixCost matrixCost = new MatrixCost();

    //variables
    private Integer[] order,
            storageBalances;

    private String[] supermarket,
            storage;

    public void demandSupplyComparison() {
        order = storages.getOrder();
        storageBalances = storages.getStorageBalance();
        supermarket = storages.getSupermarket();
        storage = storages.getStorage();

        int orderSum = 0,
                demandSum = 0,
                difference = 0;


        for (int i = 0; i < storageBalances.length; i++)
            demandSum += storageBalances[i];

        for (int i = 0; i < order.length; i++)
            orderSum += order[i];

        if (demandSum != orderSum) {

            if (demandSum > orderSum) {
                System.out.println("\nDemand is bigger than order " +
                        "\n demand:" + demandSum + " order:" + orderSum);

                difference = demandSum - orderSum;
                addFictiveField.addFictiveField(difference, 0, order, supermarket);

            } else {
                System.out.println("\nOrder is bigger than demand \n demand:" + demandSum + " order:" + orderSum);

                difference = orderSum - demandSum;
                addFictiveField.addFictiveField(difference, 1, storageBalances, storage);
            }

            System.out.println("\n!!!New matrix!!!");
            outputData.matrixOutput(matrixCost.getMatrixCost(), "cost");
        }
    }
}
