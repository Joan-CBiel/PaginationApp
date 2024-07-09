package domain;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import exceptions.*;

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
        int numPages = text.length()/(Pagination.MAX_CHARS_PER_LINE*Pagination.MAX_LINES_PER_PAGE) +1 ;
        String[] finalText = new String[numPages];
        String[] words = text.split(" ");
        int charCount = 0;
        int lineCount = 1; 
        int page = 0;
        StringBuilder pageText = new StringBuilder();
        for(String word : words ) {

            if(charCount + word.length() > Pagination.MAX_CHARS_PER_LINE) {
                pageText.append("\n");    //add endline
                charCount = 0;  //reset count
                ++lineCount;    //next line

                if(lineCount> Pagination.MAX_LINES_PER_PAGE) {
                    lineCount = 1;  //reset count
                    finalText[page] = pageText.toString();
                    ++page;     //next page
                    pageText = new StringBuilder();
                }
            }

            pageText.append(word).append(" ");  //add word followed by a whitespace
            charCount += word.length() +1;
        }
        pageText.append("\n"); //end file
        finalText[page] = pageText.toString(); //safe last page

        return finalText;

    }
    
    /**
     * Reads the file and returns it's content.
     * 
     * @param file - adress of file to read.
     * 
     * @return String of the content in {@code file}.
     *
     */
    private String readDocument(String file) throws RuntimeException, NotOneLineTextException {
        StringBuilder text= new StringBuilder();
        try {
            FileInputStream fis = new FileInputStream(file);
    
            System.setIn(fis);

            Scanner scanner = new Scanner(System.in);
            int lineCount =0;
            while (scanner.hasNextLine()) {
                if (lineCount > 0) {
                    scanner.close();
                    fis.close();
                    throw new NotOneLineTextException("The document contains more than one line.");
                }
                text.append(scanner.nextLine());
                lineCount++;
            }

            fis.close();
            scanner.close();
        } catch (IOException e  ) {
            throw new RuntimeException(e);
        } catch (NotOneLineTextException e) {
            throw e;
        }

        return text.toString();
    }

    /**
     * Creats the file or replace the content of the given Pdf document with the given paginated text
     *
     * @param docPath - txt document Path
     * @param text - Paginated text
     */
    private void writeDocumentTxt(String docPath, String[] text) throws RuntimeException {
        try (FileWriter fileWriter = new FileWriter(docPath)) {
            for (int i = 0; i < text.length; i++) {
                fileWriter.write(text[i] + System.lineSeparator());
                fileWriter.write("--End page " + (i + 1) + "--" + System.lineSeparator());
                fileWriter.write(System.lineSeparator()); // Add an extra line separator between pages
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creats the file or replace the content of the given Txt document with the given paginated text.
     * 
     * @param docPath - Name of the document.
     * 
     * @param text - Paginated text.
     */
    private void writeDocumentPdf(String docPath, String[] text) throws RuntimeException {

        // Create a new PDF document
        try {
            PDDocument document = new PDDocument();

            // Create pages with content
            for (int i = 0; i < text.length; i++) {
                PDPage page = new PDPage(PDRectangle.A4);
                document.addPage(page);
                addContent(document, page, text[i]);
                addPageNumber(document, page, i + 1);
            }
            document.save(docPath);
            document.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Writes the content on a page in the document {@code document}
     * @param document - Pdf document
     * @param page - PDPage
     * @param content - Content of the page
     * @throws IOException - External library possible exception
     */
    private static void addContent(PDDocument document, PDPage page, String content) throws IOException {
        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            // Split the content by lines
            String[] lines = content.split("\n");
            float yPosition = 750; // Starting y position

            for (String line : lines) {
                contentStream.beginText();
                contentStream.newLineAtOffset(50, yPosition); // Adjust the starting position as needed
                contentStream.showText(line);
                contentStream.endText();
                yPosition -= 15; // Move to the next line (adjust as needed)
            }
        }
    }

    /**
     * Adds the page number bellow the page.
     *
     * @param document - Pdf document
     * @param page - PDPage
     * @param pageNumber - Number of the page in the document
     * @throws IOException - External library possible exception
     */
    private static void addPageNumber(PDDocument document, PDPage page, int pageNumber) throws IOException {
        try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(550, 9); // Adjust position as needed
            contentStream.showText(String.valueOf(pageNumber));
            contentStream.endText();
        }
    }

    /**
     * Main function of the class:
     * Paginates the given input Document.txt, that contains a one line text, 
     * creates or replace a document with name stored in {@code exportDoc}
     * and writes the result on it.
     * 
     * @param inputDocTxt - Input Document with one line text.
     * @param exportDoc - Export Document to write the pagination at.
     * 
     */
    public void paginateDoc(String inputDocTxt, String exportDoc) throws RuntimeException, NotOneLineTextException{
        //Read the input document
        String text="";
        text = this.readDocument(inputDocTxt);
        //Paginate the text which will be safed in the FinalText Variable
        String[] paginatedText = this.paginateText(text);

        if(exportDoc.endsWith(".txt"))
            this.writeDocumentTxt(exportDoc, paginatedText);
        else
            this.writeDocumentPdf(exportDoc,paginatedText);
        
    }


    
}

