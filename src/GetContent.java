import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import test.ReturnCites;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import javax.net.ssl.SSLHandshakeException;


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
		  if(searchKeyword.contains(" ")) {
			  this.searchKeyword = searchKeyword.replace(" ", "+").concat("線上看");
		  }else {
		  this.searchKeyword = searchKeyword.concat("線上看");
		  }
		  this.urlGoogle = "http://www.google.com.tw/search?q=" + this.searchKeyword +"&oe=utf8&num=20";
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

				Element cite = li.getElementsByTag("a").first();
				String citeUrl = "https://www.google.com.tw"+ cite.attr("href");

				RC.add(new ReturnCites(title,citeUrl,10));
			}catch(IndexOutOfBoundsException e) {
				e.printStackTrace();
			}
		}
		return RC;
	}


	private String fetchContent(int i)throws FileNotFoundException, IOException, MalformedURLException {
		String retval = " ";
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

	//抓內容下來
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

	public ArrayList<ReturnCites> search() throws IOException{
		int index = 0;
		for (int i = 0; i < RC.size(); i++) {
			try{
				content_ownText = query(index);
			}catch (MalformedURLException e){
//				
				e.printStackTrace();
			}catch (NullPointerException e){e.printStackTrace();}
			index ++;
			
//		===============關鍵字在這邊改================
			Keyword keyword1 = new Keyword("HD", 5);
			Keyword keyword2 = new Keyword("FREE", 8);
			Keyword keyword3 = new Keyword("STREAM", 13);
			Keyword keyword4 = new Keyword("線上看", 45);
			Keyword keyword5 = new Keyword("完整版", 26);
			Keyword keyword6 = new Keyword("高清", 5);
			Keyword keyword7 = new Keyword("影片", 10);
			Keyword keyword8 = new Keyword("免費", 8);
			Keyword keyword9 = new Keyword("DramasQ", 10);
			Keyword keyword10 = new Keyword("Gimy", 10);
			Keyword keyword11 = new Keyword("劇迷", 10);
			Keyword keyword12 = new Keyword("楓林網", 10);
			Keyword keyword13 = new Keyword("酷播", 10);
			Keyword keyword14 = new Keyword("伊莉", 10);
			Keyword keyword15 = new Keyword("小鴨", 10);
			Keyword keyword16 = new Keyword("VM美劇", 10);
			Keyword keyword17= new Keyword("bilibili", 10);
			Keyword keyword18 = new Keyword("Xmovies8", 10);
			Keyword keyword19 = new Keyword("MoMoVOD", 10);
			Keyword keyword20 = new Keyword("online", 30);
			Keyword keyword21 = new Keyword("新聞", -100);
			Keyword keyword22 = new Keyword("心得", -100);
			Keyword keyword23 = new Keyword("影評", -100);
			Keyword keyword24 = new Keyword("維基百科", -100);
			Keyword keyword25 = new Keyword("星光雲", -100);
			Keyword keyword26 = new Keyword("ETtoday", -100);
			Keyword keyword27 = new Keyword("分集", -100);
			Keyword keyword28 = new Keyword("劇情", -100);
			Keyword keyword29 = new Keyword("痞客邦", -100);
			Keyword keyword30 = new Keyword("部落格", -100);
			Keyword keyword31 = new Keyword("pixnet", -100);
			Keyword keyword32 = new Keyword("文章", -100);

//		===========================================

			try{
				content_ownText = content_ownText.toUpperCase();


				String keyword1_name = keyword1.name.toUpperCase();
				String keyword2_name = keyword2.name.toUpperCase();
				String keyword3_name = keyword3.name.toUpperCase();
				String keyword4_name = keyword4.name.toUpperCase();
				String keyword5_name = keyword5.name.toUpperCase();
				String keyword6_name = keyword6.name.toUpperCase();
				String keyword7_name = keyword7.name.toUpperCase();
				String keyword8_name = keyword8.name.toUpperCase();
				String keyword9_name = keyword9.name.toUpperCase();
				String keyword10_name = keyword10.name.toUpperCase();
				String keyword11_name = keyword11.name.toUpperCase();
				String keyword12_name = keyword12.name.toUpperCase();
				String keyword13_name = keyword13.name.toUpperCase();
				String keyword14_name = keyword14.name.toUpperCase();
				String keyword15_name = keyword15.name.toUpperCase();
				String keyword16_name = keyword16.name.toUpperCase();
				String keyword17_name = keyword17.name.toUpperCase();
				String keyword18_name = keyword18.name.toUpperCase();
				String keyword19_name = keyword19.name.toUpperCase();
				String keyword20_name = keyword20.name.toUpperCase();
				String keyword21_name = keyword21.name.toUpperCase();
				String keyword22_name = keyword22.name.toUpperCase();
				String keyword23_name = keyword23.name.toUpperCase();
				String keyword24_name = keyword24.name.toUpperCase();
				String keyword25_name = keyword25.name.toUpperCase();
				String keyword26_name = keyword26.name.toUpperCase();
				String keyword27_name = keyword27.name.toUpperCase();
				String keyword28_name = keyword28.name.toUpperCase();
				String keyword29_name = keyword29.name.toUpperCase();
				String keyword30_name = keyword30.name.toUpperCase();
				String keyword31_name = keyword31.name.toUpperCase();
				String keyword32_name = keyword32.name.toUpperCase();


//		分開權重
				double count_1 = 0;
				double count_2 = 0;
				double count_3 = 0;
				double count_4 = 0;
				double count_5 = 0;
				double count_6 = 0;
				double count_7 = 0;
				double count_8 = 0;
				double count_9 = 0;
				double count_10 = 0;
				double count_11 = 0;
				double count_12 = 0;
				double count_13 = 0;
				double count_14 = 0;
				double count_15 = 0;
				double count_16 = 0;
				double count_17 = 0;
				double count_18 = 0;
				double count_19 = 0;
				double count_20 = 0;
				double count_21 = 0;
				double count_22 = 0;
				double count_23 = 0;
				double count_24 = 0;
				double count_25 = 0;
				double count_26 = 0;
				double count_27 = 0;
				double count_28 = 0;
				double count_29 = 0;
				double count_30 = 0;
				double count_31 = 0;
				double count_32 = 0;
				double count_sum = 0;

//		分開內文（不然跑過一次之後就到底了很麻煩）
				String content_ownText_1 = content_ownText;
				String content_ownText_2 = content_ownText;
				String content_ownText_3 = content_ownText;
				String content_ownText_4 = content_ownText;
				String content_ownText_5 = content_ownText;
				String content_ownText_6 = content_ownText;
				String content_ownText_7 = content_ownText;
				String content_ownText_8 = content_ownText;
				String content_ownText_9 = content_ownText;
				String content_ownText_10 = content_ownText;
				String content_ownText_11 = content_ownText;
				String content_ownText_12 = content_ownText;
				String content_ownText_13 = content_ownText;
				String content_ownText_14 = content_ownText;
				String content_ownText_15 = content_ownText;
				String content_ownText_16 = content_ownText;
				String content_ownText_17 = content_ownText;
				String content_ownText_18 = content_ownText;
				String content_ownText_19 = content_ownText;
				String content_ownText_20 = content_ownText;
				String content_ownText_21 = content_ownText;
				String content_ownText_22 = content_ownText;
				String content_ownText_23 = content_ownText;
				String content_ownText_24 = content_ownText;
				String content_ownText_25 = content_ownText;
				String content_ownText_26 = content_ownText;
				String content_ownText_27 = content_ownText;
				String content_ownText_28 = content_ownText;
				String content_ownText_29 = content_ownText;
				String content_ownText_30 = content_ownText;
				String content_ownText_31 = content_ownText;
				String content_ownText_32 = content_ownText;
				
				int i1 = content_ownText_1.indexOf(keyword1_name);
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

				int i4 = content_ownText_4.indexOf(keyword4_name);
				while(i4 != -1) {//不等於-1，意思是content中有keyword，才可以跑迴圈
					count_4++;
					i4 = content_ownText_4.indexOf(keyword4_name);
					content_ownText_4 = content_ownText_4.substring(i4 + keyword4_name.length(), content_ownText_4.length());
				}
				count_sum = count_sum + count_4 * keyword4.weight;
				
				int i5 = content_ownText_5.indexOf(keyword5_name);
				while(i5 != -1) {//不等於-1，意思是content中有keyword，才可以跑迴圈
					count_5++;
					i5 = content_ownText_5.indexOf(keyword5_name);
					content_ownText_5 = content_ownText_5.substring(i5 + keyword5_name.length(), content_ownText_5.length());
				}
				count_sum = count_sum + count_5 * keyword5.weight;
				
				int i6 = content_ownText_6.indexOf(keyword6_name);
				while(i6 != -1) {//不等於-1，意思是content中有keyword，才可以跑迴圈
					count_6++;
					i6 = content_ownText_6.indexOf(keyword6_name);
					content_ownText_6 = content_ownText_6.substring(i6 + keyword6_name.length(), content_ownText_6.length());
				}
				count_sum = count_sum + count_6 * keyword6.weight;
				
				int i7 = content_ownText_7.indexOf(keyword7_name);
				while(i7 != -1) {//不等於-1，意思是content中有keyword，才可以跑迴圈
					count_7++;
					i7 = content_ownText_7.indexOf(keyword7_name);
					content_ownText_7 = content_ownText_7.substring(i7 + keyword7_name.length(), content_ownText_7.length());
				}
				count_sum = count_sum + count_7 * keyword7.weight;
				
				int i8 = content_ownText_8.indexOf(keyword8_name);
				while(i8 != -1) {//不等於-1，意思是content中有keyword，才可以跑迴圈
					count_8++;
					i8 = content_ownText_8.indexOf(keyword8_name);
					content_ownText_8 = content_ownText_8.substring(i8 + keyword8_name.length(), content_ownText_8.length());
				}
				count_sum = count_sum + count_8 * keyword8.weight;
				
				int i9 = content_ownText_9.indexOf(keyword9_name);
				while(i9 != -1) {//不等於-1，意思是content中有keyword，才可以跑迴圈
					count_9++;
					i9 = content_ownText_9.indexOf(keyword9_name);
					content_ownText_9 = content_ownText_9.substring(i9 + keyword9_name.length(), content_ownText_9.length());
				}
				count_sum = count_sum + count_9 * keyword9.weight;
				
				int i10 = content_ownText_10.indexOf(keyword10_name);
				while(i10 != -1) {//不等於-1，意思是content中有keyword，才可以跑迴圈
					count_10++;
					i10 = content_ownText_10.indexOf(keyword10_name);
					content_ownText_10 = content_ownText_10.substring(i10 + keyword10_name.length(), content_ownText_10.length());
				}
				count_sum = count_sum + count_10 * keyword10.weight;
				
				int i11 = content_ownText_11.indexOf(keyword11_name);
				while(i11 != -1) {//不等於-1，意思是content中有keyword，才可以跑迴圈
					count_11++;
					i11 = content_ownText_11.indexOf(keyword11_name);
					content_ownText_11 = content_ownText_11.substring(i11 + keyword11_name.length(), content_ownText_11.length());
				}
				count_sum = count_sum + count_11 * keyword11.weight;
				
				int i12 = content_ownText_12.indexOf(keyword12_name);
				while(i12 != -1) {//不等於-1，意思是content中有keyword，才可以跑迴圈
					count_12++;
					i12 = content_ownText_12.indexOf(keyword12_name);
					content_ownText_12 = content_ownText_12.substring(i12 + keyword12_name.length(), content_ownText_12.length());
				}
				count_sum = count_sum + count_12 * keyword12.weight;
				
				int i13 = content_ownText_13.indexOf(keyword13_name);
				while(i13 != -1) {//不等於-1，意思是content中有keyword，才可以跑迴圈
					count_13++;
					i13 = content_ownText_13.indexOf(keyword13_name);
					content_ownText_13 = content_ownText_13.substring(i13 + keyword13_name.length(), content_ownText_13.length());
				}
				count_sum = count_sum + count_13 * keyword13.weight;
				
				int i14 = content_ownText_14.indexOf(keyword14_name);
				while(i14 != -1) {//不等於-1，意思是content中有keyword，才可以跑迴圈
					count_14++;
					i14 = content_ownText_14.indexOf(keyword14_name);
					content_ownText_14 = content_ownText_14.substring(i14 + keyword14_name.length(), content_ownText_14.length());
				}
				count_sum = count_sum + count_14 * keyword14.weight;
				
				int i15 = content_ownText_15.indexOf(keyword15_name);
				while(i15 != -1) {//不等於-1，意思是content中有keyword，才可以跑迴圈
					count_15++;
					i15 = content_ownText_15.indexOf(keyword15_name);
					content_ownText_15 = content_ownText_15.substring(i15 + keyword15_name.length(), content_ownText_15.length());
				}
				count_sum = count_sum + count_15 * keyword15.weight;
				
				int i16 = content_ownText_16.indexOf(keyword16_name);
				while(i16 != -1) {//不等於-1，意思是content中有keyword，才可以跑迴圈
					count_16++;
					i16 = content_ownText_16.indexOf(keyword16_name);
					content_ownText_16 = content_ownText_16.substring(i16 + keyword16_name.length(), content_ownText_16.length());
				}
				count_sum = count_sum + count_16 * keyword16.weight;
				
				int i17 = content_ownText_17.indexOf(keyword17_name);
				while(i17 != -1) {//不等於-1，意思是content中有keyword，才可以跑迴圈
					count_17++;
					i17 = content_ownText_17.indexOf(keyword17_name);
					content_ownText_3 = content_ownText_17.substring(i17 + keyword17_name.length(), content_ownText_17.length());
				}
				count_sum = count_sum + count_17 * keyword17.weight;
				
				int i18 = content_ownText_18.indexOf(keyword18_name);
				while(i18 != -1) {//不等於-1，意思是content中有keyword，才可以跑迴圈
					count_18++;
					i18 = content_ownText_18.indexOf(keyword18_name);
					content_ownText_18 = content_ownText_18.substring(i18 + keyword18_name.length(), content_ownText_18.length());
				}
				count_sum = count_sum + count_18 * keyword18.weight;
				
				int i19 = content_ownText_19.indexOf(keyword19_name);
				while(i19 != -1) {//不等於-1，意思是content中有keyword，才可以跑迴圈
					count_19++;
					i19 = content_ownText_19.indexOf(keyword19_name);
					content_ownText_19 = content_ownText_19.substring(i19 + keyword19_name.length(), content_ownText_19.length());
				}
				count_sum = count_sum + count_19 * keyword19.weight;
				
				int i20 = content_ownText_20.indexOf(keyword20_name);
				while(i20 != -1) {//不等於-1，意思是content中有keyword，才可以跑迴圈
					count_20++;
					i20 = content_ownText_20.indexOf(keyword20_name);
					content_ownText_20 = content_ownText_20.substring(i20 + keyword20_name.length(), content_ownText_20.length());
				}
				count_sum = count_sum + count_20 * keyword20.weight;	

				int i21 = content_ownText_21.indexOf(keyword21_name);
				while(i21 != -1) {//不等於-1，意思是content中有keyword，才可以跑迴圈
					count_21++;
					i21 = content_ownText_21.indexOf(keyword21_name);
					content_ownText_21 = content_ownText_21.substring(i21 + keyword21_name.length(), content_ownText_21.length());
				}
				count_sum = count_sum + count_21 * keyword21.weight;
				
				int i22 = content_ownText_22.indexOf(keyword22_name);
				while(i22 != -1) {//不等於-1，意思是content中有keyword，才可以跑迴圈
					count_22++;
					i22 = content_ownText_22.indexOf(keyword22_name);
					content_ownText_22 = content_ownText_22.substring(i22 + keyword22_name.length(), content_ownText_22.length());
				}
				count_sum = count_sum + count_22 * keyword22.weight;
				
				int i23 = content_ownText_23.indexOf(keyword23_name);
				while(i23 != -1) {//不等於-1，意思是content中有keyword，才可以跑迴圈
					count_23++;
					i23 = content_ownText_23.indexOf(keyword23_name);
					content_ownText_23 = content_ownText_23.substring(i23 + keyword23_name.length(), content_ownText_23.length());
				}
				count_sum = count_sum + count_23 * keyword23.weight;
				
				int i24 = content_ownText_24.indexOf(keyword24_name);
				while(i24 != -1) {//不等於-1，意思是content中有keyword，才可以跑迴圈
					count_24++;
					i24 = content_ownText_24.indexOf(keyword24_name);
					content_ownText_24 = content_ownText_24.substring(i24 + keyword24_name.length(), content_ownText_24.length());
				}
				count_sum = count_sum + count_24 * keyword24.weight;
				
				int i25 = content_ownText_25.indexOf(keyword25_name);
				while(i25 != -1) {//不等於-1，意思是content中有keyword，才可以跑迴圈
					count_25++;
					i25 = content_ownText_25.indexOf(keyword25_name);
					content_ownText_25 = content_ownText_25.substring(i25 + keyword25_name.length(), content_ownText_25.length());
				}
				count_sum = count_sum + count_25 * keyword25.weight;
				
				int i26 = content_ownText_26.indexOf(keyword26_name);
				while(i26 != -1) {//不等於-1，意思是content中有keyword，才可以跑迴圈
					count_26++;
					i26 = content_ownText_26.indexOf(keyword26_name);
					content_ownText_26 = content_ownText_26.substring(i26 + keyword26_name.length(), content_ownText_26.length());
				}
				count_sum = count_sum + count_26 * keyword26.weight;
				
				int i27 = content_ownText_27.indexOf(keyword27_name);
				while(i27 != -1) {//不等於-1，意思是content中有keyword，才可以跑迴圈
					count_27++;
					i27 = content_ownText_27.indexOf(keyword27_name);
					content_ownText_27 = content_ownText_27.substring(i27 + keyword27_name.length(), content_ownText_27.length());
				}
				count_sum = count_sum + count_27 * keyword27.weight;
				
				int i28 = content_ownText_28.indexOf(keyword28_name);
				while(i28 != -1) {//不等於-1，意思是content中有keyword，才可以跑迴圈
					count_28++;
					i28 = content_ownText_28.indexOf(keyword28_name);
					content_ownText_28 = content_ownText_28.substring(i28 + keyword28_name.length(), content_ownText_28.length());
				}
				count_sum = count_sum + count_28 * keyword28.weight;
				
				int i29 = content_ownText_29.indexOf(keyword29_name);
				while(i29 != -1) {//不等於-1，意思是content中有keyword，才可以跑迴圈
					count_29++;
					i29 = content_ownText_29.indexOf(keyword29_name);
					content_ownText_29 = content_ownText_29.substring(i29 + keyword29_name.length(), content_ownText_29.length());
				}
				count_sum = count_sum + count_29 * keyword29.weight;
				
				int i30 = content_ownText_30.indexOf(keyword30_name);
				while(i30 != -1) {//不等於-1，意思是content中有keyword，才可以跑迴圈
					count_30++;
					i30 = content_ownText_30.indexOf(keyword30_name);
					content_ownText_30 = content_ownText_30.substring(i30 + keyword30_name.length(), content_ownText_30.length());
				}
				count_sum = count_sum + count_30 * keyword30.weight;
				
				int i31 = content_ownText_31.indexOf(keyword31_name);
				while(i31 != -1) {//不等於-1，意思是content中有keyword，才可以跑迴圈
					count_31++;
					i31 = content_ownText_31.indexOf(keyword31_name);
					content_ownText_31 = content_ownText_31.substring(i31 + keyword31_name.length(), content_ownText_31.length());
				}
				count_sum = count_sum + count_31 * keyword31.weight;
				
				int i32 = content_ownText_32.indexOf(keyword32_name);
				while(i32 != -1) {//不等於-1，意思是content中有keyword，才可以跑迴圈
					count_32++;
					i32 = content_ownText_32.indexOf(keyword32_name);
					content_ownText_32 = content_ownText_32.substring(i32 + keyword32_name.length(), content_ownText_32.length());

				}
				count_sum = count_sum + count_32 * keyword32.weight;
	
				String nowTitle = RC.get(i).EachTitle;
				String nowCite = RC.get(i).EachCite;

				RC.set(i, new ReturnCites(nowTitle, nowCite, count_sum));
			}catch (NullPointerException e){e.printStackTrace();}

//			System.out.println(RC.get(i).toString());
		}


//		for (int i = 0; i < RC.size(); i++) {
//			System.out.println(i + RC.get(i).toString());
//		}

//		System.out.println("cite sorting :");
//		System.out.println(RC.size());
		Collections.sort(RC, ReturnCites.citeScoreComparator);

//		for(ReturnCites sortedcite : RC) {
//			System.out.println(sortedcite);
//		}
		return RC;
	}
}