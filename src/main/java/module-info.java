module com.example.demo {
    // Requires JavaFX modules for your application
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.media;
    requires java.desktop;

    // Export your packages so other modules can access them
    exports com.example.demo;
    exports com.example.demo.controller;
}
