package veske.com.agriculturecalculator.helper;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import veske.com.agriculturecalculator.R;

@OptionsMenu(R.menu.menu_helper)
@EActivity(R.layout.activity_helper)
public class HelperActivity extends AppCompatActivity implements OnItemSelectedListener {

    @ViewById(R.id.cultureSpinner)
    Spinner cultureSpinner;
    @ViewById(R.id.harvestSpinner)
    Spinner harvestSpinner;
    @ViewById(R.id.n_value)
    TextView N;

    CalculationValues calculationValues;

    boolean cultureSelected, harvestSelected = false;
    int culturePos, harvestPos;

    @AfterViews
    public void initialize() {
        initializeSpinners();
        calculationValues = new CalculationValues(getResources());
    }

    public void initializeSpinners() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cultures, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cultureSpinner.setAdapter(adapter);
        cultureSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.harvestValues, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        harvestSpinner.setAdapter(adapter2);
        harvestSpinner.setOnItemSelectedListener(this);

    }

    private void calculate() {
        float x = calculationValues.getX()[harvestPos];
        N.setText(String.valueOf(calculationValues.getN()[culturePos] * x));
    }

    @Override
    public void onItemSelected(AdapterView<?> adapter, View view, int position, long id) {
        switch (adapter.getId()) {
            case R.id.cultureSpinner: {
                culturePos = position;
                if (cultureSelected && harvestSelected) {
                    calculate();
                } else {
                    cultureSelected = true;
                }

                break;
            }
            case R.id.harvestSpinner: {
                harvestPos = position;
                if (cultureSelected && harvestSelected) {
                    calculate();
                } else {
                    harvestSelected = true;
                }
                break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapter) {

    }
}
