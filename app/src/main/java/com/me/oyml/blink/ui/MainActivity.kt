package com.me.oyml.blink.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils.replace
import android.util.Log
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.CompoundButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.me.oyml.blink.R
import com.me.oyml.blink.databinding.MainActivityBinding
import com.me.oyml.blink.utils.CrossfadeWrapper
import com.me.oyml.common.base.BaseVBActivity
import com.me.oyml.common.utils.KLog
import com.mikepenz.crossfader.Crossfader
import com.mikepenz.crossfader.view.CrossFadeSlidingPaneLayout
import com.mikepenz.materialdrawer.holder.BadgeStyle
import com.mikepenz.materialdrawer.interfaces.OnCheckedChangeListener
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import com.mikepenz.materialdrawer.model.SectionDrawerItem
import com.mikepenz.materialdrawer.model.SwitchDrawerItem
import com.mikepenz.materialdrawer.model.ToggleDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.Nameable
import com.mikepenz.materialdrawer.model.interfaces.withBadge
import com.mikepenz.materialdrawer.model.interfaces.withBadgeStyle
import com.mikepenz.materialdrawer.model.interfaces.withChecked
import com.mikepenz.materialdrawer.model.interfaces.withDescription
import com.mikepenz.materialdrawer.model.interfaces.withIcon
import com.mikepenz.materialdrawer.model.interfaces.withIdentifier
import com.mikepenz.materialdrawer.model.interfaces.withName
import com.mikepenz.materialdrawer.model.interfaces.withSelectable
import com.mikepenz.materialdrawer.model.interfaces.withTag
import com.mikepenz.materialdrawer.model.utils.withIsHiddenInMiniDrawer
import com.mikepenz.materialdrawer.widget.AccountHeaderView
import com.mikepenz.materialdrawer.widget.MaterialDrawerSliderView
import com.mikepenz.materialdrawer.widget.MiniDrawerSliderView
import com.mikepenz.materialize.util.UIUtils


class MainActivity : BaseVBActivity<MainActivityBinding>() {
    private val that = this

    //save our header or result
    private lateinit var headerView: AccountHeaderView
    private lateinit var miniSliderView: MiniDrawerSliderView
    private lateinit var sliderView: MaterialDrawerSliderView
    private lateinit var crossFader: Crossfader<*>

    private val onCheckedChangeListener = object : OnCheckedChangeListener {
        override fun onCheckedChanged(
            drawerItem: IDrawerItem<*>,
            buttonView: CompoundButton,
            isChecked: Boolean
        ) {
            if (drawerItem is Nameable) {
                Log.i(
                    "material-drawer",
                    "DrawerItem: " + (drawerItem as Nameable).name + " - toggleChecked: " + isChecked
                )
            } else {
                Log.i("material-drawer", "toggleChecked: $isChecked")
            }
        }
    }

