package cn.tf.html2pdf;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsoupDemo {

	public static void main(String[] args) throws IOException{
		Connection connect = Jsoup.connect("https://medium.com/@techworldwithmilan/all-estimations-are-wrong-but-none-are-useful-2550586d35e6"); //获取请求连接
		//使用Map集合存储头信息
		Map<String, String> header = new HashMap<String, String>();
		header.put("User-Agent", " Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.108 Safari/537.36");
		header.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		header.put("Accept-Language", "zh-cn,zh;q=0.5");
		header.put("Accept-Encoding", "gzip, deflate");
		header.put("Cache-Control", "max-age=0");
		header.put("Connection", "keep-alive");
		header.put("cookie", "lightstep_guid/medium-web=4d6afcd860009f2c; lightstep_session_id=6237b69e3e3b3c77; sz=1897; pr=1; tz=-480; _gid=GA1.2.213998039.1704643001; sid=1:nUToKqEOxfwJWkW+aXtLkHfJdGsb4yftulGqCTSNvaRrgnqBYIuex0PuFqO0Ngw5; xsrf=KLQiis5i8BTBSm1h; uid=8bf2a536c1a2; _ga=GA1.1.1378472687.1704464093; _ga_7JY7T788PK=GS1.1.1704642471.4.1.1704643072.0.0.0; _dd_s=rum=0&expire=1704644533293");
		
		//添加头信息
		Connection conheader = connect.headers(header);
		//使用get()请求页面内容
		Document document = conheader.proxy("127.0.0.1", 33210).get();
		//输出页面内容
		System.out.println(document);
	}
}
