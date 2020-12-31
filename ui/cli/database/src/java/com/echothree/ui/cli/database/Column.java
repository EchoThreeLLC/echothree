// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// --------------------------------------------------------------------------------

package com.echothree.ui.cli.database;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import java.util.List;

public class Column {
    
    static final int parentNone = 0;
    static final int parentDelete = 1;
    static final int parentSetNull = 2;
    
    Table table;
    
    String name;
    ColumnType columnType;
    int type;
    boolean hasMaxLength;
    long maxLength;
    boolean hasTotalDigits;
    String defaultValue;
    String sequenceSource;
    boolean nullAllowed;
    String description;
    String destinationTable;
    String destinationColumn;
    int onParentDelete;
    
    String javaType = null;
    String javaSqlType = null;
    String variableName = null;
    String getFunctionName = null;
    String getEntityFunctionName = null;
    String setFunctionName = null;
    String setEntityFunctionName = null;
    String variableSuffixName = null;
    String entityVariableName = null;
    String fkEntityClass = null;
    String fkFactoryClass = null;
    String fkPKClass = null;
    String dbColumnName = null;
    
    /** Creates a new instance of Column */
    public Column(Table table, String name, String type, String maxLength, String defaultValue, String sequenceSource, boolean nullAllowed, String description,
            String destinationTable, String destinationColumn, String onParentDelete)
            throws Exception {
        this.table = table;
        this.name = name;
        if(maxLength != null) {
            hasMaxLength = true;
            this.maxLength = Long.parseLong(maxLength);
        } else {
            hasMaxLength = false;
        }
        this.defaultValue = defaultValue;
        this.sequenceSource = sequenceSource;
        this.nullAllowed = nullAllowed;
        this.description = description;
        this.destinationTable = destinationTable;
        this.destinationColumn = destinationColumn;
        
        if(onParentDelete != null) {
            if(onParentDelete.equals("delete")) {
                this.onParentDelete = Column.parentDelete;
            } else if(onParentDelete.equals("setNull")) {
                this.onParentDelete = Column.parentSetNull;
            } else {
                throw new Exception("Illegal onParentDelete \"" + onParentDelete + "\"");
            }
        } else
            this.onParentDelete = Column.parentNone;
        
        this.columnType = null;
        if(type.equals("EID")) {
            this.type = ColumnType.columnEID;
        } else if(type.equals("Integer")) {
            this.type = ColumnType.columnInteger;
        } else if(type.equals("Long")) {
            this.type = ColumnType.columnLong;
        } else if(type.equals("String")) {
            this.type = ColumnType.columnString;
            if(hasMaxLength == false) {
                throw new Exception("String column type requires length");
            }
        } else if(type.equals("Boolean")) {
            this.type = ColumnType.columnBoolean;
        } else if(type.equals("Date")) {
            this.type = ColumnType.columnDate;
        } else if(type.equals("Time")) {
            this.type = ColumnType.columnTime;
        } else if(type.equals("CLOB")) {
            this.type = ColumnType.columnCLOB;
        } else if(type.equals("BLOB")) {
            this.type = ColumnType.columnBLOB;
        } else if(type.equals("ForeignKey")) {
            this.type = ColumnType.columnForeignKey;
            if(destinationTable == null || destinationColumn == null || onParentDelete == null) {
                throw new Exception("Foreign Key missing one or more of destinationTable, destinationColumn or onParentDelete");
            }
        } else {
            String[] types = Splitter.on(':').splitToList(type).toArray(new String[0]);
            
            for(int i = 0; i < types.length; i++) {
                ColumnType currentColumnType = table.getDatabase().getColumnType(types[i]);

                if(currentColumnType == null) {
                    throw new Exception("Illegal column type \"" + type + "\"");
                } else {
                    if(this.type != 0 && currentColumnType.getRealType() != this.type) {
                        throw new Exception("Multiple incompatible types used \"" + type + "\"");
                        
                    }
                    
                    columnType = currentColumnType;
                    this.type = columnType.getRealType();
                    
                    if(columnType.hasMaxLength()) {
                        this.maxLength = Math.max(this.maxLength, columnType.getMaxLength());
                    }

                    this.destinationTable = columnType.getDestinationTable();
                    this.destinationColumn = columnType.getDestinationColumn();
                    this.onParentDelete = columnType.getOnParentDelete();
                }
            }
        }
    };
    
    public Table getTable() {
        return table;
    }
    
