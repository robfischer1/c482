package org.robfischer.c482.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Product class stores information related to a product in Inventory and a list of associated parts.
 *
 * @author Rob Fischer
 */
public class Product {
    /**
     * All parts associated with this Product.
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    /**
     * The system-generated Product ID.
     */
    private int id;
    /**
     * The name of the Product.
     */
    private String name;
    /**
     * The price/cost of the Product.
     */
    private double price;
    /**
     * The quantity of this Product currently on hand.
     */
    private int stock;
    /**
     * The minimum quantity of this Product required to be kept on hand.
     */
    private int min;
    /**
     * The maximum quantity of this Product able to be stored in Inventory.
     */
    private int max;

    /**
     * Instantiates a new Product.
     *
     * @param id    Product ID
     * @param name  Product Name
     * @param price Product Cost/Price
     * @param stock Current Inventory Level
     * @param min   Minimum needed on-hand.
     * @param max   Maximum able to be stored.
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Default constructor.
     */
    public Product() {

    }

    /**
     * Sets the Product ID of this Product.
     *
     * @param id the id
     */
    public void setID(int id) {
        this.id = id;
    }

    /**
     * Sets the Name of this Product.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the Price/Cost of this Product.
     *
     * @param price the price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Sets the current stock of this Product.
     *
     * @param stock the stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Sets the minimum quantity of this Product which must be on hand.
     *
     * @param min the min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Sets the maximum number of this Product which can be kept in Inventory.
     *
     * @param max the max
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Gets the Product ID of this Product.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the Name of this Product.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the Price/Cost of this Product.
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Gets the current stock of this Product.
     *
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * Gets the minimum quantity of this Product which must be on hand.
     *
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * Gets the maximum number of this Product which can be kept in Inventory.
     *
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * Add an associated part.
     *
     * @param part The Part to add.
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Delete an associated part.
     *
     * @param selectedAssociatedPart The Part to delete.
     * @return True on a successful delete.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        return associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * Gets all associated parts.
     *
     * @return ObservableList&lt;Part&gt; of all associated parts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

}