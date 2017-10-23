package pl.maniak.udacity.bakingapp.ui.recipedetails.fragment;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindBool;
import butterknife.BindView;
import butterknife.OnClick;
import pl.maniak.udacity.bakingapp.App;
import pl.maniak.udacity.bakingapp.R;
import pl.maniak.udacity.bakingapp.data.Step;
import pl.maniak.udacity.bakingapp.ui.BaseFragment;
import pl.maniak.udacity.bakingapp.utils.di.recipedetails.DaggerRecipeDetailsStepFragmentComponent;
import pl.maniak.udacity.bakingapp.utils.di.recipedetails.RecipeDetailsStepFragmentModule;

import static pl.maniak.udacity.bakingapp.utils.Constants.BUNDLE_KEY_PLAYER_POSITION;
import static pl.maniak.udacity.bakingapp.utils.Constants.BUNDLE_KEY_STEP;
import static pl.maniak.udacity.bakingapp.utils.Constants.BUNDLE_KEY_STEP_ID;

public class RecipeDetailsStepFragment extends BaseFragment implements RecipeDetailsStepFragmentContract.View, RecipeDetailsStepFragmentContract.Router {

    public final static String FRAGMENT_TAG = RecipeDetailsStepFragment.class.getSimpleName();

    @BindView(R.id.recipe_details_step_title)
    TextView titleTv;

    @BindView(R.id.recipe_details_step_description)
    TextView descriptionTv;

    @BindView(R.id.recipe_details_step_next)
    ImageView nextButton;

    @BindView(R.id.recipe_details_step_previous)
    ImageView previousButton;

    @BindView(R.id.recipe_details_navigate)
    LinearLayout navigateLayout;

    @BindBool(R.bool.two_pane_mode)
    boolean isTwoPane;

    @BindView(R.id.recipe_details_step_player)
    SimpleExoPlayerView exoPlayerView;

    private SimpleExoPlayer player;
    private MediaSessionCompat mediaSession;
    private PlaybackStateCompat.Builder stateBuilder;
    private long position;

    @Inject
    RecipeDetailsStepFragmentContract.Presenter presenter;

    public static RecipeDetailsStepFragment newInstance(List<Step> stepList, int stepId) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(BUNDLE_KEY_STEP, (ArrayList<? extends Parcelable>) stepList);
        bundle.putInt(BUNDLE_KEY_STEP_ID, stepId);
        RecipeDetailsStepFragment fragment = new RecipeDetailsStepFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recipe_step;
    }

    @Override
    protected void initDaggerComponent() {
        DaggerRecipeDetailsStepFragmentComponent.builder()
                .appComponent(App.getAppComponent())
                .recipeDetailsStepFragmentModule(new RecipeDetailsStepFragmentModule())
                .build()
                .inject(this);
    }

    @Override
    protected void init() {
        initPresenter();
        initMediaSession();

        exoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);

        List<Step> stepList = getArguments().getParcelableArrayList(BUNDLE_KEY_STEP);
        presenter.onFragmentReady(stepList, getArguments().getInt(BUNDLE_KEY_STEP_ID));

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE && !isTwoPane) {
            expandVideoView(exoPlayerView);
            setViewVisibility(navigateLayout, false);
            setViewVisibility(descriptionTv, false);
        }
    }

    private void expandVideoView(SimpleExoPlayerView exoPlayer) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        exoPlayer.getLayoutParams().height = displayMetrics.heightPixels;
        exoPlayer.getLayoutParams().width = displayMetrics.widthPixels;
    }

    private void setViewVisibility(View view, boolean show) {
        if (show) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onFragmentResume();
        position = getArguments().getLong(BUNDLE_KEY_PLAYER_POSITION, C.TIME_UNSET);
        if(position != C.TIME_UNSET) {
            player.seekTo(position);
        }
    }

    @Override
    public void onPause() {
        presenter.onFragmentPause();
        super.onPause();
    }

    private void initPresenter() {
        presenter.attachView(this);
        presenter.attachRouter(this);
    }

    @Override
    public void showDescriptionDetailStep(String description) {
        descriptionTv.setText(description);
    }

    @Override
    public void showNavigationButton(boolean showPreviousButton, boolean showNextButton) {
        previousButton.setVisibility(showPreviousButton ? View.VISIBLE : View.INVISIBLE);
        nextButton.setVisibility(showNextButton ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void showExoPlayer(boolean showPlayer) {
        exoPlayerView.setVisibility(showPlayer ? View.VISIBLE : View.GONE);
    }

    @Override
    public void initVideo(Uri mediaUri) {
        initPlayer(mediaUri);
    }

    @Override
    public void releasePlayer() {
        if(player != null) {
            position = player.getCurrentPosition();
            player.stop();
            player.release();
            player = null;
        }

        if(mediaSession != null) {
            mediaSession.setActive(false);
        }
    }

    @Override
    public void saveCurrentStep(int currentStep) {
        getArguments().putInt(BUNDLE_KEY_STEP_ID, currentStep);
        getArguments().putLong(BUNDLE_KEY_PLAYER_POSITION, position);
    }

    private void initMediaSession() {
        mediaSession = new MediaSessionCompat(getContext(), FRAGMENT_TAG);
        mediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSession.setMediaButtonReceiver(null);
        stateBuilder = new PlaybackStateCompat.Builder()
                .setActions(PlaybackStateCompat.ACTION_PLAY |
                        PlaybackStateCompat.ACTION_PAUSE |
                        PlaybackStateCompat.ACTION_PLAY_PAUSE);
        mediaSession.setPlaybackState(stateBuilder.build());
        mediaSession.setCallback(new MediaSessionCompat.Callback() {
            @Override
            public void onPlay() {
                player.setPlayWhenReady(true);
            }

            @Override
            public void onPause() {
                player.setPlayWhenReady(false);
            }
        });
        mediaSession.setActive(true);
    }

    private void initPlayer(Uri mediaUri) {
        if (player == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
            exoPlayerView.setPlayer(player);
            player.addListener(new ExoPlayer.EventListener() {
                @Override
                public void onTimelineChanged(Timeline timeline, Object manifest) {

                }

                @Override
                public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

                }

                @Override
                public void onLoadingChanged(boolean isLoading) {

                }

                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                    if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
                        stateBuilder.setState(PlaybackStateCompat.STATE_PLAYING, player.getCurrentPosition(), 1f);
                    } else if (playbackState == ExoPlayer.STATE_READY) {
                        stateBuilder.setState(PlaybackStateCompat.STATE_PAUSED, player.getCurrentPosition(), 1f);
                    }
                    mediaSession.setPlaybackState(stateBuilder.build());
                }

                @Override
                public void onPlayerError(ExoPlaybackException error) {

                }

                @Override
                public void onPositionDiscontinuity() {

                }

                @Override
                public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

                }
            });

            String userAgent = Util.getUserAgent(getContext(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            player.prepare(mediaSource);
            player.setPlayWhenReady(true);
        }
    }

    @Override
    public void showTitleDetailStep(String title) {
        titleTv.setText(title);
    }

    @Override
    protected void clear() {
        presenter.clear();
    }

    @OnClick(R.id.recipe_details_step_previous)
    void previousButton() {
        presenter.onPreviousButtonClicked();
    }

    @OnClick(R.id.recipe_details_step_next)
    void nextButton() {
        presenter.onNextButtonClicked();
    }
}