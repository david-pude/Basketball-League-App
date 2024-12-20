    package com.example.basketballleague;

    import android.content.ContentValues;
    import android.content.Context;
    import android.database.Cursor;
    import android.database.sqlite.SQLiteDatabase;
    import android.database.sqlite.SQLiteOpenHelper;
    import android.widget.Toast;

    import androidx.annotation.Nullable;

    public class stand_database extends SQLiteOpenHelper {

        private Context context;
        private static final String DATABASE_NAME = "STANDING.db";
        private static final int DATABASE_VERSION = 1;

        private static final String TABLE_NAME = "STANDING";
        private static final String COLUMN_ID = "_id";
        private static final String COLUMN_TEAM = "TEAM";
        private static final String COLUMN_STATUS = "STATUS";

        public stand_database(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + TABLE_NAME +
                    " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TEAM + " TEXT, " +
                    COLUMN_STATUS + " TEXT);";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }

        public void addStand(String team, String status) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_TEAM, team);
            cv.put(COLUMN_STATUS, status);

            try {
                long result = db.insert(TABLE_NAME, null, cv);
                if (result == -1) {
                    Toast.makeText(context, "Failed to add venue", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Team and Standing added successfully", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            } finally {
                db.close();
            }
        }

        public Cursor readAllData() {
            String query = "SELECT * FROM " + TABLE_NAME;
            SQLiteDatabase db = this.getReadableDatabase();
            return db.rawQuery(query, null);
        }

        public void deleteStand(String id) {
            SQLiteDatabase db = this.getWritableDatabase();
            try {
                int rowsDeleted = db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{id});
                if (rowsDeleted > 0) {
                    Toast.makeText(context, "Team and Standing deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Failed to delete venue", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            } finally {
                db.close();
            }
        }
    }