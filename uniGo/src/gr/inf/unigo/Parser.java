package gr.inf.unigo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Parser
{
    private final String USER_AGENT = "Mozilla/5.0";
    private List<String> cookies;
    private HttpsURLConnection conn;

    public static void main( String[] args ) throws Exception
    {

        String loginUrl = "https://euniversity.uth.gr/unistudent/login.asp";
//        String gmail = "https://euniversity.uth.gr/unistudent/studentMain.asp";

        Parser http = new Parser();

        CookieHandler.setDefault( new CookieManager() ); // make sure cookies are turned on


        // 1. Send a "GET" request, so that you can extract the form's data.
        String page = http.GetPageContent( loginUrl );
        String postParams = http.getFormParams( page, "aptsaous", "YOUR_PASSWD" );

        // 2. Construct above post's content and then send a POST request for
        // authentication
        http.sendPost( loginUrl, postParams );

        // 3. success then go to gmail.
//        String result = http.GetPageContent( gmail );
//        System.out.println( result );
    }

    private void sendPost( String loginUrl, String postParams ) throws Exception
    {

        URL obj = new URL( loginUrl );
        conn = ( HttpsURLConnection ) obj.openConnection();

//        POST /unistudent/login.asp HTTP/1.1
        // Acts like a browser
        conn.setInstanceFollowRedirects(true);
        conn.setUseCaches( false );
        conn.setRequestMethod( "POST" );
        conn.setRequestProperty( "Host", "euniversity.uth.gr" );
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
        conn.setRequestProperty( "Content-Type", " application/x-www-form-urlencoded" );
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
//        System.out.println( response.toString() );

        responseCode = conn.getResponseCode();

        getStudentDetails(  response.toString() );
    }

    private String GetPageContent( String url ) throws Exception
    {

        URL obj = new URL( url );
        conn = ( HttpsURLConnection ) obj.openConnection();

        // default is GET
        conn.setRequestMethod( "GET" );

        conn.setUseCaches( false );

        // act like a browser
        conn.setRequestProperty( "User-Agent", USER_AGENT );
        conn.setRequestProperty( "Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8" );
        conn.setRequestProperty( "Accept-Language", "en-US,en;q=0.5" );
        if ( cookies != null )
        {
            for ( String cookie : this.cookies )
            {
                conn.addRequestProperty( "Cookie", cookie.split( ";", 1 )[0] );
            }
        }
        int responseCode = conn.getResponseCode();
        System.out.println( "\nSending 'GET' request to URL : " + url );
        System.out.println( "Response Code : " + responseCode + " message: " + conn.getResponseMessage() );

        BufferedReader in = new BufferedReader( new InputStreamReader( conn.getInputStream() ) );
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ( ( inputLine = in.readLine() ) != null )
        {
            response.append( inputLine + "\n" );
        }
        in.close();

        // Get the response cookies
        setCookies( conn.getHeaderFields().get( "Set-Cookie" ) );

        return response.toString();

    }

    public String getStudentDetails( String html )
    {
        Document doc = Jsoup.parse( html );

        // Google form id
        Element loginform = doc.getElementById( "subheader" );

        loginform.html();
        Elements el = loginform.getElementsByTag( "span" );
        System.out.println( "Your student id is: " + el.first().html() );
//        loginform.children().;
        return "ok";
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

            if ( key.equals( "userName" ) )
                value = username;
            else if ( key.equals( "pwd" ) )
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