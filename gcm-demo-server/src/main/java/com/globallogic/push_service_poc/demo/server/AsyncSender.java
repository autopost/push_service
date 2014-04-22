package com.globallogic.push_service_poc.demo.server;

import com.globallogic.push_service_poc.demo.entity.User;
import com.globallogic.push_service_poc.demo.sender.*;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vladyslavprytula on 4/22/14.
 */
//TODO: must be split in controller and service

@Service
public class AsyncSender {

    private static final int MULTICAST_SIZE = 1000;

    @PersistenceContext(unitName = "mongoDBUnit2")
    private EntityManager em;

    private  Sender sender;
    static final boolean DEBUG = true;

    protected final Logger logger = Logger.getLogger(getClass().getName());

    private static final Executor threadPool = Executors.newFixedThreadPool(5);

    @Inject
    ServletContext servletContext;

    @PostConstruct
    public void createSender() {
        sender = new Sender((String) servletContext.getAttribute(ApiKeyInitializer.ATTRIBUTE_ACCESS_KEY));
    }


    public String sendAll() throws ParseException, IOException {
        List<String> devices = Datastore.getDevices();
        String status;
        if (devices.isEmpty()) {
            status = "Message ignored as there is no device registered!";
        } else {
            // NOTE: check below is for demonstration purposes; a real application
            // could always send a multicast, even for just one recipient
            if (devices.size() == 1) {
                // send a single message using plain post
                String registrationId = devices.get(0);
                Message message = new Message.Builder().build();
                Result result = sender.send(message, registrationId, 5);
                status = "Sent message to one device: " + result;
            } else {
                // send a multicast message using JSON
                // must split in chunks of 1000 devices (GCM limit)
                int total = devices.size();
                List<String> partialDevices = new ArrayList<String>(total);
                int counter = 0;
                int tasks = 0;
                for (String device : devices) {
                    counter++;
                    partialDevices.add(device);
                    int partialSize = partialDevices.size();
                    if (partialSize == MULTICAST_SIZE || counter == total) {
                        asyncSend(partialDevices);
                        partialDevices.clear();
                        tasks++;
                    }
                }
                status = "Asynchronously sending " + tasks + " multicast messages to " +
                        total + " devices";
            }
        }

        return status.toString();
    }

    private void asyncSend(List<String> partialDevices) {
        // make a copy
        final List<String> devices = new ArrayList<String>(partialDevices);
        threadPool.execute(new Runnable() {

            public void run() {
//        Message message = new Message.Builder().build();
                User user = em.find(User.class,1111L);
                Message message = new Message.Builder().addData(user.getUserEmail(),user.getUserPassword()).build();

                MulticastResult multicastResult;
                try {
                    multicastResult = sender.send(message, devices, 5);
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "Error posting messages", e);
                    return;
                }
                List<Result> results = multicastResult.getResults();
                // analyze the results
                for (int i = 0; i < devices.size(); i++) {
                    String regId = devices.get(i);
                    Result result = results.get(i);
                    String messageId = result.getMessageId();
                    if (messageId != null) {
                        logger.fine("Succesfully sent message to device: " + regId +
                                "; messageId = " + messageId);
                        String canonicalRegId = result.getCanonicalRegistrationId();
                        if (canonicalRegId != null) {
                            // same device has more than on registration id: update it
                            logger.info("canonicalRegId " + canonicalRegId);
                            Datastore.updateRegistration(regId, canonicalRegId);
                        }
                    } else {
                        String error = result.getErrorCodeName();
                        if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
                            // application has been removed from device - unregister it
                            logger.info("Unregistered device: " + regId);
                            Datastore.unregister(regId);
                        } else {
                            logger.severe("Error sending message to " + regId + ": " + error);
                        }
                    }
                }
            }});
    }
}