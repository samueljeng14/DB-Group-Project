import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

public class GetContent {
	
	public String url;
	public String content;
	public String content_ownText;
	
	public GetContent(String url) {
		this.url = url;
	}
	
	private String fetchContent()throws IOException {
		String retval = "";
		URL urlStr = new URL(this.url);
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

	public String query() throws IOException{
		if(this.content == null) {
			this.content = fetchContent();
		}
		String retval = "";
		org.jsoup.nodes.Document document = Jsoup.parse(this.content);
		Elements lis = document.body().select("*");	
		StringBuilder str = new StringBuilder();
		
		for(Element li : lis){
			try {
				String title = li.ownText();
				str.append(title);
			}catch(IndexOutOfBoundsException e) {
			}
		}		
		retval = str.toString();
		return retval;
	}
	
	public double search() throws IOException{
		if(this.content_ownText == null) {
			this.content_ownText = query();
		}
//		===============關鍵字在這邊改================
		Keyword keyword1 = new Keyword("Git", 5);
		Keyword keyword2 = new Keyword("入門", 10);
		Keyword keyword3 = new Keyword("的", 20);
//		===========================================
		
		content_ownText = content_ownText.toUpperCase();
		System.out.println(content_ownText);
		
		String keyword1_name = keyword1.name.toUpperCase();
		String keyword2_name = keyword2.name.toUpperCase();
		String keyword3_name = keyword3.name.toUpperCase();

//		分開權重
		double count_1 = 0;
		double count_2 = 0;
		double count_3 = 0;
		double count_sum = 0;
		
//		分開內文（不然跑過一次之後就到底了很麻煩）
		String content_ownText_1 = content_ownText;
		String content_ownText_2 = content_ownText;		
		String content_ownText_3 = content_ownText;
		
		
		int i1 = content_ownText.indexOf(keyword1_name);
		while(i1 != -1) {//不等於-1，意思是content中有keyword，才可以跑迴圈
			count_1 ++;
			i1 = content_ownText_1.indexOf(keyword1_name);
			content_ownText_1 = content_ownText_1.substring(i1 + keyword1_name.length(), content_ownText_1.length());
		}
		count_sum = count_sum + count_1 * keyword1.weight;
		
		int i2 = content_ownText_2.indexOf(keyword2_name);
		while(i2 != -1) {//不等於-1，意思是content中有keyword，才可以跑迴圈
			count_2++;
			i2 = content_ownText_2.indexOf(keyword2_name);
			content_ownText_2 = content_ownText_2.substring(i2 + keyword2_name.length(), content_ownText_2.length());
		}
		count_sum = count_sum + count_2 * keyword2.weight;
		
		int i3 = content_ownText_3.indexOf(keyword3_name);
		while(i3 != -1) {//不等於-1，意思是content中有keyword，才可以跑迴圈
			count_3++;
			i3 = content_ownText_3.indexOf(keyword3_name);
			content_ownText_3 = content_ownText_3.substring(i3 + keyword3_name.length(), content_ownText_3.length());
		}
		count_sum = count_sum + count_3 * keyword3.weight;
		
		System.out.println(count_sum);
		return count_sum;
	}
}