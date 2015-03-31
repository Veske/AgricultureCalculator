package veske.com.agriculturecalculator.services;

import android.app.Activity;
import android.content.res.Resources;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import veske.com.agriculturecalculator.MainActivity;

public class FileService {

    private Resources resources;

    public String LoadFile(String fileName) throws IOException {


        int rID = resources.getIdentifier(fileName, "raw", MainActivity.PACKAGE_NAME);
        InputStream raw = resources.openRawResource(rID);

        byte[] buffer = new byte[raw.available()];
        raw.read(buffer);
        ByteArrayOutputStream oS = new ByteArrayOutputStream();
        oS.write(buffer);
        oS.close();
        raw.close();

        return oS.toString();
    }

    private HashMap<String, String> getArrayFromString(String string) {
        HashMap<String, String> hashMap = new HashMap<>();

        String[] arr = string.split("_");

        int count = 0;
        for (String ss : arr) {
            count++;
            if (count / 2 == 1)
                Log.i("INFO: ", ss);
        }

    }

    public FileService(Activity activity) {
        this.resources = activity.getResources();
    }
}
