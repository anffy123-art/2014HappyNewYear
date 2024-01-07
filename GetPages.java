import java.io.ByteArrayOutputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

public class GetPages {
    public static void main(String[] args) {
        try {
            // URL url = new URL("https://medium.com/?tag=software-engineering");
            URL url = new URL("https://medium.com/@tsecretdeveloper/the-problem-with-non-coding-managers-540752885668");
            // 通过代理服务器进行跳转
            InetSocketAddress addr = new InetSocketAddress("127.0.0.1", 33210);
            Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http 代理

            URLConnection conn = url.openConnection(proxy);
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            conn.setRequestProperty("Cookie",
                    "_gid=GA1.2.2067102980.1704466926; lightstep_guid/medium-web=1261c2e83584857; lightstep_session_id=5dfb55be6a02f968; sz=1903; pr=1; tz=-480; uid=8bf2a536c1a2; sid=1:974g5h3+nc4XeAwyxQlul4s4ZfNV38cQ9jdSDZZ3CSlPrEZ746VkIM6mqKAq4+kW; xsrf=fn5aHCQdhh6LbSjE; _ga=GA1.1.855557898.1704466781; _dd_s=rum=0&expire=1704506029645; _ga_7JY7T788PK=GS1.1.1704502351.3.1.1704505129.0.0.0");

            java.io.InputStream inStream = conn.getInputStream(); // 通过输入流获取html二进制数据

            byte[] data = readInputStream(inStream); // 把二进制数据转化为byte字节数据
            String htmlSource = new String(data);
            System.out.println(htmlSource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 把二进制流转化为byte字节数组
     * 
     * @param inStream
     * @return byte[]
     * @throws Exception
     */
    public static byte[] readInputStream(java.io.InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1204];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }
}