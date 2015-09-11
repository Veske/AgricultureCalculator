package veske.com.agriculturecalculator.helper;

import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;

import veske.com.agriculturecalculator.R;

@OptionsMenu(R.menu.menu_helper)
@EActivity(R.layout.activity_helper)
public class HelperActivity extends AppCompatActivity {

    @AfterViews
    public void initialize() {
        // fds
    }
}
