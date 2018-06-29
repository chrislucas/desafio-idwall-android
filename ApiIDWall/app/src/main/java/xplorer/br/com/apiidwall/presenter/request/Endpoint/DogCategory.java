package xplorer.br.com.apiidwall.presenter.request.Endpoint;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@StringDef({DogCategory.HUSKY, DogCategory.HOUND, DogCategory.PUG, DogCategory.LABRADOR})

public @interface DogCategory {
    String HUSKY = "husky";
    String HOUND = "hound";
    String PUG = "pug";
    String LABRADOR = "labrador";
}
