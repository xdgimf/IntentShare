package fr.tvbarthel.intentshare;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Simple view used to display a {@link TargetActivity}.
 */
class TargetActivityView extends FrameLayout {

    private ImageView icon;
    private TextView label;
    private TargetActivity model;
    private OnClickListener mInternalClickListener;
    private Listener listener;

    /**
     * Simple view used to display a {@link TargetActivity}.
     *
     * @param context holding context.
     */
    public TargetActivityView(Context context) {
        this(context, null);
    }

    /**
     * Simple view used to display a {@link TargetActivity}.
     *
     * @param context holding context.
     * @param attrs   attr from xml.
     */
    public TargetActivityView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Simple view used to display a {@link TargetActivity}.
     *
     * @param context      holding context.
     * @param attrs        attr from xml.
     * @param defStyleAttr style.
     */
    public TargetActivityView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            initialize(context);
        }
    }

    /**
     * Set the view model.
     *
     * @param model view model.
     */
    public void setModel(TargetActivity model) {
        this.model = model;
        if (this.model != null) {
            Picasso.with(getContext()).load(model.getIconUri()).fit().centerInside().into(icon);
            label.setText(model.getActivityLabel());
        }
    }

    /**
     * Set a listener used to catch view events.
     *
     * @param listener listener to register.
     */
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    /**
     * Initialize internal component.
     *
     * @param context holding context.
     */
    private void initialize(Context context) {
        LayoutInflater.from(context).inflate(R.layout.target_activity_view, this);

        int padding = context.getResources().getDimensionPixelSize(R.dimen.default_padding);
        setPadding(padding, padding, padding, padding);

        setForeground(
                ContextCompat.getDrawable(
                        context,
                        StyledAttributesUtils.getSelectableItemBackground(context)
                )
        );

        setBackgroundColor(
                ContextCompat.getColor(
                        context,
                        R.color.target_activity_view_background
                )
        );

        icon = ((ImageView) findViewById(R.id.target_activity_view_icon));
        label = ((TextView) findViewById(R.id.target_activity_view_label));

        mInternalClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onTargetActivitySelected(model);
                }
            }
        };
        setOnClickListener(mInternalClickListener);
    }

    /**
     * Listener used to catch view events.
     */
    public interface Listener {

        /**
         * Called when the user has chosen a target activity for his share intent.
         *
         * @param targetActivity chosen target activity.
         */
        void onTargetActivitySelected(TargetActivity targetActivity);
    }

}