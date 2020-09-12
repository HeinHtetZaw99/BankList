package com.daniel.banklist.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.app.NotificationManager
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.StrictMode
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.daniel.banklist.R
import com.daniel.banklist.components.interfaces.GenericErrorMessageFactory
import com.daniel.banklist.models.vos.ReturnResult
import com.daniel.banklist.models.vos.ReturnResult.ErrorResult
import com.daniel.banklist.showLogD
import com.daniel.banklist.showLogE
import com.daniel.banklist.viewmodels.BaseViewModel
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.DaggerAppCompatActivity
import dagger.android.support.HasSupportFragmentInjector
import java.util.*
import javax.inject.Inject
import kotlin.reflect.KClass

/**Created by Daniel McCoy @ 25th Feb 2020*/
abstract class BaseActivity<VM : BaseViewModel> : DaggerAppCompatActivity(), HasActivityInjector,
    HasSupportFragmentInjector {

    abstract val rootLayout: View?

    @Inject
    lateinit var dispatchingAndroidActivityInjector:
            DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidActivityInjector

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var genericErrorMessageFactory: GenericErrorMessageFactory

    protected val notificationManager: NotificationManager by lazy { getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }

    protected abstract val viewModel: VM

    abstract fun loadData()

    abstract fun initUI()

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        val policy: StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder().permitAll().build()

        StrictMode.setThreadPolicy(policy)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }


    fun showErrorSnackBar(view: View, msg: String) {
        val snackBar = Snackbar.make(
            view,
            msg,
            BaseTransientBottomBar.LENGTH_LONG
        )
        snackBar.view.setBackgroundColor(
            ContextCompat.getColor(
                view.context,
                R.color.error
            )
        )
        snackBar.setAction(view.context.getText(R.string.btn_msg_ok)) {
            snackBar.dismiss()
        }
        snackBar.setActionTextColor(ContextCompat.getColor(view.context, R.color.white))
        snackBar.show()
    }

    /**For Showing snackbar with action btn
     * @param view -> parent view where the snackbar will be displayed
     * @param returnResult -> error data with status
     */
    fun showSnackBar(view: View, returnResult: ReturnResult<*>) {
        val snackBar: Snackbar
        when (returnResult) {
            is ErrorResult -> {
                snackBar = Snackbar.make(
                    view,
                    getContentMsg(returnResult),
                    BaseTransientBottomBar.LENGTH_LONG
                )
                snackBar.view.setBackgroundColor(
                    ContextCompat.getColor(
                        view.context,
                        R.color.error
                    )
                )
            }
            is ReturnResult.NetworkErrorResult -> {
                snackBar = Snackbar.make(
                    view,
                    view.context.getString(R.string.error_no_internet),
                    BaseTransientBottomBar.LENGTH_LONG
                )
                snackBar.view.setBackgroundColor(
                    ContextCompat.getColor(
                        view.context,
                        R.color.error
                    )
                )

            }

            is ReturnResult.PositiveResult -> {
                snackBar = Snackbar.make(
                    view,
                    getContentMsg(returnResult),
                    BaseTransientBottomBar.LENGTH_LONG
                )
                snackBar.view.setBackgroundColor(
                    ContextCompat.getColor(
                        view.context,
                        R.color.colorPrimary
                    )
                )
            }
            else -> {
                //do nothing
                showLogE("There's nothing to show with snackbar")
                return
            }
        }
        snackBar.setAction(view.context.getText(R.string.btn_msg_ok)) {
            snackBar.dismiss()
        }
        snackBar.setActionTextColor(ContextCompat.getColor(view.context, R.color.white))
        snackBar.show()
    }

    /**helper method for returning String resources from ErrorVO
     * ReturnResult.msg or ReturnResult.errorMsg can be either a String or a String Resource ID ( For Localization Purpose) */
    fun getContentMsg(returnResult: ReturnResult<*>): String {
        when (returnResult) {
            is ErrorResult -> {
                return returnResult.let {
                    return@let if (it.errorMsg is Int)
                        getString(it.errorMsg as Int)
                    else
                        it.errorMsg.toString()
                }
            }
            is ReturnResult.PositiveResult -> {
                return returnResult.let {
                    return@let when (it.data) {
                        is Int -> getString(it.data as Int)
                        is String -> it.data.toString()
                        else -> {
                            showLogD("getContentMsg(${it.toString()})")
                            ""
                        }
                    }
                }
            }
            is ReturnResult.ValidationErrorResult -> {
                return returnResult.let {
                    return@let if (it.msg is Int)
                        getString(it.msg as Int)
                    else
                        it.msg.toString()
                }
            }
            is ReturnResult.NetworkErrorResult -> {
                return getString(R.string.error_no_internet)
            }
            else -> return ""
        }
    }


    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingAndroidInjector

    /**
     * Helper function for easily init of viewModel
     */
    inline fun <reified VM : BaseViewModel> viewModels(): Lazy<VM> =
        ViewModelLazy(VM::class)


    inner class ViewModelLazy<VM : ViewModel>(
        private val viewModelClass: KClass<VM>
    ) : Lazy<VM> {
        private var cached: VM? = null

        override val value: VM
            get() {
                var viewModel = cached
                if (viewModel == null) {
                    viewModel = ViewModelProvider(
                        this@BaseActivity,
                        viewModelFactory
                    ).get(viewModelClass.java)
                    cached = viewModel
                }
                return viewModel
            }

        override fun isInitialized() = cached != null
    }


    /**method for hiding keyboard from the screen*/
    fun hideSoftKeyboard() {
        val inputMethodManager =
            this.getSystemService(
                Activity.INPUT_METHOD_SERVICE
            ) as InputMethodManager
        try {
            inputMethodManager.hideSoftInputFromWindow(
                (this.currentFocus)?.windowToken, 0
            )
        } catch (npe: NullPointerException) {
            showLogE("hideSoftKeyboard", npe.toString())
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    /**For making pairs of optionsBundle for share element transition */
    open fun makeOptionsBundle(activity: Activity, viewList: Array<View>): Bundle? {
        val pairList: MutableList<Pair<View, String>> = ArrayList()
        for (view in viewList) {
            pairList.add(
                Pair.create(
                    view, view.transitionName
                )
            )
        }
        val options = getOptions(activity, pairList)
        return if (options == null) Bundle() else options.toBundle()
    }


    /**
     * helper function for @{makeOptionsBundle(Activity parentActivity, View[] viewList)}
     */
    protected open fun getOptions(
        activity: Activity,
        list: List<Pair<View, String>>
    ): ActivityOptionsCompat? {
        val options: ActivityOptionsCompat
        if (list.size > 1) {
            when (list.size) {
                2 -> {
                    options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity, list[0], list[1]
                    )
                    return options
                }
                4 -> {
                    options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity,
                        list[0], list[1], list[2], list[3]
                    )
                    return options
                }
                6 -> {
                    options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity,
                        list[0], list[1], list[2], list[3], list[4], list[5]
                    )
                    return options
                }
            }
        }
        return null
    }
}