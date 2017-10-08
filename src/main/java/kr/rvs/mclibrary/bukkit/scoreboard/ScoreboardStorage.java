package kr.rvs.mclibrary.bukkit.scoreboard;

import kr.rvs.mclibrary.bukkit.MCUtils;
import kr.rvs.mclibrary.collection.StringArrayList;
import kr.rvs.mclibrary.general.VarargsParser;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Junhyeong Lim on 2017-10-08.
 */
public class ScoreboardStorage {
    private final String id;
    private final String title;
    private final StringArrayList contents = new StringArrayList();
    private transient Map<String, String> replacements = new HashMap<>();

    public ScoreboardStorage(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public ScoreboardStorage(String title) {
        this("mclibrary", title);
    }

    private Map<String, String> getReplacements() {
        return replacements != null ? replacements : (replacements = new HashMap<>());
    }

    private String formatting(String content) {
        for (Map.Entry<String, String> entry : getReplacements().entrySet()) {
            content = content.replace(entry.getKey(), entry.getValue());
        }
        return MCUtils.colorize(content);
    }

    public ScoreboardStorage content(String... contents) {
        this.contents.addAll(Arrays.asList(contents));
        return this;
    }

    public ScoreboardStorage replacement(Object... args) {
        VarargsParser parser = new VarargsParser(args);
        parser.parse(section -> getReplacements().put(section.getString(0), section.getString(1)));
        return this;
    }

    public Scoreboard show(Iterable<Player> players) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective(id, "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(formatting(title));
        for (int i = 0; i < contents.size(); i++) {
            String content = formatting(contents.get(i));
            int number = contents.size() - i;
            Score score = objective.getScore(content);
            score.setScore(number);
        }

        for(Player player : players) {
            player.setScoreboard(scoreboard);
        }

        return scoreboard;
    }

    public Scoreboard show(Player... players) {
        return show(Arrays.asList(players));
    }
}
