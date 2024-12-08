module com.example.demo {
    // Use 'requires transitive' for JavaFX modules whose types are exposed in exported packages
    requires transitive javafx.controls;
    requires transitive javafx.graphics;
    requires transitive javafx.media;
    requires transitive java.desktop;
    requires transitive javafx.swing;

    // Export your packages so other modules can access them
    exports com.example.demo;
}
