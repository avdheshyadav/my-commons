/**
 * Copyright (c) 2024 Avdhesh Yadav. All rights reserved.
 */
package my.commons.idgen.snf;

import java.util.concurrent.CountDownLatch;

/**
 * @author Avdhesh Yadav
 * 05/11/24
 */
public class IdGenInitializationWorker extends Thread {

    private CountDownLatch latch;
    private int noOfIds;

    public IdGenInitializationWorker(String name, CountDownLatch latch, int noOfIds) {
        this.latch = latch;
        this.noOfIds = noOfIds;
        setName(name);
    }

    public void run() {
        try {
            latch.await();
            for (int i = 0; i < noOfIds; i++) {
                Long id = SnfIdGen.getId();
                System.out.println("id:" + id + "  " + SnfIdGen.getSnfId(id) + " created in thread: " + getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
