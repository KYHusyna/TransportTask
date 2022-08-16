package com.design;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Results extends JFrame{
    //objects
    Menu menu;
    //variables
    private JPanel resultPanel;
    private JLabel cheaperDelivery;
    private JLabel deliveryPlanFieldName;
    private JPanel DeliveryPlan;
    private JLabel DeliveryAmount;
    public JLabel DeliveryText;
    private JButton returnToMenuButton;
    public JTextArea deliveryPlanTextArea;

    public Results(){
        JPanel result = getPanel();
        setTitle("Menu");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(300, 350);
        setVisible(true);
        setContentPane(result);

        returnToMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu = new Menu();
                dispose();
            }
        });
    }

    private JPanel getPanel(){
        return  resultPanel;
    }
}
