import java.io.FileNotFoundException;
import java.util.Scanner;

public class Solution2 {
	private static Scanner sc;
	private static UserSolution usersolution = new UserSolution();
	private static int totalscore = 0;
	private static int tc = 0;
	private static int score = 100;
	private static int N;
	
	private static char[] res = new char[33];
	private static char[] ans = new char[33];
	
	private static int mstrcmp(char str1[], char str2[])
	{
		int c = 0;
		while(str1[c] != 0 && str1[c] == str2[c]) ++c;
		return str1[c] - str2[c];
	}
	private static void strtochar(String s1, char s2[]){
		for(int i = 0; i < s1.length(); i++)
			s2[i] = s1.charAt(i);
		s2[s1.length()] = '\0';
	}
	public static void main(String[] args) throws FileNotFoundException	{
	
		//System.setIn(new java.io.FileInputStream("input.txt"));
		sc = new Scanner(System.in);
		N = sc.nextInt(); 
		String buf = sc.next();
		String tmp = sc.next();
		
		strtochar(tmp, ans);
		
		while (true)
		{
			usersolution.Run(buf.charAt(0), res);
			
			if (mstrcmp(res, ans) != 0)
			{
				if(score != 0) score-- ;
			}

			buf = sc.next();
			
			if(buf.charAt(0) == 'Z') break;
			
			tmp = sc.next();
			strtochar(tmp, ans);
			
			if (buf.charAt(0) == 'C')
			{
				System.out.printf("#%d %d\n", tc + 1, score); 
				tc++;
				totalscore += score;
				score = 100;
			}
		}
		System.out.printf("#%d %d\n", tc + 1, score); 
		tc++;
		totalscore += score;
		System.out.printf("Total Score = %d\n", totalscore / tc);
		sc.close();
	}
}
