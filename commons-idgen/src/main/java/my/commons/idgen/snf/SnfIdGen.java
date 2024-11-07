/**
 * Copyright (c) 2024 Avdhesh Yadav. All rights reserved.
 */
package my.commons.idgen.snf;

import my.commons.idgen.snf.impl.DefaultSnfIdConfigImpl;
import my.commons.idgen.snf.impl.DefaultSnfIdCreator;

/**
 * @author Avdhesh Yadav
 * 04/11/24
 */
public class SnfIdGen {

    private static SnfIdCreator ID_CREATOR;

    public static long getId() {
        if (ID_CREATOR == null) {
            registerDefaultIdCreator(DefaultSnfIdConfigImpl.DEFAULT_CONFIG);
        }
        return ID_CREATOR.createId();
    }

    public static long getId(SnfId snfId) {
        return ID_CREATOR.getId(snfId);
    }

    public static SnfId getSnfId(long id) {
        return ID_CREATOR.getSnfId(id);
    }

    public synchronized static void registerDefaultIdCreator(SnfIdConfig snfIdConfig) {
        if (ID_CREATOR == null) {
            ID_CREATOR = new DefaultSnfIdCreator(snfIdConfig);
        } else {
            System.out.println("Id Creator already initialized.");
        }
    }

    public synchronized static void registerIdCreator(SnfIdCreator idCreator) {
        if (ID_CREATOR == null) {
            ID_CREATOR = idCreator;
        } else {
            System.out.println("Id Creator already initialized.");
        }
    }
}
