package components;

public class Guest implements SeatingComponent {
    private String guestName;

    public Guest(String guestName) {
        this.guestName = guestName;
    }

    @Override
    public String getName() {
        return guestName;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public void printDetails(String indent) {
        System.out.println(indent + "- Guest: " + guestName);
    }
}
