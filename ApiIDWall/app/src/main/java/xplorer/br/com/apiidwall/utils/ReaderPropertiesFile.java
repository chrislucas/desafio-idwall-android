package xplorer.br.com.apiidwall.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.Set;

public class ReaderPropertiesFile {

    /**
     * Essa classe foi criada para que quanto lermos um
     * arquivo properties as linhas sejam adicionadas
     * na ordem que e elas foram lidas
     *
     */
    public class LinkedHashProperties extends Properties {
        private static final long serialVersionUID = 110011L;
        private LinkedHashMap<Object, Object> map;

        public LinkedHashProperties() {
            map = new LinkedHashMap<>();
        }

        public LinkedHashProperties(Properties defaults) {
            super(defaults);
            map = new LinkedHashMap<>();
        }

        @Override
        public synchronized Object put(Object key, Object value) {
            Object o =  map.put(key, value);
            return super.put(key, value);
        }

        @Override
        public synchronized boolean contains(Object value) {
            return map.containsValue(value);
        }

        @Override
        public boolean containsValue(Object value) {
            return map.containsValue(value);
        }

        @Override
        public Set<Entry<Object, Object>> entrySet() {
            return map.entrySet();
        }

        @Override
        public synchronized void clear() {
            map.clear();
        }

        @Override
        public synchronized boolean containsKey(Object key) {
            return map.containsKey(key);
        }

        @Override
        public synchronized Enumeration<Object> keys() {
            return super.keys();
        }

        @Override
        public synchronized Enumeration<Object> elements() {
            return super.elements();
        }
    }

    public static LinkedHashProperties get(Context context, String filename) {
        LinkedHashProperties properties = null;
        InputStream inputStream = null;
        try {
            AssetManager assetManager = context.getAssets();
            inputStream = assetManager.open(filename);;
            if(inputStream != null) {
                properties = new ReaderPropertiesFile().new LinkedHashProperties();
                properties.load(inputStream);
            }
        }
        catch (IOException ieox) {
            Log.e("EXCP_INPUT_STREAM_OPEN", ieox.getMessage());
        }
        finally {
            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.e("EXCP_INPUT_STREAM_CLOSE", e.getMessage());
                }
            }
        }
        return properties;
    }

}
