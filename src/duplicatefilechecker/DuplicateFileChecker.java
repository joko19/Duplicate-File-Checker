/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package duplicatefilechecker;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
/**
 *
 * @author joko
 */
public class DuplicateFileChecker {

    /**
     * @param args the command line arguments
     * @throws java.security.NoSuchAlgorithmException
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {
        // Specify the path to the folder
        String folderPath = "/home/joko/NetBeansProjects/DuplicateFileChecker/src/resource";

        // Create a Path object for the folder
        Path folder = Paths.get(folderPath);
         
        // Use the Files.newDirectoryStream method to get a stream of files in the folder
        try (DirectoryStream<Path> paths = Files.newDirectoryStream(folder)) {
            Set<String> seen = new HashSet<>();    
            for (Path path : paths) {
//                if (Files.isDirectory(path)) continue;
                if (!seen.add(getHash(path))) {
                    Files.delete(path);
                }
            }
        } 
        catch (IOException e) {
            System.out.println("Terjadi kesalahan, silahkan periksa: " + e.getMessage());
        }
    }
    
    public static String getHash(Path path) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(Files.readAllBytes(path));
        return toHexadecimal(md.digest());
    }
 
    public static String toHexadecimal(byte[] bytes) {
        return IntStream.range(0, bytes.length)
        .mapToObj(i -> String.format("%02x", bytes[i]))
        .collect(Collectors.joining());
    }
}
