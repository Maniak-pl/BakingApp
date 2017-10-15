package pl.maniak.udacity.bakingapp.ui;


public interface BaseContract {

    interface View {
        void showToast(String message);
    }

    interface Router {
        void navigateBack();
    }

    interface Presenter<View, Router> {
        void attachView(View view);
        void attachRouter(Router router);
        void detachView();
        void detachRouter();
        void clear();
    }
}
