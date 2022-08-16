package com.processingData.output;

import com.processingData.algorithms.*;
import com.processingData.input.DeliveryCost;

public class MostOptimizingDelivery {
    //objects
    static DeliveryCost deliveryCost = new DeliveryCost();
    static OutputData outputData = new OutputData();
    static NorthWestCornerAlgorithm northWestCornerAlgorithm = new NorthWestCornerAlgorithm();
    static MinimumElementAlgorithm minimumElementAlgorithm = new MinimumElementAlgorithm();
    static DoublePointsAlgorithm doublePointsAlgorithm = new DoublePointsAlgorithm();
    static PotentialMethodAlgorithm potentialMethodAlgorithm = new PotentialMethodAlgorithm();
    static TransportationAmount deliveryAmount = new TransportationAmount();

    //variables
    private Integer[][] mostOptimizingDelivery = null;

    private Integer[][] mostOptimizingDelivery() {

        String mostOptimizingDeliveryName = "";

        Integer northWestCornerDeliveryAmount,
                minimumElementDeliveryAmount,
                doublePointsDeliveryAmount,
                mostOptimizingDeliveryAmount = 0;

        System.out.println("------Find most optimized method-----");

        northWestCornerDeliveryAmount = deliveryAmount.getTransportationAmount(
                potentialMethodAlgorithm.calculateDeliveryPath(northWestCornerAlgorithm.getDeliveryPath()), deliveryCost.getDeliveryCost());

        minimumElementDeliveryAmount = deliveryAmount.getTransportationAmount(
                potentialMethodAlgorithm.calculateDeliveryPath(minimumElementAlgorithm.getDeliveryPath()), deliveryCost.getDeliveryCost());

        doublePointsDeliveryAmount = deliveryAmount.getTransportationAmount(
                potentialMethodAlgorithm.calculateDeliveryPath(doublePointsAlgorithm.getDeliveryPath()), deliveryCost.getDeliveryCost());

        if (northWestCornerDeliveryAmount <= minimumElementDeliveryAmount && northWestCornerDeliveryAmount <= doublePointsDeliveryAmount) {

            mostOptimizingDeliveryName = "north west corner";
            mostOptimizingDeliveryAmount = northWestCornerDeliveryAmount;
            mostOptimizingDelivery = potentialMethodAlgorithm.calculateDeliveryPath(northWestCornerAlgorithm.getDeliveryPath());

        } else if (northWestCornerDeliveryAmount >= minimumElementDeliveryAmount && minimumElementDeliveryAmount <= doublePointsDeliveryAmount) {

            mostOptimizingDeliveryName = "minimum element";
            mostOptimizingDeliveryAmount = minimumElementDeliveryAmount;
            mostOptimizingDelivery = potentialMethodAlgorithm.calculateDeliveryPath(minimumElementAlgorithm.getDeliveryPath());

        } else if (northWestCornerDeliveryAmount >= doublePointsDeliveryAmount && minimumElementDeliveryAmount >= doublePointsDeliveryAmount) {

            mostOptimizingDeliveryName = "double points";
            mostOptimizingDeliveryAmount = doublePointsDeliveryAmount;
            mostOptimizingDelivery = potentialMethodAlgorithm.calculateDeliveryPath(doublePointsAlgorithm.getDeliveryPath());
        }

        if (northWestCornerDeliveryAmount == doublePointsDeliveryAmount && minimumElementDeliveryAmount == doublePointsDeliveryAmount) {
            System.out.println("all deliveries is equal! delivery amount:" + mostOptimizingDeliveryAmount);
        } else {
            System.out.println("most optimizing delivery is:" + mostOptimizingDeliveryName + " delivery amount:" + mostOptimizingDeliveryAmount);
        }

        outputData.matrixOutput(mostOptimizingDelivery, "optimized ");

        return mostOptimizingDelivery;
    }

    public Integer[][] getMostOptimizingDelivery() {
        if (mostOptimizingDelivery == null) {
            mostOptimizingDelivery();
        }
        return mostOptimizingDelivery;
    }
}
