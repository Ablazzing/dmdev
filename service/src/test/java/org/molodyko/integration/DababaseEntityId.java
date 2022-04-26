package org.molodyko.integration;

public enum DababaseEntityId {
    CREATED_USER_ID(3),
    EXISTED_USER_ID(1),
    FOR_DELETE_USER_ID(2),

    EXISTED_CATEGORY_ID(1),
    EXISTED_CATEGORY_ANOTHER_ID(2),
    FOR_DELETE_CATEGORY_ID(3),
    CREATED_CATEGORY_ID(4),

    EXISTED_HOLIDAY_TYPE_ID(1),
    FOR_DELETE_HOLIDAY_TYPE_ID(2),
    CREATED_HOLIDAY_TYPE_ID(3),

    EXISTED_HOLIDAY_ID(1),
    CREATED_HOLIDAY_ID(2),

    EXISTED_ENTRY_ID(1),
    CREATED_ENTRY_ID(4),

    EXISTED_DESCRIPTION_CHANGER_ID(1),
    CREATED_DESCRIPTION_CHANGER_ID(2),

    EXISTED_CATEGORY_RENAME_ID(1),
    CREATED_CATEGORY_RENAME_ID(2);
    
    private int id;

    DababaseEntityId(int id) {
        this.id = id;
    }

    public int id() {
        return id;
    }
}
