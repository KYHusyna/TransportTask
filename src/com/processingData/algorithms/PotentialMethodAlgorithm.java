package com.processingData.algorithms;

public class PotentialMethodAlgorithm {
    private MethodsHelper methodsHelper;

    public Integer[][] calculateDeliveryPath(Integer[][] deliveryMatrix) {
        methodsHelper = new MethodsHelper();
        Integer[] startCell;
        Boolean needOptimize = true;

        System.out.println("\n-----------Potential method----");

        while (needOptimize && methodsHelper.potentialPossibility(deliveryMatrix)) {
            methodsHelper.findPotential(deliveryMatrix);
            startCell = methodsHelper.indirectCost(deliveryMatrix);

            if (startCell[0] == null && startCell[1] == null) {
                needOptimize = false;
            } else {
                deliveryMatrix = methodsHelper.recalculatePath(startCell, deliveryMatrix);
            }
        }

        return methodsHelper.setZeroInNullCells(deliveryMatrix);
    }
}
