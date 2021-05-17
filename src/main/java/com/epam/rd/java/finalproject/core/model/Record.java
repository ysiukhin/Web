package com.epam.rd.java.finalproject.core.model;

import java.io.Serializable;
import java.time.Instant;

public class Record implements Serializable {
    private int id;
    private Instant start;
    private Instant end;
}
