package indi.tammy.qb.controller;

import indi.tammy.qb.model.Question;
import indi.tammy.qb.model.enums.Subject;
import indi.tammy.qb.service.EnumService;
import indi.tammy.qb.service.QuestionService;
import indi.tammy.qb.wrapper.QuestionWrapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ch.qos.logback.core.boolex.Matcher;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import com.sun.star.io.ConnectException;

@Controller
public class FileUploadController {
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private EnumService enumService;
	
	@RequestMapping(value={"/upload/file"})
	@ResponseBody
	public String ueditorImageUp(@RequestParam("file") MultipartFile upfile){
		String fname = null;
		if(upfile != null){            
			Resource r = new ClassPathResource("ApplicationContextBase.xml");
			String rootPath = null;
			try {
				rootPath = r.getFile().getParent();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			FileOutputStream out = null; 
			rootPath = rootPath + "/static/upload";
	        // 存放路径，如果不存在则创建路径  
	        File file = new File(rootPath); 
	        //System.out.println(rootPath);
	        if(!file.exists()) file.mkdirs();  
	        fname = upfile.getOriginalFilename();  
	        try {
					out = new FileOutputStream(rootPath + "/" + fname);
					out.write(upfile.getBytes());
					out.close();
					//convert(new File(rootPath + "/" + fname),rootPath);
					String str=toHtmlString(new File(rootPath + "/" + fname),rootPath);
					System.out.println(str);
					OutputStream fileWriter = new FileOutputStream(rootPath + "/" + new Date().getTime()
						        + ".html");  
			        fileWriter.write(str.getBytes("utf-8"));  
			        fileWriter.close(); // 关闭数据流  
			        List<Question> l = htmlDivider(str);
			        for(int i = 0;i < l.size();i ++){
			        	Question q = l.get(i);
			        	q.setSubject_id(-1);
			        	int type = questionService.getTypeId(q.getType_name());
			        	if(type > 0){
			        		q.setType(type);
			        		String[] knows = q.getKnow_name().split(";");
			        		int[] validKnowId = new int[knows.length];
			        		int validCount = 0;
			        		for(int j = 0;j < knows.length;j ++){
			        			int knowId = Integer.parseInt(knows[j]);
			        			if(knowId > 0){
			        				Subject s = enumService.findSubjectByKnowId(knowId);
			        				if(s == null){
			        					continue;
			        				}
			        				q.setSubject_id(s.getId());
			        				validKnowId[validCount] = knowId;
			        				validCount ++;
			        			}
			        		}
			        		if(q.getSubject_id() > 0){
			        			q.setSubmit_time(System.currentTimeMillis());
			        			questionService.insert(q);
			        			for(int k = 0;k < validCount;k ++){
			        				questionService.insertKnowQuestion(q.getId(), validKnowId[k]);
			        			}
			        		}
			        	}
			        }
					return "{\"name\":\""+fname+"\", \"originalName\": \""+fname+"\", \"size\": 8192, \"state\": \"SUCCESS\", \"type\": \".jpg\", \"url\": \"/upload/"+fname+"\"}";
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
		}
		return "FAIL";
	}
	
	@RequestMapping(value={"/upload/file2"})
	public String ueditorImageUp2(){
		return "/test/testFileUploader";
	}
	
	@RequestMapping(value={"/upload/webUploader"})
	public String getWebUplader(){
		return "/test/webUploader";
	}
	
	
	/**
     * 将word文档转换成html文档
     * 
     * @param docFile
     *                需要转换的word文档
     * @param filepath
     *                转换之后html的存放路径
     * @return 转换之后的html文件
     */
    public static File convert(File docFile, String filepath) {
    // 创建保存html的文件
	    File htmlFile = new File(filepath + "/" + new Date().getTime()
	        + ".html");
	    // 创建Openoffice连接
	    Process pro = null;  
	    String OpenOffice_HOME = "D:/openoffice/";  
	    String command = OpenOffice_HOME  
                + "program/soffice.exe -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\" -nofirststartwizard";  
        try {
			pro = Runtime.getRuntime().exec(command);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  
	    OpenOfficeConnection con = new SocketOpenOfficeConnection("127.0.0.1", 8100);  
	    // 连接
		try {
			System.out.println("connect");
			con.connect();
			 // 创建转换器
		    DocumentConverter converter = new OpenOfficeDocumentConverter(con);
		    // 转换文档问html
		    
		    converter.convert(docFile, htmlFile);	    
		    // 关闭openoffice连接
		    con.disconnect();
		} catch (java.net.ConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	    return htmlFile;
    }
    
    /**
     * 将word转换成html文件，并且获取html文件代码。
     * 
     * @param docFile
     *                需要转换的文档
     * @param filepath
     *                文档中图片的保存位置
     * @return 转换成功的html代码
     */
    public static String toHtmlString(File docFile, String filepath) {
    // 转换word文档
    File htmlFile=null;
	htmlFile = convert(docFile, filepath);
    // 获取html文件流
    StringBuffer htmlSb = new StringBuffer();
    try {
        BufferedReader br = new BufferedReader(new InputStreamReader(
            new FileInputStream(htmlFile)));
        while (br.ready()) {
        htmlSb.append(br.readLine());
        }
        br.close();
        // 删除临时文件
        htmlFile.delete();
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
    // HTML文件字符串
    String htmlStr = htmlSb.toString();
    // 返回经过清洁的html文本
    return clearFormat(htmlStr, filepath);
    }

    /**
     * 清除一些不需要的html标记
     * 
     * @param htmlStr
     *                带有复杂html标记的html语句
     * @return 去除了不需要html标记的语句
     */
    protected static String clearFormat(String htmlStr, String docImgPath) {
    	// 获取body内容的正则
	    String bodyReg = "<BODY .*</BODY>";
	    Pattern bodyPattern = Pattern.compile(bodyReg);
	    java.util.regex.Matcher bodyMatcher = bodyPattern.matcher(htmlStr);
	    if (bodyMatcher.find()) {
	        // 获取BODY内容，并转化BODY标签为DIV
	        htmlStr = bodyMatcher.group().replaceFirst("<BODY", "<DIV")
	            .replaceAll("</BODY>", "</DIV>");
	    }
	    // 调整图片地址
	    htmlStr = htmlStr.replaceAll("<IMG SRC=\"", "<IMG SRC=\"" + "/upload"
	        + "/");
	    // 把<P></P>转换成</div></div>保留样式
	    htmlStr =  htmlStr.replaceAll("(<P)([^>]*>.*?)(<\\/P>)",
	    "<div$2</div>");
	    // 把<P></P>转换成</div></div>并删除样式
	    //htmlStr = htmlStr.replaceAll("(<P)([^>]*)(>.*?)(<\\/P>)", "<p$3</p>");
	    // 删除不需要的标签
	    htmlStr = htmlStr
	        .replaceAll(
	            "<[/]?(font|FONT|span|SPAN|xml|XML|del|DEL|ins|INS|meta|META|[ovwxpOVWXP]:\\w+)[^>]*?>",
	            "");
	    // 删除不需要的属性
	    htmlStr = htmlStr
	        .replaceAll(
	            "<([^>]*)(?:lang|LANG|class|CLASS|style|STYLE|size|SIZE|face|FACE|[ovwxpOVWXP]:\\w+)=(?:'[^']*'|\"\"[^\"\"]*\"\"|[^>]+)([^>]*)>",
	            "<$1$2>");
	    return "<!DOCTYPE html>"
	    +"<html>"
	    +"<head>"
	      +"<meta charset=\"UTF-8\"></meta>"+"<body>"+htmlStr+"</body></html>";
    }
    
    
    /**
     * 将html中的题目分割出来
     * 
     * @param htmlStr 带有简单html标记的html语句
     *                
     * @return 分割后的类数组
     */
    public List<Question> htmlDivider(String htmlStr){
    	String typeName = "【题型】";
    	String contentName = "【题面】";
    	String answerName = "【答案】";
    	String analysisName = "【解析】";
    	String knowName = "【知识点】";
    	String endName = "【结束】";
    	List<Question> l = new ArrayList<Question>();
    	Document doc = Jsoup.parse(htmlStr);
		Elements divs = doc.body().child(0).children();
		for(int i = 0;i < divs.size();i ++){
			Element div = divs.get(i);
			System.out.println(div.html());
			if(div.text().startsWith(typeName)){
				Question q = new Question();
				q.setType_name(div.html().substring(typeName.length()));
				String nowType = typeName;
				for(i ++;i < divs.size();i ++){
					Element innerDiv = divs.get(i);
					if(innerDiv.html().startsWith(endName)){	
						l.add(q);
						break;
					}
					if(innerDiv.html().startsWith(contentName)){
						nowType = contentName;
						q.setContent(innerDiv.html().substring(contentName.length()));
					}
					else if(innerDiv.html().startsWith(answerName)){
						nowType = answerName;
						q.setAnswer(innerDiv.html().substring(answerName.length()));
					}
					else if(innerDiv.html().startsWith(analysisName)){
						nowType = analysisName;
						q.setAnalysis(innerDiv.html().substring(analysisName.length()));
					}
					else if(innerDiv.html().startsWith(knowName)){
						nowType = knowName;
						q.setKnow_name(innerDiv.html().substring(knowName.length()));
					}
					else{
						if(nowType.equals(contentName)){
							q.setContent(q.getContent() + "<br/>" + innerDiv.html());
						}
						else if(nowType.equals(answerName)){
							q.setAnswer(q.getAnswer() + "<br/>" + innerDiv.html());
						}
						else if(nowType.equals(analysisName)){
							q.setAnalysis(q.getAnalysis() + "<br/>" + innerDiv.html());
						}
						else if(nowType.equals(knowName)){
							q.setKnow_name(q.getKnow_name() + "<br/>" + innerDiv.html());
						}
					}
				}
			}
		}
    	return l;
    }
    
    /**
     * 分割出题面里的选项和小题
     * 
     * @param contentStr 题面内容
     *                
     * @return 分割后的类数组
     */
    public String contentAnalyzer(String contentStr){
     	String numSeparatorName = "【[0-9]+】";
     	String charSeparatorName = "【[A-Z]】";
     	String[] subStrs = contentStr.split(numSeparatorName);
     	if(subStrs.length == 1){
     		String[] optionStr = contentStr.split(charSeparatorName);
     		if(optionStr.length == 1){
     			return contentStr;
     		}
     		String outPutStr = "ContentBody=" + optionStr[0];
     		for(int i = 1;i < optionStr.length;i ++){
     			outPutStr += "Option " + i +"=" + optionStr[i];
     		}
     		return outPutStr;
     	}
     	
     	String outPutStr = "ContentBody=" + subStrs[0];
     	for(int i = 1;i < subStrs.length;i ++){
     		outPutStr += "SubQ " + i + "=" +contentAnalyzer(subStrs[i]);
     	}
    	return outPutStr;
    }
    
    @RequestMapping(value={"/upload/jacob"})
    @ResponseBody
    public String testJacob(){
    	String htmlFile = "C:/Users/Tammy/Desktop/1438194161628.html";
    	String docFile = "C:/Users/Tammy/Desktop/testJacob.doc";
    	ActiveXComponent word = new ActiveXComponent("Word.Application");
    	word.setProperty("Visible", new Variant(false));
    	Dispatch docs = word.getProperty("Documents").toDispatch();
    	Dispatch doc = Dispatch.invoke(docs, "Open", Dispatch.Method, new Object[] { htmlFile, new Variant(false), new Variant(true) }, new int[1]).toDispatch();
    	Dispatch.invoke(doc, "SaveAs", Dispatch.Method, new Object[] { docFile, new Variant(1) }, new int[1]);  
    	Variant f = new Variant(false);    
        Dispatch.call(doc, "Close", f);
        word.invoke("Quit", new Variant[0]);   
        //关闭com的线程   
        ComThread.Release();   
    	return "lalala";
    }
}
