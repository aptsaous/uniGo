import gr.inf.unigo.Parser;
import gr.inf.unigo.UniGoDB;

import javax.imageio.ImageIO;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class Test
{
    public static void main( String[] args ) throws Exception
    {
        File image = new File( "/Users/aptsaous/IdeaProjects/uniGo_proj/uniGo/web/images/profile.jpg" );

        BufferedImage image2 = ImageIO.read( image );

        ImageIO.write(image2, "jpg", new File("/Users/aptsaous/IdeaProjects/uniGo_proj/uniGo/web/images/myprofile99.jpg"));

        //        UniGoDB uniGoDB = new UniGoDB( "unigo", "unipass", "uniGo" );
//
//        uniGoDB.searchUserByUserName( "aptsaous2" );
//        uniGoDB.addUser( "dokimh", "asdfg" );
//

//
//
////            String sql = "CREATE TABLE REGISTRATION " +
////                    "(id INTEGER not NULL, " +
////                    " first VARCHAR(255), " +
////                    " last VARCHAR(255), " +
////                    " age INTEGER, " +
////                    " PRIMARY KEY ( id ))";
//
//            stmt.executeUpdate( sql );
//            System.out.println( "Created table in given database..." );
//
//        }
//        catch ( SQLException ex )
//        {
//            ex.printStackTrace();
//        }
    }

}
