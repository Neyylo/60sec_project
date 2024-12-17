module fr.l2info.sixtysec {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens fr.l2info.sixtysec.controllers to javafx.fxml;
    opens fr.l2info.sixtysec to javafx.fxml;
    exports fr.l2info.sixtysec;
    exports fr.l2info.sixtysec.controllers;
}