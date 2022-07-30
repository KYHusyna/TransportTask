package Com.CalculatingData;

import Com.InputData.CreateStorages;
import Com.InputData.MatrixCost;

import java.util.ArrayList;
import java.util.Collections;

public class MethodsHelper {

    private CreateStorages storages = new CreateStorages();
    private MatrixCost matrixOfCost = new MatrixCost();
    protected int[] tempOrder,
            tempStorage;

    protected int[][] optimizedMatrix,
            array,
            matrixCost;
    private Integer[] storagePotential,
            supermarketPotential;

    public MethodsHelper() {
        tempOrder = storages.getOrder();
        tempStorage = storages.getStorageBalance();
        optimizedMatrix = new int[tempStorage.length][tempOrder.length];
        matrixCost = matrixOfCost.getMatrixCost();
    }

    protected int fillCell(int i, int j) {
        int value = 0;

        //if storage balance smaller then order
        if ((tempStorage[i] <= tempOrder[j])) {
            value += tempStorage[i]; //write all storage balance in cell
            tempOrder[j] -= tempStorage[i];//degree balance from order, because we fill this order
            tempStorage[i] = 0;// now this storage empty

        } else {
            value += tempOrder[j];//write all order in cell
            tempStorage[i] -= tempOrder[j];//degree order from balance, because we fill this order
            tempOrder[j] = 0;// now this order full
        }
        return value;
    }

