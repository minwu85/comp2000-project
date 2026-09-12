import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

// Loads a background picture from the asserts folder. Different ways of
// running the app (a terminal in the project folder, a terminal in bin, an
// IDE's run button) start with different working directories, so this tries
// each likely one in turn. A missing or unreadable picture just means no
// background instead of a crash.
public class Assets {

    private static final String[] SEARCH_FOLDERS = {
        "asserts/",
        "bin/asserts/",
        "../asserts/",
        "COMP2000 project/asserts/",
        "COMP2000 project/bin/asserts/"
    };

    public static Image load(String fileName) {
        for (String folder : SEARCH_FOLDERS) {
            File file = new File(folder + fileName);
            if (file.exists()) {
                try {
                    return ImageIO.read(file);
                } catch (IOException ex) {
                    System.out.println("Could not read " + file.getPath() + ": " + ex.getMessage());
                }
            }
        }
        System.out.println("Could not find " + fileName + " in any asserts folder.");
        return null;
    }
}
