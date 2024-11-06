/**
 * Copyright (c) 2024 Avdhesh Yadav. All rights reserved.
 */
package my.commons.idgen.snf;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * @author Avdhesh Yadav
 * 05/11/24
 */
public class IdGenInitializationWorker implements Callable<ArrayList<SnfId>> {

    private CountDownLatch latch;
    private int noOfIds;
    private ArrayList<SnfId> idList;
    private String name;

    public IdGenInitializationWorker(String name, CountDownLatch latch, int noOfIds) {
        this.latch = latch;
        this.noOfIds = noOfIds;
        this.idList = new ArrayList<>(noOfIds);
        this.name = name;
    }

    @Override
    public ArrayList<SnfId> call() throws Exception {
        System.out.println("Thread:" + name);
        try {
            latch.await();
            for (int i = 0; i < noOfIds; i++) {
                Long id = SnfIdGen.getId();
                SnfId snfId = SnfIdGen.getSnfId(id);
                idList.add(snfId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idList;
    }
}
