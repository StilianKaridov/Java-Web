package bg.softuni.springbootintroduction.domain.view;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StatsView {

    private final int authRequests;
    private final int anonymousRequests;

    public int getTotalRequests() {
        return anonymousRequests + authRequests;
    }
}
