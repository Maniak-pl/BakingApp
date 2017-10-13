package pl.maniak.udacity.bakingapp.ui;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;
import pl.maniak.udacity.bakingapp.App;
import pl.maniak.udacity.bakingapp.utils.di.AppComponent;

public abstract class BaseActivity extends AppCompatActivity implements BaseContract.View, BaseContract.Router {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);

        initDaggerComponent();
        init();
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void initDaggerComponent();

    protected abstract void init();

    protected abstract void clear();

    @Override
    protected void onDestroy() {
        clear();
        super.onDestroy();
    }

    protected AppComponent getAppComponent() {
        return App.getAppComponent();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void navigateBack() {
        onBackPressed();
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo !=null && networkInfo.isConnectedOrConnecting();
    }
}
