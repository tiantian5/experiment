package com.experiment.core.service.syncDutyChain;

/**
 * Hello world!
 *
 */
public class App{

    static IRequestProcessor requestProcessor;

    public void setUp(){
        PrintProcessor printProcessor=new PrintProcessor();
        printProcessor.start();
        SaveProcessor saveProcessor=new SaveProcessor(printProcessor);
        saveProcessor.start();

        requestProcessor=new PrevProcessor(saveProcessor);
        ((PrevProcessor) requestProcessor).start();
    }



    public static void main(String[] args) {
//        App app=new App();
//        app.setUp();
//        Request request=new Request();
//        request.setName("Mic");
//        requestProcessor.process(request);
        System.out.println(1658892996233L - System.currentTimeMillis());
        System.out.println(1000 * 60 * 60 * 24);
        long endTime = (long) (0.2 * 60 * 60 * 1000L) / 6;
        System.out.println(endTime);
    }

}
