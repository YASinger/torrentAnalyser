import com.YASinger.TorrentFile;
import org.junit.Test;

public class test {
    @Test
    public void test1() throws Exception {
        TorrentFile torrentFile = new TorrentFile("G:/文件/Hanamonogatari1080p.torrent");
        System.out.println(torrentFile.getHash());
        System.out.println(torrentFile.getMagnet());
        System.out.println(torrentFile.getFileName());
        System.out.println(torrentFile.getName());
        System.out.println(torrentFile.getComment());
        System.out.println(torrentFile.getFlag());
        System.out.println(torrentFile.getCreatBy());
        System.out.println(torrentFile.getLength());
        System.out.println(torrentFile.getFiles());
        System.out.println(torrentFile.getAnnounce());
        System.out.println(torrentFile.getAnnounceList());
    }

}
