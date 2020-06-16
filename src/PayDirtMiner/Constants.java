package PayDirtMiner;

import org.powerbot.script.Area;
import org.powerbot.script.Tile;

public class Constants {
    public static Tile oreTileEnd = new Tile(3738, 5689, 0); // max 1 y deviation, max 5 x
    public static Tile oreTile1 = new Tile(3729, 5685, 0); // max 1 y deviation, max 2 x
    public static Tile oreTile2 = new Tile(3737, 5676, 0); // max 3 y deviation, max 3 x
    public static Tile hopTile = new Tile(3750, 5673, 0); // max 2 y deviation, max 2 x close to hopper
    //public static Tile hopBackTile = new Tile(3749, 5675, 0); // max 2 y deviation, max 2 x close to hopper
    public static Tile bagTile = new Tile(3750, 5660, 0); // max 1 y deviation, max 1 x close to bag
    public static Tile bankTile = new Tile(3759, 5666, 0); // max 1 y deviation, max 1 x close to bank
    public static Tile betweenRocks = new Tile(3732, 5681, 0); // max 1 y deviation, max 1 x close to bank

    public static Tile corner1 = new Tile(3745, 5693 , 0); // max 1 y deviation, max 1 x close to bank
    public static Tile corner2 = new Tile(3745, 5687, 0); // max 1 y deviation, max 1 x close to bank
    public static Tile corner3 = new Tile(3730, 5687, 0); // max 1 y deviation, max 1 x close to bank
    public static Tile corner4 = new Tile(3730, 5693, 0); // max 1 y deviation, max 1 x close to bank
    public static Area miningArea = new Area(corner4, corner3, corner2, corner1);

    public static int [] payDirtOreVein = {26661, 26662, 26664};
    public static int payDirtOre = 12011;

    // TODO add rockfall constant
    public static Tile wheel1 = new Tile(3742, 5669, 0); // max 1 y deviation, max 1 x close to bank
    public static Tile wheel2 = new Tile(3742, 5663, 0); // max 1 y deviation, max 1 x close to bank
    public static Tile wheelTiles = new Tile(3741, 5666, 0); // max 1 y deviation, max 1 x close to bank

    public static int depositBox = 25937;


}
