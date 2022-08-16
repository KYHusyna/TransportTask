package com.design;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ShowMatrix extends JFrame {
    //objects
    Menu menu;
    //variables
    private JTable showTable;
    private JPanel showTablePanel;
    private JButton returnToMenuButton;

    public ShowMatrix(String matrixName) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                try {
                    JPanel showTable = getPanel();
                    setTitle(matrixName + " matrix");
                    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    setSize(300, 350);
                    setContentPane(showTable);
                    setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        returnToMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu = new Menu();
                dispose();
            }
        });
    }

    private JPanel getPanel() {
        return showTablePanel;
    }

    public void showDataInTable(String tableName, ArrayList<ArrayList<String>> dbData) {
        String[] columnName = new String[]{"storages_id",
                "supermarkets_id", ""};

        switch (tableName) {
            case "delivery_cost":
                columnName[2] = "delivery_cost";
                break;

            case "north_west_corner_delivery":
            case "minimum_element_delivery":
            case "double_points_delivery":
            case "optimized_delivery":
                columnName[2] = "delivery_path";
                break;

            default:
                JOptionPane.showMessageDialog(null, "Wrong parameter: " + tableName + " in Controller.showDataInTable!" +
                        "\nChoose correct support method");
        }

        String[][] valuesDatabase = new String[dbData.size()][columnName.length];

        for (int i = 0; i < valuesDatabase.length; i++) {
            ArrayList<String> row = dbData.get(i);
            for (int j = 0; j < valuesDatabase[0].length; j++) {
                valuesDatabase[i][j] = row.get(j);
            }
        }
        showTable.setModel(new DefaultTableModel(valuesDatabase, columnName));
    }
}
