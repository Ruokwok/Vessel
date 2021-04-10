package cc.ruok.vessel;

import java.io.*;

public class Vessel {

    private String command;
    private File shell;
    private String charset = "utf8";
    private Handler handler;
    private File path;

    private Process process;
    final private BufferedWriter[] bw = {null};
    final private DataOutputStream[] out = {null};

    public Vessel() {}

    public Vessel(Handler handler) {
        this();
        this.handler = handler;
    }

    /**
     * 设置执行的命令
     * @param command 命令
     */
    public void setCommand(String command) {
        this.command = command;
    }

    public String getCharset() {
        return charset;
    }

    /**
     * 设置运行使用的编码格式
     * @param charset 编码
     */
    public void setCharset(String charset) {
        this.charset = charset;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    /**
     * 执行进程的运行路径
     * @param dir 文件夹
     */
    public void setPath(File dir) {
        this.path = dir;
    }

    /**
     * 设置执行的脚本文件
     * @param file 文件
     */
    public void setShell(File file) {
        this.shell = file;
    }

    public void run() {
        try {
            process = Runtime.getRuntime().exec((shell != null && shell.exists())? shell.getName() :command, null, path);
            bw[0] = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
            read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void read() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), charset));
            String cmdout = new String();
            while ((cmdout = br.readLine()) != null) {
                handler.printed(cmdout);
            }
        } catch (IOException e) { }
    }

    /**
     * 向进程键入内容
     * @param string 输入的内容
     * @throws IOException
     */
    public void input(String string) throws IOException {
        bw[0].write(string);
        bw[0].newLine();
        bw[0].flush();
    }

    public void close() {
        process.destroy();
    }
}
