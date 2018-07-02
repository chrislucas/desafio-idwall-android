package xplorer.br.com.apiidwall.presenter.request.retrofit.converters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import xplorer.br.com.apiidwall.model.DogFeed;
import xplorer.br.com.apiidwall.presenter.request.Endpoint.DogCategory;

public class DogFeedConverter {

    public DogFeed fromJsonToObject(String json) {
        DogFeed dogFeed = new DogFeed();
        try {
            JSONObject jsonObject = new JSONObject(json);
            String category = jsonObject.getString("category");
            switch (category) {
                case DogCategory.HUSKY:
                    dogFeed.setCategegory(category);
                    break;
                case DogCategory.HOUND:
                    dogFeed.setCategegory(category);
                    break;
                case DogCategory.PUG:
                    dogFeed.setCategegory(category);
                    break;
                case DogCategory.LABRADOR:
                    dogFeed.setCategegory(category);
                    break;
                default:
                    dogFeed.setCategegory(DogCategory.UNDEFINED);
                    break;
            }
            JSONArray jsonArrayPhotos = jsonObject.getJSONArray("list");
            List<String> urls = new ArrayList<>();
            for (int i = 0; i < jsonArrayPhotos.length() ; i++) {
                urls.add(jsonArrayPhotos.getString(i));
            }
            dogFeed.setPhotos(urls);
        }
        catch (JSONException e) {
            return null;
        }
        return dogFeed;
    }

}
