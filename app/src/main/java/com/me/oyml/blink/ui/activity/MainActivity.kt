package com.me.oyml.blink.ui.activity

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.CompoundButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.me.oyml.blink.R
import com.me.oyml.blink.databinding.MainActivityBinding
import com.me.oyml.blink.ui.fragment.SearchListFragment
import com.me.oyml.blink.ui.fragment.SearchSettingsFragment
import com.me.oyml.blink.utils.CrossfadeWrapper
import com.me.oyml.blink.viewmodel.MainViewModel
import com.me.oyml.common.base.BaseVBActivity
import com.me.oyml.common.utils.KLog
import com.mikepenz.crossfader.Crossfader
import com.mikepenz.crossfader.view.CrossFadeSlidingPaneLayout
import com.mikepenz.materialdrawer.holder.BadgeStyle
import com.mikepenz.materialdrawer.interfaces.OnCheckedChangeListener
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import com.mikepenz.materialdrawer.model.SwitchDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.withBadgeStyle
import com.mikepenz.materialdrawer.model.interfaces.withChecked
import com.mikepenz.materialdrawer.model.interfaces.withDescription
import com.mikepenz.materialdrawer.model.interfaces.withIcon
import com.mikepenz.materialdrawer.model.interfaces.withIdentifier
import com.mikepenz.materialdrawer.model.interfaces.withName
import com.mikepenz.materialdrawer.model.interfaces.withSelectable
import com.mikepenz.materialdrawer.model.interfaces.withSelected
import com.mikepenz.materialdrawer.model.interfaces.withTag
import com.mikepenz.materialdrawer.widget.AccountHeaderView
import com.mikepenz.materialdrawer.widget.MaterialDrawerSliderView
import com.mikepenz.materialdrawer.widget.MiniDrawerSliderView
import com.mikepenz.materialize.util.UIUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseVBActivity<MainActivityBinding>() {
    private val that = this

    private val viewModel: MainViewModel by viewModels()

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
            viewModel.onSwitchChanged(drawerItem.identifier, isChecked)
        }
    }

    override fun initView() {
        // 在setContentView之前设置回正常主题
        setTheme(R.style.SampleApp_DayNight)

        // 延迟执行权限申请
        lifecycleScope.launch {
            delay(500)
            requestPermissionsIfNeeded()
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        // 创建侧边栏头部
        setSupportActionBar(mBinding.toolbar)
        // 设置侧边栏头部标题
        supportActionBar?.setTitle(R.string.app_name)
//        supportActionBar?.setIcon(R.drawable.app_blink_icon)
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
                PrimaryDrawerItem()
                    .withName(R.string.blink_search_list)
                    .withIcon(R.drawable.liebiaoxingshi)
                    .withIdentifier(1)
                    .withSelected(true), // 默认选中此项,
                PrimaryDrawerItem()
                    .withName(R.string.blink_search_settings)
                    .withIcon(R.drawable.sousuoshezhi)
                    .withIdentifier(2)
//                    .withIsHiddenInMiniDrawer(true) // 隐藏不显示在侧边栏
                    .withDescription(R.string.blink_search_settings_subtitle),
                PrimaryDrawerItem()
                    .withName(R.string.blink_collect)
                    .withIcon(R.drawable.shoucang)
//                    .withBadge("22") // 角标
                    .withBadgeStyle(BadgeStyle(Color.RED, Color.RED))
                    .withIdentifier(3)
                    .withSelectable(false),
                PrimaryDrawerItem()
                    .withName(R.string.blink_permission_management)
                    .withIcon(R.drawable.quanxianguanli)
                    .withIdentifier(4),
                PrimaryDrawerItem()
                    .withName(R.string.blink_about)
                    .withIcon(R.drawable.guanyu)
                    .withIdentifier(5),
                DividerDrawerItem(),// 分割线
                SecondaryDrawerItem()
                    .withName(R.string.blink_ota_update)
                    .withIcon(R.drawable.otashengji)
                    .withIdentifier(6),
                SecondaryDrawerItem()
                    .withName(R.string.blink_use_guide)
                    .withIcon(R.drawable.shiyongyindao)
                    .withTag("Bullhorn")
                    .withIdentifier(7),
                DividerDrawerItem(),
                SwitchDrawerItem()
                    .withName(R.string.blink_start_continuous_search)
                    .withIcon(R.drawable.sousuo)
                    .withChecked(true)
                    .withIdentifier(8)
                    .withOnCheckedChangeListener(onCheckedChangeListener)
            )
            onDrawerItemClickListener = { _, item, _ ->
                viewModel.onDrawerItemClicked(item.identifier)
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

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (crossFader.isCrossFaded) {
            crossFader.crossFade()
        } else {
            super.onBackPressed()
        }
    }

    override fun initObserve() {
        viewModel.navCommand.observe(this) { command ->
            when (command) {

                // 搜索列表
                is MainViewModel.NavCommand.ShowSearchList -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.crossfade_content, SearchListFragment())
                        .commit()
                }

                // 加载设置
                is MainViewModel.NavCommand.ShowSearchSettings -> {
                    KLog.d("加载设置")
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.crossfade_content, SearchSettingsFragment())
                        .commit()
                }

                // 加载收藏页
                is MainViewModel.NavCommand.ShowCollect -> {
                    KLog.d("加载收藏页")
                }

                // 加载权限管理页
                is MainViewModel.NavCommand.ShowPermission -> {
                    KLog.d("加载权限管理页")
                }

                // 加载关于页
                is MainViewModel.NavCommand.ShowAbout -> {
                    KLog.d("加载关于页")
                }

                // 加载OTA升级页
                is MainViewModel.NavCommand.ShowOta -> {
                    KLog.d("加载OTA升级页")
                }

                // 加载使用指南页
                is MainViewModel.NavCommand.ShowGuide -> {
                    KLog.d("加载使用指南页")
                }
            }
        }

        viewModel.permissionsGranted.observe(this) { granted ->
            if (granted) {
                KLog.d("所有权限已授予，继续初始化逻辑")
                // 可继续初始化你的业务逻辑
            } else {
                KLog.e("部分权限未授予")
                // 可跳转到说明页面或提示弹窗
            }
        }
    }

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            viewModel.onPermissionsResult(permissions)
        }

    private fun requestPermissionsIfNeeded() {
        val requiredPermissions = mutableListOf<String>()

        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            != android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            requiredPermissions.add(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_SCAN)
            != android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            requiredPermissions.add(android.Manifest.permission.BLUETOOTH_SCAN)
        }

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_ADVERTISE)
            != android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            requiredPermissions.add(android.Manifest.permission.BLUETOOTH_ADVERTISE)
        }

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT)
            != android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            requiredPermissions.add(android.Manifest.permission.BLUETOOTH_CONNECT)
        }

        if (requiredPermissions.isNotEmpty()) {
            permissionLauncher.launch(requiredPermissions.toTypedArray())
        } else {
            viewModel.onPermissionsResult(requiredPermissions.associateWith { true })
        }
    }
}