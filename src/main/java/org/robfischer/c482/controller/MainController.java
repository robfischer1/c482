package org.robfischer.c482.controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.robfischer.c482.model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The Controller for the Main View in the Inventory Management System.
 *
 * @author Rob Fischer
 */
public class MainController implements Initializable {

    /**
     * Determines if we should prepopulate the TableViews with test data.
     */
    private static boolean firstRun = true;

    /**
     * The Part table.
     */
    @FXML
    public TableView<Part> partTable;
    /**
     * The Product table.
     */
    public TableView<Product> productTable;
    /**
     * The Part ID Column.
     */
    public TableColumn<Part,String> partColumnId;
    /**
     * The Part Name Column.
     */
    public TableColumn<Part,String> partColumnName;
    /**
     * The Part Inventory Column.
     */
    public TableColumn<Part,String> partColumnStock;
    /**
     * The Part Price/Cost Column.
     */
    public TableColumn<Part,String> partColumnPrice;
    /**
     * The Product ID Column.
     */
    public TableColumn<Product,String> productColumnId;
    /**
     * The Product Name Column.
     */
    public TableColumn<Product,String> productColumnName;
    /**
     * The Product Inventory Column.
     */
    public TableColumn<Product,String> productColumnStock;
    /**
     * The Product Price/Cost Column.
     */
    public TableColumn<Product,String> productColumnPrice;
    /**
     * The Part search field.
     */
    public TextField partSearchField;
    /**
     * The Product search field.
     */
    public TextField productSearchField;

