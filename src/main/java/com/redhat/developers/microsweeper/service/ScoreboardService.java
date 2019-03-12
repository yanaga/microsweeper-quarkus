package com.redhat.developers.microsweeper.service;

import com.redhat.developers.microsweeper.model.Score;

import java.util.List;

public interface ScoreboardService {

    List<Score> getScoreboard();

    void addScore(Score score) throws Exception;

    void clearScores() throws Exception;

}
