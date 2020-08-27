package com.example.proto_4;

        import androidx.room.Database;
        import androidx.room.RoomDatabase;

@Database(entities = {Client.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ClientDao clientDao();
}
