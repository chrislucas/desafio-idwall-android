package xplorer.br.com.apiidwall.utils;

import android.support.annotation.IntDef;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import xplorer.br.com.apiidwall.R;

public class ViewMessage {

    private View viewRoot;
    private Snackbar snackbar;

    private interface Callback {
        void onDismissed(Snackbar transientBottomBar, int event);
        void onShown(Snackbar transientBottomBar);
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({DURATION.INDETERMINATE, DURATION.LONG, DURATION.SHORT})
    public @interface DURATION {
        int INDETERMINATE = Snackbar.LENGTH_INDEFINITE;
        int LONG = Snackbar.LENGTH_LONG;
        int SHORT = Snackbar.LENGTH_SHORT;
    }

    public ViewMessage(View viewRoot) {
        this.viewRoot = viewRoot;
        snackbar = Snackbar.make(this.viewRoot, "", Snackbar.LENGTH_INDEFINITE);
    }

    public ViewMessage build(String message, @DURATION int duration, View.OnClickListener onClickListener) {
        snackbar.setText(message);
        snackbar.setDuration(duration);
        addOnClickListener(onClickListener);
        return this;
    }


    public ViewMessage build(String message, @DURATION int duration, Callback callback) {
        snackbar.setText(message);
        snackbar.setDuration(duration);
        addCallback(callback);
        return this;
    }

    public ViewMessage build(String message, @DURATION int duration
            ,  Callback callback, View.OnClickListener onClickListener) {
        snackbar.setText(message);
        snackbar.setDuration(duration);
        addCallback(callback);
        addOnClickListener(onClickListener);
        return this;
    }

    private void addOnClickListener(View.OnClickListener onClickListener) {
        if (onClickListener != null) {
            snackbar.setAction(R.string.close, onClickListener);
        }
    }

    private void addCallback(final Callback callback) {
        if (callback != null) {
            snackbar.addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                @Override
                public void onDismissed(Snackbar transientBottomBar, int event) {
                    callback.onDismissed(transientBottomBar, event);
                }

                @Override
                public void onShown(Snackbar transientBottomBar) {
                    callback.onShown(transientBottomBar);
                }
            });
        }
    }

    public void showWithSafety() {
        if (snackbar != null && ! snackbar.isShown())
            snackbar.show();
    }

    public void dismissWithSafety() {
        if (snackbar != null && snackbar.isShown())
            snackbar.dismiss();
    }

    public boolean isShowing() {
        return snackbar != null && snackbar.isShownOrQueued();
    }
}
