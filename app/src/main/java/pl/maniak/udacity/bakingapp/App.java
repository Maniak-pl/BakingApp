package pl.maniak.udacity.bakingapp;


import android.app.Application;

import lombok.Getter;
import pl.maniak.udacity.bakingapp.utils.di.AppComponent;
import pl.maniak.udacity.bakingapp.utils.di.AppModule;
import pl.maniak.udacity.bakingapp.utils.di.DaggerAppComponent;


public class App extends Application {

    @Getter
    private static AppComponent appComponent;

    @Getter
    private static App instance = new App();

    @Override
    public void onCreate() {
        super.onCreate();
        initAppComponent();
        instance = this;
    }

    private void initAppComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }


}
