module com.curso.appestudantes {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires transitive java.sql;

    opens com.curso.appestudantes to javafx.fxml;
    exports com.curso.appestudantes;
    exports com.curso.appestudantes.controller;
    opens com.curso.appestudantes.controller to javafx.fxml;
}