package voa.learning.english.special.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database {
	public static final String KEY_ROWID = "id";
	public static final String KEY_IDPOST = "idpost";
	public static final String KEY_TITLE = "title";
	public static final String KEY_LISTEN = "listen";
	public static final String KEY_THUMB = "thumbnaix";
	public static final String KEY_LINKMP3 = "linkmp3";

	private static final String DATABASE_NAME = "EnglishListening";
	private static final String DATABASE_TABLE = "mp3offline";
	private static final int DATABASE_VERSION = 1;

	public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ DATABASE_TABLE + " (" + KEY_ROWID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_IDPOST
			+ " INTEGER, " + KEY_TITLE + " TEXT, " + KEY_LISTEN
			+ " TEXT, " + KEY_THUMB + " TEXT, " + KEY_LINKMP3
			+ " TEXT " + ");";

	private DbHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;

	// ------------------------------------------------------------------------------
	private static class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(CREATE_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}

	}

	// ------------------------------------------------------------------------------
	public Database(Context c) {
		ourContext = c;
		ourHelper = new DbHelper(ourContext);
	}

	// ------------------------------------------------------------------------------
	public Database open() {
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}

	// ------------------------------------------------------------------------------
	public void close() {
		ourHelper.close();
	}

	// ------------------------------------------------------------------------------
	public long insertRow(int idpost, String title, String listen,
			String thumbnaix, String linkmp3) {
		ContentValues cv = new ContentValues();
		cv.put(KEY_IDPOST, idpost);
		cv.put(KEY_TITLE, title);
		cv.put(KEY_LISTEN, listen);
		cv.put(KEY_THUMB, thumbnaix);
		cv.put(KEY_LINKMP3, linkmp3);
		return ourDatabase.insert(DATABASE_TABLE, null, cv);
	}

	// ------------------------------------------------------------------------------
	public Cursor getAllRows() {
		return ourDatabase.query(DATABASE_TABLE, new String[] { KEY_ROWID,
				KEY_IDPOST, KEY_TITLE, KEY_LISTEN, KEY_THUMB, KEY_LINKMP3 },
				null, null, null, null, KEY_ROWID + " DESC");
	}

	// -------------
	public Cursor getIdRow(String idpost) {

		return ourDatabase.query(DATABASE_TABLE, null, KEY_IDPOST + " =?",
				new String[] { idpost }, null, null, null);

	}

	// -------------
	public boolean checkRowByIdPost(String idpost) {

		Cursor cursor = ourDatabase.query(DATABASE_TABLE, null, KEY_IDPOST
				+ " =?", new String[] { idpost }, null, null, null);
		if (cursor.getCount() < 1) // ROW Not Exist
		{
			cursor.close();
			return false;
		}
		return true;
	}

	// ------------------------------------------------------------------------------
	public long deleteAllRows() {
		return ourDatabase.delete(DATABASE_TABLE, null, null);
	}
	public long deleteRows(String id) {
		return ourDatabase.delete(DATABASE_TABLE, KEY_ROWID+" = ?", new String[] { id });
	}
	// ------------------------------------------------------------------------------
}
