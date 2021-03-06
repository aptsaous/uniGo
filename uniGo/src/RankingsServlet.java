import gr.inf.unigo.RankingEntry;
import gr.inf.unigo.Student;
import gr.inf.unigo.UniGoDB;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class RankingsServlet extends HttpServlet
{
    @Override
    protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException
    {
        RequestDispatcher view;

        HttpSession session = req.getSession();
        Student user = ( Student ) session.getAttribute( "user" );


        UniGoDB db = ( UniGoDB ) getServletContext().getAttribute( "db" );

        try
        {
            ArrayList<RankingEntry> rankings = db.getRankings( user.getUserName() );

            session.setAttribute( "rankings", rankings );
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }

        view = req.getRequestDispatcher( "rankings.jsp" );

        view.forward( req, resp );

    }
}
