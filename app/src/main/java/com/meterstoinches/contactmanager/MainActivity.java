package com.meterstoinches.contactmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import Data.DatabaseHandler;
import Model.Contact;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler db = new DatabaseHandler(this);
        Log.d("DB Count:",String.valueOf(db.getContactsCount()));

        //Insert Contacts
        Log.d("Insert: ", "Insertin....");
        db.addContact(new Contact("Ankush","9815455229"));
        db.addContact(new Contact("Vivek","9256159998"));
        db.addContact(new Contact("Jagdeep","9988058152"));
        db.addContact(new Contact("Harkirat","9478051052"));

        //Read them back
        Log.d("Reading: ", " Reading all contacts...");
        List<Contact> contactList = db.getallContacts();

        //get 1 contact
        //Contact oneContact = db.getContact(5);
        //in case of updation
        //oneContact.setName("Dinesh");
        //oneContact.setPhoneNumber("8146188015");

       // Log.d("One Contact: ", "Name: "+oneContact.getName()+" Phone: "+oneContact.getPhoneNumber());

        //updated contact
        //int newContact = db.updateContact(oneContact);
        //Log.d("One Contact: ","Updated Row: "+String.valueOf(newContact )+ " Name: "+oneContact.getName()+" Phone: "+oneContact.getPhoneNumber());
        //db.deleteContact(oneContact);

        for(Contact c : contactList){

            String log = "ID: "+c.getId()+" , Name: "+c.getName()+", Phone: "+c.getPhoneNumber();
            Log.d("Name: ",log);
        }

    }
}
