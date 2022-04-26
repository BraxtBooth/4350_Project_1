package com.example.a4350_project_1;

import android.util.Log;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.storage.options.StorageDownloadFileOptions;

import java.io.File;

public class AWSAmplify {

    /// Method for uploading the 3 database files to S3 bucket
    public static void uploadDBFile() {
        File file = new File("./data/data/com.example.a4350_project_1/databases/database.db");
        Amplify.Storage.uploadFile(
                "DBFile",
                file,
                result -> Log.i("Amplify", "Successfully uploaded DB: " + result.getKey()),
                storageFailure -> Log.e("Amplify", "Upload failed", storageFailure)
        );
        file = new File("./data/data/com.example.a4350_project_1/databases/database.db-shm");
        Amplify.Storage.uploadFile(
                "SHMFile",
                file,
                result -> Log.i("Amplify", "Successfully uploaded shm: " + result.getKey()),
                storageFailure -> Log.e("Amplify", "Upload failed", storageFailure)
        );
        file = new File("./data/data/com.example.a4350_project_1/databases/database.db-wal");
        Amplify.Storage.uploadFile(
                "WALFile",
                file,
                result -> Log.i("Amplify", "Successfully uploaded wal: " + result.getKey()),
                storageFailure -> Log.e("Amplify", "Upload failed", storageFailure)
        );
    }

    /// Method for downloading the database file from S3
//    public static void downloadFile(){
//        Amplify.Storage.downloadFile(
//                "DBFile",
//                new File("./data/data/com.example.a4350_project_1/databases/database.db"),
//                StorageDownloadFileOptions.defaultInstance(),
//                progress -> Log.i("Amplify", "Fraction completed: " + progress.getFractionCompleted()),
//                result -> Log.i("Amplify", "Successfully downloaded: " + result.getFile().getName()),
//                error -> Log.e("Amplify",  "Download Failure", error)
//        );
//    }
}
