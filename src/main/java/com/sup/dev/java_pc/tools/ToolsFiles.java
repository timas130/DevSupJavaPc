package com.sup.dev.java_pc.tools;

import com.sup.dev.java.libs.debug.Debug;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ToolsFiles {

    public static BufferedReader br(String path) throws FileNotFoundException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(path), Charset.forName("UTF8")));
    }

    public static BufferedWriter bw(String path) throws FileNotFoundException {
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), Charset.forName("UTF8")));
    }

    public static String readLine(String path, long l) {
        BufferedReader br = null;
        try {
            br = br(path);
            while (l-- != 0) br.readLine();

            return br.readLine();

        } catch (Exception e) {
            if(e instanceof FileNotFoundException)
                Debug.log("file ["+(new File(path).getAbsolutePath())+"]");
            throw new RuntimeException(e);
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException ignored) {

            }
        }
    }

    public static List<String> readList(String path) {
        List<String> list = new ArrayList<>();
        BufferedReader br = null;
        try {

            br = br(path);
            while (br.ready())
                list.add(br.readLine());

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException ignored) {

            }
        }

        return list;
    }

    public static String readString(String path) {
        StringBuilder s = new StringBuilder();
        BufferedReader br = null;
        try {

            br = br(path);
            while (br.ready())
                s.append(br.readLine());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }  finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException ignored) {

            }
        }

        return s.toString();
    }


    // pos: -1 to end
    public static void put(String path, long pos, String value){

        String tmpFileName = path+"x"+System.currentTimeMillis();

        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            br = br(path);
            bw = bw(tmpFileName);

            boolean added = false;
            String line;
            int p = 0;
            while ((line = br.readLine()) != null) {
                if (p++ == pos) {
                    line = value;
                    added= true;
                }
                bw.write(line+"\n");
            }

            if(!added)
                bw.write(value+"\n");

        } catch (Exception e) {
           throw new RuntimeException(e);
        } finally {
            try {
                if(br != null)
                    br.close();
            } catch (IOException ignored) {

            }
            try {
                if(bw != null)
                    bw.close();
            } catch (IOException ignored) {

            }
        }

        File oldFile = new File(path);
        oldFile.delete();


        File newFile = new File(tmpFileName);
        newFile.renameTo(oldFile);
    }

    public static byte[] readFile(String patch) {
        try {
            return Files.readAllBytes(Paths.get(patch));
        } catch (Exception e) {
            Debug.log("file patch: " + new File(patch).getAbsolutePath());
            throw new RuntimeException(e);
        }
    }

    public static void copyFile(String source, String dest) throws IOException {
        copyFile(new File(source), new File(dest));
    }

    public static void copyFile(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }

}
