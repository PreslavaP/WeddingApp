package venue;

import components.Family;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private int tableNumber;
    private List<Family> assignedFamilies = new ArrayList<>();

    public Table(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public List<Family> getAssignedFamilies() {
        return assignedFamilies;
    }

    public void setTableNumber(int tableNumber) {}
    public void addFamily(Family family){
        assignedFamilies.add(family);
    }

    public int getTotalGuests(){
        int total = 0;
        for (Family family : assignedFamilies){
            total += family.getCount();
        }

        return total;
    }

    @Override
    public String toString() {
        return "Table " + tableNumber;
    }
}
