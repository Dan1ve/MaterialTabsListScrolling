package de.veihelmann.example.materialtabsscrolling;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();
        setupRecyclerView();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        // Let's a grid with 2 columns.
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        populateRecyclerView(recyclerView);

        final View tabBar = findViewById(R.id.fake_tab);
        final View coloredBackgroundView = findViewById(R.id.colored_background_view);
        final View toolbarContainer = findViewById(R.id.toolbar_container);
        final View toolbar = findViewById(R.id.toolbar);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (Math.abs(toolbarContainer.getTranslationY()) > toolbar.getHeight()) {
                        hideToolbar();
                    } else {
                        showToolbar();
                    }
                }
            }

            private void showToolbar() {
                toolbarContainer
                        .animate()
                        .translationY(0)
                        .start();
            }

            private void hideToolbar() {
                toolbarContainer
                        .animate()
                        .translationY(-tabBar.getBottom())
                        .start();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                scrollColoredViewParallax(dy);

                if (dy > 0) {
                    hideToolbarBy(dy);
                } else {
                    showToolbarBy(dy);
                }
            }

            private void scrollColoredViewParallax(int dy) {
                coloredBackgroundView.setTranslationY(coloredBackgroundView.getTranslationY() - dy / 3);
            }


            private void hideToolbarBy(int dy) {
                if (canHideMore(dy)) {
                    toolbarContainer.setTranslationY(-tabBar.getBottom());
                } else {
                    toolbarContainer.setTranslationY(toolbarContainer.getTranslationY() - dy);
                }
            }

            private boolean canHideMore(int dy) {
                return Math.abs(toolbarContainer.getTranslationY() - dy) > tabBar.getBottom();
            }


            private void showToolbarBy(int dy) {
                if (canShowMore(dy)) {
                    toolbarContainer.setTranslationY(0);
                } else {
                    toolbarContainer.setTranslationY(toolbarContainer.getTranslationY() - dy);
                }
            }

            private boolean canShowMore(int dy) {
                return toolbarContainer.getTranslationY() - dy > 0;
            }
        });
    }


    private void populateRecyclerView(RecyclerView recyclerView) {
        recyclerView.setAdapter(new RecyclerView.Adapter() {

            private final static int DUMMY_ITEM_COUNT = 30;

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
                View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
                return new TextHolder(itemView);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
                // We are too lazy for this by now ;-)
            }

            @Override
            public int getItemCount() {
                return DUMMY_ITEM_COUNT;
            }


            class TextHolder extends RecyclerView.ViewHolder {

                public TextHolder(View itemView) {
                    super(itemView);
                }
            }
        });
    }
}
