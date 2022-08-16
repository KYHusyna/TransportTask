package com.processingData.algorithms;

import com.processingData.examples.Calculate;

public class DoublePointsAlgorithm implements Calculate {
    //objects
    private MethodsHelper methodsHelper;
    //variables
    private Integer[][] deliveryMatrix;

    public Integer[][] calculateDeliveryPath() {
        System.out.println("----DoublePoints----");
        methodsHelper = new MethodsHelper();
        deliveryMatrix = new Integer[methodsHelper.matrixCost.length][methodsHelper.matrixCost[0].length];

        Integer[][] points;
        Integer[] minRow,
                minColumn;

        Boolean doublePoints;

        while (!methodsHelper.storageOrOrderEmpty()) {
            doublePoints = false;

            //find minimum in rows
            minRow = methodsHelper.rowsMin();
            //find minimum in columns
            minColumn = methodsHelper.columnsMin();
            points = methodsHelper.setPoints(minRow, minColumn);

            for (int i = 0; i < points.length; i++) {
                for (int j = 0; j < points[0].length; j++) {
                    if (points[i][j].equals(2))
                        doublePoints = true;
                }
            }

            for (int i = 0; i < methodsHelper.matrixCost.length; i++) {
                for (int j = 0; j < methodsHelper.matrixCost[0].length; j++) {
                    if (doublePoints) {
                        if (points[i][j].equals(2)) {
                            deliveryMatrix[i][j] = methodsHelper.fillCell(i, j);
                        }
                    } else {
                        if (points[i][j]!=null&& points[i][j].equals(1)&&
                                deliveryMatrix[i][j]!=null&&  deliveryMatrix[i][j].equals(0)) {
                            deliveryMatrix[i][j] = methodsHelper.fillCell(i, j);
                        }
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
