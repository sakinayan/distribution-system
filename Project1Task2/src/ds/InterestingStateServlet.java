package ds;
/*
 *
 * This is a short example of MVC.
 * The welcome-file in the deployment descriptor (web.xml) points
 * to this servlet.  So it is also the starting place for the web
 * application.
 *
 * The servlet is acting as the controller.
 * There are three views - prompt.jsp , result.jsp, nonresult.jsp
 * prompt.jsp is a starting place.
 * result.jsp and nonresult.jsp:
 * It decides between the two by determining if there is a photo or
 * not. If there is no photo, then it uses the nonresult.jsp view
 * If there is a photo, then it searches for a
 * picture and uses the result.jsp view.
 */

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/*
 * The following WebServlet annotation gives instructions to the web container.
 * It states that when the user browses to the URL path /getAnInterestingPicture
 * then the servlet with the name InterestingPictureServlet should be used.
 *
 * This is the exact same as putting the following lines in the deployment
 * descriptor, web.xml:
 *  <servlet>
 *      <servlet-name>IPServlet</servlet-name>
 *      <servlet-class>InterestingPicture.InterestingPictureServlet</servlet-class>
 *  </servlet>
 *  <servlet-mapping>
 *      <servlet-name>IPServlet</servlet-name>
 *      <url-pattern>/getAnInterestingPicture</url-pattern>
 *  </servlet-mapping>
 */
@WebServlet(name = "InterestingStateServlet", urlPatterns = {"/getAnInterestingState"})
public class InterestingStateServlet extends HttpServlet {
    // Initiate this servlet by instantiating the model that it will use.
    StatePictureModel ipm = null;

    @Override
    public void init() {
        ipm = new StatePictureModel();
    }

    // This servlet will reply to HTTP GET requests via this doGet method
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //default view
        String nextView="prompt.jsp";
        // get the search parameter if it exists
        String search = request.getParameter("state");
        // get Statistic
        String statisticsearch = request.getParameter("statistic");

        //call getstateslist to get list of states name for user choice
        String path = "/WEB-INF/states";
        String filePath = getServletContext().getRealPath(path);
        HashMap<String, String> sl= ipm.getStateslist(filePath);

        //String searchbreviation = sl.get("");
        ArrayList<String> arr = new ArrayList<String>(sl.values());
        request.setAttribute("statelist",arr);

        //set maxdate for today's date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        request.setAttribute("maxdate",dtf.format(now));

        // determine what type of device our user is
        String ua = request.getHeader("User-Agent");
        // prepare the appropriate DOCTYPE for the view pages
        if (ua != null && ((ua.indexOf("Android") != -1) || (ua.indexOf("iPhone") != -1))) {
            /*
            * This is the latest XHTML Mobile doctype. To see the difference it
            * makes, comment it out so that a default desktop doctype is used
            * and view on an Android or iPhone.
            *             */
           request.setAttribute("doctype", "<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.2//EN\" \"http://www.openmobilealliance.org/tech/DTD/xhtml-mobile12.dtd\">");
        } else {
            request.setAttribute("doctype", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
        }

        /*
         * Check if the search parameter is present.
         * If not, then give the user instructions and prompt for a search string.
         * If there is a search parameter, then do the search and return the result.
         */
          if (search != null) {
              //fetch the API
              //get 2-character state code in lower case
              String searchab = ipm.findKey(search);
              // get date
              String StartTime = request.getParameter("start").replace("-","");
              String EndTime =request.getParameter("end").replace("-","");
              int starttime = Integer.parseInt(StartTime);
              int endtime = Integer.parseInt(EndTime);
              if (endtime>starttime) {
                  request.setAttribute("timecompare", "exist");
              };
              // get start end date
              HashMap<String, Integer> api= ipm.getapi(searchab, StartTime,EndTime,statisticsearch);
              //deal with null value for the api
              if(api!=null && api.size()==2){
                  int change = api.get("to")-api.get("From");
                  request.setAttribute("from",api.get("From"));
                  request.setAttribute("to",api.get("to"));
                  request.setAttribute("change",change);
              }

             // use model to do the search and choose the result view
              String pictureURL = ipm.doFlickrSearch(search);
              if(pictureURL!=null) {
                  //forward picture to result.jsp
                  request.setAttribute("pictureURL", pictureURL);
                  nextView = "result.jsp";
              }
              else{
                  // no search picture so choose the noresult view
                  nextView = "noresult.jsp";}
          }

        // Transfer control over the the correct "view"
        RequestDispatcher view = request.getRequestDispatcher(nextView);
        view.forward(request, response);
    }
}

