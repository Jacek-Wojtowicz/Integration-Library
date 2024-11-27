package com.jw.core.variable;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class VariablePairs {

    private final Map<String, Object> variables;

    public VariablePairs() {
        this.variables = new HashMap<>();
    }

    public VariablePairs add(String key, Object value) {
        this.variables.put(key, value);
        return this;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public boolean isEmpty() {
        return variables.isEmpty();
    }

    public boolean isNotEmpty() {
        return !variables.isEmpty();
    }

    public Set<Entry<String, Object>> getEntrySet() {
        return variables.entrySet();
    }
}
