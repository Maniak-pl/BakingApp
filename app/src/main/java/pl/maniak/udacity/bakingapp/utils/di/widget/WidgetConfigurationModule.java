package pl.maniak.udacity.bakingapp.utils.di.widget;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import dagger.Module;
import dagger.Provides;
import lombok.RequiredArgsConstructor;
import pl.maniak.udacity.bakingapp.widget.WidgetConfAdapter;
import pl.maniak.udacity.bakingapp.widget.WidgetConfigurationActivity;

@Module
@RequiredArgsConstructor
public class WidgetConfigurationModule {

    private final WidgetConfigurationActivity context;

    @Provides
    WidgetConfAdapter provideAdapter() {
        return new WidgetConfAdapter(context);
    }

    @Provides
    RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(context);
    }
}