import java.io.*;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import sun.security.validator.Validator;
import sun.security.validator.ValidatorException;

import javax.net.ssl.SSLHandshakeException;
import javax.xml.bind.ValidationException;

public class GetContent {

	public String urlGoogle;
//	搜尋結果的那個網址
	public String searchKeyword;
//	INPUT的搜尋關鍵字（使用者輸入）
//	public Array urls;
	public String content;
	public String contentGoogle;
	public String content_ownText;

	ArrayList<ReturnCites> RC = new ArrayList<ReturnCites>();


	public GetContent(String searchKeyword) throws IOException {
		this.searchKeyword = searchKeyword;
		this.urlGoogle = "http://www.google.com.tw/search?q=" + searchKeyword +"&oe=utf8&num=20";
		GetGoogle();
	}
	
	private String fetchContentGoogle()throws IOException {
//		抓資料整個網站的下來，基本上沒有什麼要改的
		String retval = "";
		URL urlStr = new URL(this.urlGoogle);
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

	public ArrayList<ReturnCites> GetGoogle() throws IOException{

		if(this.contentGoogle == null) {
			this.contentGoogle = fetchContentGoogle();
		}

		org.jsoup.nodes.Document doc = Jsoup.parse(this.contentGoogle);
		Elements lis = doc.select("div.g");

		for(Element li : lis) {
			try {
				Element h3 = li.select("h3.r").get(0);
				String title = h3.text();

				Element cite = li.select("cite").get(0);
				String citeUrl = cite.text();

				RC.add(new ReturnCites(title,citeUrl,10));
			}catch(IndexOutOfBoundsException e) {
			}
		}
		return RC;
	}


	private String fetchContent(int i)throws FileNotFoundException, IOException, MalformedURLException {
		String retval = "";
		URL urlStr = new URL(RC.get(i).EachCite);
		try{
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
		}catch (FileNotFoundException e){}
		catch (SSLHandshakeException e){}
		catch (IOException e){}

		return retval;
	}
//
	public String query(int i) throws IOException{
		this.content = fetchContent(i);
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

	public void search() throws IOException{
		int index = 0;
		for (int i = 0; i < RC.size(); i++) {
			try{
				content_ownText = query(index);
			}catch (MalformedURLException e){
				System.out.print("No Protocol: ");
				System.out.println(index);
			}catch (NullPointerException e){}
			index ++;
//		===============關鍵字在這邊改================
			Keyword keyword1 = new Keyword("yes", 5);
			Keyword keyword2 = new Keyword("movie", 10);
			Keyword keyword3 = new Keyword("good", 20);
//		===========================================

			try{
				content_ownText = content_ownText.toUpperCase();


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

				String nowTitle = RC.get(i).EachTitle;
				String nowCite = RC.get(i).EachCite;

				RC.set(i, new ReturnCites(nowTitle, nowCite, count_sum));
			}catch (NullPointerException e){}


//			System.out.println(RC.get(i).toString());
		}


//		for (int i = 0; i < RC.size(); i++) {
//			System.out.println(i + RC.get(i).toString());
//		}

		System.out.println("cite sorting :");
		System.out.println(RC.size());
		Collections.sort(RC, ReturnCites.citeScoreComparator);

		for(ReturnCites sortedcite : RC) {
			System.out.println(sortedcite);
		}
	}
}