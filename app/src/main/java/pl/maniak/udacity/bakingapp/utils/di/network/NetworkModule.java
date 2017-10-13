package pl.maniak.udacity.bakingapp.utils.di.network;


import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.maniak.udacity.bakingapp.BuildConfig;
import pl.maniak.udacity.bakingapp.api.RecipeInterface;
import pl.maniak.udacity.bakingapp.api.RecipeInvoker;
import pl.maniak.udacity.bakingapp.utils.Constants;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

import static pl.maniak.udacity.bakingapp.utils.Constants.TIMEOUT;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    RecipeInvoker provideRecipeInvoker(RecipeInterface recipeInterface) {
        return new RecipeInvoker(recipeInterface);
    }

    @Provides
    @Singleton
    RecipeInterface provideRecipeInterface(RestAdapter restAdapter) {
        return restAdapter.create(RecipeInterface.class);
    }


    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(TIMEOUT, TimeUnit.SECONDS);
        return okHttpClient;
    }

    @Provides
    @Singleton
    OkClient provideOkClient(OkHttpClient okHttpClient) {
        return new OkClient(okHttpClient);
    }

    @Provides
    @Singleton
    RestAdapter provideRestAdapter(OkClient okClient) {
        return new RestAdapter.Builder()
                .setClient(okClient)
                .setEndpoint(Constants.API_BASE_URL)
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .build();
    }
}
