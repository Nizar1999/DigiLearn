import javax.swing.JOptionPane;

public class Password
{
    public int [][] pass;
    public int SingCypher[][]=
		{
			{1,5,2},
			{3,15,6},
			{2,1,-6}
        };
    public Password(char[] p)
    {
		char[] pa=p;
		for(int i=0;i<p.length;i++)
		{
			if(Character.isLowerCase(p[i]))
				pa[i]=Character.toUpperCase(p[i]);
		}
        setPass(pa);
	}
	public Password(int[][] p)
	{
		setPassword(p);
	}
	public void setPassword(int[][]p)
	{
		pass=p;
	}
    public void setPass(char[] p)
    {
        int Passmatrix[][]=new int[3][(p.length)/3+1];
		ArraytoMatrix(p,Passmatrix);
		int Pass[][]=new int[3][(p.length)/3+1];
		DeAndEncrypte(Passmatrix,Pass,SingCypher);
		this.pass=Pass;
    } 
    public int[][] getPass()
    {
        return pass;
    }
    public int[][] EncryptPass(int [][]Passmatrix)
    {
        int[][]Encryptedpass=new int[3][(Passmatrix.length)/3+1];
		DeAndEncrypte(Passmatrix,Encryptedpass,SingCypher);
        return Encryptedpass;
    }
    public boolean Authenticate(char[]PossiblePass)
    {
		char[] PoPas=PossiblePass;
		for(int i=0;i<PossiblePass.length;i++)
		{
			if(Character.isLowerCase(PossiblePass[i]))
				PoPas[i]=Character.toUpperCase(PossiblePass[i]);
		}
        int Possmatrix[][]=new int[3][(PossiblePass.length)/3+1];
		ArraytoMatrix(PoPas,Possmatrix);
		int PossEmatrix[][]=new int[3][(PossiblePass.length)/3+1];
		DeAndEncrypte(Possmatrix, PossEmatrix, SingCypher);
		boolean same=true;
		for(int i=0;i<pass.length;i++)
		{
			for(int j=0;j<pass[i].length;j++)
			{
				if(PossEmatrix[i][j]!=pass[i][j])
				{
					same=false;
					break;
				}
			}
			if(!same)
				break;
		
		}  
		return same;
    }
//************************************************************************************************
	//This function is used to Multiply the Cypher/Inverse Cypher by the Matrix obtained from the user or by Encrypting
    public void DeAndEncrypte(int [][]orgarr,int [][]encr,int [][]cypher)
	{
		for(int a=0;a<encr.length;a++)
		{
			for(int b=0;b<encr[a].length;b++)
			{
		 		for(int i=0;i<orgarr.length;i++)
		 		{
					encr[a][b]+=(cypher[a][i]*orgarr[i][b]);
			 	}
		 	}
		 }
	}
	public void PrintMatrix(int [][]arr)
	{
		for(int i=0;i<arr.length;i++)
		{
			for(int j=0;j<arr[i].length;j++)
			{
				System.out.print(arr[i][j]+"  ");
			}
			System.out.println();
		}
	}
    //Take a one Dimantional array and injects it into a two Dimantional one
	static void ArraytoMatrix(char[]arr,int[][]arrb)
	{
		int count=0;
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<(arr.length)/3+1;j++)
			{
				if(count<arr.length)
				{
					switch(arr[count])
					{
						case 'A':arrb[i][j]=1 ;break;
						case 'B':arrb[i][j]=2;break;
						case 'C':arrb[i][j]=3;break;
						case 'D':arrb[i][j]=4;break;
						case 'E':arrb[i][j]=5;break;
						case 'F':arrb[i][j]=6;break;
						case 'G':arrb[i][j]=7;break;
						case 'H':arrb[i][j]=8;break;
						case 'I':arrb[i][j]=9;break;
						case 'J':arrb[i][j]=10;break;
						case 'K':arrb[i][j]=11;break;
						case 'L':arrb[i][j]=12;break;
						case 'M':arrb[i][j]=13;break;
						case 'N':arrb[i][j]=14;break;
						case 'O':arrb[i][j]=15;break;
						case 'P':arrb[i][j]=16;break;
						case 'Q':arrb[i][j]=17;break;
						case 'R':arrb[i][j]=18;break;
						case 'S':arrb[i][j]=19;break;
						case 'T':arrb[i][j]=20;break;
						case 'U':arrb[i][j]=21;break;
						case 'V':arrb[i][j]=22;break;
						case 'W':arrb[i][j]=23;break;
						case 'X':arrb[i][j]=24;break;
						case 'Y':arrb[i][j]=25;break;
						case 'Z':arrb[i][j]=26;break;
						case ' ':arrb[i][j]=27;break;
						case '0':arrb[i][j]=28;break;
						case '1':arrb[i][j]=29;break;
						case '2':arrb[i][j]=30;break;
						case '3':arrb[i][j]=31;break;
						case '4':arrb[i][j]=32;break;
						case '5':arrb[i][j]=33;break;
						case '6':arrb[i][j]=34;break;
						case '7':arrb[i][j]=35;break;
						case '8':arrb[i][j]=36;break;
						case '9':arrb[i][j]=37;break;
					}
					count++;
				}
				else
					arrb[i][j]=27;
			}
		}
    }
    //************************************************************************************************
	//Puts a String in an array of the same size
	static void StringtoArray(char []arr,String phrase)
	{
		for(int i=0;i<phrase.length();i++)
		{
			arr[i]=phrase.charAt(i);
		}
	}
}