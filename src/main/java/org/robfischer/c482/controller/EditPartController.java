package org.robfischer.c482.controller;

import org.robfischer.c482.model.InHouse;
import org.robfischer.c482.model.Inventory;
import org.robfischer.c482.model.Outsourced;
import org.robfischer.c482.model.Part;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Adds additional functionality to AddPartController to allow editing of existing parts.
 * @author Rob Fischer
 */
public class EditPartController extends AddPartController {


    @Override 
    protected void addPart (int id, String textName, double price, int stock, int min, int max, int machineID) {
        InHouse newPart = new InHouse(id, textName, price, stock, min, max, machineID);
        InHouse oldPart = (InHouse) Inventory.lookupPart(id);
        int index = Inventory.getAllParts().indexOf(oldPart);

        Inventory.updatePart(index, newPart);
    }

    @Override
    protected void addPart (int id, String textName, double price, int stock, int min, int max, String textType) {
        Outsourced newPart = new Outsourced(id, textName, price, stock, min, max, textType);
        Outsourced oldPart = (Outsourced) Inventory.lookupPart(id);
        int index = Inventory.getAllParts().indexOf(oldPart);

        Inventory.updatePart(index, newPart);
    }

    /**
     * Receives a Part to edit from another controller and populates the form fields with existing details.
     *
     * @param p The Part to edit.
     */
    protected void passPart(Part p) {

        if (p.getClass() == Outsourced.class) {
            partAddLabelType.setText("Company Name");
            partAddOutsourced.setSelected(true);
            partAddTextType.setText(String.valueOf(((Outsourced) p).getCompanyName()));
            setPartDetails(p);

        }
        else if (p.getClass() == InHouse.class) {
            partAddLabelType.setText("Machine ID");
            partAddInHouse.setSelected(true);
            partAddTextType.setText(String.valueOf(((InHouse) p).getMachineID()));
            setPartDetails(p);
        }

    }

    /**
     * Populates the TextFields with the attributes of the Part being edited.
     *
     * @param p The Part being edited.
     */
    protected void setPartDetails(Part p) {
        partAddTextId.setText(String.valueOf(p.getId()));
        partAddTextName.setText(String.valueOf(p.getName()));
        partAddTextStock.setText(String.valueOf(p.getStock()));
        partAddTextPrice.setText(String.valueOf(p.getPrice()));
        partAddTextMax.setText(String.valueOf(p.getMax()));
        partAddTextMin.setText(String.valueOf(p.getMin()));
        partAddTextMin.setText(String.valueOf(p.getMin()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        partLabel.setText("Edit Part");
    }

}