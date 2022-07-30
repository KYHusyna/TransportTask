package Com.CalculatingData;

import Com.Interfaces.Calculate;

public class DoublePoints implements Calculate {

    MethodsHelper methodsHelper;
    int[][] deliveryMatrix;

    public int[][] findDeliveryPath() {
        System.out.println("----DoublePoints----");
        methodsHelper = new MethodsHelper();
        deliveryMatrix = new int[methodsHelper.matrixCost.length][methodsHelper.matrixCost[0].length];

        int[][] points;
        int[] minRow,
                minColumn;

        boolean doublePoints;

        while (!methodsHelper.storageOrOrderEmpty()) {
            doublePoints = false;

            //find minimum in rows
            minRow = methodsHelper.rowsMin();
            //find minimum in columns
            minColumn = methodsHelper.columnsMin();
            points = methodsHelper.setPoints(minRow, minColumn);

            for (int i = 0; i < points.length; i++) {
                for (int j = 0; j < points[0].length; j++) {
                    if (points[i][j] == 2)
                        doublePoints = true;
                }
            }

            for (int i = 0; i < methodsHelper.matrixCost.length; i++) {
                for (int j = 0; j < methodsHelper.matrixCost[0].length; j++) {
                    if (doublePoints) {
                        if (points[i][j] == 2) {
                            deliveryMatrix[i][j] = methodsHelper.fillCell(i, j);
                        }
                    } else {
                        if (points[i][j] == 1 && deliveryMatrix[i][j] == 0) {
                            deliveryMatrix[i][j] = methodsHelper.fillCell(i, j);
                        }
                    }
                }
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
