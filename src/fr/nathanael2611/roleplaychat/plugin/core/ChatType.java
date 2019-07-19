package fr.nathanael2611.roleplaychat.plugin.core;

public class ChatType {

    private String prefix;
    private int distance;
    private String format;

    public ChatType(String prefix, int distance, String format) {
        this.prefix = prefix;
        this.distance = distance;
        this.format = format;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getDistance() {
        return distance;
    }

    public String getFormat() {
        return format;
    }
}
