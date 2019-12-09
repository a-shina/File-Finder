package edu.gcccd.csis;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Finds the largest file using DFS.
 */
public class Finder {

    /**
     * If no start location is given, the we start the search in the current dir
     *
     * @param args {@link String}[] start location for the largest file search.
     */
    public static void main(final String[] args) {
        final Path path = Paths.get(args.length < 1 ? "." : args[0]).toAbsolutePath().normalize();
        final File ex = findExtremeFile(path);
        if (ex != null) {
            System.out.printf("Starting at : %s, the largest file was found here:\n%s\n its size is: %d\n",
                    path.toAbsolutePath().toString(),
                    ex.getAbsolutePath(),
                    ex.length());
        }
    }

    /**
     * Identifies the more extreem of two given files.
     * Modifying this method allows to search for other extreems, like smallest, oldest, etc.
     *
     * @param f1 {@link File} 1st file
     * @param f2 {@link File} 2nd file
     * @return {@link File} the more extreme of the two given files.
     */
    static File extreme(final File f1, final File f2) {
        File extremeFile = null;
        if (f1.getAbsolutePath().length() > f2.getAbsolutePath().length()) {
            extremeFile = f1;
        } else if (f1.getAbsolutePath().length() < f2.getAbsolutePath().length()) {
            extremeFile = f2;
        } else extremeFile = f2;
        return extremeFile;
    }

    /**
     * DFS for the most extreme file, starting the search at a given directory path.
     *
     * @param p {@link Path} path to a directory
     * @return {@link File} most extreme file in the given path
     */
    static File findExtremeFile(final Path p) {
        return findExtremeFile2(p.toFile(), null, 0);
    }

    static File findExtremeFile2(final File dir, final File EXTREME_FILE, final long EXTREME_FILE_SIZE) {
        File extremeFile = EXTREME_FILE;
        long extremeFileSize = EXTREME_FILE_SIZE;
        File x = null;
        final File[] fa = dir.listFiles();
        if (fa != null) {
            for (File file : fa) {
                if (file.isFile()) {
                    x = file;
                } else if (file.isDirectory()) {
                    x = findExtremeFile2(file, extremeFile, extremeFileSize);
                }
                if (x != null) {
                    long xLength = x.length();
                    if (xLength > extremeFileSize) {
                        extremeFile = x;
                        extremeFileSize = xLength;
                    } else if (xLength == extremeFileSize) {
                        extremeFile = extreme(extremeFile, x);
                    }
                }
            }
        }
        return extremeFile;
    }
}
