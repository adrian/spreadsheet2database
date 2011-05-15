package com._17od.s2d;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ColumnMetaData {

    private String databaseName;
    private String defaultValue;
    
    public ColumnMetaData(String databaseName) {
        this(databaseName, null);
    }

    public ColumnMetaData(String databaseName, String defaultValue) {
        this.databaseName = databaseName;
        this.defaultValue = defaultValue;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
            return false;
        }
        ColumnMetaData rhs = (ColumnMetaData) obj;
        return new EqualsBuilder()
            .append(databaseName, rhs.databaseName)
            .append(defaultValue, rhs.defaultValue)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(databaseName)
            .append(defaultValue)
            .toHashCode();
    }

    public String toString() {
        return new ToStringBuilder(this).
            append("databaseName", databaseName).
            append("defaultValue", defaultValue).
            toString();
    }

}
