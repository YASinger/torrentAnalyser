package org.example;

import java.util.ArrayList;
import java.util.List;

public class File {
    private String name;        //文件名
    private String path;        //文件路径
    private String length;      //文件大小

    public File(List<String> path, String length){
        name = path.get(path.size()-1);
        path.remove(path.size()-1);

        this.path = "";
        path.forEach(paths->{
            this.path = this.path + paths + "/";
        });

        this.length = length;
    }

    public File(String name, String path, String length){
        this.name = name;
        this.path = path;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public String getPath(){
        return path;
    }

    public List<String> getPathL() {
        String pathS = path;
        List<String> pathL = new ArrayList<>();
        while (pathS.contains("/")) {
            pathL.add(pathS.substring(0,pathS.indexOf("/")));
            pathS = pathS.substring(pathS.indexOf("/")+1);
        }
        return pathL;
    }

    public String getLength() {
        return length;
    }

    @Override
    public String toString() {
        return "File{" +
                "name='" + name + '\'' +
                ", path=" + path +
                ", length='" + length + '\'' +
                '}';
    }
}
