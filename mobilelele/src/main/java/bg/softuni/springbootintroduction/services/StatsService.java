package bg.softuni.springbootintroduction.services;

import bg.softuni.springbootintroduction.domain.view.StatsView;

public interface StatsService {

    void onRequest();

    StatsView getStats();
}
