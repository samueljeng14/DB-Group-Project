package test;
import java.util.Comparator;

public class ReturnCites2{

    public String EachTitle;
    public String EachCite;
    public double EachScore;

    public ReturnCites2(String eachTitle, String eachCite, double eachScore) {
        this.EachCite = eachCite;
        this.EachTitle = eachTitle;
        this.EachScore = eachScore;


    }

    public static Comparator<ReturnCites2> citeScoreComparator = new Comparator<ReturnCites2>() {

        public int compare(ReturnCites2 c1, ReturnCites2 c2) {
            double citesScore1 = c1.getScore();
            double citesScore2 = c2.getScore();

            return Double.compare(citesScore2, citesScore1);
        }
    };

    public double getScore() {
        return EachScore;
    }

    @Override
    public String toString() {
        return "[title = " + EachTitle + ", cite = " + EachCite + " , score = " + EachScore + "]";
    }
}