package Com;

import Com.CalculatingData.*;
import Com.InputData.CreateStorages;
import Com.InputData.MatrixCost;
import Com.Output_data.OutputData;
import Com.Output_data.TransportationAmount;

import java.util.Arrays;

public class Main {
    //objects
    static MatrixCost matrixCost = new MatrixCost();
    static OutputData outputData = new OutputData();
    static DemandSupplyСomparison demandSupply = new DemandSupplyСomparison();
    static NorthWestCorner northWestCorner = new NorthWestCorner();
    static MinimumElement minimumElement = new MinimumElement();
    static DoublePoints doublePoints = new DoublePoints();
    static PotentialMethod potentialMethod = new PotentialMethod();
    static TransportationAmount deliveryAmount = new TransportationAmount();

    public static void main(String[] args) {

        outputData.matrixOutput(matrixCost.getMatrixCost(), "cost");
        demandSupply.demandSupply();

        CreateStorages storages = new CreateStorages();
        System.out.println(Arrays.toString(storages.getStorage()));

        outputPathAndAmount(northWestCorner.getDeliveryMatrix());

        outputPathAndAmount(minimumElement.getDeliveryMatrix());

        outputPathAndAmount(doublePoints.getDeliveryMatrix());

        mostOptimizingDelivery();
    }

    private static void outputPathAndAmount(int[][] deliveryMatrix) {
        outputData.matrixOutput(deliveryMatrix, "delivery");
        deliveryAmount.getTransportationAmount(deliveryMatrix, matrixCost.getMatrixCost());
    }

    private static int[][] mostOptimizingDelivery() {
        String mostOptimizingDelivery = "";
        int northWestCornerDeliveryAmount,
                minimumElementDeliveryAmount,
                doublePointsDeliveryAmount,
                mostOptimizingDeliveryAmount = 0;
        int[][] delivery = new int[][]{};
        System.out.println("------Find most optimized method-----");

        northWestCornerDeliveryAmount = deliveryAmount.getTransportationAmount(
                potentialMethod.findDeliveryPath(northWestCorner.getDeliveryMatrix()), matrixCost.getMatrixCost());

        minimumElementDeliveryAmount = deliveryAmount.getTransportationAmount(
                potentialMethod.findDeliveryPath(minimumElement.getDeliveryMatrix()), matrixCost.getMatrixCost());

        doublePointsDeliveryAmount = deliveryAmount.getTransportationAmount(
                potentialMethod.findDeliveryPath(doublePoints.getDeliveryMatrix()), matrixCost.getMatrixCost());

        if (northWestCornerDeliveryAmount <= minimumElementDeliveryAmount && northWestCornerDeliveryAmount <= doublePointsDeliveryAmount) {

            mostOptimizingDelivery = "north west corner";
            mostOptimizingDeliveryAmount = northWestCornerDeliveryAmount;
            delivery = potentialMethod.findDeliveryPath(northWestCorner.getDeliveryMatrix());

        } else if (northWestCornerDeliveryAmount >= minimumElementDeliveryAmount && minimumElementDeliveryAmount <= doublePointsDeliveryAmount) {

            mostOptimizingDelivery = "minimum element";
            mostOptimizingDeliveryAmount = minimumElementDeliveryAmount;
            delivery =potentialMethod.findDeliveryPath(minimumElement.getDeliveryMatrix());

        } else if (northWestCornerDeliveryAmount >= doublePointsDeliveryAmount && minimumElementDeliveryAmount >= doublePointsDeliveryAmount) {

            mostOptimizingDelivery = "double points";
            mostOptimizingDeliveryAmount = doublePointsDeliveryAmount;
            delivery = potentialMethod.findDeliveryPath(doublePoints.getDeliveryMatrix());
        }

        if (northWestCornerDeliveryAmount == doublePointsDeliveryAmount && minimumElementDeliveryAmount == doublePointsDeliveryAmount) {
            System.out.println("all deliveries is equal! delivery amount:" + mostOptimizingDeliveryAmount);
        } else {
            System.out.println("most optimizing delivery is:" + mostOptimizingDelivery + " delivery amount:" + mostOptimizingDeliveryAmount);
        }

        System.out.println("\nDelivery path:");
        for (int i = 0; i < delivery.length; i++) {
            for (int j = 0; j < delivery[0].length; j++) {
                System.out.print(delivery[i][j] + " ");
            }
            System.out.println();
        }

        return delivery;
    }
}