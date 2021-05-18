package ren.imyan.topline.core

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.Window
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import ren.imyan.activity.BaseActivity
import ren.imyan.topline.R


abstract class BaseUIActivity<viewBinding : ViewBinding, viewModel : ViewModel>(@LayoutRes val layoutID:Int) : BaseActivity() {

    private var _binding: viewBinding? = null
    private var _viewModel: viewModel? = null
    private var isSetView = true
    private var isSetToolbar = true

    val binding get() = _binding!!
    val viewModel get() = _viewModel!!

    fun notSetView() {
        isSetView = false
    }

    fun notSetToolbar() {
        isSetToolbar = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        getThemeManager().setAppTheme()
        _binding = DataBindingUtil.setContentView(this,layoutID)
        _viewModel = initViewModel()
        if (isSetView) {
            setContentView(binding.root)
        }
        if (isSetToolbar) {
            setSupportActionBar(initToolbar().first)
            when (val title = initToolbar().second) {
                is String -> setToolBarTitle(title)
                is Int -> setToolBarTitle(title)
            }
        }
        window.apply {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = getThemeColorFromId(R.attr.colorOnPrimary)
            navigationBarColor = getThemeColorFromId(R.attr.colorSurface)

            if (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK != Configuration.UI_MODE_NIGHT_YES) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    insetsController?.setSystemBarsAppearance(
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                    )
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
            }
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            window?.decorView?.let {
//                it.systemUiVisibility = (it.systemUiVisibility
//                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
//            }
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                window?.decorView?.post {
//                    if (window.decorView.rootWindowInsets?.systemWindowInsetBottom ?: 0 < Resources.getSystem().displayMetrics.density * 40) {
//                        window.navigationBarColor = Color.TRANSPARENT
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//                            window.navigationBarDividerColor = Color.TRANSPARENT
//                        }
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                            window.isNavigationBarContrastEnforced = false
//                            window.isStatusBarContrastEnforced = false
//                        }
//                    }
//                }
//            }
//        }
    }

    abstract fun initViewModel(): viewModel

    abstract fun initToolbar(): Pair<Toolbar, *>

    fun setToolBarTitle(@StringRes titleId: Int) {
        supportActionBar?.setTitle(titleId);
    }

    fun setToolBarTitle(title: CharSequence) {
        supportActionBar?.title = title;
    }

    fun getThemeColorFromId(id: Int): Int {
        val typedValue = TypedValue()
        theme.resolveAttribute(id, typedValue, true)
        return typedValue.data
    }

//    @RequiresApi(Build.VERSION_CODES.N)
//    override fun attachBaseContext(newBase: Context?) {
//        //如果不使用工具类也可以在这里处理好 Locale 传入
//        val context = newBase?.let {
//            wrap(newBase, LanguageUtil.getLocale(newBase))
//        }
//        super.attachBaseContext(context)
//    }
}