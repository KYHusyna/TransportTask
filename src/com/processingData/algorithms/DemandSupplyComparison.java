package com.processingData.algorithms;

import com.processingData.input.AddFictiveField;
import com.processingData.input.CreateStorages;
import com.processingData.input.CreateSupermarkets;
import com.processingData.input.DeliveryCost;
import com.processingData.output.OutputData;

public class DemandSupplyComparison {

    //object
    private CreateStorages storages = new CreateStorages();
    private CreateSupermarkets supermarkets = new CreateSupermarkets();
    private AddFictiveField addFictiveField = new AddFictiveField();
    private OutputData outputData = new OutputData();
    private DeliveryCost deliveryCost = new DeliveryCost();

    //variables
    private Integer[] order,
            storageBalances;

    private String[] supermarket,
            storage;

    public Boolean demandSupplyComparison() {
        order = supermarkets.getVaultsValue();
        storageBalances = storages.getVaultsValue();
        supermarket = supermarkets.getVaultsName();
        storage = storages.getVaultsName();

        Integer orderSum = 0,
                demandSum = 0,
                difference = 0;
        Boolean isChange = false;


        for (int i = 0; i < storageBalances.length; i++)
            demandSum += storageBalances[i];

        for (int i = 0; i < order.length; i++)
            orderSum += order[i];

        if (!demandSum.equals(orderSum)) {
            isChange = true;
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
            outputData.matrixOutput(deliveryCost.getDeliveryCost(), "cost");
        }

        return isChange;
    }
}
