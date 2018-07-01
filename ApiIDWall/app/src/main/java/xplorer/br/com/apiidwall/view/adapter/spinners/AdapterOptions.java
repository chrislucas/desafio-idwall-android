package xplorer.br.com.apiidwall.view.adapter.spinners;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;

import java.util.List;

public class AdapterOptions extends ArrayAdapter<String> {

    private List<String> options;

    public AdapterOptions(@NonNull Context context, int resource, @NonNull List<String> options) {
        super(context, resource, options);
        this.options = options;
    }

    @Override
    public int getCount() {
        return options.size();
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return super.getItem(position);
    }

    @Nullable
    @Override
    public Resources.Theme getDropDownViewTheme() {
        return super.getDropDownViewTheme();
    }
}
