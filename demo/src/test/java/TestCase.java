// package com.example;

import org.junit.jupiter.api.DynamicTest;

public interface TestCase {

    public Boolean run();

    public Boolean runDynamicTest();

    public DynamicTest getDynamicTest();

}
