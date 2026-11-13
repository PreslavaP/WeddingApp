package navigation;

import venue.Table;

public interface SeatingIterator {
    boolean hasNext();
    Table next();
}
