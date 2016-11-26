package gr.inf.unigo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Parser
{
    private final String USER_AGENT = "Mozilla/5.0";
    private List<String> cookies;
    private HttpsURLConnection conn;
    public String grades;
    public String [][] Urls= new String[][] {
            {"https://10.64.4.64:443/unistudent/","https://10.64.4.64/unistudent/stud_CResults.asp"},
            {"https://www.e-ce.uth.gr/wp-login.php", "https://www.e-ce.uth.gr/studies/undergraduate/fall-timetable/week/"},
            {}
            };


    public String parseUniversity( String userName, String password ,int x)
    {

        String loginUrl = Urls[x][0];
        String gradesUrl= Urls[x][1];
        String page;

//        Parser http = new Parser();

        CookieHandler.setDefault( new CookieManager() ); // make sure cookies are turned on

        try
        {
            page = GetPageContent( loginUrl ); // 1. Send a "GET" request, so that you can extract the form's data.
            String postParams = getFormParams( page, userName, password );
            //System.out.println("DONE");
            sendPost( loginUrl, postParams ); // 2. Construct above post's content and then send a POST request for authentication

            /*String result = http.GetPageContent( url ); // 3. success then go to the student details page.
            String result = GetPageContent( gradesUrl );
            grades =  getStudentGrades( result );*/
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            return e.toString();
        }

        return page;
    }

    public static String getStudentGrades( String html ) throws IOException
    {

        Document doc = Jsoup.parse( html );

        Elements rows = doc.getElementsByClass("topBorderLight");
        Elements rows2= doc.getElementsByClass("redFonts");

        ArrayList<String> gradesArray = new ArrayList<String>();
        ArrayList<String> lessonsArray = new ArrayList<String>();

        for(Element row1: rows2) {
            String grade = row1.getElementsByTag("span").first().ownText();

            if (!grade.equals("")) {

                gradesArray.add(grade);
            }
        }


        for(Element row : rows) {

            String lesson = row.getElementsByTag("td").first().text();
            if(row.hasAttr("colspan")) {

                lessonsArray.add(lesson);
            }
        }
        for(int i = 0; i < lessonsArray.size(); i++) {
            System.out.print( "Lesson: " + lessonsArray.get( i ) + "\n" );
            System.out.println("Grade: "+ gradesArray.get(i));
        }
        return "ok";
    }

    private void sendPost( String loginUrl, String postParams ) throws Exception
    {

        URL obj = new URL( loginUrl );

        conn = ( HttpsURLConnection ) obj.openConnection();

        // Acts like a browser
        conn.setInstanceFollowRedirects(true);
        conn.setUseCaches( false );
        conn.setRequestMethod( "POST" );
        conn.setRequestProperty( "Host", "e-ce.uth.gr" );
        conn.setRequestProperty( "User-Agent", USER_AGENT );
        conn.setRequestProperty( "Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8" );
        conn.setRequestProperty( "Accept-Language", "el-GR,el;q=0.8" );
        conn.setRequestProperty( "Accept-Encoding", "gzip, deflate, br" );
        for ( String cookie : this.cookies )
        {
            conn.addRequestProperty( "Cookie", cookie.split( ";", 1 )[0] );
        }
        conn.setRequestProperty( "Connection", "keep-alive" );
        conn.setRequestProperty( "Referer", "https://euniversity.uth.gr/unistudent/login.asp" );
        conn.setRequestProperty( "Content-Type", " application/x-www-form-urlencoded; charset=utf-8" );
        conn.setRequestProperty( "Content-Length", Integer.toString( postParams.length() ) );

        conn.setDoOutput( true );
        conn.setDoInput( true );

        // Send post request
        DataOutputStream wr = new DataOutputStream( conn.getOutputStream() );
        wr.writeBytes( postParams );
        wr.flush();
        wr.close();

        int responseCode = conn.getResponseCode();
        System.out.println( "\nSending 'POST' request to URL : " + loginUrl );
        System.out.println( "Post parameters : " + postParams );
        System.out.println( "Response Code : " + responseCode );

        BufferedReader in = new BufferedReader( new InputStreamReader( conn.getInputStream() ) );
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ( ( inputLine = in.readLine() ) != null )
        {
            response.append( inputLine + "\n" );
        }

        in.close();

        byte[] defaultBytes = response.toString().getBytes();

        String html = new String(defaultBytes, "UTF-8");

        //System.out.println(conn.getResponseMessage());
//        getStudentDetails(  html );
    }

    private String GetPageContent( String url ) throws Exception
    {

        URL obj = new URL( url );
        conn = ( HttpsURLConnection ) obj.openConnection();
        System.out.println("Conn ok");
        // default is GET
        conn.setRequestMethod( "GET" );
        conn.setUseCaches( false );

        // act like a browser
        conn.setRequestProperty( "User-Agent", USER_AGENT );
        conn.setRequestProperty( "Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8; charset=utf-8" );
        conn.setRequestProperty( "Accept-Language", "el-GR,el;q=0.8" );
        if ( cookies != null )
        {
            for ( String cookie : this.cookies )
            {
                conn.addRequestProperty( "Cookie", cookie.split( ";", 1 )[0] );
            }
        }
        int responseCode = conn.getResponseCode();
        System.out.println( "\nSending 'GET' request to URL : " + url );
        System.out.println( "Response Code : " + responseCode + " message: "  );

        BufferedReader in = new BufferedReader( new InputStreamReader( conn.getInputStream() ) );
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ( ( inputLine = in.readLine() ) != null )
        {
            response.append( inputLine + "\n" );
        }
        in.close();

        System.out.println(conn.getResponseMessage());
        // Get the response cookies
        setCookies( conn.getHeaderFields().get( "Set-Cookie" ) );

        return response.toString();

    }

    public String getStudentDetails( String html )
    {
        Document doc = Jsoup.parse( html );
        Student student = new Student();

        Elements elements = doc.select( "tr [height=20]" );

        Element studentIDTag = doc.getElementById( "subheader" );
        student.setFirstName( elements.first().html() );
        System.out.println( elements.first() );
        Elements studentIDinnerTag = studentIDTag.getElementsByTag( "span" );
        String str = studentIDinnerTag.first().html();
        String lastName =  elements.first().child( 1 ).html();
        student.setStudentID( Integer.parseInt( str.substring( str.indexOf( "(" ) + 1, str.indexOf( ")" ) ) ) );
        student.setLastName( lastName );
        System.out.println( "Your student id is: " + student.getStudentID() + "\nYour last name is: " + student.getLastName() );

        return student.toString();
    }

    public String getFormParams( String html, String username, String password ) throws UnsupportedEncodingException
    {

        System.out.println( "Extracting form's data..." );

        Document doc = Jsoup.parse( html );

        // Google form id
        Element loginform = doc.getElementById( "login" );
        Elements inputElements = loginform.getElementsByTag( "input" );
        List<String> paramList = new ArrayList<String>();
        for ( Element inputElement : inputElements )
        {
            String key = inputElement.attr( "name" );
            String value = inputElement.attr( "value" );

            if ( key.equals( "userName" ) || key.equals( "user_login" ) || key.equals(" "))
                value = username;
            else if ( key.equals( "pwd" ) || key.equals( "user_pass") || key.equals(" ") )
                value = password;
            paramList.add( key + "=" + URLEncoder.encode( value, "UTF-8" ) );
        }

        // build parameters list
        StringBuilder result = new StringBuilder();
        for ( String param : paramList )
        {
            if ( result.length() == 0 )
            {
                result.append( param );
            }
            else
            {
                result.append( "&" + param );
            }
        }

        result.append( "login" );
//        System.out.println( result );

        return result.toString();
    }

    public List<String> getCookies()
    {
        return cookies;
    }

    public void setCookies( List<String> cookies )
    {
        this.cookies = cookies;
    }

}