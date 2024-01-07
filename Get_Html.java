import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Get_Html {
    public static void main(String[] args) throws Exception    {
        long start= System.currentTimeMillis();
        String str_url="https://medium.com/?tag=software-engineering";
        Pattern p = Pattern.compile(">(13\\d{5}|15\\d{5}|18\\d{5}|147\\d{4})<");
     
        //String html = get_Html_2(str_url);
        //String html = get_Html_1(str_url);
        String html = get_Html_3(str_url);
        Matcher m = p.matcher(html);
         
        int num = 0;       
        while(m.find())
        {
            System.out.println("打印出的号码段落："+m.group(1)+"  编号"+(++num));    
        }
       System.out.println(num);       
       long end = System.currentTimeMillis();
        System.out.println("花费的时间"+(end-start)+"毫秒");
    } 
    public static String get_Html_2(String str_url) throws IOException{
        URL url = new URL(str_url);
        String content="";
        StringBuffer page = new StringBuffer();
        try {        
            BufferedReader in = new BufferedReader(new InputStreamReader(url
                    .openStream(), "utf-8"));            
            while((content = in.readLine()) != null){
                page.append(content);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return page.toString();
    }
     
    public static String get_Html_1(String str_url) throws IOException{
        URL url = new URL(str_url);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        InputStreamReader input = new InputStreamReader(conn.getInputStream(), "utf-8");  
        BufferedReader bufReader = new BufferedReader(input);  
        String line = "";  
        StringBuilder contentBuf = new StringBuilder();  
        while ((line = bufReader.readLine()) != null) {  
            contentBuf.append(line);  
        }
        return contentBuf.toString();
    }
     
    /**
     * 通过网站域名URL获取该网站的源码
     * @param url
     * @return String
     * @throws Exception
     */
    public static String get_Html_3(String str_url) throws Exception    {
        URL url = new URL(str_url);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5 * 1000);                        //设置连接超时
        java.io.InputStream inStream = conn.getInputStream();  //通过输入流获取html二进制数据
               
        byte[] data = readInputStream(inStream);  //把二进制数据转化为byte字节数据
        String htmlSource = new String(data);
        return htmlSource;
    }
     
    /**
     * 把二进制流转化为byte字节数组
     * @param inStream
     * @return byte[]
     * @throws Exception
     */
    public static byte[] readInputStream(java.io.InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[]  buffer = new byte[1204];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1){
            outStream.write(buffer,0,len);
        }
        inStream.close();
        return outStream.toByteArray();         
    } 
}