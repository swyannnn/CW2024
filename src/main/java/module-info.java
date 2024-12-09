module com.example.demo {
    // Use 'requires transitive' for JavaFX modules whose types are exposed in exported packages
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.media;
    requires java.desktop;
    requires javafx.swing;

    // Export your packages so other modules can access them
    exports com.example.demo;
}
