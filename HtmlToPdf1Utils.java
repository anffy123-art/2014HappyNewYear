package cn.tf.html2pdf;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.font.FontProvider;


public class HtmlToPdf1Utils {
	 
	 public static void main(String[] args) throws Exception {
	        InputStream html = new FileInputStream("C:\\Users\\liangwei\\Desktop\\2024-1-Tech\\aaa.html");
	        
	        FileOutputStream fileOutputStream = new FileOutputStream("D:/a.pdf");
	        fileOutputStream.write(convert(html));
	        fileOutputStream.close();
	        
	        String waterMarkText =  "TFBank";
//	        HtmlToPDF.convertToPdf(html, waterMarkText, "C:\\Windows\\Fonts\\微软雅黑\\msyh.ttc,0", outputStream);

	    }
	 
	    public static byte[] convert(InputStream html) throws java.io.IOException {
	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        ConverterProperties props = new ConverterProperties();
	        FontProvider fp = new FontProvider(); // 提供解析用的字体
	        fp.addStandardPdfFonts(); // 添加标准字体库、无中文
	        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//	        fp.addDirectory(classLoader.getResource("fonts").getPath()); // 自定义字体路径、解决中文,可先用绝对路径测试。
	        props.setFontProvider(fp);
//	         props.setBaseUri(baseResource); // 设置html资源的相对路径
//	        HtmlConverter.convertToPdf(html, outputStream, props); // 无法灵活设置页边距等
	        HtmlConverter.convertToPdf(html, outputStream);
//	        HtmlConverter.convertToPdf(html, outputStream);
	        byte[] result = outputStream.toByteArray();
	        outputStream.close();
	        return result;
	        
	    }

	/**
     * html转pdf
     *
     * @param inputStream  输入流
     * @param waterMark    水印
     * @param fontPath     字体路径，ttc后缀的字体需要添加<b>,0<b/>
     * @param outputStream 输出流
     * @throws java.io.IOException 
     * @date : 2021/1/15 14:07
     */
    public static void convertToPdf(InputStream inputStream, String waterMark, String fontPath, OutputStream outputStream) throws java.io.IOException {
 
        PdfWriter pdfWriter = new PdfWriter(outputStream);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        //设置为A4大小
        pdfDocument.setDefaultPageSize(PageSize.A4);
//        //添加水印
//        pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE, new WaterMarkEventHandler(waterMark));
 
        //添加中文字体支持
        ConverterProperties properties = new ConverterProperties();
        FontProvider fontProvider = new FontProvider();
 
        //        设置字体
        /*PdfFont sysFont = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
        fontProvider.addFont(sysFont.getFontProgram(), "UniGB-UCS2-H");*/
 
        //添加自定义字体，例如微软雅黑
//        if (fontPath!="") {
//            PdfFont microsoft = PdfFontFactory.createFont(fontPath, PdfEncodings.IDENTITY_H, false);
//            fontProvider.addFont(microsoft.getFontProgram(), PdfEncodings.IDENTITY_H);
//        }
 
        properties.setFontProvider(fontProvider);
        //        读取Html文件流，查找出当中的&nbsp;或出现类似的符号空格字符
        inputStream = readInputStrem(inputStream);
        if (inputStream != null) {
            //        生成pdf文档
            HtmlConverter.convertToPdf(inputStream, pdfDocument, properties);
            pdfWriter.close();
            pdfDocument.close();
            return;
        } else {
            System.out.print("转换失败！");
        }
    }
 
    /**
     * 读取HTML 流文件，并查询当中的&nbsp;或类似符号直接替换为空格
     *
     * @param inputStream
     * @return
     */
    private static InputStream readInputStrem(InputStream inputStream) {
        // 定义一些特殊字符的正则表达式 如：
        String regEx_special = "\\&[a-zA-Z]{1,10};";
        try {
            //<1>创建字节数组输出流，用来输出读取到的内容
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //<2>创建缓存大小
            byte[] buffer = new byte[1024]; // 1KB
            //每次读取到内容的长度
            int len = -1;
            //<3>开始读取输入流中的内容
            while ((len = inputStream.read(buffer)) != -1) { //当等于-1说明没有数据可以读取了
                baos.write(buffer, 0, len);   //把读取到的内容写到输出流中
            }
            //<4> 把字节数组转换为字符串
            String content = baos.toString();
            //<5>关闭输入流和输出流
            //            inputStream.close();
            baos.close();
            //            log.info("读取的内容：{}", content);
            //            判断HTML内容是否具有HTML的特殊字符标记
//            Pattern compile = Pattern.compile(regEx_special, Pattern.CASE_INSENSITIVE);
//            Matcher matcher = compile.matcher(content);
//            String replaceAll = matcher.replaceAll("");
            //            log.info("替换后的内容：{}", replaceAll);
            //            将字符串转化为输入流返回
            InputStream stringStream = getStringStream(content);
            //<6>返回结果
            return stringStream;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("错误信息：{}" + e.getMessage());
            return null;
        }
    }
 
    /**
     * 将一个字符串转化为输入流
     * @param sInputString 字符串
     * @return
     */
    public static InputStream getStringStream(String sInputString) {
        if (sInputString != null && !sInputString.trim().equals("")) {
            try {
                ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(sInputString.getBytes());
                return tInputStringStream;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
 
}
