package com.processingData.algorithms;

import com.processingData.input.CreateStorages;
import com.processingData.input.CreateSupermarkets;
import com.processingData.input.DeliveryCost;

import java.util.ArrayList;
import java.util.Collections;

public class MethodsHelper {
//objects
    private CreateStorages storages = new CreateStorages();
    private CreateSupermarkets supermarkets = new CreateSupermarkets();
    private DeliveryCost matrixOfCost = new DeliveryCost();
    //variables
    protected Integer[] tempOrder,
            tempStorage;

    protected Integer[][] optimizedMatrix,
            array,
            matrixCost;
    private Integer[] storagePotential,
            supermarketPotential;

    public MethodsHelper() {
        tempOrder = supermarkets.getVaultsValue();
        tempStorage = storages.getVaultsValue();
        optimizedMatrix = new Integer[tempStorage.length][tempOrder.length];
        matrixCost = matrixOfCost.getDeliveryCost();
    }

    protected Integer fillCell(Integer i, Integer j) {
        Integer value = 0;

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

    protected Integer[][] setPoints(Integer[] minRow, Integer[] minColumn) {
        Integer[][] points = new Integer[matrixCost.length][matrixCost[0].length];
        points = setZeroInNullCells(points);

        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points[0].length; j++) {
                if (minRow[i] != null && minRow[i].equals(matrixCost[i][j])) {
                    points[i][j] += 1;
                }
                if (minColumn[j] != null && minColumn[j].equals(matrixCost[i][j])) {
                    points[i][j] += 1;
                }
            }
        }
        return points;
    }

    protected Integer[] rowsMin() {
        Integer[] minRow = new Integer[matrixCost.length];

        for (int i = 0; i < matrixCost.length; i++) {
            if (tempStorage[i] != null && tempStorage[i].equals(0)) {
                minRow[i] = -1;

            } else {
                if (tempOrder[0] != null && !tempOrder[0].equals(0))
                    minRow[i] = matrixCost[i][0];
                else minRow[i] = -1;

                for (int j = 0; j < matrixCost[0].length; j++) {
                    if (tempOrder[j] != null && !tempOrder[j].equals(0)) {
                        if (minRow[i] > matrixCost[i][j]) {
                            minRow[i] = matrixCost[i][j];
                        } else if (minRow[i] != null && minRow[i].equals(-1)) {
                            minRow[i] = matrixCost[i][j];
                        }
                    }
                }
            }
        }
        return minRow;
    }

    protected Integer[] columnsMin() {
        Integer[] minColumn = new Integer[matrixCost[0].length];

        for (int i = 0; i < matrixCost[0].length; i++) {
            if (tempOrder[i] != null && tempOrder[i].equals(0)) {
                minColumn[i] = -1;
            } else {

                if (tempStorage[0] != null && !tempStorage[0].equals(0)) {
                    minColumn[i] = matrixCost[0][i];
                } else minColumn[i] = -1;

                for (int j = 0; j < matrixCost.length; j++) {
                    if (tempStorage[j] != null && !tempStorage[j].equals(0)) {
                        if (minColumn[i] > matrixCost[j][i]) {
                            minColumn[i] = matrixCost[j][i];
                        } else if (minColumn[i] != null && minColumn[i].equals(-1)) {
                            minColumn[i] = matrixCost[j][i];
                        }
                    }
                }
            }
        }
        return minColumn;
    }

    protected Integer findValidCells() {
        array = new Integer[matrixCost.length][matrixCost[0].length];

        for (int i = 0; i < array.length; i++) {
            if (tempStorage[i] != null && tempStorage[i].equals(0)) { // find empty storage
                for (int j = 0; j < array[0].length; j++) {
                    array[i][j] = -1;// fill cells in this line (-1)
                }
            } else {
                for (int j = 0; j < array[0].length; j++) {
                    if (tempOrder[j] != null && tempOrder[j].equals(0)) {// find empty order
                        array[i][j] = -1;// fill this cell (-1)
                    } else {
                        array[i][j] = matrixCost[i][j];
                    }
                }
            }
        }
        return minValidCell(array);
    }

    private Integer minValidCell(Integer[][] array) {
        Integer min = array[0][0];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                if (!array[i][j].equals(-1)) {//find cell with (-1) that`s meaning that cell not uses
                    if (min > array[i][j]) {//if cell is not empty, check it
                        min = array[i][j];
                    } else if (min.equals(-1))//min = first cell, check min(first cell) is empty?
                        min = array[i][j];
                }
            }
        }
        return min;
    }

    protected Integer[] findCellAtColumn(Integer[] coordinates, Integer[][] matrixDelivery) {
        coordinates[3] = 0;

        for (int i = coordinates[1]; i < matrixDelivery[0].length; i++) {
            for (int j = 0; j < matrixDelivery.length; j++) {

                if (!matrixDelivery[j][i].equals(0) && !matrixDelivery[j][i].equals(coordinates[2])) {
                    for (int b = 0; b < matrixDelivery[0].length; b++) {
                        if (!matrixDelivery[j][b].equals(0) && (b != i)) {

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

    protected Integer[] findCellAtRow(Integer[] coordinates, Integer[][] matrixDelivery) {
        coordinates[3] = 0;

        for (int i = coordinates[0]; i < matrixDelivery.length; i++) {
            for (int j = 0; j < matrixDelivery[0].length; j++) {
                if (!matrixDelivery[i][j].equals(0) && !matrixDelivery[i][j].equals(coordinates[2])) {

                    for (int b = 0; b < matrixDelivery.length; b++) {
                        if (!matrixDelivery[b][j].equals(0) && (b != i)) {

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

    protected void findPotential(Integer[][] deliveryMatrix) {
        Integer[][] pathMatrix = new Integer[deliveryMatrix.length][deliveryMatrix[0].length];
        this.supermarketPotential = new Integer[deliveryMatrix[0].length];
        this.storagePotential = new Integer[deliveryMatrix.length];

        for (int i = 0; i < deliveryMatrix.length; i++) {
            for (int j = 0; j < deliveryMatrix[0].length; j++) {
                if (deliveryMatrix[i][j] != null && !deliveryMatrix[i][j].equals(0))
                    pathMatrix[i][j] = matrixCost[i][j];
                else {
                    pathMatrix[i][j] = 0;
                }
            }
        }

        while (!potentialsIsFilled()) {
            for (int i = 0; i < deliveryMatrix.length; i++) {
                if (i == 0) {
                    storagePotential[i] = 0;

                    for (int j = 0; j < deliveryMatrix[0].length; j++) {
                        if (deliveryMatrix[i][j] != null && !deliveryMatrix[i][j].equals(0))
                            supermarketPotential[j] = pathMatrix[i][j] - storagePotential[i];
                    }

                } else {
                    for (int j = 0; j < deliveryMatrix[0].length; j++) {

                        if (deliveryMatrix[i][j] != null && !deliveryMatrix[i][j].equals(0)) {
                            if (storagePotential[i] != null && !storagePotential[i].equals(0)) {
                                supermarketPotential[j] = pathMatrix[i][j] - storagePotential[i];
                            }
                            else if (supermarketPotential[j] != null && !supermarketPotential[j].equals(0)) {
                                storagePotential[i] = pathMatrix[i][j] - supermarketPotential[j];
                            }
                        }
                    }
                }
            }
        }
    }

    protected Boolean storageOrOrderEmpty() {
        Integer demandSum = 0,
                orderSum = 0;

        Boolean empty = false;

        // data in arrays can change, rewrite it
        for (int i = 0; i < tempStorage.length; i++) {
            demandSum += tempStorage[i];
        }

        for (int i = 0; i < tempOrder.length; i++) {
            orderSum += tempOrder[i];
        }

        if (demandSum.equals(0) || orderSum.equals(0)) {
            empty = true;
        }
        return empty;
    }

    protected Boolean potentialPossibility(Integer[][] deliveryMatrix) {
        Boolean possible = false;
        Integer potentialCount = tempStorage.length + tempOrder.length,
                fillingCells = 0;

        for (int i = 0; i < deliveryMatrix.length; i++) {
            for (int j = 0; j < deliveryMatrix[0].length; j++) {
                if (!deliveryMatrix[i][j].equals(0))
                    fillingCells += 1;
            }
        }

        if (fillingCells.equals((potentialCount - 1)))
            possible = true;

        return possible;
    }

    protected Boolean potentialsIsFilled() {
        Boolean potentialsIsFilled = true;

        for (Integer integer : this.storagePotential) {
            if (integer == null) {
                potentialsIsFilled = false;
            }
        }

        for (Integer integer : this.supermarketPotential) {
            if (integer == null) {
                potentialsIsFilled = false;
            }
        }
        return potentialsIsFilled;
    }

    protected Integer[] indirectCost(Integer[][] deliveryMatrix) {
        Integer[][] indirectCostMatrix = new Integer[deliveryMatrix.length][deliveryMatrix[0].length];

        Integer[] possibleToProfit,
                profitValue,
                mostProfitable = new Integer[3];

        Integer count = 0,
                max = 0;

        for (int i = 0; i < deliveryMatrix.length; i++) {
            for (int j = 0; j < deliveryMatrix[0].length; j++) {
                if (deliveryMatrix[i][j] != null && deliveryMatrix[i][j].equals(0)) {
                    indirectCostMatrix[i][j] = storagePotential[i] + supermarketPotential[j];
                    count += 1;
                }
            }
        }
        possibleToProfit = new Integer[count];
        profitValue = new Integer[count];

        count = 0;

        for (int i = 0; i < deliveryMatrix.length; i++) {
            for (int j = 0; j < deliveryMatrix[0].length; j++) {

                if (indirectCostMatrix[i][j] != null && !indirectCostMatrix[i][j].equals(0) &&
                        indirectCostMatrix[i][j] > matrixCost[i][j]) {

                    possibleToProfit[count] = indirectCostMatrix[i][j];
                    profitValue[count] = indirectCostMatrix[i][j] - matrixCost[i][j];
                    count++;
                }
            }
        }

        if (!count.equals(0)) {
            max = profitValue[0];
            for (Integer integer : profitValue) {
                if (integer != null && integer > max) {
                    max = integer;
                }
            }

            for (int i = 0; i < profitValue.length; i++) {
                if (profitValue[i] != null && profitValue[i].equals(max)) {

                    for (int a = 0; a < indirectCostMatrix.length; a++) {
                        for (int j = 0; j < indirectCostMatrix[0].length; j++) {
                            if (indirectCostMatrix[a][j] != null &&
                                    indirectCostMatrix[a][j].equals(possibleToProfit[i])) {

                                mostProfitable[0] = a;
                                mostProfitable[1] = j;
                                mostProfitable[2] = possibleToProfit[i];
                            }
                        }
                    }
                }
            }
        } else {
            mostProfitable[0] = null;
            mostProfitable[1] = null;
            System.out.println("The path is maximally optimized");
        }

        return mostProfitable;
    }

    protected Integer[][] recalculatePath(Integer[] startCell, Integer[][] deliveryMatrix) {

        Integer[][] recalculatePath = new Integer[deliveryMatrix.length][deliveryMatrix[0].length];
        String[][] actionWithLambda = new String[deliveryMatrix.length][deliveryMatrix[0].length];

        Integer[] currentCell = new Integer[startCell.length + 1];
        ArrayList<Integer> numbersMinusLambda = new ArrayList<>();

        Integer sequence = 0;

        for (int i = 0; i < deliveryMatrix.length; i++) {
            currentCell[i] = startCell[i];
        }

        for (int i = 0; i < deliveryMatrix.length; i++) {
            for (int j = 0; j < deliveryMatrix[0].length; j++) {
                if (startCell[0] != null && startCell[0].equals(i) &&
                        startCell[1] != null && startCell[1].equals(j)) {
                    optimizedMatrix[i][j] = startCell[2];
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

        } while (!currentCell[2].equals(startCell[2]));

        for (int i = 0; i < recalculatePath.length; i++) {
            for (int j = 0; j < recalculatePath[0].length; j++) {

                if (recalculatePath[i][j] != null && !recalculatePath[i][j].equals(0) || recalculatePath[i][j].equals(startCell[2])) {
                    if (startCell[0].equals(i) && startCell[1].equals(j))
                        recalculatePath[i][j] -= startCell[2];

                    if (actionWithLambda[i][j] != null && actionWithLambda[i][j].equals("-")) {
                        recalculatePath[i][j] -= Collections.min(numbersMinusLambda);
                    } else if (actionWithLambda[i][j] != null && actionWithLambda[i][j].equals("+")) {
                        recalculatePath[i][j] += Collections.min(numbersMinusLambda);
                    }
                }
            }
        }

        return recalculatePath;
    }

    protected Integer[][] setZeroInNullCells(Integer[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                if (array[i][j] == null) {
                    array[i][j] = 0;
                }
            }
        }
        return array;
    }
}
