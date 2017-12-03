import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;


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
        String keywordsList = "\n" +
                "abstract\tcontinue\tfor\tnew\tswitch\n" +
                "assert***\tdefault\tgoto*\tpackage\tsynchronized\n" +
                "boolean\tdo\tif\tprivate\tthis\n" +
                "break\tdouble\timplements\tprotected\tthrow\n" +
                "byte\telse\timport\tpublic\tthrows\n" +
                "case\tenum****\tinstanceof\treturn\ttransient\n" +
                "catch\textends\tint\tshort\ttry\n" +
                "char\tfinal\tinterface\tstatic\tvoid\n" +
                "class\tfinally\tlong\tstrictfp**\tvolatile\n" +
                "const*\tfloat\tnative\tsuper\twhile";



        String split1 ="";
        String split2 ="";

        /*Find methods*/
        int closingIndex = allChar.indexOf(")");
        while(closingIndex != -1){


            int curlyBracketIndex = allChar.indexOf("{",closingIndex);

            if( curlyBracketIndex != -1){
                boolean status = true;

                for(int i=closingIndex+1; i<curlyBracketIndex;i++ )
                    if(allChar.charAt(i) != ' ' && allChar.charAt(i) != '\n'){
                        status = false;
                        break;
                    }

                if(curlyBracketIndex-closingIndex == 1)
                    status = true;


                if(status){
                    int lastIndex = 0;
                    int firstIndex = 0;

                    String tempString = allChar.substring(0,closingIndex);
                    int closingP = tempString.lastIndexOf("(");
                    int privateControlLastIndex = 0;
                    int privateControlFirstIndex = 0;

                    for(int i=closingP-1; i>0; i--){
                        if(allChar.charAt(i) != ' ') {
                            lastIndex = i;
                            break;
                        }
                    }

                    for(int i=lastIndex-1; i>0; i--){
                        if(allChar.charAt(i) == ' ') {
                            firstIndex = i;
                            break;
                        }
                    }
                    for(int i=firstIndex-1; i>0; i--){
                        if(allChar.charAt(i) != ' ') {
                            privateControlLastIndex = i;
                            break;
                        }
                    }
                    for(int i=privateControlLastIndex-1; i>0; i--){
                        if(allChar.charAt(i) == ' ') {
                            privateControlFirstIndex = i;
                            break;
                        }
                    }

                    String returnType = allChar.substring(privateControlFirstIndex+1,privateControlLastIndex+1);
                    int cControl = 0;
                    if(returnType.equals("\n") || returnType.equals("public")
                            || returnType.indexOf(";")!=-1 || returnType.indexOf("{") != -1
                            || returnType.indexOf("}") != -1)
                        cControl = -1;


                    for(int i=privateControlFirstIndex-1; i>0; i--){
                        if(allChar.charAt(i) != ' ') {
                            privateControlLastIndex = i;
                            break;
                        }
                    }
                    for(int i=privateControlLastIndex-1; i>0; i--){
                        if(allChar.charAt(i) == ' ') {
                            privateControlFirstIndex = i;
                            break;
                        }
                    }

                    String privateControlString = allChar.substring(privateControlFirstIndex+1,privateControlLastIndex+1);

                    if(allChar.charAt(firstIndex+1) == '}')
                        firstIndex++;

                    String temp = allChar.substring(firstIndex+1,lastIndex+1);
                    int pControl = temp.indexOf("(");

                    if(keywordsList.indexOf(temp) == -1 && !(privateControlString.equals("private")) &&
                            pControl == -1 && cControl != -1) {
                        parsedChar += returnType +" ";
                        parsedChar += allChar.substring(firstIndex+1,closingIndex+1);
                        parsedChar += "\n";
                    }

                    split1 = allChar.substring(0, firstIndex);
                    split2 = allChar.substring(closingIndex+1);
                    allChar = "";
                    allChar = split1 + split2;

                }
                else{
                    split1 = allChar.substring(0, closingIndex-1);
                    split2 = allChar.substring(closingIndex+1);
                    allChar = "";
                    allChar = split1 + split2;
                }

            }
            else{
                split1 = allChar.substring(0, closingIndex-1);
                split2 = allChar.substring(closingIndex+1);
                allChar = "";
                allChar = split1 + split2;
            }

            closingIndex = allChar.indexOf(")");

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



/*
    public static Vector<Vector<String>> lineParser(String parsedChar){

        Vector<Vector<String>> s = new Vector<Vector<String>>();
        Vector<String> subVec = new Vector<>();

        int openIndex = parsedChar.indexOf("(");
        int closeIndex = parsedChar.indexOf(")");
        int commaIndex = 0;
        String methodName ="";
        String parameters="";

        while(openIndex != -1){
            methodName = parsedChar.substring(0,openIndex-1);
            subVec.add(methodName);
            parameters = parsedChar.substring(openIndex,closeIndex);
            commaIndex = parameters.indexOf(",");

            while(commaIndex != -1){


            }


        }

        s.addElement(subVec);

        return s;
    }*/



    public static void main(String[] args) throws ClassNotFoundException {
        String methodLine=null;
      //  Vector<Vector<String>> listOfLists = new Vector<Vector<String>>();


        methodLine = parser("DiscreteFourierTransform.java");
        System.out.println(methodLine);
       // listOfLists = lineParser(methodLine);

    //    System.out.println(listOfLists.get(1).size());

    }
}