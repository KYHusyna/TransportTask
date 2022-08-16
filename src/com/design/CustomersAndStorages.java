package com.design;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CustomersAndStorages extends JFrame {
    //objects
    private Menu menu;
    //variables
    public JTable supermarkets;
    private JPanel rootPanel;
    private JTable storages;
    private JButton ReturnToMenu;

    public CustomersAndStorages() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    JPanel root = getPanel();

                    setTitle("Storages and supermarkets");
                    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    setSize(300, 350);
                    setContentPane(root);
                    setLocationRelativeTo(null);
                    setVisible(false);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        ReturnToMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu = new Menu();
                dispose();
            }
        });
    }

    private JPanel getPanel() {
        return rootPanel;
    }

    public void showDataInTable(String type, ArrayList<ArrayList<String>> dbData) {
        String[] columnName = new String[]
                {type + "_id",
                        type + "_name",
                        type + "_value"};

        String[][] valuesDatabase = new String[dbData.size()][columnName.length];

        for (int i = 0; i < valuesDatabase.length; i++) {
            ArrayList<String> row = dbData.get(i);
            for (int j = 0; j < valuesDatabase[0].length; j++) {
                valuesDatabase[i][j] = row.get(j);
            }
        }

        if (type.equals("supermarkets")) {
            supermarkets.setModel(new DefaultTableModel(valuesDatabase, columnName));
        } else if (type.equals("storages")) {
            storages.setModel(new DefaultTableModel(valuesDatabase, columnName));
        }
    }

}
