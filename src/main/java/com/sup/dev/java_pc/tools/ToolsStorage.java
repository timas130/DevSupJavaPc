package com.sup.dev.java_pc.tools;

import java.io.*;

public class ToolsStorage {

    public static String readString(String file) throws IOException {
        BufferedReader br = ToolsFiles.br(file);

        StringBuilder s = new StringBuilder();
        while (br.ready())
            s.append(br.readLine());

        return s.toString();
    }


    public static void write(String s, String file) {

        try {
            File f = new File(file);
            if (!f.exists()) {
                if (f.getParentFile() != null)
                    f.getParentFile().mkdirs();
                f.createNewFile();
            }

            BufferedWriter bw = ToolsFiles.bw(file);
            bw.write(s.toString());
            bw.flush();
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
