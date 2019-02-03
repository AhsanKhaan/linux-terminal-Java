/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package terminal;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.nio.file.Files;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Ahsan Khan
 */
public class Terminal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here

        while (true) {
            Terminal t = new Terminal();
            Scanner sc = new Scanner(System.in);
            t.checkCommand(sc.nextLine());
            // System.out.println(sc.nextLine());
            if (sc.equals("")) {
                break;
            }

        }
    }
    private ArrayList<String> list;

    Terminal() {
        this.list = new ArrayList<>();
        this.Init();
    }

    public void setList(ArrayList<String> li) {
        this.list = li;
    }

    public ArrayList<String> getlist() {
        return this.list;
    }

    public void Init() {
        System.out.print(System.getProperty("user.dir")+">");
//        Path currentRelativePath = Paths.get("");
//        String curr_path = currentRelativePath.toAbsolutePath().toString();
//        System.out.print(curr_path + ">");

    }

    void checkCommand(String line) throws IOException {
        this.setList(this.tokenSeprator(line));
        int i = 0;
        if (this.getlist().get(i).equals("")) {
            System.out.println("enter command first");
        } else if (this.getlist().get(i).equals("pwd")) {
            this.pwd();

        } else if (this.getlist().get(i).equals("exit")) {
            System.exit(0);
        } else if (this.getlist().get(i).equals("ls")) {
            this.ls();

        } else if (this.getlist().get(i).equals("touch")) {

            for (int j = 1; (this.getlist().size() != 1) && (j < this.getlist().size()); j++) {
                if (this.getlist().get(j).equals(".")) {
                    System.out.println("touch: cannot create file \'.\':File Exist");
                } else if (this.getlist().size() > 1) {
                    this.touch(this.getlist().get(j));
                }

            }
            if (this.getlist().size() == 1) {
                System.out.println("touch:missing file operand");
            }

        } else if (this.getlist().get(i).equals("mkdir")) {
            for (int j = 1; (this.getlist().size() != 1) && (j < this.getlist().size()); j++) {
                if (this.getlist().get(j).equals(".")) {
                    System.out.println("mkdir: cannot create file \'.\':Directory Exist");
                } else if (this.getlist().size() > 1) {
                    this.mkdir(this.getlist().get(j));
                }

            }
            if (this.getlist().size() == 1) {
                System.out.println("mkdir:missing file operand");
            }

        } else if (this.getlist().get(i).equals("rmdir")) {
            for (int j = 1; (this.getlist().size() != 1) && (j < this.getlist().size()); j++) {
                if (this.getlist().get(j).equals(".")) {
                    System.out.println("rmdir: failed to remove \'.\':Invalid Arguments");
                } else if (this.getlist().size() > 1) {
                    this.rmdir(this.getlist().get(j));
                }

            }
            if (this.getlist().size() == 1) {
                System.out.println("rmdir:missing operand");
            }
        } else if (this.getlist().get(i).equals("rm")) {
            for (int j = 1; (this.getlist().size() != 1) && (j < this.getlist().size()); j++) {
                if (this.getlist().get(j).equals(".")) {
                    System.out.println("rmdir: failed to remove \'.\':Invalid Arguments");
                } else if (this.getlist().size() > 1) {
                    if (this.getlist().get(j).equals("-r")) {
                        j++;
                        this.rm(this.getlist().get(j), "directory");

                    } else {
                        this.rm(this.getlist().get(j), "file");
                    }

                }
            }//end for
            if (this.getlist().size() == 1) {
                System.out.println("rm:missing operand");
            }
        } else if (this.getlist().get(i).equals("cd")) {
            int j = 1;
            if (j == 1) {
                if ((this.getlist().get(j).equals("./")) || (this.getlist().get(j).equals("."))) {
                    //      System.out.println("i am in . ./");
                }else if(this.getlist().get(j).equals("..")){
                    this.cd_dot_dot();
                } else {
                    this.cd(this.getlist().get(j));
                }
            }
            if (this.getlist().size() == 1) {
                System.out.println("i am already in current directory");
            }
        } else {
            System.out.println(this.getlist().get(i) + ": command not found");
        }

    }

    void pwd() {
     
        String curr_path=System.getProperty("user.dir");
      //  Path currentRelativePath = Paths.get("");
     //   String curr_path = currentRelativePath.toAbsolutePath().toString();
        System.out.println(curr_path);
    }

    void ls() throws IOException {
       // File f=new File(this.getCurrentDirectory());
        Files.list(new File(this.getCurrentDirectory()).toPath())
                .limit(100000)
                .forEach(path -> {
                    String[] str = path.toString().split("\\\\");
                    //System.out.println(str.length);
                    String dir = str[str.length - 1];
                    // System.out.println(str[str.length-1]);
                    File f=new File(this.getCurrentDirectory()+"\\"+dir);
                    if(f.isDirectory()){
                        System.out.print(dir + "\\" + "\t");
                    }else if(f.isFile()){
                        System.out.print(dir + "\t");
                    }
                    
//                    
//                    if (dir.contains(".")) {
//                        System.out.print(dir + "\t");
//                    } else {
//                        System.out.print(dir + "\\" + "\t");
//                    }
                    //System.out.println(path);
                });
        System.out.println();
    }

    String getCurrentDirectory() {
       String curr_path=System.getProperty("user.dir");
       //Path currentRelativePath = Paths.get("");
      //  String curr_path = currentRelativePath.toAbsolutePath().toString();
        return curr_path;
    }

    private void touch(String str) {
        //ArrayList<String> list = this.tokenSeprator(line);
        //loop for creating files
        String temp;
        if (str.equals("mkdir")) {

        } else {

            temp = this.getCurrentDirectory().replace("\\", "//");
            this.createFile(temp + "//" + str, "file",str);
        }

    }

    public boolean deleteFile(String name, String type) {
        if (type.equals("file")) {
            File file = new File(name);
            boolean isDeleted = file.delete();
            if (isDeleted) {
                System.out.println("File SuccessFully Deleted");
                return true;
            } else if (!file.exists()) {
                System.out.println("File don't exist");

            } else {
                System.out.println("Can't delete a file");

            }
            //for directory  
        } else if (type.equals("directory")) {
            File dir = new File(name);
            //System.out.println("rmdir:failed to remove\'"+this.getNameFormPath(name)+"\':No such a file or directory");

            String[] file_list = dir.list();
            if (file_list == null) {
                if (dir.isFile()) {
                    System.out.println("rmdir:failed to remove\'" + this.getNameFormPath(name) + "\'Not a directory");

                } else if (!dir.exists()) {
                    System.out.println("rmdir:failed to remove\'" + this.getNameFormPath(name) + "\':No such a file or directory");
                }
            } else {
                		File[] listFiles = dir.listFiles();
		for(File file : listFiles){
                    
			System.out.println("Deleting "+file.getName());
			file.delete();
		}
                //now directory is empty, so we can delete it
                System.out.println("Deleting Directory. Success = " + dir.delete());
//                for (int i = 0; i < file_list.length; i++) {
//                    String temp = this.getCurrentDirectory().replace("\\", "//");
//                    File file_obj = new File(temp + "//" + file_list[i]);
//                    file_obj.delete();
//
//                }

            }

        }
        return false;
    }

    public boolean createFile(String name, String type,String fname) {
        if ("file".equals(type)) {
            try {
                //String temp=this.getCurrentDirectory().replace("\\","//" );
                //File file = new File(temp + "//" + dir_name);
                File file = new File(name);
                /*If file gets created then the createNewFile() 
	      * method would return true or if the file is 
	      * already present it would return false
                 */
                boolean fvar = file.createNewFile();
                if (fvar) {
                    System.out.println("File has been created successfully");
                    return true;
                } else if (file.isDirectory()) {
                    System.out.println("Error!can't create File :directory Exist");

                    return true;
                } else {
                    System.out.println("file already present at specified location");
                    return false;
                }
            } catch (IOException e) {
                System.out.println("Exception Occurred:");
                e.printStackTrace();
            }

        } else if ("directory".equals(type)) {
            File theDir = new File(name);

// if the directory does not exist, create it
            if (!theDir.exists()) {
//                System.out.println("creating directory: " + theDir.getName());
                boolean result = false;

                try {
                    theDir.mkdir();
                    result = true;
                } catch (SecurityException se) {
                    //handle it
                }
                if (result) {
                    System.out.println("DIR created");
                }
            }else{
                System.out.println("mkdir:can't create Directory "+fname+":Directory Exist");
            }
        }
        return false;
    }

    public ArrayList<String> tokenSeprator(String line) {
        String temp = "";
        ArrayList<String> list = new ArrayList<>();
        //loop for putting values in list    
        for (int i = 0; i < line.length(); i++) {

            if (line.charAt(i) == ' ') {
                list.add(temp);
                temp = "";
                // str_Arr[index]=temp;
            } else {
                temp += line.charAt(i);
            }
        }
        list.add(temp);
        return list;
    }

    private void mkdir(String name) {
        //ArrayList<String> list = this.tokenSeprator(line);
        //loop for creating files
        String temp;

        temp = this.getCurrentDirectory().replace("\\", "//");
        this.createFile(temp + "//" + name, "directory",name);

    }

    private void rmdir(String name) {
        String temp;

        temp = this.getCurrentDirectory().replace("\\", "//");
        this.deleteFile(temp + "//" + name, "directory");

    }

    public String getNameFormPath(String path) {
        String[] temp;
        temp = path.split("//");
        //System.out.println(temp[temp.length - 1]);
        return temp[temp.length - 1];

    }

    private void rm(String name, String type) {
        String temp;
        temp = this.getCurrentDirectory().replace("\\", "//");
        if (type.equals("file")) {
            this.deleteFile(temp + "//" + name, "file");
        } else if (type.equals("directory")) {
            this.deleteFile(temp + "//" + name, "directory");
        }
    }

    private boolean cd(String path) {
        for (int i = 0; i < path.length(); i++) {
            String temp="";
            if (path.charAt(i) == '.') {
                i++;
                if (path.charAt(i) == '/') {
                    
                    for (int j = i; j < path.length(); j++) {
                        temp+=path.charAt(j);
                    }
                    Pattern p=Pattern.compile("(/[A-z](([A-z]|[0-9]))*)(/[A-z](([A-z]|[0-9]))*)*");
                    Matcher m=p.matcher(temp);
                    File file=new File(".");
                    if(m.matches()){
                    //    System.out.println(""+file.getAbsolutePath()+"ah");
                       ArrayList<String> list=this.dir_seprator(path);
                       String temp_path;
                        for (int j = 1; j < list.size(); j++) {
                            temp_path=file.getAbsolutePath().replace(".", "");
                            temp_path=temp_path+list.get(j);
                    //        System.out.println(temp_path);
                            File tempf=new File(temp_path);
                            
                            if(tempf.exists()){
                                if(tempf.isDirectory()){
                                    //System.out.println("i am directory");
                                    System.setProperty("user.dir",temp_path);
                    //                System.out.println(System.getProperty("user.dir"));
                                    return true;
                                }else if(tempf.isFile()){
                                    System.out.println("cd:"+path+"\t:Not a Directory");
                                    return false;
                                }
                            }else{
                                System.out.println("cd:"+path+"No such a File or Directory");
                                
                                return false;
                            }
                        
                        }
                    
                       
                    }
                    
                } else {
                    System.out.println("cd:" + path + ":No such file or directory");
                    return false;
                }

            } else {
                System.out.println("cd:" + path + ":No such file or directory");
                return false;
            }

        }
                return false;
    }

    private ArrayList<String> dir_seprator(String path) {
        ArrayList<String> list=new ArrayList<>();
        String[] temp=path.split("/");
        for (int i = 0; i < temp.length; i++) {
            list.add(temp[i]);
        }
    return list; }

    private void cd_dot_dot() {
         String curr_path=System.getProperty("user.dir");
         String prev=this.goToPrev(curr_path);
         System.setProperty("user.dir",prev);
        //System.getProperty(string, string1);
    }

    private String goToPrev(String curr_path) {
        String[] temp=curr_path.split("\\\\");
        String prev_dir_name,prev_dir="";
        prev_dir_name=temp[temp.length-1];
        //loop for getting last directory
        for (int i = 0; i < temp.length-1; i++) {
            if(i==temp.length-2){
            prev_dir+=temp[i];
            }else{
            prev_dir+=temp[i]+"\\";
            }
            
        }//end for
        return prev_dir;
    }
    

}
