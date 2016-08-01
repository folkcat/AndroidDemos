package com.folkcat.demo.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Tamas on 2016/8/2.
 */
public class Book  implements Parcelable{
    public int bookId;
    public String bookName;
    public Book(int id,String name){
        bookId=id;
        bookName=name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bookId);
        dest.writeString(bookName);

    }

    public static final Parcelable.Creator<Book> CREATOR=new Parcelable.Creator<Book>(){
        public Book createFromParcel(Parcel in){
            return new Book(in);
        }
        public Book[] newArray(int size){
            return new Book[size];
        }
    };
    private Book(Parcel in){
        bookId=in.readInt();
        bookName=in.readString();
    }
}
