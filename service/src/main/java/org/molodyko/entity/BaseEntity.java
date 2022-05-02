package org.molodyko.entity;

import java.io.Serializable;

public abstract class BaseEntity<T extends Serializable> {
    abstract void setId(T id);

    abstract T getId();
}
