package com.controller;

import com.DAO.CrudDAO;
import com.design.CustomersAndStorages;
import com.design.Results;
import com.design.ShowMatrix;
import com.processingData.algorithms.*;
import com.processingData.input.CreateStorages;
import com.processingData.input.CreateSupermarkets;
import com.processingData.input.DeliveryCost;
import com.processingData.output.MostOptimizingDelivery;
import com.processingData.output.TransportationAmount;

import javax.swing.*;

public class Controller {

    /*
    here we give and take data from algorithms
    give and take this data to DB
    and after that we give results to view
    */

    //input package
    private CreateSupermarkets createSupermarkets = new CreateSupermarkets();
    private CreateStorages createStorages = new CreateStorages();
    private DeliveryCost deliveryCost = new DeliveryCost();

    //output package
    private TransportationAmount deliveryAmount = new TransportationAmount();
    private MostOptimizingDelivery mostOptimizingDelivery = new MostOptimizingDelivery();

    //algorithms package
    private DemandSupplyComparison demandSupply = new DemandSupplyComparison();
    private NorthWestCornerAlgorithm northWestCornerAlgorithm = new NorthWestCornerAlgorithm();
    private MinimumElementAlgorithm minimumElementAlgorithm = new MinimumElementAlgorithm();
    private DoublePointsAlgorithm doublePointsAlgorithm = new DoublePointsAlgorithm();

    //design package
    private  CustomersAndStorages customersAndStorages = new CustomersAndStorages();
    private ShowMatrix showMatrix;

    private Results results;

    //DAO
    private CrudDAO crudDAO = new CrudDAO();

    //variables
    private Integer[] orders, storageBalances;
    private String[] supermarkets, storages;
    private String storageType = "storages", supermarketType = "supermarkets";

    public void cheaperDeliveryAndAmount() {
        Integer[][] matrix = mostOptimizingDelivery.getMostOptimizingDelivery();
        Integer amount = deliveryAmount.getTransportationAmount(matrix, deliveryCost.getDeliveryCost());
        String delivery = "";
        results = new Results();

        results.DeliveryText.setText(results.DeliveryText.getText() + " " + amount);
        storages = createStorages.getVaultsName();
        supermarkets = createSupermarkets.getVaultsName();

        System.out.println("Show most cheaper delivery");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] != null && !matrix[i][j].equals(0)) {
                   delivery = "From " + storages[i] + " to " + supermarkets[j] + " transport:" + matrix[i][j] + " things\n";

                    results.deliveryPlanTextArea.setText(results.deliveryPlanTextArea.getText()+delivery);
                }
            }
        }
        System.out.println("delivery amount:" + amount);
    }

    //create
    public void createSupermarkets() {
        orders = createSupermarkets.getVaultsValue();
        supermarkets = createSupermarkets.getVaultsName();

        //speak to crudDao to create and fill supermarkets table
        crudDAO.createVaultsTable(orders, supermarkets, supermarketType);

        System.out.println("Supermarket table create successful");
    }

    public void createStorages() {
        storageBalances = createStorages.getVaultsValue();
        storages = createStorages.getVaultsName();

        //speak to crudDao to create and fill supermarkets table
        crudDAO.createVaultsTable(storageBalances, storages, storageType);

        System.out.println("Storage table create successful");
    }

    public void createTable(String tableName) {
        Integer[][] matrix = null;

        //choose delivery path by parameter
        switch (tableName) {
            case "north_west_corner_delivery":
                matrix = northWestCornerAlgorithm.getDeliveryPath();
                break;
            case "minimum_element_delivery":
                matrix = minimumElementAlgorithm.getDeliveryPath();
                break;
            case "double_points_delivery":
                matrix = doublePointsAlgorithm.getDeliveryPath();
                break;
            case "optimized_delivery":
                matrix = mostOptimizingDelivery.getMostOptimizingDelivery();
                break;
            case "delivery_cost":
                matrix = deliveryCost.getDeliveryCost();
                break;

            default:
                JOptionPane.showMessageDialog(null, "Wrong parameter: " + tableName + " in Controller.createTable!\n" +
                        " Choose correct support method");
        }

        //speak to crudDao to create and fill delivery path
        crudDAO.createDeliveryTable(tableName, matrix);
    }

    //update
    public void checkDemandSupplyComparison() {
        //if demand not equals supply, calling crudDao inside demandSupply, and creating new field
        if (demandSupply.demandSupplyComparison()) {
            //take from algorithm new matrix and update database delivery cost matrix
            crudDAO.createDeliveryTable("delivery_cost", deliveryCost.getDeliveryCost());
            //update database tables
            createStorages();
            createSupermarkets();
        }
    }

    //read
    public void showDataInTable(String tableName) {
        switch (tableName) {
            case "storages":
            case "supermarkets":
                customersAndStorages.setVisible(true);
                customersAndStorages.showDataInTable(tableName, crudDAO.getTableData(tableName));// give value to UI
                break;

            case "delivery_cost":
            case "north_west_corner_delivery":
            case "minimum_element_delivery":
            case "double_points_delivery":
            case "optimized_delivery":
                showMatrix = new ShowMatrix(tableName);
                //speak to crudDao to create and fill delivery path
                showMatrix.showDataInTable(tableName, crudDAO.getTableData(tableName));
                break;

            default:
                JOptionPane.showMessageDialog(null, "Wrong parameter" + tableName + " in Controller.showDataInTable!" +
                        "\nChoose correct matrix to show");
        }
    }

}
