package pingballGUI;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/*
 * Filter class for FileChooser so the user can only pick files that have a .pb extension.
 */

public class pbFilter extends FileFilter {
    
    @Override
    /**
     * accept - checks 
     * @param File f - the files to check for extension type.
     * @return true if the file name ends with the .pb extension, otherwise returs false
     */
    public boolean accept(File f) {
        if (f.getName().endsWith(".pb") || f.isDirectory()) {
            return true;
        }
        else {
            return false;
        }
    }
        
    /**
     * @return the description of files to choose from
     */
    @Override
    public String getDescription() {
        return "Pingball File";
    }

}
