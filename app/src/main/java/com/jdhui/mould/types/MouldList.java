package com.jdhui.mould.types;

import java.util.ArrayList;

public class MouldList<T extends IMouldType> extends ArrayList<T> implements IMouldType {

    private static final long serialVersionUID = 1L;

    public MouldList() {
        super();
    }

    public MouldList(int capacity) {
        super(capacity);
    }

}
