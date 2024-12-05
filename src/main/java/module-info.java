module fr.l2info.sixtysec {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens fr.l2info.sixtysec to javafx.fxml;
    exports fr.l2info.sixtysec;
}