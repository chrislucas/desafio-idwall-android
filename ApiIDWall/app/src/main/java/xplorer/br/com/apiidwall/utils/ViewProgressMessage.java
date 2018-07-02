package xplorer.br.com.apiidwall.utils;


import android.os.Handler;
import android.os.Looper;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;


public class ViewProgressMessage {

    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private AppCompatActivity activity;
    private Handler handler;

    public ViewProgressMessage(AppCompatActivity activity, @LayoutRes int layoutRes) {
        this.builder = new AlertDialog.Builder(activity);
        this.builder.setView(layoutRes);
        this.activity = activity;
        this.handler = new Handler(Looper.getMainLooper());
    }


    public ViewProgressMessage safeShow() {
        if( ! activity.isFinishing()) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (! ViewProgressMessage.this.alertDialog.isShowing() && ! activity.isFinishing()) {
                        ViewProgressMessage.this.alertDialog.show();
                    }
                }
            });
        }
        return this;
    }

    public ViewProgressMessage safeDismiss() {
        if (! activity.isFinishing() ) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (ViewProgressMessage.this.alertDialog.isShowing() && ! activity.isFinishing()) {
                        ViewProgressMessage.this.alertDialog.dismiss();
                    }
                }
            });
        }
        return this;
    }

    public ViewProgressMessage createDefaultAlert(String title, String message, boolean cancelable) {
       builder
               .setTitle(title)
               .setMessage(message)
               .setCancelable(cancelable);
       return this;
    }

    public ViewProgressMessage create() {
        this.alertDialog = builder.create();
        return this;
    }

    public boolean isShowing() {
        return alertDialog != null && alertDialog.isShowing();
    }

    public AlertDialog getAlertDialog() {
        return alertDialog;
    }

    public AlertDialog.Builder getBuilder() {
        return builder;
    }



}
