package Methods;

import PageRepository.HomePage;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.selenesedriver.ClickElement;

import java.io.*;
import java.net.URL;


public class PDFReader extends GenericMethod{
    public void BrowserPDFReader(String Url) {
        try {
            java.net.URL u = new URL(Url);
            InputStream input = u.openStream();
            BufferedInputStream Bufferstream = new BufferedInputStream(input);
            PDDocument doc = PDDocument.load(Bufferstream);
            int NumberOfPages = doc.getNumberOfPages();
            System.out.println(NumberOfPages);

            PDFTextStripper pdfStrip = new PDFTextStripper();
            pdfStrip.setStartPage(1);
            pdfStrip.getText(doc);
            String getPDFText = pdfStrip.getText(doc);
            System.out.println(getPDFText);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public String LocalPDFReader(String fileName) {
        PDFParser parser = null;
        COSDocument cosDoc = null;
        PDFTextStripper pdfStripper = null;
        PDDocument pdDoc = null;
        PDFParser pdfParser = null;
        String parsedText=null;
        try {
            System.out.println("Parsing text from PDF file " + fileName + "....");
            File f = new File(fileName);
            if (!f.isFile()) {
                System.out.println("File " + fileName + " does not exist.");
                return null;
            }

            try {
                parser = new PDFParser(new RandomAccessFile(f,"r"));
            } catch (Exception e) {
                System.out.println("Unable to open PDF Parser.");
                return null;
            }

            try {
                parser.parse();
                cosDoc = parser.getDocument();
                pdfStripper = new PDFTextStripper();
                pdDoc = new PDDocument(cosDoc);
                parsedText = pdfStripper.getText(pdDoc);
            } catch (Exception e) {
                System.out.println("An exception occured in parsing the PDF Document.");
                e.printStackTrace();
                try {
                    if (cosDoc != null) cosDoc.close();
                    if (pdDoc != null) pdDoc.close();
                } catch (Exception e1) {
                    e.printStackTrace();
                }
                return null;
            }
            System.out.println("Done.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
      return parsedText;
    }

    public boolean FileExitOrNot(String location)
    {
        boolean flag=false;
        int defaultdonwloadTime=60;
        try{
            File file=new File(location);
            for (int i=0;i<defaultdonwloadTime;i++) {
                if(file.exists()){
                    System.out.println("File is exist");
                    flag=true;
                     break;}
                else {
                    flag=false;
                    Thread.sleep(1000);
                }
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
       return flag;
    }

    public boolean DeleteFile(String location)
    {
        boolean flag=false;
        try{
            File file=new File(location);
                if(file.exists())
                {
                    file.delete();
                    System.out.println("File is deleted");
                    flag=true;
                }
                else {
                    flag=false;
                }
           }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return flag;
    }

    public boolean DownloadandVerifyCRMFile(String path)
    {
        boolean flag=false;
        try{
            ClickElement(HomePage.DownloadPDF);
            flag=FileExitOrNot(path);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return flag;
    }
}
