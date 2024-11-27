package com.jw.core.exception;

import com.jw.core.type.ModuleType;

public abstract class LibraryException
        extends RuntimeException {

    private final ModuleType moduleType;

    public LibraryException(ModuleType moduleType, String message, Throwable cause) {
        super(message, cause);
        this.moduleType = moduleType;
    }

    public LibraryException(ModuleType moduleType, String message) {
        super(message);
        this.moduleType = moduleType;
    }

    public ModuleType getModuleType() {
        return moduleType;
    }

    @Override
    public String toString() {
        return String.format("LibraryException: Module=%s, Message=%s", moduleType, getMessage());
    }

}