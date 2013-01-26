import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Sonmaz
 * Date: 23-Feb-2011
 * Time: 11:06:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class Calfflac {
    
    public static boolean isPalindrome(char[] str)
    {
        for(int i = 0; i < (str.length)/2; i++)
        {
            if(!(str[i] == (str[str.length -1 - i])))
            {
                return false;
            }
        }
        return true;
    }
   public static void main(String[] args) {
        String str = "aailiaa";
        char[] a = new char[str.length()];
        for(int j = 0; j < str.length(); j++)
        {
            a[j]=str.charAt(j);
        }
        for(int k = 1; k < a.length; k++)
        {
            for(int l = 0; l < a.length; l++)
            {
                char[] b=new char[k];
                for(int t = l; t < l+k; t++)
                {
                    b[t-l]=a[t];
                }
                isPalindrome(b);    
            }
        }

        System.out.println(isPalindrome(a));
   }
}
