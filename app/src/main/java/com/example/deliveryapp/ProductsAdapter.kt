package com.example.deliveryapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.deliveryapp.databinding.RvProductItemBinding
import com.example.deliveryapp.tables.Product

class ProductsAdapter(
    private var products: ArrayList<Product?>

): RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {
    private val diffCallback = DiffCallbackProducts(products, ArrayList())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsAdapter.ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RvProductItemBinding.inflate(layoutInflater, parent, false)

        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductsAdapter.ProductViewHolder, position: Int) {
        val curCommitment = products[position]
        holder.bind(curCommitment)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun submitList(updatedList: List<Product?>){
        diffCallback.newList = updatedList

        val diffResult = DiffUtil.calculateDiff(diffCallback)

        products.clear()

        products.addAll(updatedList)
        diffResult.dispatchUpdatesTo(this)
    }


    inner class ProductViewHolder(private val binding: RvProductItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(product: Product?){
            binding.tvNameProduct.text = product?.name_product.toString()
            binding.tvDescription.text = product?.description.toString()
            binding.tvPrice.text = product?.price.toString()
            binding.tvSale.text = product?.sale.toString()
        }
    }
}