package com.processingData.algorithms;

import com.processingData.examples.Calculate;

public class NorthWestCornerAlgorithm implements Calculate {
    //objects
    private MethodsHelper methodsHelper;
    //variables
    private Integer[][] deliveryMatrix;
    private Integer i = 0,
            j = 0;

    public Integer[][] calculateDeliveryPath() {
        System.out.println("\n----NorthWestCorner----");
        methodsHelper = new MethodsHelper();
        deliveryMatrix = new Integer[methodsHelper.matrixCost.length][methodsHelper.matrixCost[0].length];

        while (i < methodsHelper.tempStorage.length && j < methodsHelper.tempOrder.length) {
            deliveryMatrix[i][j] = methodsHelper.fillCell(i, j);
            if ((methodsHelper.tempStorage[i] <= methodsHelper.tempOrder[j])) {
                i++;
            } else {
                j++;
            }
        }
        return methodsHelper.setZeroInNullCells(deliveryMatrix);
    }

    public Integer[][] getDeliveryPath() {
        if (deliveryMatrix == null) {
            calculateDeliveryPath();
        }
        return deliveryMatrix;
    }
}
