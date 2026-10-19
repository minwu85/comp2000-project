import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;

// Loads a background picture from the asserts folder. A terminal, an IDE run
// button and a different folder all start with a different working directory,
// so each likely folder is tried in turn. A missing or unreadable picture just
// means no background instead of a crash.
public class Assets {

    private static final String[] SEARCH_FOLDERS = {
        "asserts/",
        "bin/asserts/",
        "../asserts/",
        "COMP2000 project/asserts/",
        "COMP2000 project/bin/asserts/"
    };

    public static Image load(String fileName) {
        File file = Arrays.stream(SEARCH_FOLDERS)
                .map(folder -> new File(folder + fileName))
                .filter(File::exists)
                .findFirst()
                .orElse(null);

        if (file == null) {
            System.out.println("Could not find " + fileName + " in any asserts folder.");
            return null;
        }
        try {
            return ImageIO.read(file);
        } catch (IOException ex) {
            System.out.println("Could not read " + file.getPath() + ": " + ex.getMessage());
            return null;
        }
    }
}
