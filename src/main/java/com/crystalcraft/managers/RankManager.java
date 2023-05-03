package com.crystalcraft.managers;

import com.crystalcraft.classes.Rank;
import com.crystalcraft.literanks.Core;
import com.crystalcraft.literanks.Main;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class RankManager {

    private boolean initialized = false;

    public HashMap<UUID, String> playerRanks = new HashMap<>();
    public List<Rank> ranks;
    public Rank defaultRank;

    public void init() {
        if (initialized) return;

        load();

        if (ranks == null)
            ranks = new ArrayList<>();

        if (ranks.isEmpty())
            defaultRanks();

        for (Rank r : ranks) {
            if (r.getName().equalsIgnoreCase(Main.instance.getConfig().getString("defaultRank")))
                defaultRank = r;
        }

        initialized = true;
    }

    private void load() {
        Core.console("loading saved data");
        File configFile = new File(Main.instance.getDataFolder(), "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);

        // ranks
        Constructor constructor = new Constructor(Rank.class);
        Representer representer = new Representer();
        representer.addClassTag(Rank.class, Tag.MAP);
        Yaml yaml = new Yaml(constructor, representer);

        String serializedList = config.getString("ranks");
        if (serializedList == null) serializedList = "";

        ranks = yaml.loadAs(serializedList, List.class);

        // playerRanks
        ConfigurationSection playerRanksSection = config.createSection("playerRanks");

        Set<String> keys = playerRanksSection.getKeys(false);
        for (String key : keys) {
            String value = playerRanksSection.getString(key);
            playerRanks.put(UUID.fromString(key), value);
            Core.console("loaded " + Core.uuidToName(UUID.fromString(key)) + "'s rank (" + value + ")");
        }
    }

    public void saveAndDenit() {
        if (ranks.isEmpty()) return;

        File configFile = new File(Main.instance.getDataFolder(), "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);

        //ranks
        Yaml yaml = new Yaml();
        String serializedList = yaml.dump(ranks);

        config.set("ranks", serializedList);

        //player ranks
        ConfigurationSection playerRanksSection = config.createSection("playerRanks");

        for (UUID key : playerRanks.keySet()) {
            playerRanksSection.set(key.toString(), playerRanks.get(key));
        }

        //saving the config file
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void defaultRanks() {
        if(!rankExists("member")) addRank(createRank("member", "&7Member", new ArrayList<>()));
        if(!rankExists("vip")) addRank(createRank("vip","&aVip", new ArrayList<>()));
        if(!rankExists("moderator")) addRank(createRank("moderator","&2Moderator", new ArrayList<>()));
        if(!rankExists("admin")) addRank(createRank("admin","&9Admin", new ArrayList<>()));
        if(!rankExists("owner")) addRank(createRank("owner","&cOwner", new ArrayList<>()));

        Core.console(" • created the default ranks");
    }

    public @NotNull Rank createRank(String name, String prefix, List<String> permissions) {
        Rank r = new Rank();
        r.setName(name);
        r.setPrefix(prefix);
        r.setPermissions(permissions);
        return r;
    }

    public void addRank(Rank rank) {
        ranks.add(rank);
    }

    public boolean rankExists(String rName) {
        for (Rank r : ranks) {
            if (r.getName().equalsIgnoreCase(rName)) {
                return true;
            }
        }
        return false;
    }

    public Rank getRank(UUID uuid) {
        if (!(playerRanks.containsKey(uuid) && playerRanks.get(uuid) != null)) {
            playerRanks.put(uuid, defaultRank.getName());
            Core.console("Giving " + Core.uuidToName(uuid) + " the default rank");
        }
        return findRank(playerRanks.get(uuid));
    }

    public void setRank(UUID uuid, Rank r) {
        playerRanks.put(uuid,r.getName());
    }

    public Rank findRank(String rName) {
        if (!rankExists(rName)) return null;
        for (Rank r : ranks) {
            if (r.getName().equalsIgnoreCase(rName)) {
                return r;
            }
        }
        return null;
    }
}
