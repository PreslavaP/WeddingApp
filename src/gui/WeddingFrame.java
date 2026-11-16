package gui;

import venue.Table;
import components.Family;
import components.Guest;
import validation.SeatingValidationStrategy;
import validation.StandardSeatingValidationStrategy;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class WeddingFrame extends JFrame {

    private List<Table> tables;
    private List<Family> families;
    private SeatingValidationStrategy validator;

    private JTextField tableNumField;
    private JTextField familyNameField;
    private JTextField membersInputCtrl;
    private JTextField guestToFamField;

    private JComboBox<Table> tableComboBox;
    private JComboBox<Family> familyComboBox;
    private JComboBox<Family> targetFamilyComboBox;
    private JComboBox<Family> ban1ComboBox;
    private JComboBox<Family> ban2ComboBox;

    private JTextArea outputArea;
    private VenueCanvas venueCanvas;
    private JPanel detailsPanel;

    public WeddingFrame() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
        }

        setTitle("Wedding Seating Chart Studio");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tables = new ArrayList<>();
        families = new ArrayList<>();
        validator = new StandardSeatingValidationStrategy();

        JPanel mainPanel = new JPanel(new BorderLayout(12, 12));
        mainPanel.setBorder(new EmptyBorder(12, 12, 12, 12));
        mainPanel.setBackground(new Color(250, 247, 242));
        setContentPane(mainPanel);

        JPanel topHeader = new JPanel(new BorderLayout());
        topHeader.setBackground(new Color(242, 235, 224));
        topHeader.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(216, 185, 138), 1),
                new EmptyBorder(8, 12, 8, 12)
        ));

        JLabel titleInfo = new JLabel("  Wedding Seating Chart Studio");
        titleInfo.setFont(new Font("Times New Roman", Font.BOLD, 14));
        titleInfo.setForeground(new Color(110, 85, 60));
        topHeader.add(titleInfo, BorderLayout.WEST);

        JLabel statsLabel = new JLabel("Status: Ready for Venue Management   ");
        statsLabel.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        statsLabel.setForeground(new Color(122, 112, 98));
        topHeader.add(statsLabel, BorderLayout.EAST);

        mainPanel.add(topHeader, BorderLayout.NORTH);

        JPanel leftContent = new JPanel();
        leftContent.setLayout(new BoxLayout(leftContent, BoxLayout.Y_AXIS));
        leftContent.setBackground(new Color(255, 253, 250));
        leftContent.setBorder(new EmptyBorder(8, 8, 8, 8));

        JPanel creationPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        creationPanel.setBackground(new Color(255, 253, 250));
        TitledBorder border1 = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(215, 200, 180), 1),
                " 1. Management: Tables, Families & Bans ",
                TitledBorder.LEADING, TitledBorder.TOP,
                new Font("Times New Roman", Font.BOLD, 12),
                new Color(110, 85, 60)
        );
        creationPanel.setBorder(BorderFactory.createCompoundBorder(border1, new EmptyBorder(6, 6, 6, 6)));

        JPanel tableRow = createStyledRow();
        JLabel lblTable = new JLabel("Table No:");
        lblTable.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        tableRow.add(lblTable);
        tableNumField = new JTextField(4);
        tableNumField.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        tableRow.add(tableNumField);
        JButton addTableBtn = new StyledButton("Add Table");
        tableRow.add(addTableBtn);
        creationPanel.add(tableRow);

        JPanel familyRow = createStyledRow();
        JLabel lblFamily = new JLabel("Family:");
        lblFamily.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        familyRow.add(lblFamily);
        familyNameField = new JTextField(4);
        familyNameField.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        familyRow.add(familyNameField);
        JLabel lblMem = new JLabel("Mem:");
        lblMem.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        familyRow.add(lblMem);
        membersInputCtrl = new JTextField(5);
        membersInputCtrl.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        familyRow.add(membersInputCtrl);
        JButton addFamilyBtn = new StyledButton("Add Family");
        familyRow.add(addFamilyBtn);
        creationPanel.add(familyRow);

        JPanel guestRow = createStyledRow();
        JLabel lblGuest = new JLabel("Single Guest:");
        lblGuest.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        guestRow.add(lblGuest);
        JTextField singleGuestField = new JTextField(7);
        singleGuestField.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        guestRow.add(singleGuestField);
        JButton addSingleGuestBtn = new StyledButton("Add Guest");
        guestRow.add(addSingleGuestBtn);
        creationPanel.add(guestRow);

        JPanel addToFamRow = createStyledRow();
        JLabel lblNewG = new JLabel("New Guest:");
        lblNewG.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        addToFamRow.add(lblNewG);
        guestToFamField = new JTextField(4);
        guestToFamField.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        addToFamRow.add(guestToFamField);
        JLabel lblToF = new JLabel("To Family:");
        lblToF.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        addToFamRow.add(lblToF);
        targetFamilyComboBox = new JComboBox<>();
        targetFamilyComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        targetFamilyComboBox.setPreferredSize(new Dimension(75, 22));
        addToFamRow.add(targetFamilyComboBox);
        JButton addGuestToFamBtn = new StyledButton("Add to Family");
        addToFamRow.add(addGuestToFamBtn);
        creationPanel.add(addToFamRow);

        JPanel banRow = createStyledRow();
        JLabel lblBan = new JLabel("Ban:");
        lblBan.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        banRow.add(lblBan);
        ban1ComboBox = new JComboBox<>();
        ban1ComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        ban1ComboBox.setPreferredSize(new Dimension(70, 22));
        banRow.add(ban1ComboBox);
        JLabel lblAnd = new JLabel("&");
        lblAnd.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        banRow.add(lblAnd);
        ban2ComboBox = new JComboBox<>();
        ban2ComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        ban2ComboBox.setPreferredSize(new Dimension(70, 22));
        banRow.add(ban2ComboBox);
        JButton addBanBtn = new StyledButton("Apply Ban");
        banRow.add(addBanBtn);
        creationPanel.add(banRow);

        leftContent.add(creationPanel);
        leftContent.add(Box.createVerticalStrut(8));

        JPanel seatingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 4));
        seatingPanel.setBackground(new Color(255, 253, 250));
        TitledBorder border2 = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(215, 200, 180), 1),
                " 2. Seating Assignment & Validation ",
                TitledBorder.LEADING, TitledBorder.TOP,
                new Font("Times New Roman", Font.BOLD, 12),
                new Color(110, 85, 60)
        );
        seatingPanel.setBorder(BorderFactory.createCompoundBorder(border2, new EmptyBorder(6, 6, 6, 6)));

        JLabel lblGF = new JLabel("Guest/Fam:");
        lblGF.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        seatingPanel.add(lblGF);
        familyComboBox = new JComboBox<>();
        familyComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        familyComboBox.setPreferredSize(new Dimension(75, 22));
        seatingPanel.add(familyComboBox);

        JLabel lblTab = new JLabel("Table:");
        lblTab.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        seatingPanel.add(lblTab);
        tableComboBox = new JComboBox<>();
        tableComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        tableComboBox.setPreferredSize(new Dimension(55, 22));
        seatingPanel.add(tableComboBox);

        JButton seatBtn = new StyledButton("Assign");
        seatingPanel.add(seatBtn);

        JButton removeSeatBtn = new StyledButton("Remove");
        seatingPanel.add(removeSeatBtn);

        leftContent.add(seatingPanel);
        leftContent.add(Box.createVerticalStrut(8));

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setBackground(new Color(40, 35, 30));
        outputArea.setForeground(new Color(220, 205, 175));
        outputArea.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        outputArea.setRows(10);

        JScrollPane consoleScroll = new JScrollPane(outputArea);
        TitledBorder border3 = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(215, 200, 180), 1),
                " 3. System Console & Logs ",
                TitledBorder.LEADING, TitledBorder.TOP,
                new Font("Times New Roman", Font.BOLD, 12),
                new Color(110, 85, 60)
        );
        consoleScroll.setBorder(BorderFactory.createCompoundBorder(border3, new EmptyBorder(4, 4, 4, 4)));
        leftContent.add(consoleScroll);

        JScrollPane leftScrollPane = new JScrollPane(leftContent);
        leftScrollPane.setPreferredSize(new Dimension(415, 0));
        leftScrollPane.setBorder(null);
        leftScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        leftScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.add(leftScrollPane, BorderLayout.WEST);

        venueCanvas = new VenueCanvas();
        JScrollPane canvasScroll = new JScrollPane(venueCanvas);
        canvasScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(215, 200, 180), 1),
                " SEATING CHART ",
                TitledBorder.LEADING, TitledBorder.TOP,
                new Font("Times New Roman", Font.BOLD, 14),
                new Color(110, 85, 60)
        ));
        canvasScroll.setBackground(new Color(255, 252, 248));
        canvasScroll.getViewport().setBackground(new Color(255, 252, 248));
        canvasScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        canvasScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        mainPanel.add(canvasScroll, BorderLayout.CENTER);

        detailsPanel = new JPanel(new BorderLayout(10, 10));
        detailsPanel.setBackground(new Color(255, 253, 250));
        TitledBorder borderDetails = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(215, 200, 180), 1),
                " Table Details ",
                TitledBorder.LEADING, TitledBorder.TOP,
                new Font("Times New Roman", Font.BOLD, 13),
                new Color(110, 85, 60)
        );
        detailsPanel.setBorder(BorderFactory.createCompoundBorder(borderDetails, new EmptyBorder(10, 10, 10, 10)));
        detailsPanel.setPreferredSize(new Dimension(320, 0));

        JLabel placeholderLabel = new JLabel("<html><center>Click on a table from the chart to view its guest list.</center></html>", SwingConstants.CENTER);
        placeholderLabel.setFont(new Font("Times New Roman", Font.ITALIC, 13));
        placeholderLabel.setForeground(new Color(140, 125, 110));
        detailsPanel.add(placeholderLabel, BorderLayout.CENTER);

        mainPanel.add(detailsPanel, BorderLayout.EAST);

        addTableBtn.addActionListener(e -> {
            try {
                int num = Integer.parseInt(tableNumField.getText().trim());
                boolean exists = tables.stream().anyMatch(t -> t.getTableNumber() == num);
                if (exists) {
                    JOptionPane.showMessageDialog(this, "Table with this number already exists!", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                Table newTable = new Table(num);
                tables.add(newTable);
                tableComboBox.addItem(newTable);
                outputArea.append(">> Added Table #" + num + "\n");
                tableNumField.setText("");
                venueCanvas.revalidate();
                venueCanvas.repaint();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid table number!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        addFamilyBtn.addActionListener(e -> {
            String famName = familyNameField.getText().trim();
            String membersInput = membersInputCtrl.getText().trim();

            if (famName.isEmpty() || membersInput.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter family name and members!", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Family newFamily = new Family(famName);
            String[] guestNames = membersInput.split(",");
            for (String gName : guestNames) {
                String trimmedName = gName.trim();
                if (!trimmedName.isEmpty()) {
                    newFamily.addMember(new Guest(trimmedName));
                }
            }

            families.add(newFamily);
            familyComboBox.addItem(newFamily);
            targetFamilyComboBox.addItem(newFamily);
            ban1ComboBox.addItem(newFamily);
            ban2ComboBox.addItem(newFamily);

            outputArea.append(">> Added Family: '" + famName + "' (" + newFamily.getCount() + " members)\n");
            familyNameField.setText("");
            membersInputCtrl.setText("");
        });

        addSingleGuestBtn.addActionListener(e -> {
            String guestName = singleGuestField.getText().trim();
            if (guestName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter guest name!", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Family singleGuestFamily = new Family(guestName);
            singleGuestFamily.addMember(new Guest(guestName));

            families.add(singleGuestFamily);
            familyComboBox.addItem(singleGuestFamily);
            targetFamilyComboBox.addItem(singleGuestFamily);
            ban1ComboBox.addItem(singleGuestFamily);
            ban2ComboBox.addItem(singleGuestFamily);

            outputArea.append(">> Added Guest: '" + guestName + "'\n");
            singleGuestField.setText("");
        });

        addGuestToFamBtn.addActionListener(e -> {
            String guestName = guestToFamField.getText().trim();
            Family selectedFam = (Family) targetFamilyComboBox.getSelectedItem();

            if (guestName.isEmpty() || selectedFam == null) {
                JOptionPane.showMessageDialog(this, "Enter guest and select family!", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (selectedFam.getCount() >= 10) {
                JOptionPane.showMessageDialog(this, "Family reached limit of 10 people!", "Limit", JOptionPane.ERROR_MESSAGE);
                return;
            }

            selectedFam.addMember(new Guest(guestName));
            JOptionPane.showMessageDialog(this, "Added guest to family '" + selectedFam.getName() + "'.", "Success", JOptionPane.INFORMATION_MESSAGE);
            outputArea.append("[Composite] Added '" + guestName + "' to '" + selectedFam.getName() + "'\n");
            guestToFamField.setText("");
        });

        addBanBtn.addActionListener(e -> {
            Family f1 = (Family) ban1ComboBox.getSelectedItem();
            Family f2 = (Family) ban2ComboBox.getSelectedItem();

            if (f1 == null || f2 == null || f1.equals(f2)) {
                JOptionPane.showMessageDialog(this, "Select two different families for a ban!", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            f1.addBannedFamily(f2.getName());
            f2.addBannedFamily(f1.getName());

            JOptionPane.showMessageDialog(this, "Ban applied between '" + f1.getName() + "' and '" + f2.getName() + "'.", "Ban", JOptionPane.INFORMATION_MESSAGE);
            outputArea.append("[Ban] Set between '" + f1.getName() + "' & '" + f2.getName() + "'\n");
        });

        seatBtn.addActionListener(e -> {
            Family selectedFamily = (Family) familyComboBox.getSelectedItem();
            Table selectedTable = (Table) tableComboBox.getSelectedItem();

            if (selectedFamily == null || selectedTable == null) {
                JOptionPane.showMessageDialog(this, "Select family and table!", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Table alreadyAssignedTable = null;
            for (Table t : tables) {
                if (t.getAssignedFamilies().contains(selectedFamily)) {
                    alreadyAssignedTable = t;
                    break;
                }
            }

            if (alreadyAssignedTable != null) {
                JOptionPane.showMessageDialog(this,
                        "This family/guest is already seated at Table #" + alreadyAssignedTable.getTableNumber() + "!\nPlease remove them first if you want to move them.",
                        "Already Seated", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (validator.validateSeating(selectedTable, selectedFamily)) {
                selectedTable.addFamily(selectedFamily);
                JOptionPane.showMessageDialog(this, "Success! Assigned to Table #" + selectedTable.getTableNumber() + ".", "Done", JOptionPane.INFORMATION_MESSAGE);
                outputArea.append("[Assign] '" + selectedFamily + "' -> Table #" + selectedTable.getTableNumber() + "\n");
                venueCanvas.repaint();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Validation failed!\nRules: Max 2 families, max 10 guests, or active ban.",
                        "Denied", JOptionPane.ERROR_MESSAGE);
                outputArea.append("[Denied] Could not seat '" + selectedFamily + "' at Table #" + selectedTable.getTableNumber() + "\n");
            }
        });

        removeSeatBtn.addActionListener(e -> {
            Family selectedFamily = (Family) familyComboBox.getSelectedItem();
            if (selectedFamily == null) {
                JOptionPane.showMessageDialog(this, "Please select a guest or family from the dropdown!", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Table foundTable = null;
            for (Table t : tables) {
                if (t.getAssignedFamilies().contains(selectedFamily)) {
                    foundTable = t;
                    break;
                }
            }

            if (foundTable == null) {
                JOptionPane.showMessageDialog(this, "This family/guest is not assigned to any table.", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            foundTable.getAssignedFamilies().remove(selectedFamily);
            JOptionPane.showMessageDialog(this, "Successfully removed '" + selectedFamily.getName() + "' from Table #" + foundTable.getTableNumber() + ".", "Removed", JOptionPane.INFORMATION_MESSAGE);
            outputArea.append("[Unassign] '" + selectedFamily.getName() + "' removed from Table #" + foundTable.getTableNumber() + "\n");

            detailsPanel.removeAll();
            JLabel newPlaceholder = new JLabel("<html><center>Click on a table from the chart to view its guest list.</center></html>", SwingConstants.CENTER);
            newPlaceholder.setFont(new Font("Times New Roman", Font.ITALIC, 13));
            newPlaceholder.setForeground(new Color(140, 125, 110));
            detailsPanel.add(newPlaceholder, BorderLayout.CENTER);
            detailsPanel.revalidate();
            detailsPanel.repaint();

            venueCanvas.repaint();
        });
    }

    private JPanel createStyledRow() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 2));
        panel.setBackground(new Color(255, 253, 250));
        return panel;
    }

    private static class StyledButton extends JButton {
        public StyledButton(String text) {
            super(text);
            setFocusPainted(false);
            setFont(new Font("Times New Roman", Font.BOLD, 11));
            setBackground(new Color(139, 110, 80));
            setForeground(Color.WHITE);
            setBorder(BorderFactory.createEmptyBorder(3, 6, 3, 6));
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }

    private class VenueCanvas extends JPanel {
        private List<Rectangle> tableBounds = new ArrayList<>();

        public VenueCanvas() {
            setBackground(new Color(255, 252, 248));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    for (int i = 0; i < tableBounds.size(); i++) {
                        if (tableBounds.get(i).contains(e.getPoint())) {
                            Table clickedTable = tables.get(i);
                            showTableDetailsInPanel(clickedTable);
                            break;
                        }
                    }
                }
            });
        }

        private void showTableDetailsInPanel(Table t) {
            detailsPanel.removeAll();

            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
            infoPanel.setBackground(new Color(255, 253, 250));

            JLabel title = new JLabel("Table #" + t.getTableNumber());
            title.setFont(new Font("Times New Roman", Font.BOLD, 18));
            title.setForeground(new Color(110, 85, 60));
            title.setAlignmentX(Component.LEFT_ALIGNMENT);
            infoPanel.add(title);

            infoPanel.add(Box.createVerticalStrut(5));

            JLabel capacity = new JLabel("Total Guests: " + t.getTotalGuests() + " / 10");
            capacity.setFont(new Font("Times New Roman", Font.BOLD, 13));
            capacity.setForeground(new Color(139, 110, 80));
            capacity.setAlignmentX(Component.LEFT_ALIGNMENT);
            infoPanel.add(capacity);

            infoPanel.add(Box.createVerticalStrut(15));

            JLabel listTitle = new JLabel("Assigned Families & Guests:");
            listTitle.setFont(new Font("Times New Roman", Font.BOLD, 12));
            listTitle.setForeground(new Color(90, 70, 50));
            listTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
            infoPanel.add(listTitle);

            infoPanel.add(Box.createVerticalStrut(5));

            JTextArea guestsArea = new JTextArea();
            guestsArea.setEditable(false);
            guestsArea.setBackground(new Color(245, 240, 232));
            guestsArea.setForeground(new Color(60, 50, 40));
            guestsArea.setFont(new Font("Times New Roman", Font.PLAIN, 12));
            guestsArea.setMargin(new Insets(8, 8, 8, 8));

            if (t.getAssignedFamilies().isEmpty()) {
                guestsArea.setText("No guests assigned to this table yet.");
            } else {
                StringBuilder sb = new StringBuilder();
                for (Family fam : t.getAssignedFamilies()) {
                    if (fam.getCount() == 1) {
                        sb.append("• ").append(fam.getName()).append("\n");
                    } else {
                        sb.append("• ").append(fam.getName()).append(" (").append(fam.getCount()).append(" members)\n");
                    }
                }
                guestsArea.setText(sb.toString().trim());
            }

            JScrollPane scroll = new JScrollPane(guestsArea);
            scroll.setAlignmentX(Component.LEFT_ALIGNMENT);

            detailsPanel.add(infoPanel, BorderLayout.NORTH);
            detailsPanel.add(scroll, BorderLayout.CENTER);
            detailsPanel.revalidate();
            detailsPanel.repaint();

            outputArea.append("\n=== TABLE #" + t.getTableNumber() + " SELECTED ===\n");
            outputArea.append("Total Guests: " + t.getTotalGuests() + "/10\n");
            for (Family fam : t.getAssignedFamilies()) {
                if (fam.getCount() == 1) {
                    outputArea.append(" - " + fam.getName() + "\n");
                } else {
                    outputArea.append(" - " + fam.getName() + " (" + fam.getCount() + " members)\n");
                }
            }
            outputArea.append("===============================\n");
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            tableBounds.clear();

            g2d.setColor(new Color(110, 85, 60));
            g2d.setFont(new Font("Times New Roman", Font.BOLD, 18));
            String subTitle = "PLEASE FIND YOUR SEAT";
            int subWidth = g2d.getFontMetrics().stringWidth(subTitle);
            g2d.drawString(subTitle, (getWidth() - subWidth) / 2, 40);

            if (tables.isEmpty()) {
                g2d.setColor(new Color(150, 130, 110));
                g2d.setFont(new Font("Times New Roman", Font.ITALIC, 16));
                g2d.drawString("No tables created yet. Click 'Add Table' on the left panel.", 60, 100);

                setPreferredSize(new Dimension(getWidth(), 400));
                return;
            }

            int maxPerRow = 3;
            int diameter = 130;
            int colGap = 50;
            int rowGap = 50;
            int startY = 80;

            int rows = (int) Math.ceil((double) tables.size() / maxPerRow);
            int totalHeight = startY + rows * (diameter + rowGap) + 50;

            setPreferredSize(new Dimension(getWidth(), Math.max(getHeight(), totalHeight)));

            for (int i = 0; i < tables.size(); i++) {
                Table t = tables.get(i);
                int row = i / maxPerRow;
                int col = i % maxPerRow;

                int itemsInThisRow = Math.min(maxPerRow, tables.size() - row * maxPerRow);
                int rowWidth = itemsInThisRow * diameter + (itemsInThisRow - 1) * colGap;
                int startX = (getWidth() - rowWidth) / 2;
                if (startX < 40) startX = 40;

                int x = startX + col * (diameter + colGap);
                int y = startY + row * (diameter + rowGap);

                tableBounds.add(new Rectangle(x, y, diameter, diameter));

                g2d.setColor(new Color(245, 235, 220));
                g2d.fillOval(x, y, diameter, diameter);
                g2d.setColor(new Color(182, 145, 75));
                g2d.setStroke(new BasicStroke(2));
                g2d.drawOval(x, y, diameter, diameter);

                String line1 = "Table " + t.getTableNumber();
                String line2 = t.getTotalGuests() + "/10";

                g2d.setFont(new Font("Times New Roman", Font.BOLD, 13));
                FontMetrics fm1 = g2d.getFontMetrics();
                int w1 = fm1.stringWidth(line1);

                g2d.setFont(new Font("Times New Roman", Font.BOLD, 12));
                FontMetrics fm2 = g2d.getFontMetrics();
                int w2 = fm2.stringWidth(line2);

                int centerX = x + diameter / 2;
                int centerY = y + diameter / 2;

                g2d.setColor(new Color(90, 70, 50));
                g2d.setFont(new Font("Times New Roman", Font.BOLD, 13));
                g2d.drawString(line1, centerX - w1 / 2, centerY - 6);

                g2d.setColor(new Color(139, 110, 80));
                g2d.setFont(new Font("Times New Roman", Font.BOLD, 12));
                g2d.drawString(line2, centerX - w2 / 2, centerY + 14);
            }
        }
    }
}