package com.controller;

import javax.swing.*;
import java.util.Scanner;

public class ControlInput {
    //variables
    private Integer value;
    private Scanner scanner = new Scanner(System.in);
    private Boolean isNumber,
            isString;

    public Integer controlIntInput(String message) {
        Integer value = 0;
        isNumber = false;

        while (!isNumber) {
            try {
                value = Integer.parseInt(JOptionPane.showInputDialog(null, message));
                isNumber = true;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Wrong input! Enter integer");
            }
        }

        return value;
    }

    public String controlStringInput(String message) {
        String value = "";
        isString = false;

        while (!isString) {
            try {
                value = JOptionPane.showInputDialog(null, message);
                isString = true;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Wrong input! Enter string");
            }
        }
        return value;
    }
}
