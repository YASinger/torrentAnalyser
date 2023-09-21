# torrentAnalyser
一个torrent种子解析器，可解析出磁力链接、文件列表、文件总大小等信息。
调用方式
```
TorrentFile torrentFile = new TorrentFile("G:/文件/Hanamonogatari1080p.torrent");
System.out.println(torrentFile.getMagnet());          //磁力链接
System.out.println(torrentFile.getName());            //单文件:文件名 多文件:文件目录名
System.out.println(torrentFile.getFlag());            //单/多文件标志
System.out.println(torrentFile.getLength());          //文件总大小
System.out.println(torrentFile.getFiles());           //文件列表
System.out.println(torrentFile.getAnnounce());        //Tracker的url
System.out.println(torrentFile.getAnnounceList());    //备用Tracker的url列表
```
