package bg.softuni.springbootintroduction.service;

import bg.softuni.springbootintroduction.domain.view.StatsView;

public interface StatsService {

    void onRequest();

    StatsView getStats();
}