    protected int[][] setPoints(int[] minRow, int[] minColumn) {
        int[][] points = new int[matrixCost.length][matrixCost[0].length];

        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points[0].length; j++) {
                if (minRow[i] == matrixCost[i][j]) {
                    points[i][j] += 1;
                }
                if (minColumn[j] == matrixCost[i][j]) {
                    points[i][j] += 1;
                }
            }
        }
        return points;
    }

    protected int[] rowsMin() {
        int[] minRow = new int[matrixCost.length];

        for (int i = 0; i < matrixCost.length; i++) {
            if (tempStorage[i] == 0) {
                minRow[i] = -1;

            } else {
                if (tempOrder[0] != 0)
                    minRow[i] = matrixCost[i][0];
                else minRow[i] = -1;

                for (int j = 0; j < matrixCost[0].length; j++) {
                    if (tempOrder[j] != 0) {
                        if (minRow[i] > matrixCost[i][j]) {
                            minRow[i] = matrixCost[i][j];
                        } else if (minRow[i] == -1) {
                            minRow[i] = matrixCost[i][j];
                        }
                    }
                }
            }
        }
        return minRow;
    }

    protected int[] columnsMin() {
        int[] minColumn = new int[matrixCost[0].length];

        for (int i = 0; i < matrixCost[0].length; i++) {
            if (tempOrder[i] == 0) {
                minColumn[i] = -1;
            } else {

                if (tempStorage[0] != 0) {
                    minColumn[i] = matrixCost[0][i];
                } else minColumn[i] = -1;

                for (int j = 0; j < matrixCost.length; j++) {
                    if (tempStorage[j] != 0) {
                        if (minColumn[i] > matrixCost[j][i]) {
                            minColumn[i] = matrixCost[j][i];
                        } else if (minColumn[i] == -1) {
                            minColumn[i] = matrixCost[j][i];
                        }
                    }
                }
            }
        }
        return minColumn;
    }

    protected boolean storageOrOrderEmpty() {
        int demandSum = 0,
                orderSum = 0;

        boolean empty = false;

        // data in arrays can change, rewrite it
        for (int i = 0; i < tempStorage.length; i++) {
            demandSum += this.tempStorage[i];
        }

        for (int i = 0; i < tempOrder.length; i++) {
            orderSum += this.tempOrder[i];
        }

        if (demandSum == 0 || orderSum == 0) {
            empty = true;
        }
        return empty;
    }

    protected int findValidCells() {
        array = new int[matrixCost.length][matrixCost[0].length];

        for (int i = 0; i < array.length; i++) {
            if (this.tempStorage[i] == 0) { // find empty storage
                for (int j = 0; j < array[0].length; j++) {
                    this.array[i][j] = -1;// fill cells in this line (-1)
                }
            } else {
                for (int j = 0; j < array[0].length; j++) {
                    if (this.tempOrder[j] == 0) {// find empty order
                        this.array[i][j] = -1;// fill this cell (-1)
                    } else {
                        this.array[i][j] = matrixCost[i][j];
                    }
                }
            }
        }
        return minValidCell(array);
    }
    private int minValidCell(int[][] array){
        int min = array[0][0];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                if (array[i][j] != -1) {//find cell with (-1) that`s meaning that cell not uses
                    if (min > array[i][j]) {//if cell is not empty, check it
                        min = array[i][j];
                    } else if (min == -1)//min = first cell, check min(first cell) is empty?
                        min = array[i][j];
                }
            }
        }
        return min;
    }

    protected boolean potentialPossibility(int[][] deliveryMatrix) {
        boolean possible = false;
        Integer potentialCount = tempStorage.length + tempOrder.length,
                fillingCells = 0;

        for (int i = 0; i < deliveryMatrix.length; i++) {
            for (int j = 0; j < deliveryMatrix[0].length; j++) {
                if (deliveryMatrix[i][j] != 0)
                    fillingCells += 1;
            }
        }

        if (fillingCells.equals((potentialCount - 1)))
            possible = true;

        return possible;
    }

    protected void findPotential(int[][] deliveryMatrix) {
        int[][] pathMatrix = new int[deliveryMatrix.length][deliveryMatrix[0].length];
        supermarketPotential = new Integer[deliveryMatrix[0].length];
        storagePotential = new Integer[deliveryMatrix.length];

        for (int i = 0; i < deliveryMatrix.length; i++) {
            for (int j = 0; j < deliveryMatrix[0].length; j++) {
                if (deliveryMatrix[i][j] != 0)
                    pathMatrix[i][j] = matrixCost[i][j];
                else {
                    pathMatrix[i][j] = 0;
                }
            }
        }

        while (!potentialsIsFilled()) {
            for (int i = 0; i < deliveryMatrix.length; i++) {
                if (i == 0) {
                    this.storagePotential[i] = 0;

                    for (int j = 0; j < pathMatrix[0].length; j++) {
                        if (pathMatrix[i][j] != 0)
                            this.supermarketPotential[j] = pathMatrix[i][j] - storagePotential[i];
                    }
                } else {
                    for (int j = 0; j < deliveryMatrix[0].length; j++) {
                        if (pathMatrix[i][j] != 0) {
                            if (storagePotential[i] != null) {
                                this.supermarketPotential[j] = pathMatrix[i][j] - storagePotential[i];
                            } else if (supermarketPotential[j] != null) {
                                this.storagePotential[i] = pathMatrix[i][j] - supermarketPotential[j];
                            }
                        }
                    }
                }
            }
        }
    }

    protected boolean potentialsIsFilled() {
        boolean potentialsIsFilled = true;

        for (int i = 0; i < storagePotential.length; i++) {
            if (storagePotential[i] == null)
                potentialsIsFilled = false;
        }
        for (int i = 0; i < supermarketPotential.length; i++) {
            if (supermarketPotential[i] == null)
                potentialsIsFilled = false;
        }

        return potentialsIsFilled;
    }

    protected int[] indirectCost(int[][] deliveryMatrix) {
        int[][] indirectCostMatrix = new int[deliveryMatrix.length][deliveryMatrix[0].length];

        int[] possibleToProfit,
                profitValue,
                mostProfitable = new int[3];

        int count = 0,
                max = 0;

        for (int i = 0; i < deliveryMatrix.length; i++) {
            for (int j = 0; j < deliveryMatrix[0].length; j++) {
                if (deliveryMatrix[i][j] == 0) {
                    indirectCostMatrix[i][j] = storagePotential[i] + supermarketPotential[j];
                    count += 1;
                }
            }
        }
        possibleToProfit = new int[count];
        profitValue = new int[count];

        count = 0;

        for (int i = 0; i < deliveryMatrix.length; i++) {
            for (int j = 0; j < deliveryMatrix[0].length; j++) {
                if (indirectCostMatrix[i][j] != 0) {
                    if (indirectCostMatrix[i][j] > matrixCost[i][j]) {
                        possibleToProfit[count] = indirectCostMatrix[i][j];
                        profitValue[count] = indirectCostMatrix[i][j] - matrixCost[i][j];
                        count++;
                    }
                }
            }
        }

        if (count != 0) {
            max = profitValue[0];
            for (int i = 0; i < profitValue.length; i++) {
                if (profitValue[i] != 0 && profitValue[i] > max) {
                    max = profitValue[i];
                }
            }

            for (int i = 0; i < profitValue.length; i++) {
                if (profitValue[i] == max) {

                    for (int a = 0; a < indirectCostMatrix.length; a++) {
                        for (int j = 0; j < indirectCostMatrix[0].length; j++) {
                            if (possibleToProfit[i] == indirectCostMatrix[a][j]) {
                                mostProfitable[0] = a;
                                mostProfitable[1] = j;
                                mostProfitable[2] = possibleToProfit[i];
                            }
                        }
                    }
                }
            }
        } else {
            mostProfitable[0] = -1;
            mostProfitable[1] = -1;
            System.out.println("The path is maximally optimized");
        }
        return mostProfitable;
    }

    protected int[][] recalculatePath(int[] startCell, int[][] deliveryMatrix)  {

        int[][] recalculatePath = new int[deliveryMatrix.length][deliveryMatrix[0].length];
        String[][] actionWithLambda = new String[deliveryMatrix.length][deliveryMatrix[0].length];

        int[] currentCell = new int[startCell.length + 1];
        ArrayList<Integer> numbersMinusLambda = new ArrayList<>();

        int sequence = 0;

        for (int i = 0; i < deliveryMatrix.length; i++) {
            currentCell[i] = startCell[i];
        }

        for (int i = 0; i < deliveryMatrix.length; i++) {
            for (int j = 0; j < deliveryMatrix[0].length; j++) {
                if (i == startCell[0] && j == startCell[1]) {
                    optimizedMatrix[i][j] = currentCell[2];
                } else {
                    optimizedMatrix[i][j] = deliveryMatrix[i][j];
                    recalculatePath[i][j] = deliveryMatrix[i][j];
                }
            }
        }

        do {
            if (sequence % 2 == 0) {
                currentCell = findCellAtColumn(currentCell, optimizedMatrix);
                recalculatePath[currentCell[0]][currentCell[1]] = currentCell[2];
                actionWithLambda[currentCell[0]][currentCell[1]] = "-";
                numbersMinusLambda.add(currentCell[2]);

            } else {
                currentCell = findCellAtRow(currentCell, optimizedMatrix);
                recalculatePath[currentCell[0]][currentCell[1]] = currentCell[2];
                actionWithLambda[currentCell[0]][currentCell[1]] = "+";

            }
            sequence++;

        } while (currentCell[2] != startCell[2]);

        for (int i = 0; i < recalculatePath.length; i++) {
            for (int j = 0; j < recalculatePath[0].length; j++) {

                if (recalculatePath[i][j] != 0 || recalculatePath[i][j] == startCell[2]) {
                    if (startCell[0] == i && startCell[1] == j)
                        recalculatePath[i][j] -= startCell[2];

                    if (actionWithLambda[i][j] == "-") {
                        recalculatePath[i][j] -= Collections.min(numbersMinusLambda);
                    } else if (actionWithLambda[i][j] == "+") {
                        recalculatePath[i][j] += Collections.min(numbersMinusLambda);
                    }
                }
            }
        }
        return recalculatePath;
    }

    protected int[] findCellAtColumn(int[] coordinates, int[][] matrixDelivery) {
        coordinates[3] = 0;

        for (int i = coordinates[1]; i < matrixDelivery[0].length; i++) {
            for (int j = 0; j < matrixDelivery.length; j++) {

                if (matrixDelivery[j][i] != 0 && matrixDelivery[j][i] != coordinates[2]) {
                    for (int b = 0; b < matrixDelivery[0].length; b++) {
                        if (matrixDelivery[j][b] != 0 && (b != i)) {

                            coordinates[0] = j;//row
                            coordinates[1] = i;//column
                            coordinates[2] = matrixDelivery[j][i];//value
                            coordinates[3] = 1;//that mean we find cell. Like boolean true

                            b = matrixDelivery[0].length;
                            i = b;
                            j = matrixDelivery.length;
                        }
                    }
                }
            }
        }
        return coordinates;
    }

    protected int[] findCellAtRow(int[] coordinates, int[][] matrixDelivery) {
        coordinates[3] = 0;

        for (int i = coordinates[0]; i < matrixDelivery.length; i++) {
            for (int j = 0; j < matrixDelivery[0].length; j++) {
                if (matrixDelivery[i][j] != 0 && matrixDelivery[i][j] != coordinates[2]) {

                    for (int b = 0; b < matrixDelivery.length; b++) {
                        if (matrixDelivery[b][j] != 0 && (b != i)) {

                            coordinates[0] = i;//row
                            coordinates[1] = j;//column
                            coordinates[2] = matrixDelivery[i][j];//value
                            coordinates[3] = 1;//that mean we find cell. Like boolean true

                            b = matrixDelivery.length;
                            i = b;
                            j = matrixDelivery[0].length;
                        }
                    }
                }
            }
        }
        return coordinates;
    }

}
