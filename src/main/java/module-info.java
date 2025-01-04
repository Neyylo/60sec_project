module fr.l2info.sixtysec {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires mysql.connector.j;
    requires java.sql;
    requires java.naming;



    opens fr.l2info.sixtysec.controllers to javafx.fxml;
    opens fr.l2info.sixtysec to javafx.fxml;
    exports fr.l2info.sixtysec.controllers;
    exports fr.l2info.sixtysec;
}