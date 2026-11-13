import components.Family;
import components.Guest;
import navigation.TableIterator;
import validation.SeatingValidationStrategy;
import validation.StandardSeatingValidationStrategy;
import venue.Table;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Table table1 = new Table(1);
        Table table2 = new Table(2);
        Table table3 = new Table(3);

        List<Table> tables = new ArrayList<>();

        tables.add(table1);
        tables.add(table2);

        Family family1 = new Family("Ivanovi");
        Family family2 = new Family("Georgievi");
        Family family3 = new Family("Petrovi");

        Guest guest1 = new Guest("Ivan");
        Guest guest2 = new Guest("Petar");
        Guest guest3 = new Guest("Boris");
        Guest guest4 = new Guest("Marin");
        Guest guest5 = new Guest("Vanq");
        Guest guest6 = new Guest("Simona");
        Guest guest7 = new Guest("Preslava");
        Guest guest8 = new Guest("Stanislava");
        Guest guest9 = new Guest("Petq");

        family1.addMember(guest1);
        family1.addMember(guest4);
        family1.addMember(guest7);

        family2.addMember(guest2);
        family2.addMember(guest5);
        family2.addMember(guest8);

        family3.addMember(guest3);
        family3.addMember(guest6);
        family3.addMember(guest9);

        family3.addBannedFamily("Ivanovi");

        SeatingValidationStrategy validator = new StandardSeatingValidationStrategy();

        if(validator.validateSeating(table1, family3)) {
            table1.addFamily(family3);
        }

        TableIterator iterator = new TableIterator(tables, 0);

        while (iterator.hasNext()) {
            Table table = iterator.next();
            System.out.println("Table number: " + table.getTableNumber() + " | Total guests: " + table.getTotalGuests());
        }
    }
}