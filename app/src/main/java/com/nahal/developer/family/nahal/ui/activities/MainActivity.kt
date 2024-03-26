package com.nahal.developer.family.nahal.ui.activities

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColor
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.nahal.developer.family.nahal.R
import com.nahal.developer.family.nahal.adapters.DragAndDropSelectFeatureAdapter
import com.nahal.developer.family.nahal.core.UAppCompatActivity
import com.nahal.developer.family.nahal.event.Event
import com.nahal.developer.family.nahal.madules.fm_DrogableRecyclerView.Listener
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.color.ColorListener
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.color.ColorSheet
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.color.ColorView
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.core.NegativeListener
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.core.SheetStyle
import com.nahal.developer.family.nahal.madules.fm_Toaster.FMToaster

class MainActivity : UAppCompatActivity(), Listener {
    private var doubleBackToExitPressedOnce = false
    lateinit var tvEmptyListTop: TextView
    lateinit var tvEmptyListBottom: TextView
    lateinit var relMain: RelativeLayout
    lateinit var recyclerTop: RecyclerView
    lateinit var recyclerBottom: RecyclerView
    lateinit var crdShowColorPicker: MaterialCardView
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true

        FMToaster.Builder(this)
            .setDescription("برای خروج از برنامه، لطفا دوبار دکمه برگشت را لمس نمائید")
            .setDuration(FMToaster.LENGTH_LONG)
            .setTitle("خروج")
            .setStatus(FMToaster.Status.SUCCESS).show()

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            doubleBackToExitPressedOnce = false
        }, 2000)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUi()
        setupDrag()
        setupEvent()
        applyThemeMode(ThemeMode.DAY_MODE)
    }

    private fun setupEvent() {
        crdShowColorPicker.setOnClickListener { showColorSheet() }
    }

    private fun showColorSheet() {
        ColorSheet().show(this) { // Build and show
            style(SheetStyle.BOTTOM_SHEET)

            title("رنگ پس زمینه")
            defaultView(ColorView.TEMPLATE) // Set the default view when the sheet is visible
            // disableSwitchColorView() Disable switching between template and custom color view
            onPositive("انتخاب") { color ->
                // Use Color
                relMain.setBackgroundColor(color)
            }
            onNegative("لغو") {

            }
        }
    }

    private fun applyThemeMode(themeMode: ThemeMode) {
        when (themeMode) {
            ThemeMode.NIGHT_MODE -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            ThemeMode.DAY_MODE -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

    enum class ThemeMode {
        AUTO,
        NIGHT_MODE,
        DAY_MODE
    }

    private fun initUi() {
        tvEmptyListTop = findViewById(R.id.tvEmptyListTop)
        tvEmptyListBottom = findViewById(R.id.tvEmptyListBottom)
        recyclerTop = findViewById(R.id.rvTop)
        recyclerBottom = findViewById(R.id.rvBottom)
        crdShowColorPicker = findViewById(R.id.crdShowColorPicker)
        relMain = findViewById(R.id.relMain)
    }

    private fun setupDrag() {
        setTopRecyclerView()
        setBottomRecyclerView()

        tvEmptyListTop.setVisibility(View.GONE)
        tvEmptyListBottom.setVisibility(View.GONE)
    }

    private fun setTopRecyclerView() {
        try {

            recyclerTop.setHasFixedSize(true)
            recyclerTop.layoutManager = GridLayoutManager(
                this, 2,
                LinearLayoutManager.VERTICAL, false
            )

            val topList: MutableList<String> = ArrayList()
            topList.add("دورهمی")
            topList.add("چی بپزم؟")
            topList.add("چی کجا؟")
            topList.add("مافیا")
            topList.add("مدیریت مالی")
            topList.add("وام خانگی")
            topList.add("نوستالوژی")
            topList.add("دفتر یادداشت")
            topList.add("لیست خرید")
            topList.add("بانوان")
            topList.add("دفترچه تلفن")
            topList.add("دفترچه خاطرات")
            topList.add("یادداشت")
            topList.add("ابزار ها")
            topList.add("ایده نگار")
            topList.add("موزیک پلیر")

            val topListAdapter = DragAndDropSelectFeatureAdapter(topList, this)
            recyclerTop.adapter = topListAdapter
            tvEmptyListTop.setOnDragListener(topListAdapter.dragInstance)
            recyclerTop.setOnDragListener(topListAdapter.dragInstance)
        } catch (e: Exception) {
            Event.ShowErrorForTester(e, javaClass.simpleName)
        }

    }

    private fun setBottomRecyclerView() {
        try {
            recyclerBottom.setHasFixedSize(true)
            recyclerBottom.layoutManager = GridLayoutManager(
                this, 2,
                LinearLayoutManager.VERTICAL, false
            )

            val bottomList: MutableList<String> = ArrayList()
            bottomList.add("کودکانه")
            bottomList.add("اسم فامیل")

            val bottomListAdapter = DragAndDropSelectFeatureAdapter(bottomList, this)
            recyclerBottom.adapter = bottomListAdapter
            tvEmptyListBottom.setOnDragListener(bottomListAdapter.dragInstance)
            recyclerBottom.setOnDragListener(bottomListAdapter.dragInstance)
        } catch (e: Exception) {
            Event.ShowErrorForTester(e, javaClass.simpleName)
        }

    }

    override fun setEmptyListTop(visibility: Boolean) {
        tvEmptyListTop.visibility = if (visibility) View.VISIBLE else View.GONE
        recyclerTop.visibility = if (visibility) View.GONE else View.VISIBLE
    }

    override fun setEmptyListBottom(visibility: Boolean) {
        tvEmptyListBottom.visibility = if (visibility) View.VISIBLE else View.GONE
        recyclerBottom.visibility = if (visibility) View.GONE else View.VISIBLE
    }
}