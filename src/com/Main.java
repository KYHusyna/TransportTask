package com;

import com.calculating.algorithms.*;
import com.input.*;
import com.output.*;

public class Main {
    //objects

     static MatrixCost matrixCost = new MatrixCost();
     static OutputData outputData = new OutputData();
     static DemandSupplyComparison demandSupply = new DemandSupplyComparison();
     static NorthWestCornerAlgorithm northWestCornerAlgorithm = new NorthWestCornerAlgorithm();
     static MinimumElementAlgorithm minimumElementAlgorithm = new MinimumElementAlgorithm();
     static DoublePointsAlgorithm doublePointsAlgorithm = new DoublePointsAlgorithm();
     static PotentialMethodAlgorithm potentialMethodAlgorithm = new PotentialMethodAlgorithm();
     static TransportationAmount deliveryAmount = new TransportationAmount();

    public static void main(String[] args) throws Exception{

        outputData.matrixOutput(matrixCost.getMatrixCost(), "cost");
        demandSupply.demandSupplyComparison();

        outputPathAndAmount(northWestCornerAlgorithm.getDeliveryPath());

        outputPathAndAmount(minimumElementAlgorithm.getDeliveryPath());

        outputPathAndAmount(doublePointsAlgorithm.getDeliveryPath());

        mostOptimizingDelivery();
    }


    private static void outputPathAndAmount(Integer[][] deliveryMatrix) {
        outputData.matrixOutput(deliveryMatrix, "delivery");
        deliveryAmount.getTransportationAmount(deliveryMatrix, matrixCost.getMatrixCost());
    }
    private static Integer[][] mostOptimizingDelivery() {

        String mostOptimizingDelivery = "";

        int northWestCornerDeliveryAmount,
                minimumElementDeliveryAmount,
                doublePointsDeliveryAmount,
                mostOptimizingDeliveryAmount = 0;

        Integer[][] delivery = new Integer[][]{};
        System.out.println("------Find most optimized method-----");

        northWestCornerDeliveryAmount = deliveryAmount.getTransportationAmount(
                potentialMethodAlgorithm.calculateDeliveryPath(northWestCornerAlgorithm.getDeliveryPath()), matrixCost.getMatrixCost());

        minimumElementDeliveryAmount = deliveryAmount.getTransportationAmount(
                potentialMethodAlgorithm.calculateDeliveryPath(minimumElementAlgorithm.getDeliveryPath()), matrixCost.getMatrixCost());

        doublePointsDeliveryAmount = deliveryAmount.getTransportationAmount(
                potentialMethodAlgorithm.calculateDeliveryPath(doublePointsAlgorithm.getDeliveryPath()), matrixCost.getMatrixCost());

        if (northWestCornerDeliveryAmount <= minimumElementDeliveryAmount && northWestCornerDeliveryAmount <= doublePointsDeliveryAmount) {

            mostOptimizingDelivery = "north west corner";
            mostOptimizingDeliveryAmount = northWestCornerDeliveryAmount;
            delivery = potentialMethodAlgorithm.calculateDeliveryPath(northWestCornerAlgorithm.getDeliveryPath());

        } else if (northWestCornerDeliveryAmount >= minimumElementDeliveryAmount && minimumElementDeliveryAmount <= doublePointsDeliveryAmount) {

            mostOptimizingDelivery = "minimum element";
            mostOptimizingDeliveryAmount = minimumElementDeliveryAmount;
            delivery = potentialMethodAlgorithm.calculateDeliveryPath(minimumElementAlgorithm.getDeliveryPath());

        } else if (northWestCornerDeliveryAmount >= doublePointsDeliveryAmount && minimumElementDeliveryAmount >= doublePointsDeliveryAmount) {

            mostOptimizingDelivery = "double points";
            mostOptimizingDeliveryAmount = doublePointsDeliveryAmount;
            delivery = potentialMethodAlgorithm.calculateDeliveryPath(doublePointsAlgorithm.getDeliveryPath());
        }

        if (northWestCornerDeliveryAmount == doublePointsDeliveryAmount && minimumElementDeliveryAmount == doublePointsDeliveryAmount) {
            System.out.println("all deliveries is equal! delivery amount:" + mostOptimizingDeliveryAmount);
        } else {
            System.out.println("most optimizing delivery is:" + mostOptimizingDelivery + " delivery amount:" + mostOptimizingDeliveryAmount);
        }

        outputData.matrixOutput(delivery, "optimized ");

        return delivery;
    }

}