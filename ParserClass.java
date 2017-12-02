import jdk.internal.dynalink.beans.StaticClass;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class ParserClass {
    /**
     *
     * @param allChar file to process in the string type
     * @return  the file in the string type without comment lines
     */
    public static String removeComment(String allChar){

        int commentIndex =0;
        int newLineIndex =0;
        String split1 ="";
        String split2 ="";
        while(commentIndex != -1){
            commentIndex = allChar.indexOf("//");
            if(commentIndex != -1) {
                newLineIndex = allChar.indexOf("\n", commentIndex);
                split1 = allChar.substring(0,commentIndex);
                split2 = allChar.substring(newLineIndex+1);
                allChar = "";
                allChar = split1 + split2 ;

            }
        }
        commentIndex = 0;
        while(commentIndex != -1) {
            commentIndex = allChar.indexOf("/*");
            if (commentIndex != -1) {
                newLineIndex = allChar.indexOf("*/", commentIndex);
                split1 = allChar.substring(0, commentIndex);
                split2 = allChar.substring(newLineIndex + 1);
                allChar = "";
                allChar = split1 + split2;
            }
        }

        return allChar;
    }

    /**
     *
     * @param allChar file to process in the string type without comment lines
     * @return parsed methods in the String type
     */
    public static String findMethods(String allChar){
        String parsedChar = " ";

        String split1 ="";
        String split2 ="";

        /*Find Public methods*/
        int keywordIndex = allChar.indexOf("public");
        while(keywordIndex != -1){
            int closingIndex = allChar.indexOf(")",keywordIndex);
            parsedChar += allChar.substring(keywordIndex,closingIndex+1);
            parsedChar += "\n";

            split1 = allChar.substring(0, keywordIndex);
            split2 = allChar.substring(closingIndex);
            allChar = "";
            allChar = split1 + split2;

            keywordIndex = allChar.indexOf("public");
        }

        /*Find Private methods*/
        keywordIndex = allChar.indexOf("private");
        while(keywordIndex != -1){
            int closingIndex = allChar.indexOf(")",keywordIndex);
            parsedChar += allChar.substring(keywordIndex,closingIndex+1);
            parsedChar += "\n";

            split1 = allChar.substring(0, keywordIndex);
            split2 = allChar.substring(closingIndex);
            allChar = "";
            allChar = split1 + split2;

            keywordIndex = allChar.indexOf("private");
        }

        /*Find Static Methods*/
        keywordIndex = allChar.indexOf("static");
        while(keywordIndex != -1){
            int closingIndex = allChar.indexOf(")",keywordIndex);
            int semicolonIndex = allChar.indexOf(";",keywordIndex);

            if(semicolonIndex >= closingIndex || semicolonIndex == -1) {
                parsedChar += allChar.substring(keywordIndex, closingIndex + 1);
                parsedChar += "\n";

                split1 = allChar.substring(0, keywordIndex);
                split2 = allChar.substring(closingIndex);
                allChar = "";
                allChar = split1 + split2;
            }

            keywordIndex = allChar.indexOf("static");
        }

        /*Find Protected Methods*/
        keywordIndex = allChar.indexOf("protected");
        while(keywordIndex != -1){
            int closingIndex = allChar.indexOf(")",keywordIndex);
            int semicolonIndex = allChar.indexOf(";",keywordIndex);

            if(semicolonIndex >= closingIndex || semicolonIndex == -1) {
                parsedChar += allChar.substring(keywordIndex, closingIndex + 1);
                parsedChar += "\n";

                split1 = allChar.substring(0, keywordIndex);
                split2 = allChar.substring(closingIndex);
                allChar = "";
                allChar = split1 + split2;
            }

            keywordIndex = allChar.indexOf("protected");
        }

        return parsedChar;
    }

    /**
     *
     * @param path parse the path of the file
     * @return parsed methods in the String type
     */
    public static String parser(String path){


        String allChar = " ";
        String parsedChar = " ";

        BufferedReader br = null;
        FileReader fr = null;

        try {

            //br = new BufferedReader(new FileReader(FILENAME));
            fr = new FileReader(path);
            br = new BufferedReader(fr);

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                //System.out.println(sCurrentLine);
                allChar += sCurrentLine;
                allChar += "\n";
            }

            allChar = removeComment(allChar);
            parsedChar = findMethods(allChar);

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (br != null)
                    br.close();

                if (fr != null)
                    fr.close();

            } catch (IOException ex) {

                ex.printStackTrace();
            }
        }

       return parsedChar;
    }


    public static void main(String[] args) throws ClassNotFoundException {

        System.out.println(parser("C:\\Users\\murat\\Desktop\\Parser\\src\\combination.java"));

    }
}