    public String getName() {
        return name;
    }
    
    /** Used during code generation when this column appears as a variable name.
     */
    public String getEntityVariableName() {
        if(entityVariableName == null) {
            if(type == ColumnType.columnForeignKey || type == ColumnType.columnEID)
                entityVariableName = name.substring(0, 1).toLowerCase() + name.substring(1, name.length() - 2);
            else
                entityVariableName = name.substring(0, 1).toLowerCase() + name.substring(1, name.length());
        }
        return entityVariableName;
    }
    
    /** Used during code generation when this column appears as a variable name.
     */
    public String getVariableName() {
        if(variableName == null) {
            if(type == ColumnType.columnForeignKey || type == ColumnType.columnEID)
                variableName = name.substring(0, 1).toLowerCase() + name.substring(1, name.length() - 2) + "PK";
            else
                variableName = name.substring(0, 1).toLowerCase() + name.substring(1, name.length());
        }
        return variableName;
    }
    
    /** Used during code generation when this column appears appended to another string as a variable name.
     */
    public String getVariableSuffixName() {
        if(variableSuffixName == null) {
            if(type == ColumnType.columnForeignKey || type == ColumnType.columnEID)
                variableSuffixName = name.substring(0, name.length() - 2) + "PK";
            else
                variableSuffixName = name;
        }
        return variableSuffixName;
    }
    
    public String getSetFunctionName() {
        if(setFunctionName == null) {
            if(type == ColumnType.columnForeignKey || type == ColumnType.columnEID)
                setFunctionName = "set" + name.substring(0, name.length() - 2) + "PK";
            else
                setFunctionName = "set" + name;
        }
        return setFunctionName;
    }
    
    /** Same as getSetFunctionName, just with a PK on the end if its for a FK */
    public String getSetEntityFunctionName() {
        if(setEntityFunctionName == null) {
            if(type == ColumnType.columnForeignKey || type == ColumnType.columnEID)
                setEntityFunctionName = "set" + name.substring(0, name.length() - 2);
            else
                setEntityFunctionName = "set" + name;
        }
        return setEntityFunctionName;
    }
    
    public String getGetFunctionName() {
        if(getFunctionName == null) {
            if(type == ColumnType.columnForeignKey || type == ColumnType.columnEID)
                getFunctionName = "get" + name.substring(0, name.length() - 2) + "PK";
            else
                getFunctionName = "get" + name;
        }
        return getFunctionName;
    }
    
    /** Same as getGetFunctionName, just with a PK on the end if its for a FK */
    public String getGetEntityFunctionName() {
        if(getEntityFunctionName == null) {
            if(type == ColumnType.columnForeignKey || type == ColumnType.columnEID)
                getEntityFunctionName = "get" + name.substring(0, name.length() - 2);
            else
                getEntityFunctionName = "get" + name;
        }
        return getEntityFunctionName;
    }
    
    public String getNameLowerCase() {
        return getName().toLowerCase();
    }
    
    public int getType() {
        return type;
    }
    
    public String getTypeAsJavaType() {
        if(javaType == null) {
            switch(type) {
                case ColumnType.columnEID:
                    javaType = table.getPKClass();
                    break;
                case ColumnType.columnInteger:
                    javaType = "Integer";
                    break;
                case ColumnType.columnLong:
                    javaType = "Long";
                    break;
                case ColumnType.columnString:
                    javaType = "String";
                    break;
                case ColumnType.columnBoolean:
                    javaType = "Boolean";
                    break;
                case ColumnType.columnDate:
                    javaType = "Integer";
                    break;
                case ColumnType.columnTime:
                    javaType = "Long";
                    break;
                case ColumnType.columnCLOB:
                    javaType = "String";
                    break;
                case ColumnType.columnBLOB:
                    javaType = "ByteArray";
                    break;
                case ColumnType.columnForeignKey: {
                    try {
                        Table fkTable = getTable().getDatabase().getTable(destinationTable);
                        javaType = fkTable.getPKClass();
                    } catch (Exception e) {
                        javaType = "<error>";
                    }
                }
                break;
            }
        }
        return javaType;
    }
    
