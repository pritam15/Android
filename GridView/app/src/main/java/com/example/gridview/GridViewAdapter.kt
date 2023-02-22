package com.example.gridview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class GridViewAdapter(var context: Context,var productList:ArrayList<ProductModel>): BaseAdapter() {
    override fun getCount(): Int {
      return productList.size
    }

    override fun getItem(p0: Int): Any? {
        return  null
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View= LayoutInflater.from(parent!!.context).inflate(R.layout.card_layout,parent,false)
        var image: ImageView = view.findViewById(R.id.image)
        var productName : TextView = view.findViewById(R.id.productName)
        var productDesc : TextView = view.findViewById(R.id.productDescprition)
        var productPrice: TextView = view.findViewById(R.id.productPrice)

        image.setImageResource(productList.get(position).img)
        productName.text = productList.get(position).productName
        productDesc.text = productList.get(position).productDesc
        productPrice.text = productList.get(position).price

        return view
    }

}
