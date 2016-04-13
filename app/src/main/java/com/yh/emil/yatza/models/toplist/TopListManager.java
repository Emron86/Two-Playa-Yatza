package com.yh.emil.yatza.models.toplist;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

/**
 * Created by Emil on 2016-04-04.
 */
public class TopListManager {

    private final String FILE_NAME = "toplist.dat";

    public void saveTopList(TopList topList, Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(topList);
            oos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TopList loadTopList(Context context) {

        TopList topList = null;

        File file = context.getFileStreamPath(FILE_NAME);

        if (!file.exists()) {
                topList = new TopList(10, true);
                saveTopList(topList, context);
        }

        try {
            FileInputStream fis = context.openFileInput(FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            topList = (TopList)ois.readObject();
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return topList;
    }

    public void deleteTopList(Context context) {
        //these two works fine for deleting
        //context.deleteFile(file.getName());
        //file.delete();
        File file = context.getFileStreamPath(FILE_NAME);

        if (file.exists()) {
            context.deleteFile(file.getName());
        }
    }
}
