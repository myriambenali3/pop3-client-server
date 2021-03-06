package poly.mailbox;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Mailbox {

    private String mailboxPath;
    private String personalMailbox;
    private String userName;

    private List<Mail> mails;

    public List<Mail> getMails(){
        return this.mails;
    }

    public Mailbox(String personalMailbox){
        this.personalMailbox = personalMailbox;
        this.init();
    }

    public Mailbox(String mailboxPath, String userName){
        this(mailboxPath + '/' + userName);
        this.mailboxPath = mailboxPath;
        this.userName = userName;
    }

    public void refresh() {
        init();
    }

    private void init(){
        this.mails = Mailbox.loadMailbox(this.personalMailbox);
    }

    public static List<Mail> loadMailbox(String path) {
        List<Mail> mails = new ArrayList<>();
        File folder = new File(path);
        if (!folder.exists())
            folder.mkdir();
        for (File file : folder.listFiles()) {
            mails.add(new Mail(Mailbox.readMail(file.getPath())));
        }
        return mails;
    }

    public static String readMail(String path) {
        StringBuilder contentFile = new StringBuilder();
        BufferedReader buffer = null;
        try {
            InputStream flux = new FileInputStream(path);
            InputStreamReader reader = new InputStreamReader(flux);
            buffer = new BufferedReader(reader);
            String line;
            while ((line = buffer.readLine()) != null) {
                contentFile.append(line).append("\n");
            }
            buffer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentFile.toString();
    }
}