/**
 * Created by IntelliJ IDEA.
 * User: Sonmaz
 * Date: 26-Jan-2011
 * Time: 8:51:50 AM
 * To change this template use File | Settings | File Templates.
 */
import java.io.*;
import java.util.Vector;

public class SequenceAlign {
    public static Cell[][] cells;
    public static String first = "CATGA";
    public static String second = "ACGCCA";
    public static String x;
    public static String y;
    public static double extention = 0.0;
    public static double opening = -2;
    public static double mismatch = -1;
    public static double match = 1;
    public static void main(String[] args) throws IOException {
        x = "\t" + first;
        y = "\t" + second;
        cells = new Cell[x.length()][y.length()];

        int diagonal;
        for(int i = 0; i < x.length(); i++)
        {
            for(int j = 0; j < y.length(); j++)
            {
                cells[i][j] = new Cell();

                if(i > 0 && j > 0)
                {
                    cells[i][j].upScore = java.lang.Math.max(cells[i-1][j].upScore + extention, java.lang.Math.max(cells[i-1][j].diagonalScore + opening, cells[i-1][j].leftScore + opening));
                    cells[i][j].leftScore = java.lang.Math.max(cells[i][j - 1].leftScore + extention, java.lang.Math.max(cells[i][j-1].diagonalScore + opening, cells[i][j-1].upScore + opening));
                    if(x.charAt(i) == y.charAt(j)){
                        cells[i][j].diagonalScore = java.lang.Math.max(java.lang.Math.max(cells[i - 1][j-1].upScore + match, cells[i-1][j - 1].leftScore +match), match + cells[i-1][j-1].diagonalScore);
                    }
                    else
                    {
                        cells[i][j].diagonalScore = java.lang.Math.max(java.lang.Math.max(cells[i - 1][j-1].upScore + mismatch, cells[i-1][j - 1].leftScore +mismatch), cells[i-1][j-1].diagonalScore +mismatch);
                    }
                    if(cells[i][j].upScore == java.lang.Math.max(cells[i][j].upScore, java.lang.Math.max(cells[i][j].leftScore, cells[i][j].diagonalScore)))
                    {
                        cells[i][j].path.add("up");
                    }
                    if(cells[i][j].leftScore == java.lang.Math.max(cells[i][j].upScore, java.lang.Math.max(cells[i][j].leftScore, cells[i][j].diagonalScore)))
                    {
                        cells[i][j].path.add("left");
                    }
                    if(cells[i][j].diagonalScore == java.lang.Math.max(cells[i][j].upScore, java.lang.Math.max(cells[i][j].leftScore, cells[i][j].diagonalScore)))
                    {
                        cells[i][j].path.add("diagonal");
                    }
                }
                else if (i ==0 && j == 0)
                {
                    cells[i][j].leftScore = (Double.NEGATIVE_INFINITY);
                    cells[i][j].diagonalScore = 0;
                    cells[i][j].upScore = (Double.NEGATIVE_INFINITY);
                }
                else if (i ==0 && j == 1)
                {
                    cells[i][j].leftScore = + opening;
                    cells[i][j].diagonalScore = (int)(Double.NEGATIVE_INFINITY);
                    cells[i][j].upScore = (int)(Double.NEGATIVE_INFINITY);
                    cells[i][j].path.add("left");
                }
                else if (i == 0 && j > 1)
                {
                    cells[i][j].leftScore = + opening-(j-1)*(extention);
                    cells[i][j].diagonalScore = (int)(Double.NEGATIVE_INFINITY);
                    cells[i][j].upScore = (int)(Double.NEGATIVE_INFINITY);
                    cells[i][j].path.add("left");
                }
                else if (j == 0 && i == 1)
                {
                    cells[i][j].upScore = opening;
                    cells[i][j].diagonalScore = (int)(Double.NEGATIVE_INFINITY);
                    cells[i][j].leftScore = (int)(Double.NEGATIVE_INFINITY);    
                    cells[i][j].path.add("up");
                }
                else if(j == 0 && i > 1)
                {
                    cells[i][j].upScore = opening-(j-1)*(extention);
                    cells[i][j].diagonalScore = (int)(Double.NEGATIVE_INFINITY);
                    cells[i][j].leftScore = (int)(Double.NEGATIVE_INFINITY);
                    cells[i][j].path.add("up");
                }
                else
                {
                    System.out.println("error");
                }
            }
        }
        //System.out.println(cells[x.length()-1][y.length()-1]);
        printMatrix(x,y);
        System.out.println("");
        Vector fin = printAllignment(x.length() - 1, y.length() - 1, java.lang.Math.max(cells[x.length() - 1][y.length() - 1].leftScore, java.lang.Math.max(cells[x.length() - 1][y.length() - 1].diagonalScore, cells[x.length() - 1][y.length() - 1].upScore)), 1);
        for(int d = 0; d < fin.size(); d++)
        {
            System.out.println("Path "+ String.valueOf(d + 1) +":");
            System.out.println(((Sequences)fin.get(d)).first);
            System.out.println(((Sequences)fin.get(d)).second);
            System.out.println("");            
        }
    }

