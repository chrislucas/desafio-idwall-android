package xplorer.br.com.apiidwall.view.customs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;

/**
 * Futuramente implementar um metodo callback para
 * {@link Dialog#setOnDismissListener(DialogInterface.OnDismissListener)}
 * {@link Dialog#setOnShowListener(DialogInterface.OnShowListener)} (DialogInterface.OnDismissListener)}
 * */

public interface DialogFragmentCallback {
    void accessViewDialogFragment(View rootView);
    void accessAlertDialogBuilder(AlertDialog.Builder builder);
    void accessDialog(Dialog dialog);
    void changeDimensionDialog(Dialog view);
    void changeDimensionViewDialog();
    DialogInterface.OnDismissListener actionOnDismissListener();
}
