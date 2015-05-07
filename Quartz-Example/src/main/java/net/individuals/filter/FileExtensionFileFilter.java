package net.individuals.filter;

import java.io.File;
import java.io.FileFilter;

/**
 * @author Barudisshu
 */
public class FileExtensionFileFilter implements FileFilter {

    private String extension;   //文件后缀

    public FileExtensionFileFilter(String extension) {
        this.extension = extension;
    }


    @Override
    public boolean accept(File pathname) {//只接受指定后缀的文件

        //如果file是个目录
        if(pathname.isDirectory()) return false;

        // Lowercase the filename for easier comparison
        String LCaseFilename = pathname.getName().toLowerCase();    //文件名转换为小写

        return (pathname.isFile() && (LCaseFilename.indexOf(extension) > 0));
    }
}
