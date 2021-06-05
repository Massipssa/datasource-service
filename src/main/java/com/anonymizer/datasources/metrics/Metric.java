package com.anonymizer.datasources.metrics;

import java.io.Serializable;

public interface Metric extends Serializable {
    Long getValue();
}
