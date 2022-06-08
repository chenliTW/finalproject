package cpe_client.localjudge;

import javax.tools.Tool;
import javax.tools.*;
import java.io.*;
import java.util.concurrent.TimeUnit;

public class executer {

    public static String execute(String code,String language,String input) {
        /*
            回傳 執行結果
            args:
                (String) code-> 程式碼 ex "print('hello '+input())"
                (String) language-> 程式語言 ex "python" (有 "c++" "python" "java")
                (String) input-> 程式 stdin 輸入 ex "owo"
            return value:
                (string) -> 程式 stdout 輸出 ex "hello owo"
                            如果CE/RE則會回傳 "CE/RE\n"+error
        */


        if(language.equals("java")) {
            return executeJava(code,input);
        }else if(language.equals("python")){
            return executePython(code,input);
        }else if(language.equals("c++")){
            return executeCpp(code,input);
        }
        return "bad language";
    }

    private static String executeJava(String code,String input){
        String ret="";
        try {
            File file=new File("./output/cpeclient.java");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(code);
            fileWriter.close();

            Tool javac = ToolProvider.getSystemJavaCompiler();

            Process p;

            OutputStream err = new OutputStream() {
                private StringBuilder string = new StringBuilder();

                @Override
                public void write(int b) throws IOException {
                    this.string.append((char) b );
                }

                //Netbeans IDE automatically overrides this toString()
                public String toString() {
                    return this.string.toString();
                }
            };
            javac.run(null, null, err, "./output/cpeclient.java");

            if(err.toString().length()>0){
                return "CE/RE\n"+err.toString();
            }
            try {
                ProcessBuilder pb = new ProcessBuilder(new String[]{"java","-cp","./output","cpeclient"});
                p = pb.start();
            }catch (Exception e){
                return "CE/RE";
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));

            writer.write(input);
            writer.newLine();
            writer.close();

            String line;

            while ((line = reader.readLine()) != null) {
                ret+=line;
            }

            if(!p.waitFor(8, TimeUnit.SECONDS)) {
                p.destroy();
                ret="TLE";
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }

    private static String executePython(String code,String input){
        String ret="";
        try {
            File file=new File("./output/cpeclient.py");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(code);
            fileWriter.close();

            ProcessBuilder pb;
            if(System.getProperty("os.name").equals("Mac OS X")) {
                pb = new ProcessBuilder(new String[]{"python3", "output/cpeclient.py"});
            }else{
                pb = new ProcessBuilder(new String[]{"./executils/windows/python3/python.exe", "output/cpeclient.py"});
            }
            Process p = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
            BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            writer.write(input);
            writer.newLine();
            writer.close();

            String line;

            while ((line = reader.readLine()) != null) {
                ret+=line;
            }

            boolean haserror=false;

            while ((line = error.readLine()) != null) {
                haserror=true;
                ret+=line;
            }

            if(haserror){
                return "CE/RE\n"+ret;
            }

            if(!p.waitFor(8, TimeUnit.SECONDS)) {
                p.destroy();
                ret="TLE";
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }

    private static String executeCpp(String code,String input){
        String ret="";
        try {
            File file=new File("./output/cpeclient.cpp");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(code);
            fileWriter.close();

            ProcessBuilder pb;
            Process p;
            if(System.getProperty("os.name").equals("Mac OS X")) {
                pb = new ProcessBuilder(new String[]{"g++", "output/cpeclient.cpp", "-o", "output/cpeclient"});
                p = pb.start();
                p.waitFor();

                pb = new ProcessBuilder(new String[]{"./output/cpeclient"});
                p = pb.start();
            }else{

                try {
                    pb = new ProcessBuilder(new String[]{"cmd.exe","/c","del", ".\\output\\cpeclient.exe"});
                    p = pb.start();
                    p.waitFor();
                }catch(Exception e){

                }
                pb = new ProcessBuilder(new String[]{".\\executils\\windows\\MinGW64\\bin\\g++.exe","-std=c++14" ,"output\\cpeclient.cpp","-o","output\\cpeclient.exe"});
                p = pb.start();
                p.waitFor();

                BufferedReader compilereader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line,compileMsg = "";
                while ((line = compilereader.readLine()) != null) {
                    compileMsg+=line;
                }

                try {
                    pb = new ProcessBuilder(new String[]{"./output/cpeclient.exe"});
                    p = pb.start();
                }catch (Exception e){
                    return "CE/RE\n"+compileMsg;
                }
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));

            writer.write(input);
            writer.newLine();
            writer.close();
            String line;
            while ((line = reader.readLine()) != null) {
                ret+=line;
            }

            if(!p.waitFor(8, TimeUnit.SECONDS)) {
                p.destroy();
                ret="TLE";
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }

}
