package com.nahal.developer.family.nahal.ui.activities.testActivities

import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.textfield.TextInputLayout
import com.nahal.developer.family.nahal.R
import com.nahal.developer.family.nahal.adapters.DragAndDropSelectFeatureAdapter
import com.nahal.developer.family.nahal.core.UAppCompatActivity
import com.nahal.developer.family.nahal.event.Event
import com.nahal.developer.family.nahal.event.Event.Companion.showColorSheet
import com.nahal.developer.family.nahal.event.SoundEngine.Companion.PlayClickSound
import com.nahal.developer.family.nahal.madules.fm_DrogableRecyclerView.Listener
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.core.IconButton
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.core.Image
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.core.SheetStyle
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.core.TopStyle
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.duration.DurationSheet
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.duration.DurationTimeFormat
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.info.InfoSheet
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.input.InputSheet
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.input.type.InputCheckBox
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.input.type.InputEditText
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.input.type.InputRadioButtons
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.input.type.spinner.InputSpinner
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.option.DisplayMode
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.option.Option
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.option.OptionSheet
import com.nahal.developer.family.nahal.madules.fm_KBottomSheet.sheets.input.Validation
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern


class TestActivity : UAppCompatActivity(), Listener {
    lateinit var tvEmptyListTop: TextView
    lateinit var tvEmptyListBottom: TextView
    lateinit var relMain: RelativeLayout
    lateinit var recyclerTop: RecyclerView
    lateinit var recyclerBottom: RecyclerView
    lateinit var crdShowColorPicker: MaterialCardView
    lateinit var crdOption: MaterialCardView
    lateinit var crdInfo: MaterialCardView
    lateinit var crdSnooze: MaterialCardView
    lateinit var crdPassword: MaterialCardView
    lateinit var crdForm: MaterialCardView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        initUi()
        setupDrag()
        setupEvent()
        applyThemeMode(ThemeMode.DAY_MODE)
    }

    private fun setupEvent() {
        crdShowColorPicker.setOnClickListener {
            ChangeBackground(relMain)
            PlayClickSound(this)
        }
        crdOption.setOnClickListener {
            showOptionsSheetGridMiddle(DisplayMode.GRID_VERTICAL)
            PlayClickSound(this)
        }
        crdInfo.setOnClickListener {
            showInfoSheetTopStyleTop()
            PlayClickSound(this)
        }
        crdSnooze.setOnClickListener {
            showTimeSheet(DurationTimeFormat.MM_SS)
            PlayClickSound(this)
        }
        crdPassword.setOnClickListener {
            showInputSheetPassword()
            PlayClickSound(this)
        }
        crdForm.setOnClickListener {
            showInputSheetLong()
            PlayClickSound(this)
        }
    }

    private fun showInputSheetLong() {

        InputSheet().show(this) {
            style(SheetStyle.BOTTOM_SHEET)
            title("Short survey")
            with(InputEditText {
                required()
                defaultValue(buildSpannedString {
                    append("The ")
                    bold {
                        append("Office")
                    }
                })
                startIconDrawable(R.drawable.ic_mail)
                label("Your favorite TV-Show")
                hint("The Mandalorian, ...")
                inputType(InputType.TYPE_CLASS_TEXT)
                changeListener { value -> showToast("Text change", value.toString()) }
                resultListener { value -> showToast("Text result", value.toString()) }
            })
            with(InputCheckBox("binge_watching") { // Read value later by index or custom key from bundle
                label("Binge Watching")
                text("I'm regularly binge watching shows on Netflix.")
                defaultValue(false)
                changeListener { value -> showToast("CheckBox change", value.toString()) }
                resultListener { value -> showToast("CheckBox result", value.toString()) }
            })

            with(InputSpinner {
                required()
                drawable(R.drawable.ic_telegram)
                label("Favorite show in the list")
                noSelectionText("Select Show")
                options(
                    mutableListOf(
                        "Westworld",
                        "Fringe",
                        "The Expanse",
                        "Rick and Morty",
                        "Attack on Titan",
                        "Death Note",
                        "Parasite",
                        "Jujutsu Kaisen"
                    )
                )
                changeListener { value -> showToast("Spinner change", value.toString()) }
                resultListener { value -> showToast("Spinner result", value.toString()) }
            })

            with(InputRadioButtons("") {
                required()
                drawable(R.drawable.ic_telegram)
                label("Streaming service of your choice")
                options(mutableListOf("Netflix", "Amazon", "Other"))
                selected(0)
                changeListener { value -> showToast("RadioButton change", value.toString()) }
                resultListener { value -> showToast("RadioButton result", value.toString()) }
            })
            onNegative { showToast("InputSheet cancelled", "No result") }
            onPositive { result ->
                showToastLong("InputSheet result", result.toString())
                val text = result.getString("0") // Read value of inputs by index
                val check = result.getBoolean("binge_watching") // Read value by passed key
            }
        }
    }

    private fun showToast(id: String, value: String?) {
        Toast.makeText(
            this@TestActivity,
            id.plus(": $value"),
            Toast.LENGTH_SHORT
        ).apply { show() }
    }

    private fun showToastLong(id: String, value: String?) {
        Toast.makeText(
            this@TestActivity,
            id.plus(": $value"),
            Toast.LENGTH_LONG
        ).apply { show() }
    }

    private fun showInputSheetPassword() {

        var password1: String? = "1"
        var password2: String?
        val regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";
        val errorText =
            "Must contain at least one digit, lower case letter, upper case letter, special character, no whitespace and at least 8 characters."

        InputSheet().show(this) {
            style(SheetStyle.BOTTOM_SHEET)
            title("Choose a password")
            withIconButton(IconButton(R.drawable.ic_help)) {
                showToast(
                    "IconButton",
                    "Help clicked..."
                )
            }
            with(InputEditText {
                required()
                hint("Password")
                startIconDrawable(R.drawable.ic_lock)
                endIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE)
                passwordVisible(false /* Don't display password in clear text. */)
                validationListener { value ->
                    password1 = value.toString()
                    val pattern = Pattern.compile(regex)
                    val matcher = pattern.matcher(value)
                    val valid = matcher.find()
                    if (valid) Validation.success()
                    else Validation.failed(errorText)
                }
                changeListener { value -> showToast("Text change", value.toString()) }
                resultListener { value -> showToast("Text result", value.toString()) }
            })
            with(InputEditText {
                required()
                startIconDrawable(R.drawable.ic_lock)
                hint("Repeat password")
                endIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE)
                passwordVisible(false)
                validationListener { value ->
                    password2 = value.toString()
                    if (password1 != password2) {
                        Validation.failed("Passwords don't match.")
                    } else Validation.success()
                }
                resultListener { value -> showToast("Text result", value.toString()) }
            })
            onNegative("cancel") { showToast("InputSheet cancelled", "No result") }
            onPositive("register") {
                showToastLong("InputSheet result", "Passwords matched.")
            }
        }
    }

    private fun showTimeSheet(timeFormat: DurationTimeFormat) {

        DurationSheet().show(this) {
            style(SheetStyle.BOTTOM_SHEET)
            title("تایمر بازی پانتومیم")
            format(timeFormat)
            currentTime(
                TimeUnit.HOURS.toSeconds(0)
                    .plus(TimeUnit.MINUTES.toSeconds(50).plus(TimeUnit.SECONDS.toSeconds(12)))
            )
//            currentTime(0) // Set current time in seconds
//            minTime(1) // Set minimum time in seconds
//            maxTime(600) // Set maximum time in seconds
            onPositive { timeInSec ->
                // Use selected time in millis

            }
        }
    }

    private fun showInfoSheetTopStyleTop() {

        InfoSheet().show(this) {
            style(SheetStyle.BOTTOM_SHEET)
            cornerFamily(CornerFamily.CUT)
            topStyle(TopStyle.ABOVE_COVER)
            withCoverImage(Image("https://gorgine.com/wp-content/uploads/2023/10/god_father_16_9-1024x574.webp"))
            withIconButton(IconButton(R.drawable.ic_github)) { /* e. g. open website. */ }
            withIconButton(IconButton(R.drawable.ic_mail)) { /* Will not automatically dismiss the sheet. */ }
            title("پدر خوانده")
            content("او از یک بار شلیک شب لئون مصون است.یک جلیقه دارد.تعیین شلیک شب از طرف گروه به عهده پدرخوانده است و اگر از بازی خارج شود دیگر اعضا به جای او شلیک می کنند.پدرخوانده دارای توانایی حس ششم است و اگر در شب تصمیم بگیرد به جای شلیک از حس ششم استفاده کند باید نقش بازیکنی را درست حدس بزند و توسط گرداننده تائید شود.بازیکنی که پدرخوانده نقش او را درست حدس زده است سلاخی می شود یعنی اگر سپر داشته باشد یا دکتر او را سیو کرده باشد بازهم از بازی خارج می شود و آن شب توانایی وی اعمال نخواهد شد و پس از خروج از بازی توسط کنستانتین قابل احضار نمی باشد.استعلام پدرخوانده برای نوستراداموس شهروندی بوده ولی برای همشهری کین مافیایی و مثبت خواهد بود.")
            onNegative("")
            onPositive("متوجه شدم")
        }
    }

    private fun showOptionsSheetGridMiddle(displayMode: DisplayMode) {

        OptionSheet().show(this) { // Build and show
            style(SheetStyle.BOTTOM_SHEET)
            displayMode(displayMode) // Display mode for list/grid + scroll into height or width
            title("چه موادی برای تهیه غذا دارید؟")
            multipleChoices() // Apply to make it multiple choices
            minChoices(3) // Set minimum choices
            maxChoices(4) // Set maximum choices
            displayMultipleChoicesInfo() // Show info view for selection
            displayButtons()
            with(
                Option(R.drawable.ic_apple, "سیب"),
                Option(
                    R.drawable.ic_fruit_cherries,
                    "گیلاس"
                ).disable(), // An option can be disabled
                Option(R.drawable.ic_food_pasta, "پاستا"),
                Option(R.drawable.ic_fruit_watermelon, "هندوانه"),
                Option(
                    R.drawable.ic_fruit_grapes,
                    "انگور"
                ).select(), // An option can be preselected
                Option(R.drawable.ic_food_burger, "برگر"),
                Option(R.drawable.ic_fruit_pineapple, "آناناس"),
                Option(R.drawable.ic_food_croissant, "کروسان")
            )
            onPositiveMultiple { selectedIndices: MutableList<Int>, selectedOptions: MutableList<Option> ->
                // All selected indices / options
            }
        }
    }

    fun ChangeBackground(view: View) {
        showColorSheet(this, view)
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
        crdOption = findViewById(R.id.crdOption)
        crdInfo = findViewById(R.id.crdInfo)
        crdSnooze = findViewById(R.id.crdSnooze)
        crdPassword = findViewById(R.id.crdPassword)
        crdForm = findViewById(R.id.crdForm)
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