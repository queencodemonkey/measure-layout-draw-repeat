package randomlytyping.mldr.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import randomlytyping.util.Intents;
import randomlytyping.mldr.R;
import randomlytyping.widget.TallyCounter;

/**
 * Activity for demoing the tally counter.
 */
public class TallyCounterActivity extends AppCompatActivity {

    @BindView(R.id.tally_counter)
    TallyCounter tallyCounter;

    private boolean toastOnClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Intent intent = getIntent();
        final int layoutResId = intent
            .getIntExtra(Intents.EXTRA_LAYOUT_RES_ID,R.layout.activity_drawn);
        setContentView(layoutResId);

        ButterKnife.bind(this);

        toastOnClick = intent.getBooleanExtra(Intents.EXTRA_TOAST_ON_CLICK, false);
    }

    //
    // Listeners
    //

    @Optional
    @OnClick(R.id.increment)
    void onIncrementClick() {
        tallyCounter.increment();

        if (toastOnClick) {
            Toast.makeText(this, getString(R.string.click), Toast.LENGTH_SHORT).show();
        }
    }

    @Optional
    @OnClick(R.id.reset)
    void onRestClick() {
        tallyCounter.reset();
    }
}
