package xplorer.br.com.apiidwall.view.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Device {

    public static boolean simpleIsConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos;
        if (manager != null) {
            networkInfos = manager.getAllNetworkInfo();
            for(NetworkInfo net : networkInfos) {
                if(net != null) {
                    /**
                     * Indicates whether network connectivity exists and it is possible to establish
                     * connections and pass data.
                     * <p>Always call this before attempting to perform data transactions.
                     * @return {@code true} if network connectivity exists, {@code false} otherwise.
                     */
                    boolean isConnected = net.isConnected();
                    if(isConnected) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