    private static Vector printAllignment(int m, int n, double score, int index) {
        Cell current = cells[m][n];
        // =============================instead of one sequence, create a vector of sequences
        Vector aligned = new Vector();
        if(m ==0 && n ==0)
        {
            Sequences tmp = new Sequences();
            aligned.add(tmp);
            /*if(index == 0)
            {}
            else if (index == 1)
            {
                if(x.charAt(m+1) == y.charAt(n+1))
                {

                }
                else
            }
            else if (index == 2)
            {

            } */

            return aligned;
        }
        if(m == x.length()-1 && n == y.length()-1)
        {
            if(cells[m][n].upScore == java.lang.Math.max(cells[m][n].upScore, java.lang.Math.max(cells[m][n].leftScore, cells[m][n].diagonalScore)))
            {
                Vector soFar = printAllignment(m-1, n, cells[m][n].upScore, 2);
                for(int k = 0; k < soFar.size(); k++)
                {
                    Sequences tmp = new Sequences();
                    tmp.first = x.charAt(m) + ((Sequences)soFar.get(k)).first;
                    tmp.second = "-" + ((Sequences)soFar.get(k)).second;
                    aligned.add(tmp);
                }
            }
            if(cells[m][n].leftScore == java.lang.Math.max(cells[m][n].upScore, java.lang.Math.max(cells[m][n].leftScore, cells[m][n].diagonalScore)))
            {
                Vector soFar = printAllignment(m, n - 1, cells[m][n].leftScore, 0);
                for(int k = 0; k < soFar.size(); k++)
                {
                    Sequences tmp = new Sequences();
                    tmp.first = "-" + ((Sequences)soFar.get(k)).first;
                    tmp.second = y.charAt(n) + ((Sequences)soFar.get(k)).second;
                    aligned.add(tmp);
                }
            }
            if(cells[m][n].diagonalScore == java.lang.Math.max(cells[m][n].upScore, java.lang.Math.max(cells[m][n].leftScore, cells[m][n].diagonalScore)))
            {
                Vector soFar = printAllignment(m-1, n - 1, cells[m][n].diagonalScore, 1);
                for(int k = 0; k < soFar.size(); k++)
                {
                    Sequences tmp = new Sequences();
                    tmp.first = x.charAt(m) + ((Sequences)soFar.get(k)).first;
                    tmp.second = y.charAt(n) + ((Sequences)soFar.get(k)).second;
                    aligned.add(tmp);
                }
            }
            index = -1;
        }
        if(index == 0)
        {
            if(n > 0)
            {
                if(score == (cells[m][n].leftScore + extention))
                {
                    Vector soFar = printAllignment(m, n - 1, cells[m][n].leftScore, 0);
                    for(int k = 0; k < soFar.size(); k++)
                    {
                        Sequences tmp = new Sequences();
                        tmp.first = "-" + ((Sequences)soFar.get(k)).first;
                        tmp.second = y.charAt(n) + ((Sequences)soFar.get(k)).second;
                        aligned.add(tmp);
                    }
                }
            }
            if(m > 0)
            {
                if (score == (cells[m][n].upScore + opening))
                {
                    Vector soFar = printAllignment(m - 1, n, cells[m][n].upScore, 2);
                    for(int k = 0; k < soFar.size(); k++)
                    {
                        Sequences tmp = new Sequences();
                        tmp.first = x.charAt(m) + ((Sequences)soFar.get(k)).first;
                        tmp.second = "-" + ((Sequences)soFar.get(k)).second;
                        aligned.add(tmp);
                    }
                }
            }
            if(m > 0 && n > 0)
            {    if (score == (cells[m][n].diagonalScore + opening))
                {
                    Vector soFar = printAllignment(m - 1, n - 1, cells[m][n].diagonalScore, 1);
                    for(int k = 0; k < soFar.size(); k++)
                    {
                        Sequences tmp = new Sequences();
                        tmp.first = x.charAt(m) + ((Sequences)soFar.get(k)).first;
                        tmp.second = y.charAt(n) + ((Sequences)soFar.get(k)).second;
                        aligned.add(tmp);
                    }
                }
            }
        }

        else if(index == 1)
        {
            if(x.charAt(m+1) == y.charAt(n+1))
            {
                if(score == (cells[m][n].leftScore + match))
                {
                    Vector soFar = printAllignment(m, n - 1, cells[m][n].leftScore, 0);
                    for(int k = 0; k < soFar.size(); k++)
                    {
                        Sequences tmp = new Sequences();
                        tmp.first = "-" + ((Sequences)soFar.get(k)).first;
                        tmp.second = y.charAt(n) + ((Sequences)soFar.get(k)).second;
                        aligned.add(tmp);
                    }
                }
                if (score == (cells[m][n].upScore +match))
                {
                    Vector soFar = printAllignment(m - 1, n, cells[m][n].upScore, 2);
                    for(int k = 0; k < soFar.size(); k++)
                    {
                        Sequences tmp = new Sequences();
                        tmp.first = x.charAt(m) + ((Sequences)soFar.get(k)).first;
                        tmp.second = "-" + ((Sequences)soFar.get(k)).second;
                        aligned.add(tmp);
                    }
                }
                if (score == (cells[m][n].diagonalScore +match))
                {
                    Vector soFar = printAllignment(m - 1, n - 1, cells[m][n].diagonalScore, 1);
                    for(int k = 0; k < soFar.size(); k++)
                    {
                        Sequences tmp = new Sequences();
                        tmp.first = x.charAt(m) + ((Sequences)soFar.get(k)).first;
                        tmp.second = y.charAt(n) + ((Sequences)soFar.get(k)).second;
                        aligned.add(tmp);
                    }
                }
            }
            else
            {
                if(score == (cells[m][n].leftScore + mismatch))
                {
                    Vector soFar = printAllignment(m, n - 1, cells[m][n].leftScore, 0);
                    for(int k = 0; k < soFar.size(); k++)
                    {
                        Sequences tmp = new Sequences();
                        tmp.first = "-" + ((Sequences)soFar.get(k)).first;
                        tmp.second = y.charAt(n) + ((Sequences)soFar.get(k)).second;
                        aligned.add(tmp);
                    }
                }
                if (score == (cells[m][n].upScore + mismatch))
                {
                    Vector soFar = printAllignment(m - 1, n, cells[m][n].upScore, 2);
                    for(int k = 0; k < soFar.size(); k++)
                    {
                        Sequences tmp = new Sequences();
                        tmp.first = x.charAt(m) + ((Sequences)soFar.get(k)).first;
                        tmp.second = "-" + ((Sequences)soFar.get(k)).second;
                        aligned.add(tmp);
                    }
                }
                if (score == (cells[m][n].diagonalScore + mismatch))
                {
                    Vector soFar = printAllignment(m - 1, n - 1, cells[m][n].diagonalScore, 1);
                    for(int k = 0; k < soFar.size(); k++)
                    {
                        Sequences tmp = new Sequences();
                        tmp.first = x.charAt(m) + ((Sequences)soFar.get(k)).first;
                        tmp.second = y.charAt(n) + ((Sequences)soFar.get(k)).second;
                        aligned.add(tmp);
                    }
                }
            }

        }
        else if(index == 2)
        {
            if(score == (cells[m][n].leftScore + opening))
            {
                Vector soFar = printAllignment(m, n - 1, cells[m][n].leftScore, 0);
                for(int k = 0; k < soFar.size(); k++)
                {
                    Sequences tmp = new Sequences();
                    tmp.first = "-" + ((Sequences)soFar.get(k)).first;
                    tmp.second = y.charAt(n) + ((Sequences)soFar.get(k)).second;
                    aligned.add(tmp);
                }
            }
            if (score == (cells[m][n].upScore + extention))
            {
                Vector soFar = printAllignment(m - 1, n, cells[m][n].upScore, 2);
                for(int k = 0; k < soFar.size(); k++)
                {
                    Sequences tmp = new Sequences();
                    tmp.first = x.charAt(m) + ((Sequences)soFar.get(k)).first;
                    tmp.second = "-" + ((Sequences)soFar.get(k)).second;
                    aligned.add(tmp);
                }
            }
            if (score == (cells[m][n].diagonalScore + opening))
            {
                Vector soFar = printAllignment(m - 1, n - 1, cells[m][n].diagonalScore, 1);
                for(int k = 0; k < soFar.size(); k++)
                {
                    Sequences tmp = new Sequences();
                    tmp.first = x.charAt(m) + ((Sequences)soFar.get(k)).first;
                    tmp.second = y.charAt(n) + ((Sequences)soFar.get(k)).second;
                    aligned.add(tmp);
                }
            }
        }

        return aligned;   
    }

    public static void printMatrix(String x, String y)
    {
        for (int j = 0; j < y.length(); j++)
        {
            System.out.print(y.charAt(j)+"\t");
        }

        System.out.println("");
        for(int i = 0; i < x.length();i++)
        {
            System.out.print(x.charAt(i));
            if(i > 0)
            {
                System.out.print("\t");
            }
            for(int j = 0; j < y.length(); j++)
            {
                System.out.print("("+String.valueOf(cells[i][j].leftScore) +","+ String.valueOf(cells[i][j].diagonalScore)+","+String.valueOf(cells[i][j].upScore)+")   ");
            }
            System.out.println("");
        }
    }
}
