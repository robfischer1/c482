package org.robfischer.c482.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.robfischer.c482.model.InHouse;
import org.robfischer.c482.model.Inventory;
import org.robfischer.c482.model.Outsourced;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static org.robfischer.c482.MainApplication.nextPartId;

/**
 * The Controller for the Add Part View.
 * @author Rob Fischer
 */
public class AddPartController implements Initializable {

    /**
     * Label value which changes based on part type.
     */
    public Label partAddLabelType;
    /**
     * Label value which changes based on the selected controller.
     */
    public Label partLabel;
    /**
     * Add an Outsourced Part.
     */
    public RadioButton partAddOutsourced;
    /**
     * Add an InHouse Part.
     */
    public RadioButton partAddInHouse;
    /**
     * ToggleGroup for the type of part to add.
     */
    public ToggleGroup partAddType;
    /**
     * The system-generated PartID
     */
    public TextField partAddTextId;
    /**
     * The Part Name
     */
    public TextField partAddTextName;
    /**
     * The Part Inventory
     */
    public TextField partAddTextStock;
    /**
     * The Part Price.
     */
    public TextField partAddTextPrice;
    /**
     * The Part Maximum Quantity
     */
    public TextField partAddTextMax;
    /**
     * Additional attribute (Machine ID / Company Name) based on selected part type.
     */
    public TextField partAddTextType;
    /**
     * The Part Minimum Quantity.
     */
    public TextField partAddTextMin;

    /**
     * Changes the label to Machine ID when In House is selected.
     */
    public void addInHouse() {
        partAddLabelType.setText("Machine ID");
    }

    /**
     * Changes the label to Company Name when Outsourced is selected.
     */
    public void addOutsourced() {
        partAddLabelType.setText("Company Name");
    }

    /**
     * Returns to the Main View when the user clicks cancel.
     *
     * @param actionEvent The ActionEvent which triggered this method.
     */
    @FXML
    protected void addPartCancel(ActionEvent actionEvent) throws IOException {
        openMain(actionEvent);
    }

    /**
     * Validates user input and creates a new Part from provided data.
     *
     * @param actionEvent The ActionEvent which triggered this method.
     */
    public void addPartSave(ActionEvent actionEvent) throws IOException {

        //Get the value of the form fields.
        String textID = partAddTextId.getText();
        String textName = partAddTextName.getText();
        String textStock = partAddTextStock.getText();
        String textPrice = partAddTextPrice.getText();
        String textMax = partAddTextMax.getText();
        String textMin = partAddTextMin.getText();
        String textType = partAddTextType.getText();

        //Make sure none of the fields are blank.
        if (isInvalid(textID, "How did this even happen?")) { return; }
        if (isInvalid(textName, "Please enter a part name.")) { return; }
        if (isInvalid(textStock, "Please enter a quantity.")) { return; }
        if (isInvalid(textPrice, "Please enter a price.")) { return; }
        if (isInvalid(textMax, "Please enter a maximum quantity.")) { return; }
        if (isInvalid(textMin, "Please enter a minimum quantity.")) { return; }


        if (textType.isBlank()) {
            if (partAddInHouse.isSelected()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a machine id.");
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a company name.");
                alert.showAndWait();
            }
            return;
        }

        //Convert user data to the appropriate formats.
        String field = null;
        try {
            int id = Integer.parseInt(textID);

            field = "quantity.";
            int stock = Integer.parseInt(textStock);

            field = "price.";
            double price = Double.parseDouble(textPrice);

            field = "maximum quantity.";
            int max = Integer.parseInt(textMax);

            field = "minimum quantity.";
            int min = Integer.parseInt(textMin);

            field = null;

            //Validate Stock is within acceptable range
            if (min > max) {
                throw new NumberFormatException("Minimum quantity must be below maximum quantity.");
            }
            else if (stock < min) {
                throw new NumberFormatException("Available stock is less than the specified minimum");
            } else if (stock > max) {
                throw new NumberFormatException("Stock on hand exceeds capacity.");
            }

            if (partAddInHouse.isSelected()) {
                field = "machine ID.";
                int machineID = Integer.parseInt(textType);
                field = null;
                addPart(id, textName, price, stock, min, max, machineID);
            }
            else {
                addPart(id, textName, price, stock, min, max, textType);
            }
            openMain(actionEvent);

        }
        catch (NumberFormatException n) {
            if (field != null) {
                String message = "Please enter a valid " + field;
                Alert alert = new Alert(Alert.AlertType.ERROR, message);
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR, n.getMessage());
                alert.showAndWait();
            }
        }

    }


    /**
     * Adds an InHouse Part to inventory and increments the nextProductId
     *
     * @param id        Product ID
     * @param textName  Product Name
     * @param price     Product Price
     * @param stock     Product Inventory
     * @param min       Minimum Inventory
     * @param max       Maximum Inventory
     * @param machineID The Machine ID
     */
    protected void addPart (int id, String textName, double price, int stock, int min, int max, int machineID) {
        InHouse part = new InHouse(id, textName, price, stock, min, max, machineID);
        nextPartId += 2;
        Inventory.addPart(part);
    }

    /**
     * Adds an Outsourced Part to inventory and increments the nextProductId
     *
     * @param id       Product ID
     * @param textName Product Name
     * @param price    Product Price
     * @param stock    Product Inventory
     * @param min      Minimum Inventory
     * @param max      Maximum Inventory
     * @param textType Company Name
     */
    protected void addPart (int id, String textName, double price, int stock, int min, int max, String textType) {
        Outsourced part = new Outsourced(id, textName, price, stock, min, max, textType);
        nextPartId += 2;
        Inventory.addPart(part);
    }



    /**
     * Displays the desired error message if the provided string is blank.
     *
     * @param str The string to validate.
     * @param error The error message to display.
     * @return True if string is blank, false otherwise.
     */
    private boolean isInvalid(String str, String error) {
        if (str.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, error);
            alert.showAndWait();
            return true;
        }
        return false;
    }

    /**
     * Returns to the Main View.
     *
     * @param actionEvent The ActionEvent which triggered this method.
     */
    @FXML
    protected void openMain(ActionEvent actionEvent) throws IOException {
        //Load the FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/robfischer/c482/view/main-view.fxml"));
        Parent root = loader.load();

        //Set the stage
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 820, 400);
        stage.setTitle("C482 Performance Assessment");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Initializes the controller for the Add Product View and populates the TableViews.
     * @param url Unused
     * @param resourceBundle Unused
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partAddType = new ToggleGroup();
        partAddOutsourced.setToggleGroup(partAddType);
        partAddInHouse.setToggleGroup(partAddType);

        //Use the next part ID
        partAddTextId.setText(String.valueOf(nextPartId));
    }
    
}