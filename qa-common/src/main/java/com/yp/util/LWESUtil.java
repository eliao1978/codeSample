package com.yp.util;

import com.atti.lwes.emitter.YPMulticastEmitter;
import com.atti.lwes.event.monitor.YPMonInfo;
import org.apache.log4j.Logger;
import org.lwes.Event;
import org.lwes.EventSystemException;
import org.lwes.listener.DatagramEventListener;
import org.lwes.listener.EventPrintingHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

public class LWESUtil {
    private YPMulticastEmitter emitter;
    private HTTPUtil httpService = new HTTPUtil();
    private Logger logger = new LogUtil().getLogger();

    public StringBuilder getLWESEvent(final String url) throws IOException, InterruptedException {
        final StringBuilder eventLog = new StringBuilder();

        // lwes filter listener thread
        String command = System.getProperty("lwes.filter.listener")
                + " -m " + System.getProperty("lwes.multicast.monitor.address")
                + " -p " + System.getProperty("lwes.multicast.monitor.port")
                + " -a " + System.getProperty("lwes.filter");
        logger.debug("lwes command " + command);
        final Process p = Runtime.getRuntime().exec(command);
        Thread printingThread = new Thread(new Runnable() {
            public void run() {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        eventLog.append(line);
                    }
                } catch (Exception ex) {
                    logger.error(ex.getStackTrace());
                }
            }

        });
        printingThread.start();
        Thread.sleep(1000);

        // firefly request thread
        Thread requestThread = new Thread(new Runnable() {
            public void run() {
                try {
                    httpService.getResponse(url);
                } catch (IOException io) {
                    logger.error(io.getStackTrace());
                }
            }
        });
        requestThread.start();
        Thread.sleep(3000);

        // kill filter listener parent and child process
        Runtime.getRuntime().exec("../qa-common/src/main/resources/kill-cmd.sh");
        requestThread.stop();
        printingThread.stop();

        logger.debug("LWES event => " + eventLog);
        return eventLog;
    }

    public void init(String address, String port, String applicationId) {
        try {
            emitter = new YPMulticastEmitter();
            emitter.setMulticastAddress(InetAddress.getByName(address));
            emitter.setMulticastPort(Integer.parseInt(port));
            emitter.setEmitHeartbeat(true);
            emitter.setApplicationId(applicationId);
            emitter.initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void emit(Map<String, String> attributeMap) {
        emit(YPMonInfo.class, attributeMap);
    }

    public void emit(Class<?> eventClass, Map<String, String> attributeMap) {
        try {
            Event event = createEvent(attributeMap, eventClass);
            emitter.emit(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Event createEvent(Map<String, String> attributeMap, Class<?> eventClass) throws Exception {
        Event event = (Event) eventClass.newInstance();
        for (String key : attributeMap.keySet()) {
            event.setString(key, attributeMap.get(key));
        }
        return event;
    }

    public void listener(String address, String port) throws EventSystemException, UnknownHostException {
        LogEventHandler handler = new LogEventHandler();
        InetAddress inetAddress = InetAddress.getByName(address);
        DatagramEventListener listener = new DatagramEventListener();

        listener.setAddress(inetAddress);
        listener.setPort(Integer.valueOf(port));
        listener.addHandler(handler);
        listener.initialize();
    }

    private class LogEventHandler extends EventPrintingHandler {
        @Override
        public void handleEvent(Event event) {
            try {
                String aid = event.getString("aid");
                if (aid.equals("firefly")) {
                    System.out.print(event);
                }
            } catch (EventSystemException e) {
                e.printStackTrace();
            }
        }
    }
}
