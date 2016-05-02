
import com.ant.myteam.gcm.Content;
import com.ant.myteam.gcm.POST2GCM;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Darka
 */
public class MainTest {
    public static void main( String[] args )
    {
        System.out.println( "Sending POST to GCM" );

        String apiKey = "AIzaSyCwsKIPZ6SBCXaS0O0yW5DJVx57Kpm5e-Y";
        Content content = createContent();

        POST2GCM.post(apiKey, content);
    }

    public static Content createContent(){

        Content c = new Content();

        c.addRegId("ffZnZl4RSpA:APA91bFZZFqRNoKXi53sGXRMgogPxokSziEpntIUtVc1SgzrgqWXlwRntufbX2vmhYjosbnk4k1BFDaRaFCCqTyPBVAAkNYocrphW62waV14xqypDqjalk7YQ-c3c2BhESTaiB0wqVu-");
        c.createData("Test Title", "Test Message");

        return c;
    }
}
