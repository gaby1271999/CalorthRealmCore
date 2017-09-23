package me.trolking1.calorthrealmcore.playerinfo;

import me.trolking1.calorthrealmcore.playerinfo.classes.*;

import java.util.UUID;

/**
 * Created by Gabriel on 4/10/2017.
 */
public class PlayerData {

    private UUID uuid;
    private LevelUp levelUp;
    private PlayerClass playerClass;
    private Skill skill;
    private Data data;
    private boolean using;

    public PlayerData(UUID uuid, LevelUp levelUp, PlayerClass playerClass, Skill skill, Data data, boolean using) {
        this.uuid = uuid;
        this.levelUp = levelUp;
        this.playerClass = playerClass;
        this.skill = skill;
        this.data = data;
        this.using = using;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LevelUp getLevelUp() {
        return levelUp;
    }

    public void setLevelUp(LevelUp levelUp) {
        this.levelUp = levelUp;
    }

    public PlayerClass getPlayerClass() {
        return playerClass;
    }

    public void setPlayerClass(PlayerClass playerClass) {
        this.playerClass = playerClass;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public boolean isUsing() {
        return using;
    }

    public void setUsing(boolean using) {
        this.using = using;
    }
}
