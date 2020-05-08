package org.example;

import java.io.IOException;
import javafx.fxml.FXML;

public class indexcontroller {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("login");
    }
}