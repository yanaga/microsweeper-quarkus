package com.redhat.developers.microsweeper.model;

import org.bson.Document;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "SCORES", uniqueConstraints = @UniqueConstraint(columnNames = "scoreId"))
public class Score {

    @Id
    @TableGenerator(
            name = "idGen",
            table = "id_seq_table",
            pkColumnValue = "scoreId"
    )
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "idGen")
    @Column(name = "scoreId", nullable = false)
    private long scoreId;
    private String name;
    private String level;
    private int time;
    private boolean success;

    public Score() {

    }

    public Score(String name, String level, int time, boolean success) {
        this.name = name;
        this.level = level;
        this.time = time;
        this.success = success;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public long getScoreId() {
        return scoreId;
    }

    public void setScoreId(long scoreId) {
        this.scoreId = scoreId;
    }

    @Override
    public String toString() {
        return name + "/" + level + "/" + time + "/" + success + "/" + scoreId;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> obj = new HashMap<>();
        obj.put("name", name);
        obj.put("level", level);
        obj.put("time", time);
        obj.put("success", success);
        return obj;
    }

    public static Score fromDocument(Document d) {
        Score score = new Score();
        score.setName(d.getString("name"));
        score.setLevel(d.getString("level"));
        score.setTime(d.getInteger("time"));
        score.setSuccess(d.getBoolean("success"));
        return score;
    }

}
