package org.robfischer.c482.controller;

import org.robfischer.c482.model.Part;
import org.robfischer.c482.model.Product;
import org.robfischer.c482.model.Inventory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Adds additional functionality to AddProductController to allow editing of existing products.
 * @author Rob Fischer
 */
public class EditProductController extends AddProductController {

    /**
     * Receives a Product to edit from another controller and populates the form fields with existing details.
     *
     * @param p The Product to edit.
     */
    protected void passProduct(Product p) {

        theProduct = p;

        productAddTextID.setText(String.valueOf(p.getId()));
        productAddTextName.setText(String.valueOf(p.getName()));
        productAddTextStock.setText(String.valueOf(p.getStock()));
        productAddTextPrice.setText(String.valueOf(p.getPrice()));
        productAddTextMax.setText(String.valueOf(p.getMax()));
        productAddTextMin.setText(String.valueOf(p.getMin()));
        productAddTextMin.setText(String.valueOf(p.getMin()));

        assocPartsTable.setItems(theProduct.getAllAssociatedParts());

    }

    @Override
    protected void addProduct(int id, String textName, double price, int stock, int min, int max) {
        Product newProduct = new Product(id, textName, price, stock, min, max);
        Product oldProduct = Inventory.lookupProduct(id);
        int index = Inventory.getAllProducts().indexOf(oldProduct);

        for (Part p : theProduct.getAllAssociatedParts()) {
            newProduct.addAssociatedPart(p);
        }

        Inventory.updateProduct(index, newProduct);
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        productLabel.setText("Edit Product");
    }

}