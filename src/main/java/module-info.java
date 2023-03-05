module org.robfischer.c482 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.robfischer.c482 to javafx.fxml;
    exports org.robfischer.c482;
    exports org.robfischer.c482.model;
    opens org.robfischer.c482.model to javafx.fxml;
    exports org.robfischer.c482.controller;
    opens org.robfischer.c482.controller to javafx.fxml;
}