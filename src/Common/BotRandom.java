package Common;

import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Game;
import org.powerbot.script.rt4.GameObject;

import java.awt.*;
import java.util.Random;


public class BotRandom {
    protected final ClientContext ctx;

    public BotRandom(ClientContext ctx) {
        this.ctx = ctx;
    }

    public void randTurnTo(GameObject gameObject, double chanceZValue, int dev) {
        Random randomno = new Random();
        double randNum = randomno.nextGaussian();

        // Chance of happening
        if(!((-chanceZValue < randNum) && (randNum < chanceZValue))) {
            ctx.camera.turnTo(gameObject, dev);

            System.out.println("randTurnTo Initated");

        }
    }
    public int randInt(int maxLength, int minLength) {
        return (int) (Math.random()*((maxLength-minLength)+1))+minLength;

    }
    public void randSleep(int maxLength, int minLength, double chanceZValue) {
        int sleepLength = (int) (Math.random()*((maxLength-minLength)+1))+minLength;
        Random randomno = new Random();
        double randNum = randomno.nextGaussian();

        // Chance of happening
        if(!((-chanceZValue < randNum) && (randNum < chanceZValue))) {
            System.out.println("randSleep initiated " + sleepLength + " Random num: " + randNum);
            Condition.sleep(sleepLength);
        }
    }

    public void ranInvSkillSwitch(double chanceZValue, int max, int min) {
        Random randomno = new Random();
        double randNum = randomno.nextGaussian();

        // TODO hover over skill

        // Chance of happening
        if(!((-chanceZValue < randNum) && (randNum < chanceZValue))) {
            System.out.println("Random skill switch initiated " + " Random num: " + randNum);

            if(ctx.game.tab() != Game.Tab.STATS) {
                int rand = (int) (Math.random()*((max-min)+1))+min;

                Game.Tab prevTab = ctx.game.tab();
                ctx.game.tab(Game.Tab.STATS);
                Condition.sleep(rand);
                ctx.game.tab(prevTab);
            }
        }
    }

    public Tile randTile(int x, int y, int z, int maxX, int minX, int maxY, int minY, int maxZ, int minZ) {

        int randX = (int) (Math.random()*((maxX-minX)+1))+minX;
        int randY = (int) (Math.random()*((maxY-minY)+1))+minY;
        int randZ = (int) (Math.random()*((maxZ-minZ)+1))+minZ;

        System.out.println("randTile " + randX + randY + randZ);

        return new Tile(randX + x, randY + y, randZ + z);

    }

    public void randCamera(int yaw, int pitch, int maxYaw, int minYaw, int maxPitch, int minPitch, double chanceZValue) {
        Random randomno = new Random();
        double randNum = randomno.nextGaussian();

        // 0.80	1.281551565545
        // 0.90	1.644853626951
        // 0.95	1.959963984540

// TODO add camera tilt

        // Chance of happening
        if(!((-chanceZValue < randNum) && (randNum < chanceZValue))) {
            System.out.println("randCamera initiated");

            int randYaw = (int) (Math.random()*((maxYaw-minYaw)+1))+minYaw;
            int randpitch = (int) (Math.random()*((maxPitch-minPitch)+1))+minPitch;

            ctx.camera.angleTo(yaw + randYaw);
            ctx.camera.pitch(pitch + randpitch);
            System.out.println("randCamera performed" + (yaw + randYaw) + (pitch + randpitch));
        }
    }
    public void randScrollWheel(double chanceZValue) {
        // TODO implement method if possible
    }



    public void randMissClickDev(double chanceZValue, boolean left, int maxX, int minX, int maxY, int minY) {
        Random randomno = new Random();
        double randNum = randomno.nextGaussian();

        // 0.80	1.281551565545
        // 0.90	1.644853626951
        // 0.95	1.959963984540

        // Chance of happening
        if(!((-chanceZValue < randNum) && (randNum < chanceZValue))) {
            System.out.println("randMissClickDev initiated");

            int randX = (int) (Math.random()*((maxX-minX)+1))+minX;
            int randY = (int) (Math.random()*((maxY-minY)+1))+minY;

            Point mousePoint = ctx.input.getLocation();
            ctx.input.click( mousePoint.x + randX,  mousePoint.y + randY, left);
            System.out.println("randMissClickDev performed" + (mousePoint.x + randX) + (mousePoint.y + randY));

        }
    }
    public void randMissClickLastPress(double chanceZValue, boolean left, int maxX, int minX, int maxY, int minY) {
        Random randomno = new Random();
        double randNum = randomno.nextGaussian();

        // 0.80	1.281551565545
        // 0.90	1.644853626951
        // 0.95	1.959963984540

        // Chance of happening
        if(!((-chanceZValue < randNum) && (randNum < chanceZValue))) {
            System.out.println("randMissClickLastPress initiated");

            int randX = (int) (Math.random()*((maxX-minX)+1))+minX;
            int randY = (int) (Math.random()*((maxY-minY)+1))+minY;

            Point mousePoint = ctx.input.getPressLocation();
            ctx.input.click( mousePoint.x + randX,  mousePoint.y + randY, left);
            System.out.println("randMissClickLastPress performed " + " X:" + mousePoint.x + randX  + " Y:" +
                    mousePoint.y + randY);

        }
    }
}
