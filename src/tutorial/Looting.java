package tutorial;

import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GroundItem;
import org.powerbot.script.rt4.Npc;

import java.util.ArrayList;
import java.util.concurrent.Callable;


public class Looting extends Task {
    int[] lootIDs = {526};
    Tile kill_tile = Tile.NIL;
    ArrayList<Tile> lootTile = new ArrayList<Tile>();

    public Looting(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        if(ctx.players.local().interacting().valid() &&
                !ctx.players.local().interacting().tile().equals(kill_tile) &&
                !ctx.players.local().interacting().inMotion() &&
                ctx.players.local().speed()==0
        ){
            kill_tile = ctx.players.local().interacting().tile();

            int length = 2; //length of a cow
            Npc goblin = (Npc) ctx.players.local().interacting();

            Tile newTile = new Tile(kill_tile.x(), kill_tile.y(), kill_tile.floor()); // Area of loot tile

            lootTile.add(newTile);
            System.out.println("We just added tile: " + newTile + " The cows tile was " + goblin.tile());

        }

        boolean lootExists = false;
        for(Tile t : lootTile){
            if(!ctx.groundItems.select().at(t).id(lootIDs).isEmpty()){
                lootExists = true;
            }
        }

        return lootTile!=null && lootExists &&
                !ctx.players.local().interacting().valid() &&
                !ctx.inventory.isFull();
    }

    @Override
    public void execute() {
        ArrayList<Tile> toRemove = new ArrayList<Tile>();

        for(Tile t : lootTile){
            if(!ctx.groundItems.select().at(t).id(lootIDs).isEmpty()){

                GroundItem hide = ctx.groundItems.select().at(t).id(lootIDs).poll();
                hide.interact("Take", hide.name());

                Callable<Boolean> booleanCallable = new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return !hide.valid();
                    }
                };
                Condition.wait(booleanCallable, 300, 10);

                toRemove.add(t);

            }
        }
        //System.out.println("We removed " + toRemove.size() + " tiles from our list");
        lootTile.removeAll(toRemove);
    }
}