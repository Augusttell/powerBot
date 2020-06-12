package Common;

/**
 *
 * Created by Seth on Oct 24, 2016 at 3:10:08 PM.
 * @author Seth Davis <sethdavis321@gmail.com>
 * @Skype Sethrem
 *
 */
public enum Skill {

    ATTACK("Attack", 0, 1),
    DEFENSE("Defense", 1, 3),
    STRENGTH("Strength", 2, 2),
    HITPOINTS("Hitpoints", 3, 9),
    RANGE("Range", 4, 4),
    PRAYER("Prayer", 5, 5),
    MAGIC("Magic", 6, 6),
    COOKING("Cooking", 7, 20),
    WOODCUTTING("Woodcutting", 8, 22),
    FLETCHING("Fletching", 9, 14),
    FISHING("Fishing", 10, 19),
    FIREMAKING("Firemaking", 11, 21),
    CRAFTING("Crafting", 12, 13),
    SMITHING("Smithing", 13, 18),
    MINING("Mining", 14, 17),
    HERBLORE("Herblore", 15, 11),
    AGILITY("Agility", 16, 10),
    THIEVING("Thieving", 17, 12),
    SLAYER("Slayer", 18, 15),
    FARMING("Farming", 19, 23),
    RUNECRAFTING("Runecrafting", 20, 7),
    HUNTER("Hunting", 21, 16),
    CONSTRUCTION("Construction", 22, 8),
    NILL("", -1, -1);

    private int id, childId;
    private String name;
    private Skill(String name, int id, int childId) {
        this.name = name;
        this.id = id;
        this.childId = childId;
    }

    public int id() {
        return id;
    }

    public int childId() {
        return childId;
    }

    public Skill forId(int id) {
        for (Skill skill : values()) {
            if (skill.id == id) {
                return skill;
            }
        }
        return NILL;
    }

    public Skill forName(String name) {
        for (Skill skill : values()) {
            if (name.equalsIgnoreCase(skill.name)) {
                return skill;
            }
        }
        return NILL;
    }

}