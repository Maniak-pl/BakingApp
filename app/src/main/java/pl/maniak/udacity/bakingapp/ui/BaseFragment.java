package pl.maniak.udacity.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment implements BaseContract.Router, BaseContract.View {

    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);

        initDaggerComponent();
        init();
        return view;
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void initDaggerComponent();

    protected abstract void init();

    protected abstract void clear();

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        clear();
        super.onDestroyView();
    }

    @Override
    public void showToast(String message) {
        ((BaseActivity) getActivity()).showToast(message);
    }

    @Override
    public void navigateBack() {
        getActivity().onBackPressed();
    }
}