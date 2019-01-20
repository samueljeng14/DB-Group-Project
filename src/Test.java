
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import test.ReturnCites;

/**
 * Servlet implementation class Test
 */
@WebServlet("/Test")
public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Test() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		if(request.getParameter("keyword")== null) {
			String requestUri = request.getRequestURI();
			request.setAttribute("requestUri", requestUri);
			request.getRequestDispatcher("Search.jsp").forward(request, response);
			return;
		}
		GetContent ctn = new GetContent(request.getParameter("keyword"));
		System.out.println("hi");
		ArrayList<ReturnCites> SC = ctn.search();
//		ArrayList<String> childs = 
		for(int i = 0 ; i < SC.size(); i++) {
			ReturnCites rc = SC.get(i);
			WebPage wb = new WebPage(rc.EachTitle, rc.EachCite);
			wb.setScore(null);
			System.out.println(i + "th has done!");
		}
		System.out.println("hello");
		
//		ArrayList<ReturnCites> SC = new ArrayList();
//		SC.add(new ReturnCites("hi","hi",1));
		
		
		//System.out.println("DONE");
//		String[][] s = new String[SC.size()][2];
		request.setAttribute("query", SC);
//		int num = 0;
//		for(ReturnCites sortedCite : SC) {
//			String key = sortedCite.EachTitle;
//			String value = sortedCite.EachCite;
//			s[num][0] = key;
//			s[num][1] = value;
//			num++;
//		}
		
		request.getRequestDispatcher("WebItem.jsp").forward(request, response); 
		
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
