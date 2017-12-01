
import java.io.File;
import java.io.IOException;
import java.io.*;
import java.util.*;



public class autoRun {

    public static void main (String[] args){

        UnZip unZip = new UnZip();
        unZip.unZipIt("/home/serhan/Desktop/master.zip","/home/serhan/Desktop/unziphali");

        /*
        ProcessBuilder builder = new ProcessBuilder("java", "calculator");
        builder.directory(new File("/home/serhan/Desktop"));//I don't know you're on linux or windows. Just go to where your "SecondApplication" is located.
        try {
            Process process = builder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }
}
