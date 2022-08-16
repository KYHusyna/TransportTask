package com.design;

import com.controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Menu extends JFrame {
    //objects
    private Controller controller;
    //variables
    private JButton showCustomersAndStoragesButton;
    private JButton fillCustomerAndStorageButton;
    private JButton showNorthWestDeliveryButton;
    private JButton showMinimumElementDeliveryButton;
    private JButton showDoublePointsDeliveryButton;
    private JButton cheaperDeliveryButton;
    private JButton showCheaperDeliveryButton;
    private JButton showCostMatrix;
    private JButton fillDeliveryCost;
    private JPanel menuPanel;


    private static String storageType = "storages",
            supermarketType = "supermarkets",
            northWestCornerDelivery = "north_west_corner_delivery",
            minimumElementDelivery = "minimum_element_delivery",
            doublePointsDelivery = "double_points_delivery",
            optimizedDelivery = "optimized_delivery",
            deliveryCost = "delivery_cost";

    public Menu() {
        JPanel menu = getPanel();
        setTitle("Menu");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(450, 350);
        setVisible(true);
        setContentPane(menu);

        controller = new Controller();

        showCustomersAndStoragesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.createSupermarkets();
                controller.createStorages();
                controller.showDataInTable(supermarketType);
                controller.showDataInTable(storageType);
            }
        });

        fillCustomerAndStorageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.createSupermarkets();
                controller.createStorages();
                JOptionPane.showMessageDialog(null, "Storages and supermarkets created");
            }
        });

        fillDeliveryCost.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.createTable(deliveryCost);
                JOptionPane.showMessageDialog(null, "Delivery cost created");
            }
        });

        showCostMatrix.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.createTable(deliveryCost);
                controller.showDataInTable(deliveryCost);
            }
        });

        showNorthWestDeliveryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.createTable(northWestCornerDelivery);
                controller.showDataInTable(northWestCornerDelivery);
            }
        });

        showMinimumElementDeliveryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.createTable(minimumElementDelivery);
                controller.showDataInTable(minimumElementDelivery);
            }
        });

        showDoublePointsDeliveryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                controller.createTable(doublePointsDelivery);
                controller.showDataInTable(doublePointsDelivery);
            }
        });

        showCheaperDeliveryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.createTable(optimizedDelivery);
                controller.showDataInTable(optimizedDelivery);
            }
        });

        cheaperDeliveryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.createSupermarkets();
                controller.createStorages();
                controller.createTable(deliveryCost);
                controller.checkDemandSupplyComparison();

                controller.createTable(northWestCornerDelivery);
                controller.createTable(minimumElementDelivery);
                controller.createTable(doublePointsDelivery);
                controller.createTable(optimizedDelivery);

                controller.cheaperDeliveryAndAmount();
            }
        });
    }

    private JPanel getPanel() {
        return menuPanel;
    }
}
