package com.berge.drawer;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.berge.drawer.lisener.MaterialSectionListener;
import com.berge.drawer.lisener.OnItemDrawerSelectedListener;
import com.berge.drawer.model.MaterialSection;

import java.util.LinkedList;
import java.util.List;


/**
 * Fragment Drawer, is a basic version, one Header and list of sections
 *
 * Created by Adrià Bergé on 16/01/15
 */
public abstract class MenuDrawerFragment extends Fragment{

    private static final int BOTTOM_SECTION_START = 100;

    protected OnItemDrawerSelectedListener listenerActivity;

    private LinearLayout sections;
    private LinearLayout bottomSections;

    private List<MaterialSection> sectionList;
    private List<MaterialSection> bottomSectionList;

    private MaterialSection currentSection;

    private float density;

    private int idStatusBar = -1;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listenerActivity = (OnItemDrawerSelectedListener)activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_drawer, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sections = (LinearLayout) view.findViewById(R.id.sections);
        bottomSections = (LinearLayout) view.findViewById(R.id.bottom_sections);

        sectionList = new LinkedList<>();
        bottomSectionList = new LinkedList<>();

        density = getResources().getDisplayMetrics().density;

        onStartDrawerStructure();

        FrameLayout header = (FrameLayout)view.findViewById(R.id.drawer_header);
        View contentHeader = onConfigureHeader();
        if(contentHeader != null) {
            header.addView(contentHeader);
        }
        // init section
        currentSection = sectionList.get(0);
        currentSection.select();
        listenerActivity.onSectionSelected(currentSection);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        configureActionBar(currentSection);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listenerActivity = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /**
     * Create a View to insert in the position header 
     * @return
     */
    public abstract View onConfigureHeader();

    /**
     * generate all sections 
     */
    public abstract void onStartDrawerStructure();

    /**
     * delete all sections of drawer 
     */
    protected void removedStructure(){
        sectionList.removeAll(sectionList);
        sections.removeAllViews();
        bottomSections.removeAllViews();
        bottomSectionList.removeAll(bottomSectionList);
    }

    /**
     * change id status bar 
     * @param idStatusBar
     */
    public void setIdStatusBar(int idStatusBar) {
        this.idStatusBar = idStatusBar;
    }

    /**
     * add section basic 
     * @param section
     */
    public void addSection(MaterialSection section){
        section.setOnClickListener(materialSectionListener);
        section.setPosition(sectionList.size());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,(int)(48 * density));
        sectionList.add(section);
        sections.addView(section.getView(),params);
    }

    /**
     * add bottom section, not scrolling sections
     * @param section
     */
    public void addBottomSection(MaterialSection section) {
        section.setOnClickListener(materialSectionListener);
        section.setPosition(BOTTOM_SECTION_START + bottomSectionList.size());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,(int)(48 * density));
        bottomSectionList.add(section);
        bottomSections.addView(section.getView(),params);
    }

    /**
     * Add divider in list sections 
     */
    public void addDivisor() {
        View view = new View(getActivity());
        view.setBackgroundColor(getResources().getColor(R.color.menu_divisor));
        // height 1 px
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,1);
        params.setMargins(0,(int) (8 * density), 0 , (int) (8 * density));
        sections.addView(view, params);
    }

    /**
     * add header in list sections 
     * @param title
     */
    public void addSubheader(CharSequence title) {
        View subheader = LayoutInflater.from(getActivity()).inflate(R.layout.layout_material_subheader,sections,false);
        TextView subheaderTitle = (TextView) subheader.findViewById(R.id.subheader_text);
        subheaderTitle.setText(title);
        addDivisor();
        sections.addView(subheader);
    }


    /**
     *  This object generated all secunce of succesors in he user click one section.
     */
    private MaterialSectionListener materialSectionListener = new MaterialSectionListener() {
        @Override
        public void onClick(MaterialSection section) {

            configureActionBar(section);

            listenerActivity.onSectionSelected(section);
            listenerActivity.closeDrawer();
            currentSection = section;
            //section.select();

            int position = section.getPosition();

            for (MaterialSection mySection : sectionList) {
                if (position != mySection.getPosition()) {
                    mySection.unSelect();
                }
            }

            for (MaterialSection mySection : bottomSectionList) {
                if (position != mySection.getPosition()) {
                    mySection.unSelect();
                }
            }
            //sections.invalidate();
        }

    };

    /**
     * Configure status, navigation, actionbar with section clicked
     * @param section
     */
    private void configureActionBar(MaterialSection section) {

        int primaryColor;
        int primaryDarkColor;

        if(section.hasSectionColor()){
            primaryColor = section.getSectionColor();
            primaryDarkColor = section.getSectionColorDark();
        }else {
            Resources.Theme theme = getActivity().getTheme();
            TypedValue typedValue = new TypedValue();
            theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
            primaryColor = typedValue.data;
            theme.resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
            primaryDarkColor = typedValue.data;
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();

            window.setFlags(
                    WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS,
                    WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
            );

            View statusBar = getActivity().findViewById(idStatusBar);
            if(statusBar != null) {
                window.setFlags(
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                );
                statusBar.setBackgroundColor(primaryDarkColor);
            }else{
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(primaryDarkColor);

            }
            window.setNavigationBarColor(primaryDarkColor);


        } else {
            View statusBar = getActivity().findViewById(idStatusBar);
            if(statusBar != null) {
                statusBar.setVisibility(View.GONE);
            }
        }


        if(getActivity() instanceof ActionBarActivity) {
            ActionBar bar = ((ActionBarActivity) getActivity()).getSupportActionBar(); // or MainActivity.getInstance().getActionBar()
            if(bar != null) {
                bar.setBackgroundDrawable(new ColorDrawable(primaryColor));
                bar.setDisplayShowTitleEnabled(false);  // required to force redraw, without, gray color
                bar.setDisplayShowTitleEnabled(true);
            }
        }
    }
}
