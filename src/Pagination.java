package src;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 *  Class with the purpose to paginate a doucment with 80 characters per line and 25 lines per page.
 * 
 *  <p> Transform a {@code String text} to {@code String[] paginatedText} in which element represents a page.</p> 
 */
public class Pagination {

    /**
     * The maximum characters allowed for every line of text.
     */
    private static final int MAX_CHARS_PER_LINE=80; 

    /**
     * The maximum lines allowed for every page.
     */
    private static final int MAX_LINES_PER_PAGE=25; 

    /**
     * Paginates the given text and .
     * 
     * <p>Time Complexity: O(n), where {@code n} is the length of {@code text}</p>
     * 
     * @param text - One line text.
     *    
     * @return String[] with the parameter {@code text} paginated and each element of the array represents a page.  
     */
    private String[] paginateText(String text) {
        int numPages = text.length()/(Pagination.MAX_CHARS_PER_LINE*Pagination.MAX_LINES_PER_PAGE) + 1;
        String[] finalText = new String[numPages];
        String[] words = text.split(" ");
        int charCount = 0;
        int lineCount = 1; 
        int page = 0;
        for(String word : words ) {

            if(charCount + word.length() > Pagination.MAX_CHARS_PER_LINE) {
                finalText[page] += '\n';    //add endline
                charCount = 0;  //reset count
                ++lineCount;    //next line

                if(lineCount> Pagination.MAX_LINES_PER_PAGE) {
                    ++page;     //next page
                    lineCount = 1;  //reset count
                }
            }

            finalText[page] += word +" ";  //add word followed by a whitespace
            charCount += word.length();
        }
        //remove last whitespace
        if(words.length >0)
            this.finalText[page].substring(0, this.finalText[page].length()-1);

        return finalText;

    }
    
    /**
     * Reads the file and returns it's content.
     * 
     * @param file - adress of file to read.
     * 
     * @return String of the content in {@code file}.  
     */
    private String readDocument(String file) {
        String text=new String();
        try {
            FileInputStream fis = new FileInputStream(file);
    
            System.setIn(fis);

            Scanner scanner = new Scanner(System.in);
            
            while (scanner.hasNextLine()) 
               text += scanner.nextLine();

            fis.close();
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
    
    /**
     * Creats the file or replace the content of the given document name with the given paginated text. 
     * 
     * @param docName - Name of the document.
     * 
     * @param text - Paginated text, 
     */
    private void writeDocument(String docName, String[] text) {
        //not implemented yet
    }
    /**
     * Main function of the class:
     * Paginates the given input Document.txt, that contains a one line text, 
     * creates or replace a document with name stored in {@code outputDocName}
     * and writes the result on it.
     * 
     * @param inputDoc - Input Document with one line text.
     * 
     * @param outputDocName - Output Document where to write the paginated {@code inputDoc}.
     */
    public void paginateDoc(String inputDocTxt, String outputDocName){
        //Read the input document 
        String text = this.readDocument(inputDocTxt);   

        //Paginate the text which will be safed in the FinalText Variable
        String[] paginatedText = this.paginateText(text);

        this.writeDocument(outputDocName, paginatedText);
        
    }


    
}

