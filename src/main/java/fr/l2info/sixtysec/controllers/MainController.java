package fr.l2info.sixtysec.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

import javax.swing.*;

public class MainController {
    @FXML
    private CheckBox test;

    public void feur(){
        System.out.println(test.isSelected());
    }
}
