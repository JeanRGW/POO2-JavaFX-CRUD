module com.curso.appestudantes {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires transitive java.sql;

    opens com.curso.appestudantes.model to javafx.base;
    opens com.curso.appestudantes.controller to javafx.fxml;
    opens com.curso.appestudantes to javafx.fxml;

    exports com.curso.appestudantes;
    exports com.curso.appestudantes.controller;
}