package veske.com.agriculturecalculator.germinative;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
            float tempResult = (Float.parseFloat(germinativeSeed.getText().toString()) *
                    Float.parseFloat(seedMass.getText().toString()) /
                    Float.parseFloat(clean.getText().toString()) /
                    Float.parseFloat(germinative.getText().toString())) / 100;
            calculationResult.setText(Float.toString(tempResult) + " kg/ha");
        } catch (NumberFormatException ex) {
            Log.e(TAG, "ERROR: No numbers found for calculation!");
            calculationResult.setText("0");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("");
        //getActionBar().setDisplayShowTitleEnabled(false);
        //setTitle(TitleEnum.GERMINATIVE_ACTIVITY_TITLE.toString());
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
    }
}
