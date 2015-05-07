package veske.com.agriculturecalculator.germinative;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import veske.com.agriculturecalculator.MainActivity;
import veske.com.agriculturecalculator.R;
import veske.com.agriculturecalculator.services.FileService;

public class GerminativeActivity extends ActionBarActivity {

    private EditText seedMass;
    private EditText clean;
    private EditText germinativeSeed;
    private EditText germinative;
    private TextView calculationResult;
    private RelativeLayout germinativeLayout;
    private CountDownTimer countDownTimer;

    private Toast toast;

    private FileService fileService;

    private static final String TAG = "GerminativeActivity";
    private String simpleToastText;

    public void calculateGerminative(View v) {
        // (Idanevat tera * 1000 tera / Puhtus / Idavevus) / 100
        try {
            double a = Double.parseDouble(germinativeSeed.getText().toString());
            double b = Double.parseDouble(seedMass.getText().toString());
            double c;
            double d;

            if (clean.getText().toString().equals(""))
                c = (double) 100;
            else
                c = Double.parseDouble(clean.getText().toString());

            if (germinative.getText().toString().equals(""))
                d = (double) 100;
            else
                d = Double.parseDouble(germinative.getText().toString());

            double result2 = (a * b / c / d) / 100.0;
            // Multiply by 10 000 because of ha conversion
            result2 *= (double) 10000;
            calculationResult.setText(String.format("%.2f kg/ha", result2));
        } catch (NumberFormatException ex) {
            Log.e(TAG, "ERROR: No numbers found for calculation!");
            calculationResult.setText("0.00 kg/ha");
        }
    }

    public void showInfoToast(View v) {
        if (toast != null) {
            toast.cancel();
            countDownTimer.cancel();
        }

        switch (v.getTag().toString()) {
            case "seedMassInfo":
                createTableToast("kultuur");
                break;
            case "germinativeInfo":
                simpleToastText = "Idanevuse protsent väljendatakse protsentides, " +
                        "mis on saadud analüüsitulemustes. Sertifitseeritud seemne " +
                        "puhul on idanevuse protsent märgitud seemneetiketil.";
                createToast();
                break;
            case "cleanInfo":
                simpleToastText = "Seemne puhtuse protsenti mõjutavad erinevad lisandid" +
                        " seemnematerjalis. Teralisandid, umbrohuseemned, katkised terad" +
                        " ja muu materjal mõjutavad seemnematerjali kvaliteeti" +
                        " ja lõppkokkuvõttes külvisenormi.";
                createToast();
                break;
            case "seedInfo":
                createTableToast("idanevat_tera");
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_germinative);
        initializeVariables();

        germinativeLayout = (RelativeLayout) findViewById(R.id.germinativeLayout);
        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);

        tableLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (toast != null) {
                    toast.cancel();
                    countDownTimer.cancel();
                }
                return false;
            }
        });

        germinativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (toast != null) {
                    toast.cancel();
                    countDownTimer.cancel();
                }
                return false;
            }
        });
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
        fileService = new FileService();

        calculationResult.setAllCaps(true);
    }

    private void createTableToast(String fileName) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_layout_root));

        TableLayout t = (TableLayout) layout.findViewById(R.id.toastTable);
        View mTableRow;

        try {
            int rID = getResources().getIdentifier(fileName, "raw", MainActivity.PACKAGE_NAME);
            InputStream raw = getResources().openRawResource(rID);
            List<String> toastStrings = fileService.loadFile(raw);

            for (int i = 0, j = toastStrings.size(); i < j; i += 2) {
                mTableRow = inflater.inflate(R.layout.toast_table_layout_row, (ViewGroup) findViewById(R.id.toast_row));
                // Set text for name field
                TextView name = (TextView) mTableRow.findViewWithTag("toast_name");
                name.setText(toastStrings.get(i).trim());
                // Set text for param field
                TextView param = (TextView) mTableRow.findViewWithTag("toast_params");
                param.setText(toastStrings.get(i + 1));

                t.addView(mTableRow);
            }
        } catch (IOException ex) {
            Log.i("INFO: ", ex.getMessage());
        }

        toast = new Toast(getApplicationContext());
        //toast.setGravity(Gravity.CENTER_VERTICAL, 0 ,0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

        countDownTimer = new CountDownTimer(15000, 1000) {
            public void onTick(long millisUntilFinished) {
                toast.show();
            }

            public void onFinish() {
                toast.show();
            }
        }.start();
    }

    public void createToast() {
        toast = Toast.makeText(getApplicationContext(), simpleToastText, Toast.LENGTH_LONG);
        toast.show();

        countDownTimer = new CountDownTimer(15000, 1000) {
            public void onTick(long millisUntilFinished) {
                toast.show();
            }

            public void onFinish() {
                toast.show();
            }
        }.start();
    }
}
