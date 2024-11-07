/**
 * Copyright (c) 2024 Avdhesh Yadav. All rights reserved.
 */
package my.commons.idgen.snf;

public interface SnfIdCreator {

    long createId();

    SnfId getSnfId(long id);

    long getId(SnfId snfId);
}
