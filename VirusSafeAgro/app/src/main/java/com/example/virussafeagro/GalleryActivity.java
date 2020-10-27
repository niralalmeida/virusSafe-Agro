package com.example.virussafeagro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.virussafeagro.adapters.ListImageGalleryAdapter;
import com.example.virussafeagro.adapters.ListQuizResultAdapter;
import com.example.virussafeagro.fragments.VirusDetailNewFragment;
import com.example.virussafeagro.fragments.VirusInfoListFragment;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.List;

public class GalleryActivity extends AppCompatActivity {
    private GalleryActivity galleryActivity;

    // views
    private AppBarLayout galleryTitleAppBarLayout;
    private CollapsingToolbarLayout galleryTitleCollapsingToolbarLayout;
    private androidx.appcompat.widget.Toolbar galleryTitleToolbar;
    private ImageButton closeImageButton;
    private ImageView virusImageView;

    // gallery list
    private ListImageGalleryAdapter listImageGalleryAdapter;
    private RecyclerView recyclerViewForVirusImageResult;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChangeBounds bounds = new ChangeBounds();
        bounds.setDuration(100);
        getWindow().setSharedElementEnterTransition(bounds);
        setContentView(R.layout.activity_gallery);
        // hide top status bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // set activity
        this.galleryActivity = this;

        // initialize Views
        this.initializeViews();

        // show Initial Views
        this.showInitialViews();
        // show Image Grid List
        this.showImageGridList();
        // set close button on click
        this.setCloseButtonOnClickListener();
    }

    private void initializeViews() {
        this.galleryTitleAppBarLayout = findViewById(R.id.abl_virus_title_image_gallery);
        this.galleryTitleCollapsingToolbarLayout = findViewById(R.id.collapsingToolbarLayout_image_gallery);
        this.galleryTitleToolbar = findViewById(R.id.toolbar_image_gallery);
        this.closeImageButton = findViewById(R.id.imgbtn_close_image_gallery);
        this.virusImageView = findViewById(R.id.img_image_gallery);
    }

    // show Initial Views
    private void showInitialViews() {
        // set virusDetailCollapsingToolbarLayout
        this.galleryTitleCollapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorWhite));
        this.galleryTitleCollapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorWhite));
        // set title AppBarLayout height
        int heightDp = getResources().getDisplayMetrics().heightPixels / 2;
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams)this.galleryTitleAppBarLayout.getLayoutParams();
        lp.height = heightDp;
        this.galleryTitleAppBarLayout.setLayoutParams(lp);
        // set virus image
        this.virusImageView.setImageBitmap(VirusInfoListFragment.currentVirusImageBitmap);
        // set virus abbreviation title
        this.galleryTitleToolbar.setTitle(VirusDetailNewFragment.currentVirusModel.getVirusAbbreviation());
    }

    // show gallery image grid list
    private void showImageGridList() {
        List<String> virusImageURLStringList = VirusDetailNewFragment.currentVirusModel.getVirusPictureURLList();
        if (!virusImageURLStringList.isEmpty()) {
            // show the recycler view with grid layout manager
            this.listImageGalleryAdapter = new ListImageGalleryAdapter(virusImageURLStringList, this);
            this.recyclerViewForVirusImageResult = findViewById(R.id.rv_image_gallery);
            int numberOfColumns = 3;
            this.recyclerViewForVirusImageResult.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
            this.recyclerViewForVirusImageResult.setAdapter(this.listImageGalleryAdapter);
            this.gridLayoutManager = new GridLayoutManager(this, numberOfColumns);
            this.recyclerViewForVirusImageResult.setLayoutManager(this.gridLayoutManager);
            // set On Image Card Item Click Listener
            this.setOnImageCardItemClickListener();
        } else {

        }
    }

    // set On Image Card Item Click Listener
    private void setOnImageCardItemClickListener() {
        this.listImageGalleryAdapter.setOnImageCardClickListener(position -> {
            ListImageGalleryAdapter.ViewHolder itemViewHolder = (ListImageGalleryAdapter.ViewHolder) recyclerViewForVirusImageResult.findViewHolderForAdapterPosition(position);
            CardView itemImageCardView = itemViewHolder.virusImageCardView;
            ImageView itemImageView = itemViewHolder.virusImageView;
            BitmapDrawable itemImageBitmapDrawable = (BitmapDrawable) itemImageView.getDrawable();
            Bitmap itemImageBitmap = itemImageBitmapDrawable.getBitmap();
            if (itemImageBitmap != null){
                ImageViewActivity.currentImageBitmap = itemImageBitmap;
                // open the image view activity
                Intent intent = new Intent(galleryActivity, ImageViewActivity.class);
                // animation
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(galleryActivity, itemImageCardView, ViewCompat.getTransitionName(itemImageCardView));
                galleryActivity.startActivity(intent, options.toBundle());
            } else {
                Toast.makeText(galleryActivity, "Cannot get the image!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setCloseButtonOnClickListener() {
        this.closeImageButton.setOnClickListener(closeButtonView -> {
            supportFinishAfterTransition();
        });
    }
}
