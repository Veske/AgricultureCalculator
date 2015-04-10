package veske.com.agriculturecalculator.services;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileService {

    public List<String> loadFile(InputStream raw) throws IOException {
        StringWriter writer = new StringWriter();
        IOUtils.copy(raw, writer, "UTF-8");
        String string = writer.toString();
        raw.close();

        return getArrayFromString(string);
    }

    private List<String> getArrayFromString(String string) {
        List<String> items = new ArrayList<>();
        String[] arr = string.split("_");
        Collections.addAll(items, arr);

        return items;
    }
}
