module com.example.esparzaproyecto {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
   // requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    // equires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens com.example.esparzaproyecto to javafx.fxml;
    opens com.example.esparzaproyecto.models to javafx.fxml;
    opens com.example.esparzaproyecto.controllers to javafx.fxml;
    exports com.example.esparzaproyecto;
    exports com.example.esparzaproyecto.controllers;
    exports com.example.esparzaproyecto.models;
}