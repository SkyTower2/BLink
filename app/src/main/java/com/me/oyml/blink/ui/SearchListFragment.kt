package com.me.oyml.blink.ui

import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.me.oyml.blink.R
import com.me.oyml.blink.databinding.FragmentSearchListBinding
import com.me.oyml.common.base.BaseVBFragment
import com.me.oyml.common.item.VegaLayoutManager

class SearchListFragment : BaseVBFragment<FragmentSearchListBinding>() {
    private val dataList = ArrayList<StockEntity>()
    private var redColor: Int = 0
    private var greenColor: Int = 0
    private lateinit var adapter: RecyclerView.Adapter<*>
    private lateinit var handler: Handler
    private var currentPage = 0

    override fun FragmentSearchListBinding.initBinding() {

        // 3. recyclerView数据填充
        val recyclerView: RecyclerView = mainRecyclerView
        recyclerView.layoutManager = VegaLayoutManager()
        adapter = getAdapter()
        recyclerView.adapter = adapter

        redColor = requireContext().getColor(R.color.teal_200)
        greenColor = requireContext().getColor(R.color.purple_200)

        appendDataList()
        adapter.notifyDataSetChanged()

        // 4. refreshLayout
        refreshLayout.setOnRefreshListener {
            currentPage = 0
            requestHttp()
        }
    }

    private fun requestHttp() {
        if (!this::handler.isInitialized) {
            handler = Handler()
        }

        handler.postDelayed({
            mBinding.refreshLayout.isRefreshing = false
            appendDataList()
            adapter.notifyDataSetChanged()
        }, 900)
    }

    private fun appendDataList() {
        if (currentPage == 0) {
            dataList.clear()
        }
        currentPage++

        dataList.add(StockEntity("Google Inc.", 921.59f, 1, "+6.59 (+0.72%)"))
        dataList.add(StockEntity("Apple Inc.", 158.73f, 1, "+0.06 (+0.04%)"))
        dataList.add(StockEntity("Vmware Inc.", 109.74f, -1, "-0.24 (-0.22%)"))
        dataList.add(StockEntity("Microsoft Inc.", 75.44f, 1, "+0.28 (+0.37%)"))
        dataList.add(StockEntity("Facebook Inc.", 172.52f, 1, "+2.51 (+1.48%)"))
        dataList.add(StockEntity("IBM Inc.", 144.40f, -1, "-0.15 (-0.10%)"))
        dataList.add(StockEntity("Alibaba Inc.", 180.04f, 1, "+0.06 (+0.03%)"))
        dataList.add(StockEntity("Tencent Inc.", 346.400f, 1, "+2.200 (+0.64%)"))
        dataList.add(StockEntity("Baidu Inc.", 237.92f, -1, "-1.15 (-0.48%)"))
        dataList.add(StockEntity("Amazon Inc.", 969.47f, -1, "-4.72 (-0.48%)"))
        dataList.add(StockEntity("Oracle Inc.", 48.03f, -1, "-0.30 (-0.62%)"))
        dataList.add(StockEntity("Intel Inc.", 37.22f, 1, "+0.22 (+0.61%)"))
        dataList.add(StockEntity("Cisco Systems Inc.", 32.49f, -1, "-0.03 (-0.08%)"))
        dataList.add(StockEntity("Qualcomm Inc.", 52.30f, 1, "+0.05 (+0.10%)"))
        dataList.add(StockEntity("Sony Inc.", 37.65f, -1, "-0.74 (-1.93%)"))
    }

    private fun getAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder> {
        return object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                val view = layoutInflater.inflate(R.layout.recycler_item, parent, false)
                return MyViewHolder(view)
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                val myHolder = holder as MyViewHolder
                myHolder.bindData(dataList[position])

                if (position == dataList.size - 1) {
                    requestHttp()
                }
            }

            override fun getItemCount(): Int = dataList.size
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTv: TextView = itemView.findViewById(R.id.item_name_tv)
        private val currentPriceTv: TextView = itemView.findViewById(R.id.item_current_price)
        private val trendFlagIv: ImageView = itemView.findViewById(R.id.item_trend_flag)
        private val grossTv: TextView = itemView.findViewById(R.id.item_gross)

        fun bindData(stockEntity: StockEntity) {
            nameTv.text = stockEntity.name
            currentPriceTv.text = "$${stockEntity.price}"
            trendFlagIv.setImageResource(if (stockEntity.flag > 0) R.drawable.up_red else R.drawable.down_green)
            grossTv.text = stockEntity.gross
            grossTv.setTextColor(if (stockEntity.flag > 0) redColor else greenColor)
        }
    }
}