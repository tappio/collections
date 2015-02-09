package com.test.list;

import java.util.List;

public class MyLinkedListTest extends MyListTest {

    @Override
    protected List<String> initList() {
        return new MyLinkedList<>();
    }

}