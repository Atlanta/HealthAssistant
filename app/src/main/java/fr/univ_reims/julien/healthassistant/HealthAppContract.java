package fr.univ_reims.julien.healthassistant;

import android.provider.BaseColumns;

/**
 * Created by Julien on 24/02/2017.
 */

public final class HealthAppContract {

    private HealthAppContract() {}

    public static class HealthAppEntry implements BaseColumns {
        public static final String TABLE_NAME = "profile_users";
        public static final String COLUMN_LOGIN = "login";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_FIRST_NAME = "firstName";
        public static final String COLUMN_LAST_NAME = "lastName";
        public static final String COLUMN_BIRTHDAY = "birthday";
        /*public static final String COLUMN_WEIGHT = "weight";
        public static final String COLUMN_HEIGHT = "height";*/
        public static final String COLUMN_TOKEN_VALIDITY = "tokenValidity";
    }

}
