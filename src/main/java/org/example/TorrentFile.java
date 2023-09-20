package org.example;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TorrentFile {
    private String fileName;            //源文件名
    private String name;                //单文件:文件名 多文件:文件目录名
    private String flag;                //单/多文件标志
    private Long Length;                //文件总大小
    private Long pieceLength;           //文件块大小
    private List<File> files;           //每个文件的名字，路径，大小
    private String hash;                //磁力链接
    private String creatBy;             //创建人或创建程序信息
    private String comment;             //备注
    private String announce;            //Tracker的url
    private String announceList;  //备用Tracker的url
    //private String pieces;              //每个文件块sha1的集成Hash

    public TorrentFile(Bencoding bencoding) {
        bencoding.getTorrentMap().forEach((key,value)->{
            this.fileName = key;
            analyze((Map<String, Object>) value);
        });
        countHash(bencoding.getFileData(),bencoding.getStartInfo(),bencoding.getEndInfo());
    }

    private void analyze(Map<String, Object> torrentFile){
        announce = (String) torrentFile.get("announce");
        announceList = "";
        List<List<String>> announceListL = (List<List<String>>) torrentFile.get("announce-list");
        if (announceListL != null && !announceListL.isEmpty()) {
            announceListL.forEach(announceL->{
                announceList = announceList + announceL.get(0) + " ";
            });
        }

        comment = (String) torrentFile.get("comment");
        creatBy = (String) torrentFile.get("creat by");

        Map<String,Object> info = (Map<String, Object>) torrentFile.get("info");
        //pieces = (String) info.get("pieces");
        name = (String) info.get("name");
        pieceLength = Long.parseLong((String) info.get("piece length"));

        List<Map<String,Object>> filesMap = (List<Map<String, Object>>) info.get("files");
        if (filesMap == null || filesMap.isEmpty()) {
            Length = Long.parseLong((String) info.get("length"));
            flag = "单文件";
            return;
        } else {
            flag = "多文件";
        }
        Length = 0L;
        files = new ArrayList<>();
        filesMap.forEach(fileMap->{
            Length += Long.parseLong((String) fileMap.get("length"));
            files.add(new File((List<String>)fileMap.get("path"), (String) fileMap.get("length")));
        });
    }

    //根据info值计算sha1值即磁力链接
    private void countHash(byte[] fileData, int startInfo, int endInfo) {
        byte[] infoByte = new byte[endInfo - startInfo];
        System.arraycopy(fileData, startInfo, infoByte, 0, endInfo - startInfo);
        hash = getSha1(infoByte);
    }

    public String getFileName() {
        return fileName;
    }

    public String getComment() {
        return comment;
    }

    public String getHash() {
        return hash;
    }

    public String getFlag() {
        return flag;
    }

    public String getAnnounce() {
        return announce;
    }

    public String getAnnounceList() {
        return announceList;
    }

    public List<String> getAnnounceL() {
        String announceS = announceList;
        List<String> announceL = new ArrayList<>();
        while (announceS.contains("/")) {
            announceL.add(announceS.substring(0,announceS.indexOf(" ")));
            announceS = announceS.substring(announceS.indexOf(" ")+1);
        }
        return announceL;
    }

    public String getCreatBy() {
        return creatBy;
    }

//    public String getPieces() {
//        return pieces;
//    }

    public String getName() {
        return name;
    }

    public Long getPieceLength() {
        return pieceLength;
    }

    public List<File> getFiles() {
        return files;
    }

    public Long getLength() {
        return Length;
    }

    //计算sha1
    public static String getSha1(byte[] Byte) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(Byte);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return "TorrentFile{" +
                "name='" + name + '\'' +
                '}';
    }
}
