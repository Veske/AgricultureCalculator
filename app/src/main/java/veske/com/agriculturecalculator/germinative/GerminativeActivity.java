package veske.com.agriculturecalculator.germinative;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayList;

import veske.com.agriculturecalculator.R;

public class GerminativeActivity extends ActionBarActivity {

    private EditText seedMass;
    private EditText clean;
    private EditText germinativeSeed;
    private EditText germinative;
    private TextView calculationResult;

    private static final String TAG = "GerminativeActivity";

    public void calculateGerminative(View v) {
        // (Idanevat tera * 1000 tera / Puhtus / Idavevus) / 100
        try {
            BigDecimal result2;

            BigDecimal a = new BigDecimal(germinativeSeed.getText().toString());
            BigDecimal b = new BigDecimal(seedMass.getText().toString());
            BigDecimal c = new BigDecimal(clean.getText().toString());
            BigDecimal d = new BigDecimal(germinative.getText().toString());
            BigDecimal hundred = new BigDecimal("100");
            BigDecimal test = new BigDecimal("10000");

            result2 = a.multiply(b);
            result2 = result2.divide(c);
            result2 = result2.divide(d);
            result2 = result2.divide(hundred);
            result2 = result2.multiply(test);

            float tempResult = (Float.parseFloat(germinativeSeed.getText().toString()) *
                    Float.parseFloat(seedMass.getText().toString()) /
                    Float.parseFloat(clean.getText().toString()) /
                    Float.parseFloat(germinative.getText().toString())) / 100;

            //calculationResult.setText(Float.toString(tempResult) + " kg/ha" + "  Alternate value: " + result2.toString());
            calculationResult.setText(String.format("%.0f kg/ha", result2));

            Toast.makeText(getApplicationContext(), (String) result2.toString(), Toast.LENGTH_LONG).show();
        } catch (NumberFormatException ex) {
            Log.e(TAG, "ERROR: No numbers found for calculation!");
            calculationResult.setText("0");
        }
    }

    public void showInfoToast(View v) {
        /*
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_layout_root));
        TextView textView = (TextView) layout.findViewById(R.id.toast_name);
        textView.setText("Kultuur");
        TextView textView1 = (TextView) layout.findViewById(R.id.toast_meta_info);
        textView1.setText("100 tera mass, g");

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0 ,0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
*/

        String toastFormat = "%-20s %-15s%n";
        ArrayList<String> strings = new ArrayList<>();
        strings.add(String.format(toastFormat, "Kultuur", "      100 tera mass, g"));
        strings.add(String.format(toastFormat, "Talinisu", "      min. 36"));
        strings.add(String.format(toastFormat, "Suvinisu", "     min. 35"));
        strings.add(String.format(toastFormat, "Söödaoder", "  min. 38"));
        strings.add(String.format(toastFormat, "Õlleoder", "     min. 41"));
        strings.add(String.format(toastFormat, "Toidukaer", "   min. 31 (keskm. 36-39)"));
        strings.add(String.format(toastFormat, "Söödakaer", "  min. 31"));
        strings.add(String.format(toastFormat, "Rukis", "       min. 28"));
        strings.add(String.format(toastFormat, "Talirüps", "      keskm. 2,8 - 3,2"));
        strings.add(String.format(toastFormat, "Suvirüps", "     keskm. 2,3 - 2,4"));
        strings.add(String.format(toastFormat, "Taliraps", "      keskm. 4,4 - 4,6"));
        strings.add(String.format(toastFormat, "Suviraps", "     keskm. 3,3 - 3,4"));
        strings.add(String.format(toastFormat, "Söödahernes", "min 220"));

        StringBuilder builder = new StringBuilder();
        for (String s : strings) {
            builder.append(s);
        }

        Toast.makeText(this, builder, Toast.LENGTH_LONG).show();

        /*
        Toast.makeText(getApplicationContext(),
                "Kultuur\t\t1000 tera mass, g\n" +
                "Talinisu\t\tmin. 36\n" +
                "Suvinisu\t\tmin. 35\n" +
                "Söödaoder\t\tmin. 38\n" +
                "Õlleoder\t\tmin. 41\n" +
                "Toidukaer\t\tmin. 31 (keskm. 36-39)\n" +
                "Söödakaer\t\tmin. 31\n" +
                "Rukis\t\tmin 28\n" +
                "Talirüps\t\tkeskm. 2,8-3,2\n" +
                "Suvirüps\t\tkeskm. 2,3-2,4\n" +
                "Taliraps\t\tkeskm. 4,4-4,6\n" +
                "Suviraps\t\tkeskm. 3,3-3,4\n" +
                "Söödahernes\t\tmin. 220", Toast.LENGTH_LONG).show();
        */
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTitle("");

        setContentView(R.layout.activity_germinative);
        initializeVariables();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_germinative, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initializeVariables() {
        seedMass = (EditText) findViewById(R.id.editTeraMass);
        clean = (EditText) findViewById(R.id.editPuhtus);
        germinativeSeed = (EditText) findViewById(R.id.editIdanevTera);
        germinative = (EditText) findViewById(R.id.editIdanevus);
        calculationResult = (TextView) findViewById(R.id.textViewCalculationResult);

        calculationResult.setAllCaps(true);
    }
}
