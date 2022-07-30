package Com.CalculatingData;

import Com.InputData.ChangeMatrix;
import Com.InputData.CreateStorages;
import Com.InputData.MatrixCost;
import Com.Output_data.OutputData;

public class DemandSupplyСomparison {

    //object
    private CreateStorages storages = new CreateStorages();
    private ChangeMatrix changeMatrix = new ChangeMatrix();
    private OutputData outputData = new OutputData();
    private MatrixCost matrixCost = new MatrixCost();

    //variables
    private int[] order,
            storageBalances;

    private String[] supermarket,
            storage;

    public void demandSupply() {
        order = storages.getOrder();
        storageBalances = storages.getStorageBalance();
        supermarket = storages.getSupermarket();
        storage = storages.getStorage();

        int orderSum = 0,
                demandSum = 0,
                difference = 0;


        for (int i = 0; i < storageBalances.length; i++)
            demandSum += storageBalances[i];

        for (int i = 0; i < order.length; i++)
            orderSum += order[i];

        if (demandSum != orderSum) {

            if (demandSum > orderSum) {
                System.out.println("\nDemand is bigger than order " +
                        "\n demand:" + demandSum + " order:" + orderSum);

                difference = demandSum - orderSum;
                changeMatrix.change_matrix(difference, 0, order, supermarket);

            } else {
                System.out.println("\nOrder is bigger than demand \n demand:" + demandSum + " order:" + orderSum);

                difference = orderSum - demandSum;
                changeMatrix.change_matrix(difference, 1, storageBalances, storage);
            }

            System.out.print("\n!!!New matrix!!!");
            outputData.matrixOutput(matrixCost.getMatrixCost(), "cost");
        }
    }
}