    /**
     * Searches for Parts by name, then by ID, when the user presses enter.
     */
    @FXML
    protected void partSearchHandler() {

        ObservableList<Part> p = Inventory.lookupPart(partSearchField.getText());

        if (p.size() == 0)  {
            try {
                int id = Integer.parseInt(partSearchField.getText());
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
            partTable.setItems(p);
            partSearchField.setText("");
        }
        else {
            partTable.setItems(Inventory.getAllParts());
        }

    }

    /**
     * Searches for Products by name, then by ID, when the user presses enter.
     */
    @FXML
    protected void productSearchHandler() {
        String s = productSearchField.getText();
        ObservableList<Product> p = Inventory.lookupProduct(s);

        if (p.size() == 0) {
            try {
                int id = Integer.parseInt(s);
                Product theProduct = Inventory.lookupProduct(id);
                if (theProduct != null) {
                    p.add(theProduct);
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "No products match your search criteria.");
                alert.showAndWait();
            }

        }

        if (p.size() != 0) {
            productTable.setItems(p);
            productSearchField.setText("");
        } else {
            productTable.setItems(Inventory.getAllProducts());
        }
    }

    /**
     * Deletes the selected part.
     * Prompts the user to confirm, then deletes a Part, if one is selected in the partTable.
     * Prompts the user to select a Part, if one is not selected.
     *
     */
    @FXML
    protected void  onDeletePartButtonClick() {
        Part thePart = partTable.getSelectionModel().getSelectedItem();

        if (thePart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a part to delete.");
            alert.showAndWait();
            return;
        }

        Alert prompt = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete "+ thePart.getName() +"?");
        prompt.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Inventory.deletePart(thePart);
            }
        });

    }

    /**
     * Deletes the selected product.
     * Prompts the user to confirm, then deletes a Product, if one is selected in the productTable.
     * Prompts the user to select a Product, if one is not selected.
     */
    @FXML
    protected void  onDeleteProductButtonClick() {
        Product theProduct = productTable.getSelectionModel().getSelectedItem();

        if (theProduct == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a product to delete.");
            alert.showAndWait();
            return;
        }

        if (theProduct.getAllAssociatedParts().size() != 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "A Product may not be deleted while associated with one or more parts.");
            alert.showAndWait();
            return;
        }

        Alert prompt = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete "+ theProduct.getName() +"?");
        prompt.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                System.out.println(theProduct.getAllAssociatedParts());
                Inventory.deleteProduct(theProduct);
            }
        });

    }

    /**
     * Opens the Add Part View, and sets the controller to AddPartController.
     *
     * @param actionEvent The ActionEvent which triggered this method.
     */
    @FXML
    protected void openAddPart(ActionEvent actionEvent) throws IOException {
        //Create an AddPartController
        AddPartController controller = new AddPartController();

        //Load the FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/robfischer/c482/view/add-part-view.fxml"));
        loader.setController(controller);
        Parent root = loader.load();

        //Set the stage
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 450, 350);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Opens the Add Product View, and sets the controller to AddProductController.
     *
     * @param actionEvent The ActionEvent which triggered this method.
     */
    @FXML
    protected void openAddProduct(ActionEvent actionEvent) throws IOException {
        //Create an AddProductController
        AddProductController controller = new AddProductController();

        //Load the FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/robfischer/c482/view/add-product-view.fxml"));
        loader.setController(controller);
        Parent root = loader.load();

        //Set the stage
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 775, 550);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Opens the Add Part View, and sets the controller to EditPartController.
     * Passes EditPartController a Part to edit, if one is selected.
     * <p>
     * RUNTIME ERROR: Initially I had the fx-controller set in add-part-view to AddPartController.
     * I then tried to call loader.setController to change to the EditPart Controller. This resulted in
     * a runtime error, which was subsequently corrected by removing the fx-controller attribute from the fxml,
     * and setting the controller for both Add Part and Edit part in their respective methods.
     *
     * @param actionEvent The ActionEvent which triggered this method.
     */
    @FXML
    protected void openEditPart(ActionEvent actionEvent) throws IOException {
        //Create an EditPartController
        EditPartController controller = new EditPartController();

        //Load the FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/robfischer/c482/view/add-part-view.fxml"));
        loader.setController(controller);
        Parent root = loader.load();

        //Pass the part to edit
        Part thePart = partTable.getSelectionModel().getSelectedItem();
        if (thePart == null) return;
        controller.passPart(thePart);

        //Set the stage
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 450, 350);
        stage.setTitle("Edit Part");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Opens the Add Product View, and sets the controller to EditProductController.
     * Passes EditProductController the Product to edit, if one is selected.
     *
     * @param actionEvent The ActionEvent which triggered this method.
     */
    @FXML
    protected void openEditProduct(ActionEvent actionEvent) throws IOException {
        //Create an EditProductController
        EditProductController controller = new EditProductController();

        //Load the FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/robfischer/c482/view/add-product-view.fxml"));
        loader.setController(controller);
        Parent root = loader.load();

        //Pass the product to edit
        Product theProduct = productTable.getSelectionModel().getSelectedItem();
        if (theProduct == null) return;
        controller.passProduct(theProduct);

        //Set the stage
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 775, 550);
        stage.setTitle("Edit Product");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Exits the application when the Exit button is clicked.
     */
    @FXML
    protected void onExitButtonClick() {
        Platform.exit();
    }

    /**
     * Adds test data. Nobody likes an empty table.
     */
    private void addTestData() {
        if (!firstRun) return;

        firstRun = false;

        Inventory.addPart(new Outsourced(1, "Part One", 1.00, 2, 1, 3, "Company One"));
        Inventory.addPart(new Outsourced(2, "Part Two", 2.00, 2, 1, 3, "Company Two"));
        Inventory.addPart(new Outsourced(3, "Part Three", 3.00, 2, 1, 3, "Company Two"));
        Inventory.addPart(new InHouse(4, "Part Four", 4.00, 2, 1, 3, 8675));
        Inventory.addPart(new InHouse(5, "Part Five", 5.00, 2, 1, 3, 309));
        Inventory.addProduct(new Product(1, "Product One", 1.00, 2, 1, 3));
        Inventory.addProduct(new Product(2, "Product Two", 2.00, 2, 1, 3));
        Inventory.addProduct(new Product(3, "Product Three", 3.00, 2, 1, 3));
        Inventory.addProduct(new Product(4, "Product Four", 4.00, 2, 1, 3));
        Inventory.addProduct(new Product(5, "Product Five", 5.00, 2, 1, 3));
    }

    /**
     * Initializes the controller for the Main Window and populates the TableViews.
     * @param url Unused
     * @param resourceBundle Unused
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addTestData();

        partTable.setItems(Inventory.getAllParts());
        partColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partColumnStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTable.setItems(Inventory.getAllProducts());
        productColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        productColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productColumnStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

}