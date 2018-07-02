package xplorer.br.com.apiidwall.presenter.request.retrofit.converters;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import xplorer.br.com.apiidwall.model.User;
import xplorer.br.com.apiidwall.utils.HelperDateFormat;

public class UserConverter {

    public User fromJsonToObject(String json) {
        User user = new User();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject jsonUser = jsonObject.getJSONObject("user");
            user.setId(jsonUser.getString("_id"));
            user.setToken(jsonUser.getString("token"));

            String createAt = jsonUser.getString("createdAt");
            createAt = HelperDateFormat.convertFormatDateToOtherFormatDate(createAt
                    , "yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", HelperDateFormat.DEFAULT_BR_FORMAT_DATE);
            user.setCreatedAt(createAt);

            String updateAt = jsonUser.getString("updatedAt");
            updateAt = HelperDateFormat.convertFormatDateToOtherFormatDate(updateAt
                    , "yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", HelperDateFormat.DEFAULT_BR_FORMAT_DATE);
            user.setUpdatedAt(updateAt);
        }
        catch (JSONException e) {
            String message = e.getMessage();
            Log.e("EXCP", message != null ? message : "Não foi possível recuperar o erro");
            return null;
        }
        return user;
    }

    public String fromObjectToJson(User user) {
        JSONObject data = new JSONObject();
        try {
            data.put("email", user.getEmail());
        } catch (JSONException e) {
            String message = e.getMessage();
            Log.e("EXCP", message != null ? message : "Não foi possível recuperar o erro");
        }
        return data.toString();
    }

}
