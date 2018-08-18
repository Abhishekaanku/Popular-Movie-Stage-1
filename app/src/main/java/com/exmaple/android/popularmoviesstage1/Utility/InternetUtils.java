package com.exmaple.android.popularmoviesstage1.Utility;
import android.net.Uri;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import com.exmaple.android.popularmoviesstage1.MainActivity;
import com.exmaple.android.popularmoviesstage1.R;

import javax.net.ssl.HttpsURLConnection;

public class InternetUtils {

    public interface URLGenerator {
        String fetchURL();
    }


    public static URL getURL(URLGenerator mURLGenerator) {
        String BASE_URL= mURLGenerator.fetchURL() +
                MainActivity.getContext().getString(R.string.APIkey);
        Uri uri=Uri.parse(BASE_URL);
        URL movieURL=null;
        try {
            movieURL=new URL(uri.toString());
        }
        catch(MalformedURLException e) {
            e.printStackTrace();
        }

        return movieURL;
    }

    public static String get_Response(URL movieURL) throws IOException {

        HttpURLConnection connection=(HttpsURLConnection) movieURL.openConnection();
        String response;
        try {
            InputStream in=connection.getInputStream();
            Scanner scanner=new Scanner(in);
            scanner.useDelimiter("\\A");
            if(scanner.hasNext()) {
                response=scanner.next();
                return response;
            }
            else {
                return null;
            }
        }
        finally {
            connection.disconnect();
        }
    }
}
