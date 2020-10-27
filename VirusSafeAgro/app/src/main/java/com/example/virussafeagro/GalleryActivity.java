package com.example.virussafeagro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.transition.ChangeBounds;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.virussafeagro.adapters.ListImageGalleryAdapter;
import com.example.virussafeagro.adapters.ListQuizResultAdapter;
import com.example.virussafeagro.fragments.VirusDetailNewFragment;
import com.example.virussafeagro.fragments.VirusInfoListFragment;
import com.github.chrisbanes.photoview.PhotoView;
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
            // show the recycler view
            ListImageGalleryAdapter listImageGalleryAdapter = new ListImageGalleryAdapter(virusImageURLStringList, this);
            RecyclerView recyclerViewForVirusImageResult = findViewById(R.id.rv_image_gallery);
            recyclerViewForVirusImageResult.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            recyclerViewForVirusImageResult.setAdapter(listImageGalleryAdapter);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerViewForVirusImageResult.setLayoutManager(layoutManager);
        } else {

        }
    }

    private void setCloseButtonOnClickListener() {
        this.closeImageButton.setOnClickListener(closeButtonView -> {
            supportFinishAfterTransition();
        });
    }
}
