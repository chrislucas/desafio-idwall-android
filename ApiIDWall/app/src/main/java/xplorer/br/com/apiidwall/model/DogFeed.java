package xplorer.br.com.apiidwall.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import xplorer.br.com.apiidwall.presenter.request.Endpoint.DogCategory;

public class DogFeed implements Parcelable{

    @DogCategory
    private String categegory;

    private List<String> photos;

    public String getCategegory() {
        return categegory;
    }

    public void setCategegory(String categegory) {
        this.categegory = categegory;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public DogFeed() {
        // segundo a documentacao da API é categoria padrao
        categegory = DogCategory.HUSKY;
        photos = new ArrayList<>();
    }

    public DogFeed(Parcel reader) {
        this();
        readToParcel(reader);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel writer, int i) {
        writer.writeString(categegory);
        writer.writeStringList(photos);
    }

    private void readToParcel(Parcel reader) {
        categegory = reader.readString();
        reader.readStringList(photos);
    }

    public static final Parcelable.Creator<DogFeed> CREATOR = new Parcelable.Creator<DogFeed>() {
        @Override
        public DogFeed createFromParcel(Parcel parcel) {
            return new DogFeed();
        }

        @Override
        public DogFeed[] newArray(int i) {
            return new DogFeed[i];
        }
    };

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "Categoria %s.\nQuantidade de fotos %d"
                , categegory.isEmpty() ? "Padrão" : categegory
                , photos.size()
        );
    }
}
