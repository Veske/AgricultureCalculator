package veske.com.agriculturecalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.analytics.GoogleAnalytics;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;

@OptionsMenu(R.menu.menu_loading)
@EActivity(R.layout.activity_loading)
public class LoadingActivity extends AppCompatActivity {

    private boolean skipSplash = false;

    @AfterViews
    @Background
    public void newThread() {
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            Log.e("Thread interrupted", e.getLocalizedMessage());
        } finally {
            if (!skipSplash) {
                launchMainActivity();
            }
        }
    }

    public void skipLoadingSplash(View v) {
        skipSplash = true;
        launchMainActivity();
    }

    private void launchMainActivity() {
        Intent intent = MainActivity_.intent(getApplicationContext()).get();
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
    }

    @Override
    protected void onStop() {
        GoogleAnalytics.getInstance(this).reportActivityStop(this);
        super.onStop();
    }
}
