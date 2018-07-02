package xplorer.br.com.apiidwall.view.adapter.spinners;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import xplorer.br.com.apiidwall.R;
import xplorer.br.com.apiidwall.model.DogFeed;
import xplorer.br.com.apiidwall.presenter.request.Endpoint.DogCategory;

public class AdapterOptionsDogCategory extends ArrayAdapter<String> {

    private List<String> options;

    public AdapterOptionsDogCategory(@NonNull Context context, int resource, @NonNull List<String> options) {
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
        return options.get(position);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_simple_string_adapter_data, parent, false);
        String option = getItem(position);
        if (option != null) {
            ( (TextView) view.findViewById(R.id.text_data)).setText(option);
        }
        return view;
    }

}
