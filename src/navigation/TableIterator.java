package navigation;

import venue.Table;
import java.util.ArrayList;
import java.util.List;

public class TableIterator implements SeatingIterator {
    private List<Table> tables = new ArrayList<Table>();
    private int position = 0;

    public TableIterator(List<Table> tables, int position) {
        this.tables = tables;
        this.position = position;
    }

    @Override
    public boolean hasNext() {
        return position < tables.size();
    }

    @Override
    public Table next() {
        Table table = tables.get(position);
        position++;
        return table;
    }
}
