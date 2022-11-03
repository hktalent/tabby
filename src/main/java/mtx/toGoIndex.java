package mtx;


import com.google.gson.Gson;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;

/*
-Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true
* */
public class toGoIndex {

    public static String toSHA1(String convertme) {
        return toSHA1(convertme.getBytes(StandardCharsets.UTF_8));
    }
    public static String toSHA1(byte[] convertme) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        }
        catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return byteArrayToHexString(md.digest(convertme));
    }
    public static String byteArrayToHexString(byte[] b) {
        String result = "";
        for (int i=0; i < b.length; i++) {
            result +=
                    Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
        }
        return result;
    }
    public static String toJson(Object o){
        Gson gson = new Gson();
        String json = gson.toJson(o);
        return json;
    }
    public static void Save2GoIndex(Collection<?> list){
        try{
            CloseableHttpClient httpclient = HttpClients.createSystem();
            java.util.Iterator e = list.iterator();
            while (e.hasNext()) {
                String data = toJson(e.next()),id = toSHA1(data);
                HttpPut httpPost = new HttpPut("https://127.0.0.1:8081/api/java/" + id);

                httpPost.setHeader("Connection", "keep-alive");
                httpPost.setHeader("Accept", "application/json, text/plain, */*");
                httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
                httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.135 Safari/537.36");
                httpPost.setEntity(new StringEntity(data, ContentType.APPLICATION_JSON));

                CloseableHttpResponse response2 = httpclient.execute(httpPost);
                System.out.println(response2.getCode() + " " + response2.getReasonPhrase());
                response2.close();
                System.out.println(data + " save end");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
