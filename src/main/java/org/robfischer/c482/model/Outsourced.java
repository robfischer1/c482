package org.robfischer.c482.model;

/**
 * Parts which were manufactured by a third party.
 *
 * Outsourced extends Parts by tracking the name of the company which manufactured the Part.
 *
 * @author Rob Fischer
 * @see org.robfischer.c482.model.Part
 */
public class Outsourced extends Part {

    /**
     * A String containing the name of the company which manufactured this Part.
     */
    private String companyName;

    /**
     * Initializes a newly created Outsourced object given the provided data.
     *
     * @param id A unique integer representing this part.
     * @param name A String containing the name of the part.
     * @param price A double containing the part's price.
     * @param stock An int indicating the number of parts in stock.
     * @param min An int indicating the minimum number of parts needed in inventory.
     * @param max An int indicating the maximum number of parts needed in inventory.
     * @param companyName A String containing the name of the company which manufactured the part.
     */

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Accessor for companyName.
     *
     * @return Returns a String containing the name of the company which manufactured this Part.
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Mutator for companyName.
     *
     * @param companyName A String containing the name of the company which manufactured this Part.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
