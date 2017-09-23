package me.trolking1.calorthrealmcore.guilds;

import java.util.List;

/**
 * Created by Gabriel on 3/30/2017.
 */
public class GuildState {

    private List<String> inWar, ally, neutral;

    public GuildState(List<String> inWar, List<String> ally, List<String> neutral) {
        this.inWar = inWar;
        this.ally = ally;
        this.neutral = neutral;
    }

    public List<String> getInWar() {
        return inWar;
    }

    public void setInWar(List<String> inWar) {
        this.inWar = inWar;
    }

    public List<String> getAlly() {
        return ally;
    }

    public void setAlly(List<String> ally) {
        this.ally = ally;
    }

    public List<String> getNeutral() {
        return neutral;
    }

    public void setNeutral(List<String> neutral) {
        this.neutral = neutral;
    }
}
