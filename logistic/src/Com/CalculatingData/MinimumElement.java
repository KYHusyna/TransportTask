package Com.CalculatingData;

import Com.Interfaces.Calculate;

public class MinimumElement implements Calculate {

    MethodsHelper methodsHelper;
    int[][] deliveryMatrix;
    int min;

    public int[][] findDeliveryPath() {
        System.out.println("----MinimumElement----");
        methodsHelper = new MethodsHelper();
        deliveryMatrix = new int[methodsHelper.matrixCost.length][methodsHelper.matrixCost[0].length];

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
        return deliveryMatrix;
    }

    public int[][] getDeliveryMatrix() {
        if (deliveryMatrix == null) {
            findDeliveryPath();
        }
        return deliveryMatrix;
    }
}
