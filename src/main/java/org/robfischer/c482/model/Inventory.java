package org.robfischer.c482.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * All parts and products in the company Inventory.
 *
 * @author Rob Fischer
 */
public class Inventory {
    /**
     * Contains all the Parts in inventory.
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    /**
     * Contains all the Products in inventory.
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds the specified part to allParts.
     *
     * @param newPart A Part object to add to inventory.
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /** Adds the specified product to allProducts.
     *
     * @param newProduct A Product object to add to inventory.
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Searches for a Part by ID.
     *
     * @param partId The part ID to search.
     * @return The Part object with the specified ID, null if none can be found.
     */
    public static Part lookupPart(int partId) {
        for (Part p : allParts) {
            if (p.getId() == partId) {
                return p;
            }
        }
        return null;
    }

    /**
     * Searches for a Product by ID.
     *
     * @param productId The product ID to search.
     * @return The Product object with the specified ID, null if none can be found.
     */
    public static Product lookupProduct(int productId) {

        for (Product p : allProducts) {
            if (p.getId() == productId) {
                return p;
            }
        }
        return null;
    }

    /**
     * Searches for a Part by name.
     *
     * @param partName A string containing the full or partial name of a part to search.
     * @return An ObservableList of all matching Parts.
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> matchingParts = FXCollections.observableArrayList();

        for (Part p : allParts) {
            if (p.getName().contains(partName)) {
                matchingParts.add(p);
            }
        }
        return matchingParts;
    }

    /**
     * Searches for a Product by name.
     *
     * @param productName A string containing the full or partial name of a product to search.
     * @return An ObservableList of all matching Products.
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> matchingProducts = FXCollections.observableArrayList();

        for (Product p : allProducts) {
            if (p.getName().contains(productName)) {
                matchingProducts.add(p);
            }
        }
        return matchingProducts;
    }

    /**
     * Replaces or Updates the details of a Part in inventory.
     *
     * @param index The index of the part in allParts to update.
     * @param selectedPart A Part object to insert at the specified index.
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Replaces or Updates the details of a Product in inventory.
     *
     * @param index The index of the product in allProducts to update.
     * @param newProduct A Product object to insert at the specified index.
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * Deletes a Part from inventory.
     *
     * @param selectedPart The part to delete from inventory.
     * @return True if the part was successfully deleted, false otherwise.
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.removeAll(selectedPart);
    }

    /**
     * Deletes a Product from inventory.
     *
     * @param selectedProduct The product to delete from inventory.
     * @return True if the product was successfully deleted, false otherwise.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.removeAll(selectedProduct);
    }

    /**
     * Accessor for the list of parts in inventory.
     * @return The list of all parts in inventory.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Accessor for the list of products in inventory.
     * @return The list of all products in inventory.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

}

