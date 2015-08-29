package veske.com.agriculturecalculator.germinative;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import veske.com.agriculturecalculator.MainActivity;
import veske.com.agriculturecalculator.R;
import veske.com.agriculturecalculator.services.FileService;

@OptionsMenu(R.menu.menu_germinative)
@EActivity(R.layout.activity_germinative)
public class GerminativeActivity extends AppCompatActivity {


    private static final String TAG = "GerminativeActivity";
    @ViewById(R.id.editSeedMass)
    EditText seedMass;
    @ViewById(R.id.editClean)
    EditText clean;
    @ViewById(R.id.editGerminativeSeed)
    EditText germinativeSeed;
    @ViewById(R.id.editGerminative)
    EditText germinative;
    @ViewById(R.id.textViewCalculationResult)
    TextView calculationResult;

    private CountDownTimer countDownTimer;
    private Toast toast;
    private FileService fileService;
    private String simpleToastText;


    @AfterViews
    public void initialize() {
        calculationResult.setAllCaps(true);
        fileService = new FileService();


        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);

        tableLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                cleanToast();
                return false;
            }
        });
    }

    private void cleanToast() {
        if (toast != null) {
            toast.cancel();
            countDownTimer.cancel();
        }
    }

    @Click(R.id.calculateGerminative)
    public void calculateGerminative(View v) {
        cleanToast();
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
            Log.e(TAG, "No numbers found for calculation!", ex);
            calculationResult.setText("0.00 kg/ha");
        }
    }

    @Click({R.id.seedMassInfo, R.id.seedInfo, R.id.germinativeInfo, R.id.cleanInfo})
    public void showInfoToast(View v) {
        if (toast != null) {
            toast.cancel();
            countDownTimer.cancel();
        }

        switch (v.getTag().toString()) {
            case "seedMassInfo": {
                createTableToast("kultuur");
                break;
            }
            case "germinativeInfo": {
                simpleToastText = "Idanevuse protsent väljendatakse protsentides, " +
                        "mis on saadud analüüsitulemustes. Sertifitseeritud seemne " +
                        "puhul on idanevuse protsent märgitud seemneetiketil.";
                createToast();
                break;
            }
            case "cleanInfo": {
                simpleToastText = "Seemne puhtuse protsenti mõjutavad erinevad lisandid" +
                        " seemnematerjalis. Teralisandid, umbrohuseemned, katkised terad" +
                        " ja muu materjal mõjutavad seemnematerjali kvaliteeti" +
                        " ja lõppkokkuvõttes külvisenormi.";
                createToast();
                break;
            }
            case "seedInfo": {
                createTableToast("idanevat_tera");
                break;
            }
            default: {
                throw new RuntimeException("Unable to create info toast");
            }
        }
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
