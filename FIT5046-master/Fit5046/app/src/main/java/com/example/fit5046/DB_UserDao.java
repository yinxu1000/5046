package com.example.fit5046;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface DB_UserDao {
    @Query("SELECT * FROM db_user")
    List<DB_User> getAll();
    @Query("SELECT * FROM db_user WHERE name LIKE :first AND " +
            "surname LIKE :last LIMIT 1")
    DB_User findByFirstandLastName(String first, String last);
    @Query("SELECT * FROM db_user WHERE id = :id LIMIT 1")
    DB_User findByID(int id);
    @Insert
    void insertAll(DB_User... db_users);
    @Insert
    long insert(DB_User db_user);
    @Delete
    void delete(DB_User db_user);
    @Update(onConflict = REPLACE)
    public void updateUsers(DB_User... db_users);
    @Query("DELETE FROM db_user")
    void deleteAll();
}
