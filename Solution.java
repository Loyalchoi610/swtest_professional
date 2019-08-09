
import java.io.File;
import java.util.Scanner;

class Solution {
	private static Scanner sc;
	private static UserSolution usersolution = new UserSolution();
	
	private final static int MAKE = 1;
	private final static int CHANGE = 2;
	private final static int REMOVE = 3;
	private final static int FIND = 4;
	
	private static int cmd, ret, answer;	
	private static char[] keyword = new char[21];
	
	private static void copyStr(char[] dst, String src){
		for(int i = 0; i < src.length(); i++)
			dst[i] = src.charAt(i);
		for(int i = src.length(); i < dst.length; i++ )
			dst[i] = '\0';
	}
	
	private static int run(int Ans)
	{
		String tmpstr;
		usersolution.init();			
					
		int N = sc.nextInt();		
		for(int i = 0; i < N; i++)
		{
			cmd = sc.nextInt();
			tmpstr = sc.next();
			
			copyStr(keyword, tmpstr);
			
			switch (cmd)
			{
			case MAKE:						
				usersolution.make(keyword);
				break;

			case CHANGE:						
				usersolution.change(keyword);
				break;

			case REMOVE:
				answer = sc.nextInt();
				ret = usersolution.remove(keyword);
				if(answer != ret) Ans = 0;
				break;
				
			case FIND:				
				answer = sc.nextInt();
				ret = usersolution.find(keyword);
				if(answer != ret) Ans = 0;
				break;
			}			
		}	
	
		return Ans;
	}
	public static void main(String[] args) throws Exception {
		System.out.println(new File(".").getAbsolutePath());
		System.setIn(new java.io.FileInputStream("folder_input.txt"));
		sc = new Scanner(System.in);

		int TC = sc.nextInt();
		int Ans = 100;
		for (int testcase = 1; testcase <= TC; ++testcase) {
            System.out.println("#" + testcase + " " + run(Ans));
		}
		sc.close();
	}
}
