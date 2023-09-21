package com.YASinger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;


public class Bencoding {
    private byte[] fileData;                //torrent源文件
    private int length;                     //torrent文件大小
    private int offset;                     //文本指针偏移量
    private Map<String, Object> torrentFile;//Bencoding解码后得到的字典
    private int startInfo;                  //hash原文起始地址
    private int endInfo;                    //hash原文结束地址

    public Bencoding(String filePath) throws IOException {
        readFile(filePath);
        String name = filePath.substring(filePath.lastIndexOf("/")+1);
        offset = -1;
        torrentFile = new HashMap<>();
        torrentFile.put(name,readMap().get(null));
    }

    //读取torrent文件
    private void readFile(String file) throws IOException {
        File thisFile = new File(file);
        if(!thisFile.exists()){
            throw new FileNotFoundException(file);
        }
        FileInputStream inputStream = new FileInputStream(thisFile);
        length = inputStream.available();
        fileData = new byte[length];
        inputStream.read(fileData);
        inputStream.close();
    }

    //解码字符串 字符串长度:字符串原文
    private String readString(){
        int length = 0;
        String string = "";
        while (fileData[offset] != ':') {
            length = length * 10 + fileData[offset] - '0';
            offset++;
        }
        offset++;
        while (length > 0) {
            string = string + (char)fileData[offset];
            length--;
            offset++;
        }
        return string;
    }

    //解码整型 i整型e
    private String readInt(){
        offset++;
        String string = "";
        while (fileData[offset] != 'e') {
            string = string + (char)fileData[offset];
            offset++;
        }
        offset++;
        return string;
    }

    //解码列表类型 l列表e
    private List<Object> readList(){
        offset++;
        List<Object> list =new ArrayList<>();
        while(fileData[offset] != 'e'){
            switch (fileData[offset]){
                case 'l':
                    list.add(readList());
                    break;
                case 'd':
                    list.add(readMap());
                    break;
                case 'i':
                    list.add(readInt());
                    break;
                case '9':
                case '8':
                case '7':
                case '6':
                case '5':
                case '4':
                case '3':
                case '2':
                case '1':
                case '0':
                    list.add(readString());
                    break;
            }
        }
        offset++;
        return list;
    }

    //解码字典类型 d字典e
    private Map<String, Object> readMap(){
        offset++;
        Map<String, Object> map = new HashMap<>();
        // key为null时，字符串为键，否则为值
        String key = null;
        // 读取到第一个'e'为止
        while(offset != length && fileData[offset] != 'e') {
            switch (fileData[offset]) {
                case 'l' :
                    map.put(key, readList());
                    key = null;
                    break;
                case 'd' :
                    if(key != null && key.equals("info")) startInfo = offset;
                    map.put(key, readMap());
                    if(key != null && key.equals("info")) endInfo = offset;
                    key = null;
                    break;
                case 'i' :
                    map.put(key, readInt());
                    key = null;
                    break;
                case '9':
                case '8':
                case '7':
                case '6':
                case '5':
                case '4':
                case '3':
                case '2':
                case '1':
                case '0':
                    String data = readString();
                    // key为null时，字符串为键，否则为值
                    if (key == null) {
                        key = data;
                    } else {
                        map.put(key, data);
                        key = null;
                    }
                break;
            }
        }
        offset++;
        return map;
    }

    public Map<String, Object> getTorrentMap() {
        return torrentFile;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public int getStartInfo() {
        return startInfo;
    }

    public int getEndInfo() {
        return endInfo;
    }

    @Override
    public String toString() {
        return torrentFile.toString();
    }
}
