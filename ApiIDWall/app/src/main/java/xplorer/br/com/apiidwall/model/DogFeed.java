package xplorer.br.com.apiidwall.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import xplorer.br.com.apiidwall.presenter.request.Endpoint.DogCategory;

public class DogFeed implements Parcelable{

    @DogCategory
    private String category;

    private List<String> photos;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public DogFeed() {
        // segundo a documentacao da API é categoria padrao
        category = DogCategory.HUSKY;
    }

    private DogFeed(Parcel reader) {
        this();
        readToParcel(reader);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel writer, int i) {
        writer.writeString(category);
        writer.writeStringList(photos);
    }

    private void readToParcel(Parcel reader) {
        category = reader.readString();
        if (photos == null)
            photos = new ArrayList<>();
        reader.readStringList(photos);
    }

    public static final Parcelable.Creator<DogFeed> CREATOR = new Parcelable.Creator<DogFeed>() {
        @Override
        public DogFeed createFromParcel(Parcel parcel) {
            return new DogFeed(parcel);
        }

        @Override
        public DogFeed[] newArray(int i) {
            return new DogFeed[i];
        }
    };

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "Categoria %s.\nQuantidade de fotos %d"
                , category.isEmpty() ? "Padrão" : category
                , photos.size()
        );
    }
}
