package pl.maniak.udacity.bakingapp.data.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.maniak.udacity.bakingapp.data.Recipe;

@Getter
@Setter
@AllArgsConstructor
public class NavigateEvent {

    private Recipe recipe;
    private int stepId;

}
