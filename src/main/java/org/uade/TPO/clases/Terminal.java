package org.uade.TPO.clases;

public class Terminal {
    final String code;
    final String description;

    public Terminal(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return description;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Terminal otra = (Terminal) obj; // Convierte obj de tipo 'Object' a tipo 'Terminal'
        return this.code.equals(otra.code);
    }

    @Override
    public String toString() {
        return code + "-" + description;
    }

    @Override
    public int hashCode() {return code.hashCode();}
}