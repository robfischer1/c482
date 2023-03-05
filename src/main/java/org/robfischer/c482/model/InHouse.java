package org.robfischer.c482.model;

/**
 * Parts which were manufactured by this company.
 *
 * InHouse extends Part by tracking the ID of the machine which manufactured the Part.
 *
 * @author Rob Fischer
 * @see org.robfischer.c482.model.Part
 */
public class InHouse extends Part {

    /**
     * An int indicating the machine which manufactured the Part.
     */
    private int machineID;

    /**
     * Initializes a newly created InHouse object given the provided data.
     *
     * @param id A unique integer representing this part.
     * @param name A String containing the name of the part.
     * @param price A double containing the part's price.
     * @param stock An int indicating the number of parts in stock.
     * @param min An int indicating the minimum number of parts needed in inventory.
     * @param max An int indicating the maximum number of parts needed in inventory.
     * @param machineID An int indicating the machine which manufactured this Part.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }

    /**
     * Mutator for machineID.
     *
     * @param machineID An int containing the ID of the machine which manufactured this Part.
     */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }

    /**
     * Accessor for machineID.
     *
     * @return An int containing the ID of the machine which manufactured this Part.
     */
    public int getMachineID() {
        return machineID;
    }
}
