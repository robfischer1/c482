package org.robfischer.c482.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.robfischer.c482.model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static org.robfischer.c482.MainApplication.nextProductId;

/**
 * The Controller for the Add Product View.
 * @author Rob Fischer
 */
public class AddProductController implements Initializable {

    /**
     * The product.
     */
    protected Product theProduct = new Product();

    /**
     * Label value which changes based on the selected controller.
     */
    public Label productLabel;

    /**
     * The all Parts table.
     */
    public TableView<Part> allPartsTable;
    /**
     * The Part ID Column (All Parts)
     */
    public TableColumn<Part, String> allPartsID;
    /**
     * The Part Name Column (All Parts)
     */
    public TableColumn<Part, String> allPartsName;
    /**
     * The Part Inventory Column (All Parts)
     */
    public TableColumn<Part, String> allPartsStock;
    /**
     * The Part Price/Cost Column (All Parts)
     */
    public TableColumn<Part, String> allPartsPrice;

    /**
     * The Associated Parts table.
     */
    public TableView<Part> assocPartsTable;
    /**
     * The Part ID Column (Associated Parts)
     */
    public TableColumn<Part, String> assocPartsID;
    /**
     * The Part Name Column (Associated Parts)
     */
    public TableColumn<Part, String> assocPartsName;
    /**
     * The Part Inventory Column (Associated Parts)
     */
    public TableColumn<Part, String> assocPartsStock;
    /**
     * The Part Cost/Price Column (Associated Parts)
     */
    public TableColumn<Part, String> assocPartsPrice;

    /**
     * The part search field.
     */
    public TextField allPartSearchField;

    /**
     * The Product ID.
     */
    public TextField productAddTextID;
    /**
     * The Product Inventory.
     */
    public TextField productAddTextStock;
    /**
     * The Product Name.
     */
    public TextField productAddTextName;
    /**
     * The Product Price.
     */
    public TextField productAddTextPrice;
    /**
     * The Product Maximum Qty
     */
    public TextField productAddTextMax;
    /**
     * The Product Minimum Qty
     */
    public TextField productAddTextMin;

    /**
     * Searches for Parts by name, then by ID, when the user presses enter.
     */
    @FXML
    protected void partSearchHandler() {

        ObservableList<Part> p = Inventory.lookupPart(allPartSearchField.getText());

        if (p.size() == 0)  {
            try {
                int id = Integer.parseInt(allPartSearchField.getText());
                Part thePart = Inventory.lookupPart(id);
                if (thePart != null) {
                    p.add(thePart);
                }

            }
            catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "No parts match your search criteria.");
                alert.showAndWait();
            }

        }

        if (p.size() != 0)  {
            allPartsTable.setItems(p);
            allPartSearchField.setText("");
        }
        else {
            allPartsTable.setItems(Inventory.getAllParts());
        }

    }

    /**
     * Associates the selected Part with this Product, if one is selected.
     */
    @FXML
    protected void onAddAssocPartButtonClick() {
        Part thePart = allPartsTable.getSelectionModel().getSelectedItem();

        if (thePart == null) return;
        theProduct.addAssociatedPart(thePart);
        assocPartsTable.setItems(theProduct.getAllAssociatedParts());

    }

    /**
     * Deletes the selected part.
     * Prompts the user to confirm, then deletes a Part, if one is selected in the partTable.
     * Prompts the user to select a Part, if one is not selected.
     *
     */
    @FXML
    protected void onDeleteAssocPartButtonClick() {
        Part thePart = assocPartsTable.getSelectionModel().getSelectedItem();

        if (thePart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a part to delete.");
            alert.showAndWait();
            return;
        }

        Alert prompt = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete "+ thePart.getName() +"?");
        prompt.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                theProduct.deleteAssociatedPart(thePart);
                assocPartsTable.setItems(theProduct.getAllAssociatedParts());
            }
        });

    }

    /**
     * Returns to the Main View when the user clicks cancel.
     *
     * @param actionEvent The ActionEvent which triggered this method.
     */
    @FXML
    protected void addProductCancel(ActionEvent actionEvent) throws IOException {
        openMain(actionEvent);
    }

    /**
     * Validates user input and creates a new Product from provided data.
     *
     * @param actionEvent The ActionEvent which triggered this method.
     */
    @FXML
    public void addProductSave(ActionEvent actionEvent) throws IOException {

        //Get the value of the form fields.
        String textID = productAddTextID.getText();
        String textName = productAddTextName.getText();
        String textStock = productAddTextStock.getText();
        String textPrice = productAddTextPrice.getText();
        String textMax = productAddTextMax.getText();
        String textMin = productAddTextMin.getText();

        //Make sure none of the fields are blank.
        if (isInvalid(textID, "How did this even happen?")) { return; }
        if (isInvalid(textName, "Please enter a part name.")) { return; }
        if (isInvalid(textStock, "Please enter a quantity.")) { return; }
        if (isInvalid(textPrice, "Please enter a price.")) { return; }
        if (isInvalid(textMax, "Please enter a maximum quantity.")) { return; }
        if (isInvalid(textMin, "Please enter a minimum quantity.")) { return; }

        /*
        // Just kidding, a Product doesn't require an associated part to be valid.
        if (theProduct.getAllAssociatedParts().size() == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please associate at least one part with " + textName);
                alert.showAndWait();
            return;
        }
        */


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
            }
            else if (stock > max) {
                throw new NumberFormatException("Stock on hand exceeds capacity.");
            }

            addProduct(id, textName, price, stock, min, max);
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
     * Adds the Product to inventory and increments the nextProductId
     *
     * @param id       Product ID
     * @param textName Product Name
     * @param price    Product Price
     * @param stock    Product Inventory
     * @param min      Minimum Inventory
     * @param max      Maximum Inventory
     */
    protected void addProduct(int id, String textName, double price, int stock, int min, int max) {
        Product newProduct = new Product(id, textName, price, stock, min, max);
        for (Part p : theProduct.getAllAssociatedParts()) {
            newProduct.addAssociatedPart(p);
        }
        Inventory.addProduct(newProduct);
        nextProductId += 2;
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

        //Set the stage
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load(), 820, 400);
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

        allPartsTable.setItems(Inventory.getAllParts());
        allPartsID.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartsStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        assocPartsTable.setItems(theProduct.getAllAssociatedParts());
        assocPartsID.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocPartsStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPartsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Use the next Product ID
        productAddTextID.setText(String.valueOf(nextProductId));
    }

}