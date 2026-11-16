package validation;

import components.Family;
import venue.Table;

public class StandardSeatingValidationStrategy implements SeatingValidationStrategy {

    @Override
    public boolean validateSeating(Table table, Family familyOrGuest) {

        boolean isSingleGuest = familyOrGuest.getName().startsWith("Гост:") || familyOrGuest.getCount() == 1;

        if (!isSingleGuest) {
            long familiesCount = table.getAssignedFamilies().stream()
                    .filter(f -> !f.getName().startsWith("Гост:") && f.getCount() > 1)
                    .count();

            if (familiesCount >= 2) {
                System.out.println("Denied: Table " + table.getTableNumber() + " already has the maximum number of families (2).");
                return false;
            }
        }

        if (table.getTotalGuests() + familyOrGuest.getCount() > 10) {
            System.out.println("Denied: Table " + table.getTableNumber() + " would exceed the 10-person limit.");
            return false;
        }

        for (Family existingFamily : table.getAssignedFamilies()) {
            if (existingFamily.isBannedWith(familyOrGuest.getName()) ||
                    familyOrGuest.isBannedWith(existingFamily.getName())) {
                System.out.println("Denied: Conflict! Family/Guest cannot sit together.");
                return false;
            }
        }

        return true;
    }
}