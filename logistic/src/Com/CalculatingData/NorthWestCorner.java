package Com.CalculatingData;

import Com.Interfaces.Calculate;

public class NorthWestCorner implements Calculate {

    MethodsHelper methodsHelper;
    int[][] deliveryMatrix;
    private int i = 0,
            j = 0;

    public int[][] findDeliveryPath() {
        System.out.println("----NorthWestCorner----");
        methodsHelper = new MethodsHelper();
        deliveryMatrix = new int[methodsHelper.matrixCost.length][methodsHelper.matrixCost[0].length];

        while (i < methodsHelper.tempStorage.length && j < methodsHelper.tempOrder.length) {
            deliveryMatrix[i][j] = methodsHelper.fillCell(i, j);
            if ((methodsHelper.tempStorage[i] <= methodsHelper.tempOrder[j])) {
                i++;
            } else {
                j++;
            }
        }
        return deliveryMatrix;
    }

    public int[][] getDeliveryMatrix(){
        if(deliveryMatrix==null){
            findDeliveryPath();
        }
        return deliveryMatrix;
    }
}
