package com.processingData.algorithms;

import com.processingData.examples.Calculate;

public class MinimumElementAlgorithm implements Calculate {
    //objects
    private MethodsHelper methodsHelper;

    //variables
    private Integer[][] deliveryMatrix;
    private Integer min;

    public Integer[][] calculateDeliveryPath() {
        System.out.println("----MinimumElement----");
        methodsHelper = new MethodsHelper();
        deliveryMatrix = new Integer[methodsHelper.matrixCost.length][methodsHelper.matrixCost[0].length];

        while (!methodsHelper.storageOrOrderEmpty()) {
            min = methodsHelper.findValidCells();

            for (int i = 0; i < deliveryMatrix.length; i++) {
                for (int j = 0; j < deliveryMatrix[0].length; j++) {
                    if (methodsHelper.matrixCost[i][j] == min) {
                        deliveryMatrix[i][j] = methodsHelper.fillCell(i, j);
                    }
                }
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
