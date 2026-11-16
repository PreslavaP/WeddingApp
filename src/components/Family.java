package components;

import java.util.ArrayList;
import java.util.List;

public class Family implements SeatingComponent {
    private String familyName;
    private List<SeatingComponent> members = new ArrayList<>();
    private List<String> bannedFamilies = new ArrayList<>();

    public Family(String familyName) {
        this.familyName = familyName;
    }

    public void addMember(SeatingComponent component)
    {
        members.add(component);
    }

    public void addBannedFamily(String familyName)
    {
        bannedFamilies.add(familyName);
    }

    public boolean isBannedWith(String otherFamilyName)
    {
        return bannedFamilies.contains(otherFamilyName);
    }

    @Override
    public String getName() {
        return familyName;
    }

    @Override
    public int getCount() {
        int count = 0;
        for (SeatingComponent m : members){
            count += m.getCount();
        }
        return count;
    }

    @Override
    public void printDetails(String indent) {
        System.out.println(indent + "+ Family: " + familyName);
        for (SeatingComponent m : members) {
            m.printDetails(indent + "  ");
        }
    }

    @Override
    public String toString() {
        return familyName;
    }
}
