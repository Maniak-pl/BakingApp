package pl.maniak.udacity.bakingapp.utils.di.widget;

import dagger.Component;
import pl.maniak.udacity.bakingapp.utils.di.AppComponent;
import pl.maniak.udacity.bakingapp.widget.WidgetConfigurationActivity;

@WidgetConfigurationScope
@Component(dependencies = AppComponent.class,
        modules = WidgetConfigurationModule.class
)
public interface WidgetConfigurationComponent {
    void inject(WidgetConfigurationActivity widgetConfigurationActivity);
}