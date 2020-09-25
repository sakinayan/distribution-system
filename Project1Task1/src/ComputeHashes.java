import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@WebServlet(name = "ComputeHashes", urlPatterns = {"/ComputeHashes"})
public class ComputeHashes extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //if submit button is clicked
        String action = request.getParameter("action");
        //get the text information
        String search = request.getParameter("inputt");
        //get the hash type
        String text = request.getParameter("hashtype");
        MessageDigest md = null;
        //if submit button and choice is MD5
        if (action!=null && text.equals("MD5")) {
            try {
                 md = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        if (action != null && text.equals("SHA-256")) {
            try {
                 md = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        md.update(search.getBytes());
        byte[] d = md.digest();
        //two ways encoding
        String base64 = DatatypeConverter.printBase64Binary(d);
        String hexbinary = DatatypeConverter.printHexBinary(d);

        //set up string
        StringBuilder backall = new StringBuilder();
        backall.append(String.format("<p style=\"overflow-wrap: break-word\">%s hash of text: %s:</p>\n",text, search));
        backall.append(String.format("<p style=\"overflow-wrap: break-word\"> Base64 encoding: %s </p>\n", base64));
        backall.append(String.format("<p style=\"overflow-wrap: break-word\"> Hexadecimal encoding: %s </p>\n", hexbinary));

        //save temporary
        String output = backall.toString();
        request.setAttribute("back", output);

        //transfer back to jsp
        RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
        rd.forward(request, response);
    }
}