package src;

/**
 *  pagination
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
     * Paginates the given text.
     * 
     * <p>Time Complexity: O(n), where {@code n} is the length of {@code text}</p>
     * 
     * @param text - One line text.
     * 
     * @return String[] with the parameter {@code text} paginated and each element of the array represents a page.   
     */
    public String[] paginate(String text) {
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
            finalText[page].substring(0, finalText[page].length()-1);

        return finalText;

    }
}

