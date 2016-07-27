package randomlytyping.mldr.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import randomlytyping.util.Intents;
import randomlytyping.mldr.R;

/**
 * Launcher activity that directs to different custom view demos.
 */
public final class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        findViewById(R.id.drawn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(
                    Intents.getDemoIntent(MainActivity.this, R.layout.activity_drawn, true));
            }
        });
    }

    @OnClick(R.id.grouped)
    void onGroupedClick() {
        startActivity(new Intent(this, CustomViewGroupActivity.class));
    }

    @OnClick({R.id.drawn, R.id.invalidated, R.id.measured, R.id.attributed, R.id.interacted})
    void onClick(View view) {

        boolean toastOnClick = false;
        @LayoutRes final int layoutResId;
        switch (view.getId()) {
            case R.id.drawn:
                layoutResId = R.layout.activity_drawn;
                toastOnClick = true;
                break;
            case R.id.invalidated:
                layoutResId = R.layout.activity_invalidated;
                break;
            case R.id.measured:
                layoutResId = R.layout.activity_measured;
                break;
            case R.id.attributed:
                layoutResId = R.layout.activity_attributed;
                break;
            case R.id.interacted:
                layoutResId = R.layout.activity_interacted;
                break;
            case R.id.grouped:
                layoutResId = R.layout.activity_custom_view_group;
                break;
            default:
                layoutResId = -1;
                break;
        }

        if (layoutResId == -1) {
            return;
        }

        startActivity(Intents.getDemoIntent(this, layoutResId, toastOnClick));
    }
}
