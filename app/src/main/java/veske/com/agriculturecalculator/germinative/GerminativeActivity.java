package veske.com.agriculturecalculator.germinative;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.math.BigDecimal;

import veske.com.agriculturecalculator.R;
import veske.com.agriculturecalculator.services.FileService;

public class GerminativeActivity extends ActionBarActivity {

    private EditText seedMass;
    private EditText clean;
    private EditText germinativeSeed;
    private EditText germinative;
    private TextView calculationResult;

    private Resources resources;
    private Context context;

    private FileService fileService;

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
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_layout_root));

        TableLayout t = (TableLayout) layout.findViewById(R.id.toastTable);
        View mTableRow = null;

        mTableRow = inflater.inflate(R.layout.toast_table_layout_row, null);
        //mTableRow = (TableRow) View.inflate(getActivity(), R.layout.mRowLayout, null);
        t.addView(mTableRow);

        try {
            String lol = fileService.LoadFile("kultuur");

            String[] arr = lol.split("_");

            for (String ss : arr) {
                Log.i("INFO: ", ss);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0 ,0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTitle("");

        setContentView(R.layout.activity_germinative);
        initializeVariables();
        fileService = new FileService(this);
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
