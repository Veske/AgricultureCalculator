package veske.com.agriculturecalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;

import veske.com.agriculturecalculator.germinative.GerminativeActivity_;

@OptionsMenu(R.menu.menu_main)
@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    public static String PACKAGE_NAME;

    @AfterViews
    public void setPackageName() {
        PACKAGE_NAME = getApplicationContext().getPackageName();
    }

    @Click
    public void buttonGerminative(View v) {
        Intent intent = GerminativeActivity_.intent(getApplicationContext()).get();
        startActivity(intent);
    }
}
