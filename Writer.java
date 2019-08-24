import java.io.*;
import java.lang.*;
import java.util.Formatter;
import java.util.*;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
public class Writer {
    private Formatter x;
    public void openFile(String path)
    {
        try{
            x=new Formatter(path);
        }
        catch(Exception e)
        {
            System.out.println("error");
        }
    }
    public void createFile(String path) {
        try {
        File f = new File("C:/" + path);
        f.createNewFile();
        }catch(Exception e) {
            System.out.println("Error, file cannot be created");
        }
    }
    public void writeTextFiles(String path, String textToAdd) {
        try {
            FileWriter fw = new FileWriter("C:/" + path);//writes to a file for the first time
            fw.write(textToAdd);
            fw.close();
        }
        catch(IOException ioe){
            System.err.println("IOException: " + ioe.getMessage());
        }
    }
    public void updateTextFiles(String path, String textToAdd) {
        try {
            FileWriter fw = new FileWriter("C:/" + path,true); //the true will append the new data if flase it will overwrite
            fw.write("\r\n" + textToAdd);//appends the string to the file on a new line
            fw.close();
        }
        catch(IOException ioe){
            System.err.println("IOException: " + ioe.getMessage());//The "standard" error output stream. This stream is already open and ready to accept output data.Typically this stream corresponds to display output or another output destination specified by the host environment or user.
        }
    }
    public void addRecords(String UserName,int[][] EncPass)
    {
        String str="";
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<EncPass.length;j++)
            {
                str+=EncPass[i][j]+" ";
            }

        }
        x.format("%s",str);
    }
    public void closeFile()
    {
        x.close();
    }
    public void copyFile(File sourceFile, File destFile) throws IOException {
        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;//Declare and initialize a file channel for the source file to be copied (connection to a file that  is read byte by byte)
        FileChannel destination = null;//Declare and initialize a file channel for the destination file to be copied (connection to a file that  is read byte by byte)
        try {
            source = new FileInputStream(sourceFile).getChannel();//Creates a FileInputStream by opening a connection to an actual file, the file named by the File object file in the file system
            destination = new FileOutputStream(destFile).getChannel();//Creates a file output stream to write to the file represented by the specified File object
            long count = 0;
            long size = source.size();
            while ((count += destination.transferFrom(source, count, size - count)) < size);//Transfers bytes into this channel's file from the given readable byte channel until the size of Destination file becomes equal or bigger to the source
        } finally {
            if (source != null) {
                source.close();//closes the channel
            }
            if (destination != null) {
                destination.close();
            }
        }
        int width = 64, height = 64; //specify resize dimensions to 64x64
        BufferedImage inputImage = ImageIO.read(sourceFile); //sets a buffered image to the data read from the source Image
        BufferedImage outputImage = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB); //readies a buffered image with the dimensions specified before and makes it colored
        Graphics2D g = outputImage.createGraphics(); //creates a 2D graphical object that is a copy of the BufferedImage outputImage
        //sets the graphics of the object created above
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BICUBIC); //rendering algorithm
        g.clearRect(0, 0, width, height);//Clears the default background color of the graphical object
        g.drawImage(inputImage, 0, 0, width, height, null);//draws the input image into g
        g.dispose();//releases system resources and the object out of memory
        ImageIO.write(outputImage,"png",destFile);//writes the image to the destFile with .png format
    }
    public void saveProgress(int[] Progress)
    {
        String str="";
        
        for(int i=0;i<Progress.length;i++)
        {
            str+=Progress[i];
        }
        
        x.format("%s",str);
    }
}
