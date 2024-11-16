/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.bmicalculatorapp.bmicalculatorapp;
import controller.BMICalculatorController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
