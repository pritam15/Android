package com.example.gridview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var gridView: GridView
        gridView = findViewById(R.id.gridView)
        var productList = ArrayList<ProductModel>()
        productList.add(ProductModel(R.drawable.mac_book,"Mac Book", "Macbook Air M1 - (8Gb/256GB SSD/Mac OS Big Sur)","88,990"))
        productList.add(ProductModel(R.drawable.mac_book,"Mac Book", "Macbook Air M1 - (8Gb/256GB SSD/Mac OS Big Sur)","88,990"))
        productList.add(ProductModel(R.drawable.mac_book,"Mac Book", "Macbook Air M1 - (8Gb/256GB SSD/Mac OS Big Sur)","88,990"))
        productList.add(ProductModel(R.drawable.mac_book,"Mac Book", "Macbook Air M1 - (8Gb/256GB SSD/Mac OS Big Sur)","88,990"))
        productList.add(ProductModel(R.drawable.mac_book,"Mac Book", "Macbook Air M1 - (8Gb/256GB SSD/Mac OS Big Sur)","88,990"))
        productList.add(ProductModel(R.drawable.mac_book,"Mac Book", "Macbook Air M1 - (8Gb/256GB SSD/Mac OS Big Sur)","88,990"))
        productList.add(ProductModel(R.drawable.mac_book,"Mac Book", "Macbook Air M1 - (8Gb/256GB SSD/Mac OS Big Sur)","88,990"))
        productList.add(ProductModel(R.drawable.mac_book,"Mac Book", "Macbook Air M1 - (8Gb/256GB SSD/Mac OS Big Sur)","88,990"))
        productList.add(ProductModel(R.drawable.mac_book,"Mac Book", "Macbook Air M1 - (8Gb/256GB SSD/Mac OS Big Sur)","88,990"))
        productList.add(ProductModel(R.drawable.mac_book,"Mac Book", "Macbook Air M1 - (8Gb/256GB SSD/Mac OS Big Sur)","88,990"))
        productList.add(ProductModel(R.drawable.mac_book,"Mac Book", "Macbook Air M1 - (8Gb/256GB SSD/Mac OS Big Sur)","88,990"))
        productList.add(ProductModel(R.drawable.mac_book,"Mac Book", "Macbook Air M1 - (8Gb/256GB SSD/Mac OS Big Sur)","88,990"))
        productList.add(ProductModel(R.drawable.mac_book,"Mac Book", "Macbook Air M1 - (8Gb/256GB SSD/Mac OS Big Sur)","88,990"))
        productList.add(ProductModel(R.drawable.mac_book,"Mac Book", "Macbook Air M1 - (8Gb/256GB SSD/Mac OS Big Sur)","88,990"))
        productList.add(ProductModel(R.drawable.mac_book,"Mac Book", "Macbook Air M1 - (8Gb/256GB SSD/Mac OS Big Sur)","88,990"))
        productList.add(ProductModel(R.drawable.mac_book,"Mac Book", "Macbook Air M1 - (8Gb/256GB SSD/Mac OS Big Sur)","88,990"))
        productList.add(ProductModel(R.drawable.mac_book,"Mac Book", "Macbook Air M1 - (8Gb/256GB SSD/Mac OS Big Sur)","88,990"))
        productList.add(ProductModel(R.drawable.mac_book,"Mac Book", "Macbook Air M1 - (8Gb/256GB SSD/Mac OS Big Sur)","88,990"))
        productList.add(ProductModel(R.drawable.mac_book,"Mac Book", "Macbook Air M1 - (8Gb/256GB SSD/Mac OS Big Sur)","88,990"))
        productList.add(ProductModel(R.drawable.mac_book,"Mac Book", "Macbook Air M1 - (8Gb/256GB SSD/Mac OS Big Sur)","88,990"))
        productList.add(ProductModel(R.drawable.mac_book,"Mac Book", "Macbook Air M1 - (8Gb/256GB SSD/Mac OS Big Sur)","88,990"))
        productList.add(ProductModel(R.drawable.mac_book,"Mac Book", "Macbook Air M1 - (8Gb/256GB SSD/Mac OS Big Sur)","88,990"))
        productList.add(ProductModel(R.drawable.mac_book,"Mac Book", "Macbook Air M1 - (8Gb/256GB SSD/Mac OS Big Sur)","88,990"))
        productList.add(ProductModel(R.drawable.mac_book,"Mac Book", "Macbook Air M1 - (8Gb/256GB SSD/Mac OS Big Sur)","88,990"))

        var adapter: GridViewAdapter = GridViewAdapter(this@MainActivity,productList)
        gridView.adapter = adapter



    }
}