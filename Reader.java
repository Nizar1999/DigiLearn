import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Reader {
    
    //a function that reads multiple line data in files and returns them as one string seperated by a /
	public String readMLFile(String path) {
		File text = new File("C:/" + path);
		String str = "";
		try(Scanner sc = new Scanner(text)) {
			while(sc.hasNextLine()) {
				str += sc.nextLine() + "/";
			}
		} catch(FileNotFoundException e) {
			System.out.println("Error, file not found!");
		}
		return str;
	}

    //a function to count the number of lines in a file
	public int countLines(String path) {
		File text = new File("C:/" + path);
		int count = 0;
		try(Scanner sc = new Scanner(text)) {
			while(sc.hasNextLine()) {
				count++;
				sc.nextLine();
			}
		} catch(FileNotFoundException e) {
			System.out.println("Error, file not found!");
		}
		return count;
	}
    //read single line files
    public String readFile(String path)
    {
        String str="";
        try(Scanner sc = new Scanner(new File("C:/" + path))) {
            while(sc.hasNext())
            {
                str += sc.next() + " ";
            }
        }catch(FileNotFoundException e) {
            System.out.println("Error, file not found!");
        }
        if(str.length() > 0) {
            return str.substring(0,str.length()-1); //removes the space that is at the end of the line
        }
        return str;
    }
    private Scanner x;
    public void openFile(String Path)
    {
        try{
            String str="C:\\"+Path;
            x=new Scanner(new File(str));
        }
        catch(Exception e)
        {
            System.out.println("Error, file not found!");
        }
    }
    public int PassSize()
    {
        int count=0;
        while(x.hasNext())
        {
            String a=x.next();
            count++;
        }
        return count;
    }

    public int[][] readPass(int count)
    {
        int[][]pass=new int[3][count/3];
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<(count/3);j++)
            {
               
                if(x.hasNext()){
                    String a=x.next();
                    pass[i][j]=Integer.parseInt(a);
                }
            }
        }
        return pass;
    }
    public void closeFile(){
        x.close();
    }
}	