package com.example.fit5046;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDataDao {
    @Query("SELECT * FROM userdata")
    List<UserData> getAll();
    @Query("SELECT * FROM userdata WHERE dailyStepId = :dailyStepId LIMIT 1")
    UserData findByID(int dailyStepId);
    @Insert
    void insertAll(UserData... userdatas);
    @Insert
    long insert(UserData userdata);
    @Delete
    void delete(UserData userdata);
    @Update(onConflict = REPLACE)
    public void updateUsers(UserData... userdatas);
    @Query("DELETE FROM userdata")
    void deleteAll();
}
