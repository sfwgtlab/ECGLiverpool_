package com.example.ecgliverpool.adapters

import android.content.Context
import android.graphics.PaintFlagsDrawFilter
import android.icu.text.AlphabeticIndex
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecgliverpool.R
import com.example.ecgliverpool.models.Record
import com.squareup.picasso.Picasso
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.NonDisposableHandle.parent
import java.io.Serializable
import kotlin.coroutines.coroutineContext

class ProductAdapter(val records: List<Record>): RecyclerView.Adapter<ProductAdapter.ServiceHolder>(), Serializable {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ServiceHolder(layoutInflater.inflate(R.layout.product_adapter, parent, false))
    }

    override fun onBindViewHolder(holder: ServiceHolder, position: Int) {
        val element = records[position]
        holder.render(element)
    }

    override fun getItemCount(): Int {
        return records.size
    }

    class ServiceHolder(val view: View):RecyclerView.ViewHolder(view) {

        fun render(record: Record){
            val img = view.findViewById<ImageView>(R.id.IV_product)
            val name = view.findViewById<TextView>(R.id.Tv_name)
            val price1 = view.findViewById<TextView>(R.id.Tv_price1)
            val price2 = view.findViewById<TextView>(R.id.Tv_price2)
            val LinearColor = view.findViewById<LinearLayout>(R.id.Ll_colores)

            name.text = record.productDisplayName.slice(0..12)
            price1.text =  "$"+record.promoPrice.toString()
            val promo= "$"+record.maximumListPrice.toString();
            val minorPrice = SpannableString(promo)
            minorPrice.setSpan(StrikethroughSpan(), 0, minorPrice.length,0)
            price2.text = minorPrice

            Picasso.get().load(record.smImage).into(img)


            var textColor = TextView(view.context)

            val colors = record.variantsColor.size
            /*for (i in 0.. colors){
                textColor.text = record.variantsColor[i].toString()
                LinearColor.addView(textColor)
            }*/
        }
    }
}

