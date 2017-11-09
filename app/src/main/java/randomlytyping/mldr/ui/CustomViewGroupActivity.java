package randomlytyping.mldr.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import randomlytyping.mldr.R;

/**
 * Activity with example of a custom ViewGroup.
 */
public class CustomViewGroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_group);

        final RecyclerView list = ButterKnife.findById(this, R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(new ListItemAdapter(this));
    }

    //
    // Inner classes
    //

    /**
     * {@link RecyclerView.ViewHolder} subclass that holds references to
     * views for each layout example.
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        //
        // Fields
        //

        @BindView(R.id.icon)
        ImageView mIconView;

        @BindView(R.id.title)
        TextView mTitleView;

        @BindView(R.id.subtitle)
        TextView mSubtitleView;

        //
        // Constructors
        //

        /**
         * Constructor
         *
         * @param itemView View for the item.
         */
        ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

        }

        //
        // Getters/Setters
        //

        void setListItem(ListItem item) {
            mIconView.setImageResource(item.iconResId);
            mTitleView.setText(item.stringResId);
            mSubtitleView.setText(item.descResId);
        }
    }


    class ListItemAdapter extends RecyclerView.Adapter<ViewHolder> {

        //
        // Fields
        //
        private final LayoutInflater mInflater;

        //
        // Constructors
        //

        /**
         * Constructor.
         *
         * @param context The current context.
         */
        ListItemAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        //
        // RecyclerView.Adapter<ViewHolder> implementation
        //

        @Override
        public int getItemCount() {
            return ListItem.getItemCount();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(mInflater.inflate(R.layout.list_item_simple, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.setListItem(ListItem.getItem(position));
        }
    }
}
