package Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import Model.Contact;
import Utils.Util;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }
    //crete tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        //SQL - Structured Query Language
        String CREATE_CONTACT_TABLE = "CREATE TABLE "+ Util.TABLE_NAME + "("
                + Util.KEY_ID + " INTEGER PRIMARY KEY,"+ Util.KEY_NAME + " TEXT,"
                + Util.KEY_PHONE_NUMBER +" TEXT"+")";

        db.execSQL(CREATE_CONTACT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Dropping
        db.execSQL("DROP TABLE IF EXISTS "+Util.TABLE_NAME);

        //Create table again
        onCreate(db);
    }
    /**
     * CRUD Operations - CREATE, READ, UPDATE, DELETE
     */
    //Add Contact
    public void addContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME, contact.getName());
        values.put(Util.KEY_PHONE_NUMBER,contact.getPhoneNumber());

        //Insert to row
        db.insert(Util.TABLE_NAME,null,values);
        db.close();

    }
    //get a contact

    public Contact getContact(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.TABLE_NAME,new String[] {

                Util.KEY_ID, Util.KEY_NAME, Util.KEY_PHONE_NUMBER

                },
                Util.KEY_ID +"=?",
                new String[] {
                        String.valueOf(id)
                },
                null,null,null,null);

        if (cursor != null){
            cursor.moveToFirst();
        }
        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),cursor.getString(2));
        return contact;
    }
    //get all contacts
    public List<Contact> getallContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        String selectQuery = "select * from " + Util.TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));

                //Contact contact = new Contact(Integer.parseInt(
                // cursor.getString(0)),cursor.getString(1),cursor.getString(2));
                // Adding contact to list

                contactList.add(contact);

            } while (cursor.moveToNext());

        }
        return contactList;
    }
    //update contact
    public int updateContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME, contact.getName());
        values.put(Util.KEY_PHONE_NUMBER, contact.getPhoneNumber());

        //update row
        return db.update(Util.TABLE_NAME,values,Util.KEY_ID+ "=?",
                new String[] {
                        String.valueOf(contact.getId())
                });

    }
    //delete single contact
    public void deleteContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Util.TABLE_NAME, Util.KEY_ID + " = ? ",
                new String[]{
                        String.valueOf(contact.getId())
                });
        db.close();
    }
    //get contact count
    public int getContactsCount(){
        String countQuery =  "select * from " +Util.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery,null);
        //cursor.close();
        return cursor.getCount();
    }

}
