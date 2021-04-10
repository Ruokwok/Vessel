package cc.ruok.vessel;

interface Handler {

    /**
     * 进程打印内容时会调用此方法
     * @param str 打印的内容
     */
    void printed(String str);

}
