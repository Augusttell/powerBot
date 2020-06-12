package Common;

import org.powerbot.script.rt4.ClientContext;
import Common.Skill;

import java.util.LinkedList;

/**
 * Created by Seth on Oct 24, 2016 at 3:35:32 PM.
 * @author Seth Davis <sethdavis321@gmail.com>
 * @Skype Sethrem
 * @PB Reminisce_
 * @Use used to tracked all experience gained in a skill(s)
 * Main class uses naming conventions similar to the Powerbot api
 */
public class ExperienceTracker {

    protected final ClientContext ctx;
    private final LinkedList<Tracker> trackers;

    public ExperienceTracker(ClientContext ctx) {
        this.ctx = ctx;
        this.trackers = new LinkedList<Tracker>();
    }

    /**
     * Starts tracking xp said skill
     * @param skill
     */

    public void start(Skill skill) {
        trackers.add(new Tracker(skill));
    }

    /**
     * Starts tracking xp for all skills & clears all previously tracked data
     */
    public void startAll() {
        startAll(true);
    }

    /**
     * Starts tracking xp for all skills
     * @param clear clears all previously tracked xp data
     */
    public void startAll(boolean clear) {
        if (clear)
            trackers.clear();

        for (Skill skill : Skill.values()) {
            start(skill);
        }
    }


    /**
     *
     * @param skill
     * @return true if you're already tracking a certain skill
     */
    public boolean tracking(Skill skill) {
        for (Tracker tracker : trackers) {
            if (skill == tracker.skill) {
                return true;
            }
        }
        return false;
    }

    /**
     * Note: will never return null
     * @param skill
     * @return Returns the tracker corresponding to this skill
     */
    private Tracker getTracker(Skill skill) {
        for (Tracker tracker : trackers) {
            if (skill == tracker.skill) {
                return tracker;
            }
        }
        return null;
    }

    /**
     *
     * @param skill
     * @return elapsed time since start of tracking said skill
     */
    public long elapsed(Skill skill) {
        return tracking(skill) ? getTracker(skill).getElapsed() : 0;
    }

    /**
     *
     * @param skill
     * @return total levels gained of said skill
     */
    public int gainedLevels(Skill skill) {
        return tracking(skill) ? getTracker(skill).getGainedLevels() : 0;
    }

    /**
     *
     * @param skill
     * @return total exp gained of said skill
     */
    public int gainedXP(Skill skill) {
        return tracking(skill) ? getTracker(skill).getGainedXP() : 0;
    }

    /**
     *
     * @param skill
     * @return xph being gained of said skill
     */
    public int gainedXPPerHour(Skill skill) {
        return tracking(skill) ? getTracker(skill).getGainedXPPerHour() : 0;
    }

    /**
     *
     * @param skill
     * @return time left till leving said skill to skill level + 1
     */
    public long timeToLevel(Skill skill) {
        return tracking(skill) ? getTracker(skill).getTimeToLevel() : 0;
    }

    /**
     * Tracking class of a said skill
     */
    private class Tracker {

        private final Skill skill;
        private final long startTime;
        private final int level, experience;

        public Tracker(Skill skill) {
            if (skill == null)
                skill = Skill.NILL;
            this.skill = skill;
            this.level = ctx.skills.realLevel(skill.id());
            this.experience = ctx.skills.experience(skill.id());
            this.startTime = System.currentTimeMillis();
        }

        public int getStartXP() {
            return experience;
        }

        public int getStartLevel() {
            return level;
        }

        public long getElapsed() {
            return System.currentTimeMillis() - startTime;
        }

        public int getGainedLevels() {
            return ctx.skills.realLevel(skill.id()) - level;
        }

        public int getGainedXP() {
            return ctx.skills.experience(skill.id()) - experience;
        }

        public int getGainedXPPerHour() {
            return (int) ((getGainedXP() * 3600000D) / (System.currentTimeMillis() - startTime));
        }

        public int getCurrentXP() {
            return ctx.skills.experience(skill.id());
        }

        public int getCurrentLevel() {
            return ctx.skills.realLevel(skill.id());
        }

        private long getTimeToLevel() {
            return (long) (((ctx.skills.experienceAt(ctx.skills.realLevel(skill.id()) + 1) - ctx.skills.experience(skill.id())) * 3600000D) / (double) getGainedXPPerHour());
        }

        /**
         * @return checks if two trackers are the same
         */
        @Override
        public boolean equals(Object obj) {
            return obj == null ? false : obj instanceof Tracker ? ((Tracker) obj).skill.id() == skill.id() ? true : false : false;
        }

    }

}