module com.curso.appestudantes {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires transitive java.sql;

    opens com.curso.appestudantes.model to javafx.base; // Open model package
    opens com.curso.appestudantes.controller to javafx.fxml; // Open controller package if necessary
    opens com.curso.appestudantes.view to javafx.fxml; // Open view package if necessary
    opens com.curso.appestudantes to javafx.fxml;
    exports com.curso.appestudantes;
    exports com.curso.appestudantes.controller;
}