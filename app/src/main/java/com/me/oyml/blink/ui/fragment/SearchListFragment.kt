package com.me.oyml.blink.ui.fragment

import android.annotation.SuppressLint
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.alibaba.android.arouter.launcher.ARouter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.me.oyml.blink.R
import com.me.oyml.blink.bean.BleRssiDevice
import com.me.oyml.blink.constants.ARouterPath
import com.me.oyml.blink.databinding.SearchListFragmentBinding
import com.me.oyml.common.base.BaseVBFragment
import com.me.oyml.common.item.VegaLayoutManager
import com.me.oyml.common.utils.KLog
import com.me.oyml.module_ble.Ble
import com.me.oyml.module_ble.callback.BleScanCallback
import com.me.oyml.module_ble.model.ScanRecord

class SearchListFragment : BaseVBFragment<SearchListFragmentBinding>() {
    private var redColor: Int = 0
    private var greenColor: Int = 0
    private lateinit var adapter: RecyclerView.Adapter<*>
    private var currentPage = 0

    private lateinit var floatingActionButton: FloatingActionButton
    private val ble: Ble<BleRssiDevice> = Ble.getInstance()
    private lateinit var bleRssiDevices: ArrayList<BleRssiDevice>

    override fun SearchListFragmentBinding.initBinding() {

        bleRssiDevices = ArrayList()

        // 悬浮按钮
        floatingActionButton = floatingButton

        // recyclerView数据填充
        val recyclerView: RecyclerView = mainRecyclerView
        recyclerView.layoutManager = VegaLayoutManager()
        adapter = getAdapter()
        recyclerView.adapter = adapter
        // 设置RecyclerView的Item动画，关闭动画
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        redColor = requireContext().getColor(R.color.red)
        greenColor = requireContext().getColor(R.color.green)

        // 下拉刷新
        refreshLayout.setOnRefreshListener {
            currentPage = 0
            rescan()
            KLog.d("下拉刷新")
        }

        floatingActionButton.setOnClickListener {
            rescan()
        }
    }

    private fun rescan() {
        if (ble != null && !ble.isScanning) {
            bleRssiDevices.clear()
            adapter.notifyDataSetChanged()
            ble.startScan(scanCallback)
        }
    }

    private val scanCallback = object : BleScanCallback<BleRssiDevice>() {
        override fun onLeScan(device: BleRssiDevice, rssi: Int, scanRecord: ByteArray) {
            synchronized(ble.locker) {
                for (i in bleRssiDevices.indices) {
                    val rssiDevice = bleRssiDevices[i]
                    if (TextUtils.equals(rssiDevice.bleAddress, device.bleAddress)) {
                        if (rssiDevice.rssi != rssi && System.currentTimeMillis() - rssiDevice.rssiUpdateTime > 1000L) {
                            rssiDevice.rssiUpdateTime = System.currentTimeMillis()
                            rssiDevice.rssi = rssi
                            adapter.notifyItemChanged(i)
                        }
                        return
                    }
                }
                device.scanRecord = ScanRecord.parseFromBytes(scanRecord)
                device.rssi = rssi
                bleRssiDevices.add(device)

                // ⭐⭐ 重点：每次新增后都按rssi重新排序 ⭐⭐
//                bleRssiDevices.sortBy { it.rssi * -1 } // 越大排越前面
//                adapter.notifyDataSetChanged()
            }
        }

        override fun onStart() {
            super.onStart()
            startBannerLoadingAnim()
        }

        override fun onStop() {
            super.onStop()
            stopBannerLoadingAnim()
        }

        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)
            KLog.e("onScanFailed: $errorCode")
        }
    }

    private fun startBannerLoadingAnim() {
        // 设置悬浮按钮图标搜索状态
        floatingActionButton.setImageResource(R.drawable.ic_loading)
    }

    private fun stopBannerLoadingAnim() {
        // 重新设置悬浮按钮图标
        floatingActionButton.setImageResource(R.drawable.ic_bluetooth_audio_black_24dp)
        floatingActionButton.rotation = 0f
        // 停止下拉刷新
        mBinding.refreshLayout.isRefreshing = false
    }

    private fun getAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder> {
        return object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): RecyclerView.ViewHolder {
                val view = layoutInflater.inflate(R.layout.recycler_item, parent, false)
                return MyViewHolder(view)
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                val myHolder = holder as MyViewHolder

                if (bleRssiDevices.size > position) {
                    myHolder.bindData(bleRssiDevices[position])
                }
            }

            override fun getItemCount(): Int = bleRssiDevices.size
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // 蓝牙设备名称
        private val nameBle: TextView = itemView.findViewById(R.id.item_name_ble)

        // 蓝牙设备地址
        private val macBle: TextView = itemView.findViewById(R.id.item_mac_ble)

        // 蓝牙设备信号强度
        private val rssiBle: TextView = itemView.findViewById(R.id.item_rssi_ble)

        // 蓝牙连接
        private val connectBle: Button = itemView.findViewById(R.id.item_connect_ble)

        // 蓝牙设备menu
        private val menuBle: ImageButton = itemView.findViewById(R.id.item_select_menu)

        // 使用注解临时禁用 lint 检查
        @SuppressLint("SetTextI18n")
        fun bindData(bleRssiDevice: BleRssiDevice) {
            rssiBle.text = "%ddBm".format(bleRssiDevice.rssi)
            macBle.text = bleRssiDevice.bleAddress

            // 判断蓝牙名称是否为空
            if (TextUtils.isEmpty(bleRssiDevice.bleName)) {
                nameBle.text = "N/A"
            } else {
                nameBle.text = bleRssiDevice.bleName
            }

            // 设置蓝牙信号强度dbm字体颜色
            if (bleRssiDevice.rssi > -70) {
                rssiBle.setTextColor(greenColor)
            } else if (bleRssiDevice.rssi < -90) {
                rssiBle.setTextColor(redColor)
            } else {
                rssiBle.setTextColor(requireContext().getColor(R.color.colorAccent))
            }

            // 连接按钮点击事件
            connectBle.setOnClickListener {
                KLog.d("点击连接按钮: ${bleRssiDevice.bleName} (${bleRssiDevice.bleAddress})")

                ARouter.getInstance()
                    .build(ARouterPath.DEVICE_DETAIL)
                    .navigation()
            }

            // 更多按钮点击事件
            menuBle.setOnClickListener {
                KLog.d("点击更多按钮: ${bleRssiDevice.bleAddress}")
            }
        }
    }
}