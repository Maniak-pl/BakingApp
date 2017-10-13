package pl.maniak.udacity.bakingapp.utils.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lombok.RequiredArgsConstructor;
import pl.maniak.udacity.bakingapp.App;


@Module
@RequiredArgsConstructor
public class AppModule {

    private final App application;

    @Provides
    @Singleton
    Context provideAppContext() {
        return application;
    }

}
