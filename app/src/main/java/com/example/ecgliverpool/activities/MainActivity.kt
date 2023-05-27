package com.example.ecgliverpool.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecgliverpool.adapters.ProductAdapter
import com.example.ecgliverpool.apiService.ApiService
import com.example.ecgliverpool.apiService.RetrofitService
import com.example.ecgliverpool.databinding.ActivityMainBinding
import com.example.ecgliverpool.models.ProductsResponse
import com.example.ecgliverpool.models.Record
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding
    lateinit var retrofitService: RetrofitService
    lateinit var records: ArrayList<Record>
    lateinit var producto: String
    var order: Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)


        records = arrayListOf<Record>()

        mainBinding.icSearchMain.setOnClickListener {
            producto = mainBinding.EtSearchMain.text.toString().trim()
            if(producto.length > 0){
                searchProducts(producto, "")
            }else{
                Toast.makeText(this, "Poner algo en el buscador", Toast.LENGTH_LONG).show()
            }
        }

        //filtrar
        mainBinding.icFilter.setOnClickListener {
            producto = mainBinding.EtSearchMain.text.toString().trim()
            if (producto.length < 1){
                Toast.makeText(this, "Poner algo en el buscador", Toast.LENGTH_LONG).show()
            }
            var add = "&minSortPrice|"
            var addZero = "";
            var addOne = ""

            order++
            if(order > 2){
                order = 0
                mainBinding.TvOrderMain.text = "Orden:Predefinido"
                searchProducts(producto, "")
            }

            if(order == 1){
                add = add + "0"
                mainBinding.TvOrderMain.text = "Orden:Menor"
                searchProducts(producto, addZero)
            }

            if(order == 2){
                add = add + "1"
                mainBinding.TvOrderMain.text = "Orden:Mayor"
                searchProducts(producto, addOne)
            }

            Log.d("orden", order.toString())
            Log.d("add", add )
        }

    }


    //buscar productos
    private fun searchProducts(products : String, add: String){


        retrofitService = RetrofitService()
        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<ProductsResponse> = retrofitService.getProducts().create(ApiService::class.java).getProducts("plp?search-string=%7B%7B$products%7D%7D&page-number=1$add")
            val products: ProductsResponse? = call.body()
            records = products?.plpResults!!.records as ArrayList<Record>
            runOnUiThread{
                if (call.isSuccessful){
                    Log.d("res", products?.plpResults!!.records.toString())
                    initRecycler()
                }else{
                    Serror()
                }
            }
        }
    }

    private fun Serror(){
        Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()

    }

    private fun initRecycler(){
        mainBinding.RvProductsMain.layoutManager = LinearLayoutManager(this)
        val adapter = ProductAdapter(records)
        mainBinding.RvProductsMain.adapter = adapter;
    }



    /*
    . Predefinida -
https://shoppapp.liverpool.com.mx/appclienteservices/services/v3/plp?searchstring={{termino-de-busqueda}}&page-number={{numero-de-pagina}}
2. Menor precio -
https://shoppapp.liverpool.com.mx/appclienteservices/services/v3/plp?searchstring=iphone&page-number=1&minSortPrice|0
3. Mayor precio -
https://shoppapp.liverpool.com.mx/appclienteservices/services/v3/plp?search-string=%7b%7btermino-de-busqueda%7d%7d&page-number=%7b%7bnumero-de-pagina%7d%7d&minSortPrice|1
     */
}