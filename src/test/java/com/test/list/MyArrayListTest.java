package com.test.list;

import java.util.List;

class MyArrayListTest extends MyListTest {

    @Override
    protected List<String> initList() {
        return new MyArrayList<>();
    }

}
