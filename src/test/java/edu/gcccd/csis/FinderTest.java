package edu.gcccd.csis;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class FinderTest {

    @Test
    public void extreme() throws IOException {
        Path d1 = Files.createTempDirectory("testDir");

        final File f1 = File.createTempFile( "test1_", ".tmp", d1.toFile());
        final File f2 = File.createTempFile("test2_", ".tmp");
        final File f3 = File.createTempFile( "test3_", ".tmp", d1.toFile());

        f1.deleteOnExit();
        f2.deleteOnExit();
        f3.deleteOnExit();

        assertEquals(Finder.extreme(f1, f2), f1); //f1: larger path length
        assertEquals(Finder.extreme(f1, f3), f3); //f3: found last (same path length)

    }

    @Test
    public void findExtremeFile() {
        final Path path = Paths.get(".").toAbsolutePath().getParent().normalize();
        assertEquals(Finder.findExtremeFile(path), BFS_finder(path));
    }

    public static File BFS_finder(Path p){
        File extremeFile = null;
        long extremeFileSize = 0;
        File f = p.toFile();
        List<File> files = new ArrayList<File>();
        List<File> newFiles = new ArrayList<File>();
        files.add(f);
        while (!files.isEmpty()){
            File file = files.remove(0);
            if(file.isFile()){
                newFiles.add(file);
            }
            else if (file.isDirectory()){
                File[] fs = file.listFiles();
                files.addAll(Arrays.asList(fs));
            }
        }
        for(File file : newFiles){
            long fileSize = file.length();
            if(fileSize > extremeFileSize){
                extremeFile = file;
                extremeFileSize = fileSize;
            }
            else if(fileSize == extremeFileSize){
                extremeFile = Finder.extreme(extremeFile, file);
            }
        }
        return extremeFile;
    }
}