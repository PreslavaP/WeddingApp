package validation;

import components.Family;
import venue.Table;

public interface SeatingValidationStrategy {
    boolean validateSeating(Table table, Family family);
}
