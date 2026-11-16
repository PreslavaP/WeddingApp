import components.Family;
import components.Guest;
import gui.WeddingFrame;
import navigation.TableIterator;
import validation.SeatingValidationStrategy;
import validation.StandardSeatingValidationStrategy;
import venue.Table;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new WeddingFrame().setVisible(true);
        });
    }
}