    override fun initView() {
        // 在setContentView之前设置回正常主题
        setTheme(R.style.SampleApp_DayNight)
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        // 创建侧边栏头部
        setSupportActionBar(mBinding.toolbar)
        // 设置侧边栏头部标题
        supportActionBar?.setTitle(R.string.app_name)
        supportActionBar?.setIcon(R.drawable.blink_app_icon)
        // 设置侧边栏头部背景
        supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(
                    this@MainActivity,
                    R.color.colorPrimary
                )
            )
        )

        sliderView = MaterialDrawerSliderView(that).apply {
            customWidth = MATCH_PARENT
            itemAdapter.add(
                // 搜索列表：支持多设备连接，默认首页
                PrimaryDrawerItem().withName("搜索列表").withIcon(R.drawable.ico_account)
                    .withIdentifier(1),
                // 以弹窗的形式设置条件：蓝牙名称、RSSI信号范围、蓝牙模式； 设置完蓝牙名称后要出现在搜索设置后面
                PrimaryDrawerItem().withName("搜索设置").withIcon(R.drawable.ico_account)
                    .withIdentifier(100)
                    .withIsHiddenInMiniDrawer(true).withDescription("bbq"),
                // 收藏：显示所有已收藏的设备，不管设备现在是否广播，对于现在广播的需要高亮显示，可以管理（断开、重连、收藏、取消收藏）
                PrimaryDrawerItem().withName("收藏").withIcon(R.drawable.ico_account)
                    .withBadge("22")
                    .withBadgeStyle(BadgeStyle(Color.RED, Color.RED)).withIdentifier(2)
                    .withSelectable(false),
                PrimaryDrawerItem().withName("权限管理").withIcon(R.drawable.ico_account)
                    .withIdentifier(3),
                PrimaryDrawerItem().withName("关于").withIcon(R.drawable.ico_account)
                    .withIdentifier(4),
                PrimaryDrawerItem().withDescription("A more complex sample").withName("000888")
                    .withIcon(R.drawable.ico_account).withIdentifier(5),
                SectionDrawerItem().withName("77789999"),
                SecondaryDrawerItem().withName("OTA升级").withIcon(R.drawable.ico_account),
                SecondaryDrawerItem().withName("使用引导").withIcon(R.drawable.ico_account)
                    .withTag("Bullhorn"),
                DividerDrawerItem(),
                SwitchDrawerItem().withName("开启持续搜索").withIcon(R.drawable.ico_account)
                    .withChecked(true)
                    .withOnCheckedChangeListener(onCheckedChangeListener),
                ToggleDrawerItem().withName("搜索设备").withIcon(R.drawable.ico_account)
                    .withChecked(true)
                    .withOnCheckedChangeListener(onCheckedChangeListener)
            )
            onDrawerItemClickListener = { v, item, position ->
                if (item is Nameable) {
                    Toast.makeText(
                        this@MainActivity,
                        item.name?.getText(this@MainActivity),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                false
            }
        }

        miniSliderView = MiniDrawerSliderView(this).apply {
            drawer = sliderView
        }

        // 侧边栏展开后宽度
        val firstWidth = UIUtils.convertDpToPixel(300f, that).toInt()
        // 侧边栏收缩后宽度
        val secondWidth = UIUtils.convertDpToPixel(78f, that).toInt()

        //create and build our crossfader (see the MiniDrawer is also builded in here, as the build method returns the view to be used in the crossfader)
        //the crossfader library can be found here: https://github.com/mikepenz/Crossfader
        crossFader = Crossfader<CrossFadeSlidingPaneLayout>()
            .withContent(findViewById<View>(R.id.crossfade_content))
            .withFirst(sliderView, firstWidth)
            .withSecond(miniSliderView, secondWidth)
            .withSavedInstance(savedInstanceState)
            .build()

        // 自动切换侧边栏
        miniSliderView.crossFader = CrossfadeWrapper(crossFader)

        // 设置侧边栏阴影
        crossFader.crossFadeSlidingPaneLayout.setShadowResourceLeft(com.mikepenz.materialdrawer.R.drawable.material_drawer_shadow_right)

        // 添加SearchListFragment到容器中
        supportFragmentManager.beginTransaction()
            .replace(R.id.crossfade_content, SearchListFragment())
            .setReorderingAllowed(true)
            .commit()
    }

    override fun MainActivityBinding.initBinding() {
        KLog.d("MainActivityBinding.initBinding")
    }

    override fun onSaveInstanceState(_outState: Bundle) {
        var outState = _outState
        //add the values, which need to be saved from the drawer to the bundle
        if (::sliderView.isInitialized) {
            outState = sliderView.saveInstanceState(outState)
        }
        //add the values, which need to be saved from the accountHeader to the bundle
        if (::headerView.isInitialized) {
            outState = headerView.saveInstanceState(outState)
        }
        //add the values, which need to be saved from the crossFader to the bundle
        if (::crossFader.isInitialized) {
            outState = crossFader.saveInstanceState(outState)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (crossFader.isCrossFaded) {
            crossFader.crossFade()
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        private const val PROFILE_SETTING = 1
    }

    override fun initObserve() {

    }
}