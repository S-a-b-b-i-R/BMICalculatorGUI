/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.bmicalculatorapp.bmicalculatorapp;
import controller.BMICalculatorController;
import javax.swing.*;
import view.BMICalculatorUI;
/**
 *
 * @author sabbir-ahmed
 */
public class BMICalculatorApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BMICalculatorController controller = new BMICalculatorController();
            BMICalculatorUI ui = new BMICalculatorUI(controller);
            ui.setVisible(true);
        });
    }
}
