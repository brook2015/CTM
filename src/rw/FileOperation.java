package rw;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaokaibin on 16-2-13.
 */
public class FileOperation {

    public static boolean writeFile(String source, String encoding, File file) {
        boolean flag = false;
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(source.getBytes(encoding));
            outputStream.close();
            flag = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static List<String> readFileToList(File file) {
        try {
            ArrayList<String> result = new ArrayList<>();
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String lineString;
            while ((lineString = bufferedReader.readLine()) != null) {
                result.add(lineString);
            }
            bufferedReader.close();
            fileReader.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
