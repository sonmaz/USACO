/*
ID: s_krish1
LANG: JAVA
TASK: barn1
*/
import java.io.*;
import java.util.*;

class barn1 {
    public static int fBlock[];
    public static void sort()
    {
        int swap;
        for (int i = 0; i < fBlock.length;i++)
        {
            for(int j = 1; j < fBlock.length;j++)
            {
                if(fBlock[j-1] < fBlock[j])
                {
                    swap = fBlock[j];
                    fBlock[j]=fBlock[j-1];
                    fBlock[j-1]=swap;
                }
            }
        }
    }
    public static int temp[];
    public static void sort1()
    {
        int swap;
        for (int i = 0; i < temp.length;i++)
        {
            for(int j = 1; j < temp.length;j++)
            {
                if(temp[j-1] > temp[j])
                {
                    swap = temp[j];
                    temp[j]=temp[j-1];
                    temp[j-1]=swap;
                }
            }
        }
    }
    public static void main (String [] args) throws IOException {
    // Use BufferedReader rather than RandomAccessFile; it's much faster
    BufferedReader f = new BufferedReader(new FileReader("barn1.in"));
                                                  // input file name goes above
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("barn1.out")));
    // Use StringTokenizer vs. readLine/split -- lots faster
    StringTokenizer st = new StringTokenizer(f.readLine());
						  // Get line, break into tokens
    int M = Integer.parseInt(st.nextToken());    // first integer
    int S = Integer.parseInt(st.nextToken());
    int C = Integer.parseInt(st.nextToken());  // second integer
    temp=new int[C];
    for(int i = 0; i < C;i++)
    {
        temp[i] = Integer.parseInt(f.readLine());
    }
    sort1();
    int num= temp[0];
    int tmp=0;
    int ind=0;

    ind=num;
    tmp=num;
    int first = ind;
    fBlock = new int [C];

    for(int i = 1; i < C; i++)
    {
        num= temp[i];

            tmp = num;    // first integer
        fBlock[i-1]=tmp - ind-1;
        ind = tmp;
    }
    S = tmp - first+1;
    sort();
    int sect = 1;
    while(sect < M)
    {
        if(fBlock.length > 0)
            if(fBlock[sect - 1] <= 0)
                break;
            else
            {
                S = S - fBlock[sect-1];
                sect++;
            }
    }
    //    System.out.println(S);
    out.println(S);                           // output result
    out.close();                                  // close the output file
    System.exit(0);                               // don't omit this!
  }
}