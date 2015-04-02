package veske.com.agriculturecalculator.services;

import android.app.Activity;
import android.content.res.Resources;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import veske.com.agriculturecalculator.MainActivity;

public class FileService {

    private Resources resources;
    private List<String> items;

    public List<String> loadFile(String fileName) throws IOException {
        int rID = resources.getIdentifier(fileName, "raw", MainActivity.PACKAGE_NAME);
        InputStream raw = resources.openRawResource(rID);

        byte[] buffer = new byte[raw.available()];
        raw.read(buffer);
        ByteArrayOutputStream oS = new ByteArrayOutputStream();
        oS.write(buffer);
        oS.close();
        raw.close();

        return getArrayFromString(oS.toString());
    }

    private List<String> getArrayFromString(String string) {
        items = new ArrayList<>();
        String[] arr = string.split("_");

        for (String ss : arr) {
            items.add(ss);
        }
        return items;
    }

    public FileService(Activity activity) {
        this.resources = activity.getResources();
    }
}
