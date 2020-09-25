package ds;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

public class StatePictureModel {
    String APIURL = "https://api.covidtracking.com/v1/states/";
    String FlagURL = "https://statesymbolsusa.org/categories/state-flag";
    private HashMap<String, String> stateslist;
    private HashMap<String, Integer> apilist;

    //read file to get the list of states
    public HashMap<String, String> getStateslist(String f) throws IOException {
        File file = new File(f);
        stateslist =  new HashMap<String, String>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while((line = reader.readLine())!= null){
            String[] matches = line.split(",");
            stateslist.put(matches[0],matches[1]);
    }
        return stateslist;
    }
    //get 2-character state code in lower case
    public String findKey(String state){
        String key = "";
        for (Map.Entry entry: stateslist.entrySet()) {
            if(state.equals(entry.getValue())){
                key = (String) entry.getKey();
            }
        }
        return key;
    }

    //get APIlist
    public HashMap<String, Integer> getapi(String searchState, String start, String end, String statisticsearch) throws IOException {
        searchState = URLEncoder.encode(searchState, "UTF-8");
        apilist = new HashMap<String, Integer>();

        // use start date to get the information needed
        String flickrURL = APIURL + searchState +"/"+start+".csv";
        URL url = new URL(flickrURL);
        Scanner scan = new Scanner( new InputStreamReader(url.openStream()));
        boolean inputLine;
        String[] headLine = scan.nextLine().split(",");
        int from =0;
        while (inputLine = scan.hasNextLine()){
            String line = scan.nextLine();
            String[] covid = line.split(",");
            for (int j = 0; j < headLine.length; j++) {
                if (headLine[j].equals(statisticsearch)){
                    try {
                        from = Integer.parseInt(covid[j]);
                    }catch(NumberFormatException ex)
                    {
                        return null;
                    }
                    apilist.put("From",from);
                    break;
                }
            }
        }
        // use end date csv. to get the information needed
        String endURL = APIURL + searchState +"/"+end+".csv";
        URL enddurl = new URL(endURL);
        scan = new Scanner(new InputStreamReader(enddurl.openStream()));
        String[] endheadLine = scan.nextLine().split(",");
        while (inputLine = scan.hasNext()){
            String line = scan.nextLine();
            String[] covid = line.split(",");
            for (int j = 0; j < endheadLine.length; j++) {
                if (endheadLine[j].equals(statisticsearch)){
                    try {
                        from = Integer.parseInt(covid[j]);
                    }catch(NumberFormatException ex)
                    {
                        return null;
                    }
                    apilist.put("to",from);
                    break;
                }
            }
        }
        return apilist;
    }

    public String doFlickrSearch(String searchState) throws UnsupportedEncodingException  {
        /*
         * URL encode the searchTag, e.g. to encode spaces as %20
         *
         * There is no reason that UTF-8 would be unsupported.  It is the
         * standard encoding today.  So if it is not supported, we have
         * big problems, so don't catch the exception.
         */

        // Create a URL for the page to be screen scraped and fetch the response
        String flagRL = FlagURL;
        String response = fetch(flagRL);

        /*
         * Search the page to find the picture URL
         *
         * Screen scraping is an art that requires creatively looking at the
         * HTML that is returned and figuring out how to cut out the data that 
         * is important to you.
         *
         * These particular searches were crafted by carefully looking at 
         * the HTML that Flag returned, and finding (by experimentation) the
         * generalizable steps that will reliably get a picture URL.
         * 
         * First do a String search that gets me close to the picture URL target
         */
        //use flag of state name to find the index
        String find = "Flag of "+searchState;
        int cutLeft = response.indexOf(find);

        // If not found, then no such photo is available.
        if (cutLeft == -1) {
            System.out.println("pictureURL= null");
            return (String) null;
        }
        //find the photo url
        int index = 0;
        int maxindex=0;
        String image = "img typeof=\"foaf:Image\" src=\"";
        while(index != -1 && index <cutLeft){
            index = response.indexOf(image, index+1);
            if(index!=-1 && index<cutLeft){
                maxindex=index;
            }
        }

        // Skip past this string.
        maxindex += image.length();
        // The next character would be the // which in a URL separates the 

        // Look for the right close parenthesis: first occurance ?
        int cutRight = response.indexOf("?", maxindex);

        // Now snip out the part from positions cutLeft to cutRight
        // and return the photo
        String pictureURL = response.substring(maxindex, cutRight);
        return pictureURL;
    }

    /*
     * Make an HTTP request to a given URL
     * 
     * @param urlString The URL of the request
     * @return A string of the response from the HTTP GET.  This is identical
     * to what would be returned from using curl on the command line.
     */
    private String fetch(String urlString) {
        String response = "";
        try {
            URL url = new URL(urlString);
            /*
             * Create an HttpURLConnection.  This is useful for setting headers
             * and for getting the path of the resource that is returned (which 
             * may be different than the URL above if redirected).
             * HttpsURLConnection (with an "s") can be used if required by the site.
             */
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Read all the text returned by the server
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String str;
            // Read each line of "in" until done, adding each to "response"
            while ((str = in.readLine()) != null) {
                // str is one line of text readLine() strips newline characters
                response += str;
            }
            in.close();
        } catch (IOException e) {
            System.out.println("Eeek, an exception");
        }
        return response;
    }

}
