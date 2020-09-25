

/*
 *
 * This file is the Model component of the MVC, and it models the business
 * logic for the web application.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClickModel {
    Map<String, Integer> frequency= new HashMap<String, Integer>();

    //clear the answer list
    void empty()
    {
        frequency.clear();
    }

    //add into list
    void add(String s)
    {
            if (frequency.containsKey(s)) {
                frequency.put(s,frequency.get(s)+1);
            } else {
                frequency.put(s, 1);
            }
    }


}

