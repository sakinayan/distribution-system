
/*
 *
 * This is a short example of MVC.
 * The welcome-file in the deployment descriptor (web.xml) points
 * to this servlet.  So it is also the starting place for the web
 * application.
 *
 * The servlet is acting as the controller.
 * There are two views - prompt.jsp and result.jsp.
 * prompt.jsp acts as a starting place.
 * It decides between the two by determining if there is \getResults.
 * If no, then it uses the prompt.jsp view
 * If there is, then it uses results.jsp.
 */

import org.apache.commons.collections.map.HashedMap;

import java.io.IOException;
import java.util.*;
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
@WebServlet(name = "ClickServlet", urlPatterns = {"/getClick","/submit","/getResults"})
public class ClickServlet extends HttpServlet {

    ClickModel ipm = null;

    // Initiate this servlet by instantiating the model that it will use.
    @Override
    public void init() {
        ipm = new ClickModel();
    }

    // This servlet will reply to HTTP post requests via this dopost method
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // get the student choice and post back
        String choice = request.getParameter("option");
        request.setAttribute("c",choice);

        //append answer
        ipm.add(choice);

        // prepare the appropriate DOCTYPE for the view pages
        // determine what type of device our user is
        String ua = request.getHeader("User-Agent");
        if (ua != null && ((ua.indexOf("Android") != -1) || (ua.indexOf("iPhone") != -1))) {
            /*
             * This is the latest XHTML Mobile doctype. To see the difference it
             * makes, comment it out so that a default desktop doctype is used
             * and view on an Android or iPhone.
             */
            request.setAttribute("doctype", "<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.2//EN\" \"http://www.openmobilealliance.org/tech/DTD/xhtml-mobile12.dtd\">");
        } else {
            request.setAttribute("doctype", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
        }

        //forward to the view
        RequestDispatcher view = request.getRequestDispatcher("prompt.jsp");
        view.forward(request, response);
    }
    // This servlet will reply to HTTP get requests via this doget method
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // prepare the appropriate DOCTYPE for the view pages
        // determine what type of device our user is
        String ua = request.getHeader("User-Agent");
        if (ua != null && ((ua.indexOf("Android") != -1) || (ua.indexOf("iPhone") != -1))) {
            /*
             * This is the latest XHTML Mobile doctype. To see the difference it
             * makes, comment it out so that a default desktop doctype is used
             * and view on an Android or iPhone.
             */
            request.setAttribute("doctype", "<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.2//EN\" \"http://www.openmobilealliance.org/tech/DTD/xhtml-mobile12.dtd\">");
        } else {
            request.setAttribute("doctype", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
        }

        //get servelt path if contains \getResults will go to result jsp.
        if (request.getServletPath().contains("/getResults")){
            List<String> keys = new ArrayList<String>(ipm.frequency.keySet());
            Collections.sort(keys); //sort alphebatically
            List<Integer> values = new ArrayList<Integer>();
            for(String s: keys)
            {
                values.add(ipm.frequency.get(s)); //sort value by key order
            }
            request.setAttribute("check",ipm.frequency);
            request.setAttribute("keys",keys);
            request.setAttribute("values",values);

            //forward to the view
            RequestDispatcher view = request.getRequestDispatcher("result.jsp");
            view.forward(request, response);
            ipm.empty(); //clear the results list
        }
        else
        {
            RequestDispatcher view = request.getRequestDispatcher("prompt.jsp");
            view.forward(request, response);
        }
    }
}

