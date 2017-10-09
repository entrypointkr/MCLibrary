package kr.rvs.mclibrary.bukkit.storage;

import kr.rvs.mclibrary.collection.StringArrayList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Arrays;

/**
 * Created by Junhyeong Lim on 2017-10-08.
 */
public class ScoreboardStorage extends ReplaceableStoorage<ScoreboardStorage> {
    private final String id;
    private final String title;
    private final StringArrayList contents = new StringArrayList();

    public ScoreboardStorage(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public ScoreboardStorage(String title) {
        this("mclibrary", title);
    }

    public ScoreboardStorage content(String... contents) {
        this.contents.addAll(Arrays.asList(contents));
        return this;
    }

    public Scoreboard create() {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective(id, "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(formatting(title));

        StringArrayList contents = new StringArrayList(this.contents.size());
        for (String content : this.contents) {
            contents.add(formatting(content));
        }

        for (int i = 0; i < contents.size(); i++) {
            String content = formatting(contents.get(i));
            int number = contents.size() - i;
            Score score = objective.getScore(content);
            score.setScore(number);
        }

        return scoreboard;
    }

    public Scoreboard show(Iterable<Player> players) {
        Scoreboard scoreboard = create();
        for (Player player : players) {
            if (player == null)
                continue;
            player.setScoreboard(scoreboard);
        }
        return scoreboard;
    }

    public Scoreboard show(Player... players) {
        return show(Arrays.asList(players));
    }
}
