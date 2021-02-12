package com.example.kopaimport android.annotation.SuppressLintimport android.content.Intentimport android.os.Bundleimport android.view.Viewimport android.widget.*import androidx.appcompat.app.AppCompatActivityimport androidx.fragment.app.Fragmentimport androidx.fragment.app.FragmentManagerimport androidx.lifecycle.ViewModelProviderimport androidx.recyclerview.widget.RecyclerViewimport com.example.kopa.fragments.adapters.DeclarationsAdapterimport com.example.kopa.fragments.all_declarations.AllDeclarationsFragmentimport com.example.kopa.fragments.all_declarations.AllDeclarationsFragmentVewModelimport com.example.kopa.fragments.code_input.CodeInputFragmentimport com.example.kopa.fragments.dialog.NumberPickerDialogimport com.example.kopa.fragments.liked_declarations.LikedDeclarationsFragmentimport com.example.kopa.fragments.my_declarations.MyDeclarationsFragmentimport com.example.kopa.fragments.profile.ProfileFragmentimport com.google.android.material.bottomnavigation.BottomNavigationViewimport com.google.android.material.bottomsheet.BottomSheetBehaviorimport com.google.android.material.floatingactionbutton.FloatingActionButtonimport com.google.android.material.textfield.TextInputEditTextimport com.google.firebase.auth.ktx.authimport com.google.firebase.ktx.Firebaseclass BottomNavBarActivity : AppCompatActivity() {    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>    private lateinit var viewModel: AllDeclarationsFragmentVewModel    @SuppressLint("CutPasteId", "SetTextI18n", "ResourceType")    override fun onCreate(savedInstanceState: Bundle?) {        super.onCreate(savedInstanceState)        val arguments = intent.extras        val userID = arguments!!["id"].toString()        var allDeclarationsFragment = AllDeclarationsFragment()        var myDeclarationsFragment = MyDeclarationsFragment(userID)        var likedDeclarationsFragment = LikedDeclarationsFragment(userID)        var profileFragment = ProfileFragment(userID)        viewModel = ViewModelProvider(this).get(AllDeclarationsFragmentVewModel::class.java)        setContentView(R.layout.bottom_nav_bar_activity)        this.replaceFragment(AllDeclarationsFragment())        val bottomNavigationView =            findViewById<View>(R.id.bottomNavigationView) as BottomNavigationView        bottomNavigationView.background = null        bottomNavigationView.menu.getItem(2).isEnabled = false        bottomNavigationView.setOnNavigationItemSelectedListener {            when(it.itemId){                R.id.main_page -> this.replaceFragment(allDeclarationsFragment)                R.id.my_declarations -> this.replaceFragment(myDeclarationsFragment)                R.id.liked -> this.replaceFragment(likedDeclarationsFragment)                R.id.settings -> {                    this.replaceFragment(profileFragment)                    bottomSheetBehavior                }            }            true        }        val bottomLinearLayout = findViewById<View>(R.id.layout_bottom_sheet) as LinearLayout        bottomSheetBehavior = BottomSheetBehavior.from<LinearLayout>(bottomLinearLayout)        bottomSheetBehavior.addBottomSheetCallback(object :            BottomSheetBehavior.BottomSheetCallback() {            override fun onStateChanged(bottomSheet: View, state: Int) {                print(state)                when (state) {                    BottomSheetBehavior.STATE_HIDDEN -> {                    }                    BottomSheetBehavior.STATE_EXPANDED ->                        bottomLinearLayout.findViewById<ImageView>(R.id.sheet_image)                            .setImageResource(                                R.drawable.ic_open_line_bottom_sheet                            )                    BottomSheetBehavior.STATE_COLLAPSED -> {                        bottomLinearLayout.findViewById<ImageView>(R.id.sheet_image)                            .setImageResource(                                R.drawable.ic_line_bottom_sheet                            )                    }                    BottomSheetBehavior.STATE_DRAGGING -> {                    }                    BottomSheetBehavior.STATE_SETTLING -> {                    }                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {                    }                }            }            override fun onSlide(bottomSheet: View, slideOffset: Float) {            }        })        findViewById<TextInputEditText>(R.id.model_input).setOnClickListener {            val newFragment = NumberPickerDialog(                findViewById<TextInputEditText>(R.id.model_input),                "model",findViewById<TextView>(R.id.text_lenght),"1"            )            newFragment.show(supportFragmentManager, "Виберіть модель")        }        findViewById<TextInputEditText>(R.id.material_input).setOnClickListener {            val newFragment = NumberPickerDialog(                findViewById<TextInputEditText>(R.id.material_input),                "material",findViewById<TextView>(R.id.text_lenght),"1"            )            newFragment.show(supportFragmentManager, "Виберіть матеріал")        }        findViewById<TextInputEditText>(R.id.size_input1).setOnClickListener {            val newFragment = NumberPickerDialog(                findViewById<TextInputEditText>(R.id.size_input1),                "size",findViewById<TextView>(R.id.text_lenght),"1"            )            newFragment.show(supportFragmentManager, "Виберіть розмір")        }        findViewById<TextInputEditText>(R.id.size_input2).setOnClickListener {            val newFragment = NumberPickerDialog(                findViewById<TextInputEditText>(R.id.size_input2),                "size",findViewById<TextView>(R.id.text_lenght),"1"            )            newFragment.show(supportFragmentManager, "Виберіть розмір")        }        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {            val intent = Intent(this, CreateDeclarationActivity::class.java)            intent.putExtra("userID", userID)            this.finish()            this.startActivity(intent)        }        val fm: FragmentManager = supportFragmentManager        val recycler = findViewById<RecyclerView>(R.id.declarations_list)        findViewById<TextView>(R.id.apply_button).setOnClickListener {//            allDeclarationsFragment.sort(findViewById<TextInputEditText>(R.id.model_input),//                    findViewById<TextInputEditText>(R.id.material_input),//                    findViewById<TextInputEditText>(R.id.size_input1),//                    findViewById<TextInputEditText>(R.id.size_input2),//                    findViewById<TextInputEditText>(R.id.price_input1),//                    findViewById<TextInputEditText>(R.id.price_input2))//                viewModel.sort(//                    allDeclarationsFragment.adapter,//                    findViewById<TextInputEditText>(R.id.model_input),//                    findViewById<TextInputEditText>(R.id.material_input),//                    findViewById<TextInputEditText>(R.id.size_input1),//                    findViewById<TextInputEditText>(R.id.size_input2),//                    findViewById<TextInputEditText>(R.id.price_input1),//                    findViewById<TextInputEditText>(R.id.price_input2)//                )        }    }    fun AppCompatActivity.replaceFragment(fragment: Fragment){        val fragmentManager = supportFragmentManager        val transaction = fragmentManager.beginTransaction()        transaction.replace(R.id.fragment_container_bar_activity, fragment)        transaction.addToBackStack(null)        transaction.commit()    }}