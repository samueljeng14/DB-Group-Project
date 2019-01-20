
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import test.ReturnCites;

public class WebPage {

	public String URL;
	public String name;
	public GetContent ctn;
	public double score;
	public static String content1;
	public static String content2;
	public static String content3;
	public static String content4;
	public static String content5;
	public static String content6;
	public static String content7;
	public static String content8;
	public static String content9;
	public static String content10;
//	public String contentGoogle;
//	ArrayList<ReturnCites2> RC2 = new ArrayList<ReturnCites2>();
	
	public WebPage(String name, String URL) throws IOException {
		this.URL = URL;
		this.name = name;
		this.ctn = new GetContent(URL);
		
	}
	public ArrayList<String> getChild(){
		try {
			String content = getContent(this.URL);
			Document elements = Jsoup.parse(content);
			Elements eles = elements.select("a");
			ArrayList<String> list = new ArrayList<>();
			for(Element li : eles) {
				String ss = li.attr("href");
				if(ss.contains("http"))
				list.add(ss);
		
			}
			return list;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
		
	}
	public String getContent(String url) throws IOException {
		String retval = "";
		URL urlStr = new URL(url);
		URLConnection connection = urlStr.openConnection();
		connection.setRequestProperty("User-Agent", "Mozilla/5.0(Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2)Gecko/20100316 Firefox/3.6.2");
		connection.connect();
		InputStream inputStream = connection.getInputStream();
		InputStreamReader inReader = new InputStreamReader(inputStream,"UTF8");
		BufferedReader bf = new BufferedReader(inReader);

		String line = null;
		while((line = bf.readLine())!=null) {
			retval += line;
		}
		return retval;
	}
	
	ArrayList<String> child_content;
	
	public void setScore(ArrayList<Keyword> keywords) throws IOException {
		score = 0;
		ArrayList<String> childs = getChild(); // use them
		
		System.out.println("childs : " + childs);
//		
//		for(int i = 0; i <= childs.size(); i++) {
//			child_content.add(getContent(childs.get(i)));
		}
//		content1 = getContent(childs.get(0));
//		content2 = getContent(childs.get(1));	
//		content3 = getContent(childs.get(2));
//		content4 = getContent(childs.get(3));
//		content5 = getContent(childs.get(4));
//		content6 = getContent(childs.get(5));
//		content7 = getContent(childs.get(6));
//		content8 = getContent(childs.get(7));
//		content9 = getContent(childs.get(8));
//		content10 = getContent(childs.get(9));
		
//		child_content.add(content1);
//		child_content.add(content2);
//		
//		for(Keyword k : keywords) {
//			score += ctn.search(k.name) * k.weight;
//			
//		}
		
	
	@Override
	public String toString() {
		return "["+ name + "," + URL +"]";
	}

	
}
