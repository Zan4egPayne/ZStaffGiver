package ru.zan4eg.zstaffgiver.Utils;

public class Cooldown {
    private String name;
    private long time;
    private long starttime;
    private String playerName;

    public Cooldown(String playerName, String name, long time) {
        this.name = name;
        this.time = time;
        this.playerName = playerName;
        this.starttime = System.currentTimeMillis();
    }

    public String getName() {
        return this.name;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public long getLeftTime() {
        return this.time - (System.currentTimeMillis() - this.starttime) / 1000L;
    }

    public boolean isLeft() {
        return this.getLeftTime() <= 0L;
    }
}
