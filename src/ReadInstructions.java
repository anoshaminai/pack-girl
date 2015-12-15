import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * Class for reading in an instructions file (or any file) and returning as a string
 * @author Main
 *
 */
public class ReadInstructions {

    String instructions;
            
    
    public ReadInstructions(String fileName) {
        try {
            File file = new File(fileName);
            Scanner in = new Scanner(file);
            int fileLength = (int)file.length();
            StringBuilder fileContents = new StringBuilder(fileLength);
            while (in.hasNextLine()) {
                fileContents.append(in.nextLine().trim() + "\n");
            }
            
            in.close();
            instructions = fileContents.toString();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getInstructions() {
        return instructions;
    }
    
    

}
