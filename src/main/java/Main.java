import java.io.IOException;
import java.util.Scanner;
import sun.security.validator.ValidatorException;

public class Main {

	public static void main(String[] args) throws IOException, ValidatorException {

		Scanner in = new Scanner(System.in);
		while(in.hasNextLine()) {
			String d = in.next();
			GetContent ctn = new GetContent(d);

//			底下的為輸出部分
//			System.out.println(ctn.GetGoogle().get(5).EachCite);
			ctn.search();
//			純粹知道跑完了用的

			System.out.println("DONE");
		}
		in.close();
	}
}