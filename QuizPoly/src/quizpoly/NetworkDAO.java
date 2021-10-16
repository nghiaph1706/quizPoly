
package quizpoly;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;



//Tôi thử nhiều cách nhưng mỗi này chạy được, tôi cũng chẳng hiểu khúc này
public class NetworkDAO {

    public static String request(String endpoint) throws Exception {
        StringBuilder sb = new StringBuilder();

        URL url = new URL(endpoint);

        // open a connection to this URL
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(((HttpURLConnection) (new URL(endpoint)).openConnection()).getInputStream(), Charset.forName("UTF-8")));

            // read one line at a time.
            String inputLine = bufferedReader.readLine();
            while (inputLine != null) {
                // add this to our output
                sb.append(inputLine);
                // reading the next line
                inputLine = bufferedReader.readLine();
            }
        } finally {
            urlConnection.disconnect();
        }
        return  sb.toString();
    }
}
