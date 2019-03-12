package com.redhat.developers.microsweeper.service;

import com.redhat.developers.microsweeper.model.Score;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

public class JpaScoreboardService implements ScoreboardService {

    EntityManager entityManager;

    private JpaScoreboardService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public static JpaScoreboardService of(EntityManager entityManager) {
        return new JpaScoreboardService(entityManager);
    }

    @Override
    public List<Score> getScoreboard() {
        return entityManager.createQuery("SELECT s FROM Score s", Score.class).getResultList();
    }

    @Override
    @Transactional
    public void addScore(Score score) {
        entityManager.persist(score);
        entityManager.flush();
    }

    @Override
    public void clearScores() {
        entityManager.createQuery("DELETE FROM Score").executeUpdate();
        entityManager.flush();
    }

}
