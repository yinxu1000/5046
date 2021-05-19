package com.example.fit5046;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {UserData.class}, version = 2, exportSchema = false)
public abstract class UserDataDatabase extends RoomDatabase {

    public abstract UserDataDao userDataDao();

    private static volatile UserDataDatabase INSTANCE;
    static UserDataDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UserDataDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(),
                                    UserDataDatabase.class, "UserDataDatabase")
                                    .build();
                }
            }
        }
        return INSTANCE;
    }

}
