package com.crystalcraft.classes;

import java.util.List;

public class Rank {

    private String name;
    private String prefix;
    private List<String> permissions;

    public Rank() {
        // no-args constructor required by SnakeYAML
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
