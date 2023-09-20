import org.example.Bencoding;
import org.example.File;
import org.example.TorrentFile;
import org.junit.Test;

public class test {
    @Test
    public void test1() throws Exception {
        Bencoding bencoding = new Bencoding("G:/文件/Hanamonogatari1080p.torrent");
        TorrentFile torrentFile = new TorrentFile(bencoding);
//        System.out.println(torrentFile.getFileName());
//        System.out.println(torrentFile.getName());
//        System.out.println(torrentFile.getComment());
//        System.out.println(torrentFile.getFlag());
//        System.out.println(torrentFile.getCreatBy());
//        System.out.println(torrentFile.getLength());
//        System.out.println(torrentFile.getFiles());
        System.out.println(torrentFile.getAnnounceList());
        System.out.println(torrentFile.getAnnounceL());

    }
}