    public String getTypeAsJavaSqlType() {
        if(javaSqlType == null) {
            switch(type) {
                case ColumnType.columnEID:
                    javaSqlType = "BIGINT";
                    break;
                case ColumnType.columnInteger:
                    javaSqlType = "INTEGER";
                    break;
                case ColumnType.columnLong:
                    javaSqlType = "BIGINT";
                    break;
                case ColumnType.columnString:
                    javaSqlType = "VARCHAR";
                    break;
                case ColumnType.columnBoolean:
                    javaSqlType = "INTEGER";
                    break;
                case ColumnType.columnDate:
                    javaSqlType = "INTEGER";
                    break;
                case ColumnType.columnTime:
                    javaSqlType = "BIGINT";
                    break;
                case ColumnType.columnCLOB:
                    javaSqlType = "CLOB";
                    break;
                case ColumnType.columnBLOB:
                    javaSqlType = "BLOB";
                    break;
                case ColumnType.columnForeignKey:
                    javaSqlType = "BIGINT";
                    break;
            }
        }
        return javaSqlType;
    }
    
    public String getFKEntityClass() {
        if(fkEntityClass == null) {
            switch(type) {
                case ColumnType.columnForeignKey: {
                    try {
                        Table fkTable = getTable().getDatabase().getTable(destinationTable);
                        fkEntityClass = fkTable.getEntityClass();
                    } catch (Exception e) {
                        fkEntityClass = "<error>";
                    }
                }
                break;
            }
        }
        return fkEntityClass;
    }
    
    public String getFKFactoryClass() {
        if(fkFactoryClass == null) {
            switch(type) {
                case ColumnType.columnForeignKey: {
                    try {
                        Table fkTable = getTable().getDatabase().getTable(destinationTable);
                        fkFactoryClass = fkTable.getFactoryClass();
                    } catch (Exception e) {
                        fkFactoryClass = "<error>";
                    }
                }
                break;
            }
        }
        return fkFactoryClass;
    }
    
    public String getFKPKClass() {
        if(fkPKClass == null) {
            switch(type) {
                case ColumnType.columnForeignKey: {
                    try {
                        Table fkTable = getTable().getDatabase().getTable(destinationTable);
                        fkPKClass = fkTable.getPKClass();
                    } catch (Exception e) {
                        fkPKClass = "<error>";
                    }
                }
                break;
            }
        }
        return fkPKClass;
    }
    
    public String getDbColumnName(String columnPrefixLowerCase)
            throws Exception {
        String result;
        
        if(type == ColumnType.columnForeignKey) {
            Table fkTable = getTable().getDatabase().getTable(destinationTable);

            boolean referencesSelf = fkTable.getNamePlural().equals(table.getNamePlural());
            boolean columnNameNotPk = !fkTable.getEID().getName().equals(name);

            result = columnPrefixLowerCase + "_" + (referencesSelf || columnNameNotPk? "": fkTable.getColumnPrefixLowerCase() + "_") + name.toLowerCase();
        } else {
            result = columnPrefixLowerCase + "_" + name.toLowerCase();
        }
        
        return result;
    }
    
    public String getDbColumnName()
            throws Exception {
        if(dbColumnName == null) {
            dbColumnName = getDbColumnName(table.getColumnPrefixLowerCase());
        }
        
        return dbColumnName;
    }
    
    public ColumnType getColumnType() {
        return columnType;
    }
    
    public boolean hasMaxLength() {
        return hasMaxLength;
    }
    
    public long getMaxLength() {
        return maxLength;
    }
    
    public boolean hasTotalDigits() {
        return hasTotalDigits;
    }
    
    public String getDefaultValue() {
        return defaultValue;
    }
    
    public Column getSequenceSource() throws Exception {
        if(sequenceSource == null)
            return null;
        else {
            int thePeriod = sequenceSource.indexOf('.');
            String stringTable = new String(sequenceSource.getBytes(Charsets.UTF_8), 0, thePeriod, Charsets.UTF_8);
            String stringColumn = new String(sequenceSource.getBytes(Charsets.UTF_8), thePeriod + 1, sequenceSource.length() - thePeriod - 1, Charsets.UTF_8);
            return table.getDatabase().getTable(stringTable).getColumn(stringColumn);
        }
    }
    
    public void setNullAllowed(boolean nullAllowed) {
        this.nullAllowed = nullAllowed;
    }
    
    public boolean getNullAllowed() {
        return nullAllowed;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getDestinationTable() {
        return destinationTable;
    }
    
    public String getDestinationColumn() {
        return destinationColumn;
    }
    
    public int getOnParentDelete() {
        return onParentDelete;
    }
    
}
