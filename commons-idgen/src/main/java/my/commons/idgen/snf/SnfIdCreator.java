/**
 * Copyright (c) 2024 Avdhesh Yadav. All rights reserved.
 */
package my.commons.idgen.snf;

import my.commons.idgen.IdGenException;

public interface SnfIdCreator {

    Long createId() throws IdGenException;
}
