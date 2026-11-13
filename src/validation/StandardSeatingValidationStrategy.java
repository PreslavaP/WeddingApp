package validation;

import components.Family;
import venue.Table;

public class StandardSeatingValidationStrategy implements SeatingValidationStrategy {

    @Override
    public boolean validateSeating(Table table, Family family) {
        if (table.getAssignedFamilies().size() >= 2) {
            System.out.println("Denied: Table " + table.getTableNumber() + " already has the maximum number of families (2).");
            return false;
        }

        if (table.getTotalGuests() + family.getCount() > 10) {
            System.out.println("Denied: Table " + table.getTableNumber() + " would exceed the 10-person limit.");
            return false;
        }

        for (Family existingFamily : table.getAssignedFamilies()) {
            if (existingFamily.isBannedWith(family.getName()) ||
                    family.isBannedWith(existingFamily.getName())) {
                System.out.println("Denied: Conflict! Family " + family.getName() +
                        " and Family " + existingFamily.getName() + " cannot sit together.");
                return false;
            }
        }

        return true;
    }
}
